package com.spring.data.jpa.controllers;

import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.dao.IVentaODao;
import com.spring.data.jpa.models.entity.*;
import com.spring.data.jpa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    @Autowired
    private ICardService cardService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IUsuarioDao usuarioDao;
    @Autowired
    private IVentaODao ventaOservice;
    @Autowired
    private IVentaDService ventaDService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private EnvioEmail emailService;
    @Autowired
    private IAgenteService agenteService;
    @Autowired
    private MailClient mailClient;


    @RequestMapping(value="/orderConfirmation",method = RequestMethod.GET)
    public String orderConfirm(Map<String,String> requestParams, Model model) throws ParseException {


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);
        String ID = cartService.findByRecentDate(usuario.getCliente());
      List<VentaD> renglones = new ArrayList<>();




            Date ahora = new Date();
            SimpleDateFormat formateador = new SimpleDateFormat("YYYY-dd-MM");//HH:mm:s
            String fechaAlta = formateador.format(ahora);

            Card card =cartService.findByID(Integer.parseInt(ID));
            List<CardD> cards =cardService.findAllByID(Integer.parseInt(ID));
            double impuestos = 0;
            double importe = 0;
            for(int i=0;i<cards.size();i++)
            {
                if(cards.get(i).getIsaviable()==0)
                {

                }else{
                    importe += cards.get(i).getTotal().doubleValue();
                    impuestos += cards.get(i).getImpuesto1().doubleValue();
                }

            }
            card.setImporte(BigDecimal.valueOf(importe));
            card.setImpuestos(BigDecimal.valueOf(impuestos));
            card.setTotal(BigDecimal.valueOf(impuestos+importe));
            try
            {
               cartService.save(card);
            }catch (Exception e)
            {
                System.out.println("Something went wrong.1");
            }
            try
            {

            }catch (Exception e)
            {
                System.out.println("Something went wrong.2");
            }
            try{

                  List<CardD> articulosCard= cardService.findAllByID(Integer.parseInt(ID));
                 List<CardD> articulosAlmacen1 = articulosCard.stream().filter(item -> item.getAlmacen()==1).collect(Collectors.toList());
                 List<CardD> articulosAlmacen3 = articulosCard.stream().filter(item -> item.getAlmacen()==3).collect(Collectors.toList());

                if(!articulosAlmacen1.isEmpty())
                {
                    try
                    {
                        Calendar fecha = Calendar.getInstance();
                        int año = fecha.get(Calendar.YEAR);
                        int mes = fecha.get(Calendar.MONTH) + 1;

                        int renglon = 0;
                        int renglonid =0;

                        VentaOrder newOrder = new VentaOrder();
                        newOrder.setAgente(card.getAgente());
                        newOrder.setCliente(card.getCliente());
                        newOrder.setEmpresa("ACT");
                        newOrder.setEnviara(card.getEnviara());
                        newOrder.setFecharequerida(fechaAlta);
                        newOrder.setOrdencompra(card.getOrdencompra());
                        newOrder.setMov("Pedido Web");
                        newOrder.setMoneda("PESOS");
                        newOrder.setAlmacen(1);
                        newOrder.setEstatus("PENDIENTE");
                        newOrder.setObservaciones("PEDIDO");
                        newOrder.setUsuario("LCAMBEROS");
                        newOrder.setReferencia("Pedido desde B2B");
                        newOrder.setImporte(card.getImporte().divide(BigDecimal.valueOf(1.16),4,  RoundingMode.CEILING));
                        newOrder.setImpuestos(card.getImpuestos());
                        newOrder.setTipocambio(1);
                        newOrder.setCondicion(card.getCondicion());
                        newOrder.setOrigentipo("VTAs");
                        String MovID = ventaOservice.findTheLastOne();
                        System.out.println(MovID+"movID");
                        if(MovID ==null)
                        {
                            MovID="GDL0";
                        }
                        newOrder.setFechaemision(fechaAlta);
                        newOrder.setEjercicio(String.valueOf(año));
                        newOrder.setPeriodo(String.valueOf(mes));
                        newOrder.setListapreciosesp(usuario.getListapreciosesp());
                        char[] aCaracteres2 = MovID.toCharArray();

                        String numbers=MovID.substring(3,aCaracteres2.length);
                        int nmov =Integer.parseInt(numbers);
                        nmov=nmov+1;
                        String letters =MovID.substring(0,3);
                        System.out.println(letters+nmov);
                        newOrder.setMovid(letters+nmov);

                        VentaOrder ventaComparar = ventaOservice.findByMovidAndMov(newOrder.getMovid(),newOrder.getMov());
                        if(ventaComparar ==null)
                        {
                            model.addAttribute("order",newOrder);


                            ventaOservice.save(newOrder);
                        }else
                        {
                            numbers=MovID.substring(3,aCaracteres2.length);
                             nmov =Integer.parseInt(numbers);
                            nmov=nmov+1;
                             letters =MovID.substring(0,3);
                            System.out.println("SI HABIA Y SE CREO OTRO"+letters+nmov);
                            newOrder.setMovid(letters+nmov);
                            model.addAttribute("order",newOrder);


                            ventaOservice.save(newOrder);
                        }




                        Agente agente = agenteService.findByAgente(usuario.getAgente());

                       String subject="Pedido Desde B2B : "+newOrder.getMovid()+" Almacen 1";
                        Map objetillo = new HashMap();
                        objetillo.put("agente", agente.getEmail());
                        objetillo.put("ordencompra", newOrder.getOrdencompra());
                        objetillo.put("fechaemision", newOrder.getFechaemision());
                        objetillo.put("condicion", newOrder.getCondicion());
                        objetillo.put("movid", newOrder.getMovid());
                        objetillo.put("importe", newOrder.getImporte());
                        objetillo.put("impuesto", newOrder.getImpuestos());


                        mailClient.prepareAndSend(usuario.getEmail1(),"PedidoB2B ","intranet@acteck.com",subject,objetillo);
                        mailClient.prepareAndSend(agente.getEmail(),"PedidoB2B ","intranet@acteck.com",subject,objetillo);


                        int id2 = ventaOservice.findTheLAstOneID();
                        System.out.println(id2);

                        for(int i =0; i<articulosAlmacen1.size();i++)
                        {
                            if(articulosAlmacen1.get(i).getIsaviable()!=0)
                            {
                                VentaD ventaDetail = new VentaD();
                                ventaDetail.setAlmacen(articulosAlmacen1.get(i).getAlmacen());
                                System.out.println(articulosAlmacen1.get(i).getAlmacen());
                                ventaDetail.setArticulo(articulosAlmacen1.get(i).getArticulo());
                                System.out.println(articulosAlmacen1.get(i).getArticulo());
                                ventaDetail.setRenglon(renglon);
                                System.out.println(articulosAlmacen1.get(i).getRenglon());
                                ventaDetail.setRenglonid(renglonid);
                                System.out.println(ventaDetail.getRenglonid());
                                ventaDetail.setCantidad(articulosAlmacen1.get(i).getCantidad());
                                ventaDetail.setCantidadinventario(articulosAlmacen1.get(i).getCantidad());
                                ventaDetail.setCantidadreservada(articulosAlmacen1.get(i).getCantidad());
                                ventaDetail.setUltimoreservadocantidad(articulosAlmacen1.get(i).getCantidad());
                                ventaDetail.setUltimoreservadofecha(fechaAlta);

                                ventaDetail.setRenglontipo('L');
                                int imp = 16;
                                ventaDetail.setImpuesto1(imp);
                                ventaDetail.setPrecio(BigDecimal.valueOf(articulosAlmacen1.get(i).getPrecio().doubleValue()/Double.parseDouble(String.valueOf(articulosAlmacen1.get(i).getCantidad()))));
                                ventaDetail.setPreciosugerido(BigDecimal.valueOf(articulosAlmacen1.get(i).getPrecio().doubleValue()/Double.parseDouble(String.valueOf(articulosAlmacen1.get(i).getCantidad()))));
                                ventaDetail.setPreciomoneda("PESOS");
                                ventaDetail.setUnidad("PZA");
                                ventaDetail.setPreciotipocambio(1);
                                ventaDetail.setID(id2);
                                renglon+=2048;
                                renglonid++;
                                renglones.add(ventaDetail);


                                ventaDService.save(ventaDetail);
                                try
                                {
                                    ventaDService.updateSaldoU(articulosAlmacen1.get(i).getCantidad(),articulosAlmacen1.get(i).getArticulo(),1);

                                }catch (Exception e)
                                {

                                }

                            }
                        }

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                if(!articulosAlmacen3.isEmpty())
                {
                    try
                    {
                        Calendar fecha = Calendar.getInstance();
                        int año = fecha.get(Calendar.YEAR);
                        int mes = fecha.get(Calendar.MONTH) + 1;

                        int renglon = 0;
                        int renglonid =0;
                        VentaOrder newOrder = new VentaOrder();
                        newOrder.setAgente(card.getAgente());
                        newOrder.setCliente(card.getCliente());
                        newOrder.setEmpresa("ACT");
                        newOrder.setEnviara(card.getEnviara());
                        newOrder.setFecharequerida(fechaAlta);
                        newOrder.setOrdencompra(card.getOrdencompra());
                        newOrder.setMov("Pedido Web");
                        newOrder.setMoneda("PESOS");
                        newOrder.setAlmacen(3);
                        newOrder.setEstatus("PENDIENTE");
                        newOrder.setObservaciones("PEDIDO");
                        newOrder.setUsuario("LCAMBEROS");
                        newOrder.setReferencia("Pedido desde B2B");
                        newOrder.setImporte(card.getImporte().divide(BigDecimal.valueOf(1.16),4,  RoundingMode.CEILING));

                        newOrder.setImpuestos(card.getImpuestos());
                        newOrder.setTipocambio(1);
                        newOrder.setCondicion(card.getCondicion());
                        newOrder.setOrigentipo("VTAs");
                        String MovID = ventaOservice.findTheLastOne();
                        System.out.println(MovID+"movID");

                        newOrder.setFechaemision(fechaAlta);
                        newOrder.setEjercicio(String.valueOf(año));
                        newOrder.setPeriodo(String.valueOf(mes));
                        newOrder.setListapreciosesp(usuario.getListapreciosesp());
                        char[] aCaracteres2 = MovID.toCharArray();

                        String numbers=MovID.substring(3,aCaracteres2.length);
                        int nmov =Integer.parseInt(numbers);
                        nmov=nmov+1;
                        String letters =MovID.substring(0,3);
                        System.out.println(letters+nmov);
                        newOrder.setMovid(letters+nmov);
                        VentaOrder ventaComparar = ventaOservice.findByMovidAndMov(newOrder.getMovid(),newOrder.getMov());
                        if(ventaComparar ==null)
                        {
                            model.addAttribute("order",newOrder);


                            ventaOservice.save(newOrder);
                        }else
                        {
                            numbers=MovID.substring(3,aCaracteres2.length);
                            nmov =Integer.parseInt(numbers);
                            nmov=nmov+1;
                            letters =MovID.substring(0,3);
                            System.out.println("SI HABIA Y SE CREO OTRO"+letters+nmov);
                            newOrder.setMovid(letters+nmov);
                            model.addAttribute("order",newOrder);


                            ventaOservice.save(newOrder);
                        }

                        Agente agente = agenteService.findByAgente(usuario.getAgente());

                        String subject="Pedido Desde B2B : "+newOrder.getMovid()+" Almacen 3";
                        Map objetillo = new HashMap();
                        objetillo.put("agente", agente.getEmail());
                        objetillo.put("ordencompra", newOrder.getOrdencompra());
                        objetillo.put("fechaemision", newOrder.getFechaemision());
                        objetillo.put("condicion", newOrder.getCondicion());
                        objetillo.put("movid", newOrder.getMovid());
                        objetillo.put("importe", newOrder.getImporte());
                        objetillo.put("impuesto", newOrder.getImpuestos());


                        mailClient.prepareAndSend(usuario.getEmail1(),"PedidoB2B ","intranet@acteck.com",subject,objetillo);
                        mailClient.prepareAndSend(agente.getEmail(),"PedidoB2B ","intranet@acteck.com",subject,objetillo);
                        int id2 = ventaOservice.findTheLAstOneID();
                        System.out.println(id2);

                        for(int i =0; i<articulosAlmacen3.size();i++)
                        {
                            if(articulosAlmacen3.get(i).getIsaviable()!=0)
                            {
                                VentaD ventaDetail = new VentaD();
                                ventaDetail.setUltimoreservadocantidad(articulosAlmacen3.get(i).getCantidad());
                                ventaDetail.setUltimoreservadofecha(fechaAlta);
                                ventaDetail.setAlmacen(articulosAlmacen3.get(i).getAlmacen());
                                System.out.println(articulosAlmacen3.get(i).getAlmacen());
                                ventaDetail.setArticulo(articulosAlmacen3.get(i).getArticulo());
                                System.out.println(articulosAlmacen3.get(i).getArticulo());
                                ventaDetail.setRenglon(renglon);
                                System.out.println(articulosAlmacen3.get(i).getRenglon());
                                ventaDetail.setRenglonid(renglonid);
                                System.out.println(ventaDetail.getRenglonid());
                                ventaDetail.setCantidad(articulosAlmacen3.get(i).getCantidad());
                                ventaDetail.setCantidadinventario(articulosAlmacen3.get(i).getCantidad());
                                ventaDetail.setCantidadreservada(articulosAlmacen3.get(i).getCantidad());

                                ventaDetail.setRenglontipo('L');
                                int imp = 16;
                                ventaDetail.setImpuesto1(imp);
                                ventaDetail.setPrecio(BigDecimal.valueOf(articulosAlmacen3.get(i).getPrecio().doubleValue()/Double.parseDouble(String.valueOf(articulosAlmacen3.get(i).getCantidad()))));
                                ventaDetail.setPreciosugerido(BigDecimal.valueOf(articulosAlmacen3.get(i).getPrecio().doubleValue()/Double.parseDouble(String.valueOf(articulosAlmacen3.get(i).getCantidad()))));
                                ventaDetail.setPreciomoneda("PESOS");
                                ventaDetail.setUnidad("PZA");
                                ventaDetail.setPreciotipocambio(1);
                                ventaDetail.setID(id2);
                                renglon+=2048;
                                renglonid++;
                                renglones.add(ventaDetail);


                                ventaDService.save(ventaDetail);

                                try
                                {
                                    ventaDService.updateSaldoU(articulosAlmacen3.get(i).getCantidad(),articulosAlmacen3.get(i).getArticulo(),3);
                                }catch (Exception e)
                                {

                                }

                            }
                        }

                    }catch (Exception e)
                    {
                      e.printStackTrace();
                    }
                }

System.out.println(card.getEnviara());
                cteEnviarA adres= addressService.findByIDAndCliente(card.getEnviara(),usuario.getCliente());
                System.out.println(adres.getCliente());
                model.addAttribute("adres",adres);
                model.addAttribute("orderD",renglones);
            }catch (Exception e)
            {
                System.out.println("Something went wrong.3");
            }

card.setIsactive('0');
        SimpleDateFormat formateador2 = new SimpleDateFormat("yyyy-dd-MM");//HH:mm:ss
        Date emision = formateador.parse(card.getFechaalta());
        String fecha2 = formateador2.format(emision);
        System.out.println(fecha2);
        card.setFechaalta(fecha2);
        cartService.save(card);

      return "redirect:/history?page=0&estatus=PENDIENTE";







    }


@RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    public String orderDetail (@RequestParam Map<String,String> requestParams, Model model)
{
System.out.println(requestParams.get("movId"));
    VentaOrder ventaO = ventaOservice.findByMovidAndID(requestParams.get("movId"),Integer.parseInt(requestParams.get("id")));
    String fecha=ventaO.getFechaemision();
    String[] fechaCom = fecha.split(" ");
    System.out.println(fechaCom[0]);
    String fecha2 = fechaCom[0];
    String[] year = fecha2.split("-");
    System.out.println(year[0]);
    System.out.println(year[1]);
    int mes = Integer.parseInt(year[1]);
    System.out.println(year[2]);


    model.addAttribute("year",year[0]);
    model.addAttribute("month",mes);
    model.addAttribute("order",ventaO);
    model.addAttribute("CTE", ventaO.getCliente());
    List<VentaD> ventas = ventaDService.findByID(ventaO.getID());

    for (int i=0;i<ventas.size();i++)
    {
        BigDecimal precio =BigDecimal.valueOf(ventas.get(i).getPrecio().doubleValue()*ventas.get(i).getCantidad());
        ventas.get(i).setPrecio(precio.setScale(2, RoundingMode.DOWN));
        ventas.get(i).setImpuesto1(16);
    }

    model.addAttribute("orderD",ventas);
    cteEnviarA adres = addressService.findByIDAndCliente(ventaO.getEnviara(),ventaO.getCliente());
    model.addAttribute("adres",adres);

    return "orderDetail";
}


}
