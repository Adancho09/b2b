package com.spring.data.jpa.controllers;

import com.spring.data.jpa.Paginator.PageRender;
import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.entity.CardD;
import com.spring.data.jpa.models.entity.Usuario;
import com.spring.data.jpa.models.entity.artCaracteristicas;
import com.spring.data.jpa.models.entity.vw_articulosBR_row;
import com.spring.data.jpa.models.entity.vw_b2barticulos_row;
import com.spring.data.jpa.service.IArticuloService;
import com.spring.data.jpa.service.ICardService;
import com.spring.data.jpa.service.ICartService;
import com.spring.data.jpa.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductController {


    @Autowired
    private IProductService productoService;
    
    @Autowired
    private IArticuloService articuloService;

    @Autowired
    private IUsuarioDao usuarioDao;
    @Autowired
    private ICardService cardService;
    @Autowired
    private ICartService cartService;


    @RequestMapping(value="/getNav", method = RequestMethod.GET)
    public List listarCategorias() {
    	
    	
        List<String> categories=productoService.findByCategoriaL("1","");
    	return categories;
    }

    @RequestMapping(value="/listarArticulos", method = RequestMethod.GET)
    public List listarArticulos() {
    	
        List<vw_articulosBR_row> articulos=articuloService.findByTodo();
    	return articulos;
    }
    @RequestMapping(value="/detalleArticulo/{sku}", method = RequestMethod.GET)
    public    List getDetallesArticulo(@PathVariable(value="sku") String sku) {


        List<artCaracteristicas> articuloDetalle = articuloService.getDetallesArticulo(sku);
        return articuloDetalle;
    }

//obtencion de articulos por Categoria
    @RequestMapping(value="/findByCat/{cat}", method = RequestMethod.GET)
    public    List<vw_articulosBR_row> getByCat(@PathVariable(value="cat") String categoria) {


        List<vw_articulosBR_row> artsByCat=articuloService.findAllByCategoria(categoria);
        return artsByCat;
    }

    @RequestMapping(value="/findAllBy/{cat}/{sub}", method = RequestMethod.GET)
    public    List<vw_articulosBR_row> findAllBy(@PathVariable(value="cat") String categoria,@PathVariable(value="sub") String sub) {


        List<vw_articulosBR_row> artsByCat=new ArrayList<>();
        if(sub!=null)
        {
           artsByCat=articuloService.findAllByCategoriaAndSub(categoria,sub);

        }else
        {
            artsByCat  =articuloService.findAllByCategoria(categoria);
        }
        return artsByCat;
    }

    @RequestMapping(value="/findAllBy/{cat}/{sub}/{fam}", method = RequestMethod.GET)
    public    List<vw_articulosBR_row> findAllByfam(@PathVariable(value="cat") String categoria,@PathVariable(value="sub") String sub,@PathVariable(value="fam") String fam) {

        List<vw_articulosBR_row> artsByCat=articuloService.findAllByCategoriaAndSubAndFam(categoria,sub,fam);
        return artsByCat;
    }

    @RequestMapping(value="/listarCategorias", method = RequestMethod.GET)
    public List listarCategoriasBalam() {
    	
        List<String> categorias=articuloService.findByCategoria();
    	return categorias;
    }

    @RequestMapping(value="/listarSubcategorias/{categoria}", method = RequestMethod.GET)
    public List listarSubCategoriasBalam(@PathVariable(value="categoria") String categoria) {
    	
        List<String> subcategorias=articuloService.findBySubcategoria(categoria);
    	return subcategorias;
    }

    @RequestMapping(value="/listarFamilias/{categoria}/{subcategoria}", method = RequestMethod.GET)
    public List listarFamiliaBalam(@PathVariable(value="categoria") String categoria, @PathVariable(value="subcategoria") String subcategoria) {
    	
        List<String> familias=articuloService.findByFamilia(categoria,subcategoria);
    	return familias;
    }
    @RequestMapping(value="/articulo/{codigo}", method = RequestMethod.GET)
    public vw_articulosBR_row DetalleArticulo(@PathVariable(value="codigo") String codigo) {
    	
        vw_articulosBR_row detalleArticulo=articuloService.findByArticulo(codigo);
    	return detalleArticulo;
    }

    @RequestMapping(value="/articulo",method = RequestMethod.GET)
    public String articulo(@RequestParam Map<String,String> requestParams,Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);
        vw_b2barticulos_row articulo = productoService.findByArticuloAndLista(requestParams.get("SKU"),usuario.getListaPreciosEsp());
      String palabra = requestParams.get("SKU").toUpperCase();

        if(articulo==null){
            List<vw_b2barticulos_row> arts = productoService.findAllByLista(usuario.getListaPreciosEsp());
            List<vw_b2barticulos_row> matches = arts.stream().filter(item-> item.getDescripcion().contains(palabra)).collect(Collectors.toList());
            if(requestParams.get("filtro")!=null)
            {
                List<vw_b2barticulos_row> artsFiltro = productoService.findAllByListaFiltro(usuario.getListaPreciosEsp());
                List<vw_b2barticulos_row> matches2 = artsFiltro.stream().filter(item-> item.getDescripcion().contains(palabra)).collect(Collectors.toList());
                model.addAttribute("Products", matches2);
                model.addAttribute("siProduct",1);
                model.addAttribute("notPage",0);
                model.addAttribute("palabra",palabra);

                return "listarFiltro";
            }



                if(!matches.isEmpty())
                {
                    model.addAttribute("Products", matches);
                    model.addAttribute("siProduct",1);
                    model.addAttribute("notPage",0);
                    model.addAttribute("palabra",palabra);

                    return "listarFiltro";
                }



            String error = "No se encontraron resultados para tu busqueda";

            model.addAttribute("Error", error);
            return "errorArt";
    }else if(articulo != null) {


            model.addAttribute("Product", articulo);
            return "articulo";
        }

        return "errorArt";
    }

    @RequestMapping(value="/filterArt",method = RequestMethod.POST)
    public String filterArt(@RequestParam Map<String,String> requestParams,Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);

        String ID = cartService.findByRecentDate(usuario.getCliente());
        if(ID!=null)
        {
            List<CardD>cardd=cardService.findAllByID(Integer.parseInt(ID));
            if(cardd.size()>0)
            {
                model.addAttribute("carCount",cardd.size());
            }
        }


       int opcion = Integer.parseInt(requestParams.get("opcion"));

        System.out.println(opcion);
        if(opcion == 3)
        {
            double range = Double.parseDouble(requestParams.get("range"));
            if(range >0)
            {
                System.out.println("entre a opcion 3 RANGO ");
                System.out.println(range);



                List<vw_b2barticulos_row> arts = productoService.findAllByLista(usuario.getListaPreciosEsp());
                List<vw_b2barticulos_row> matches = arts.stream().filter(item-> item.getPrecio().doubleValue()<=range).collect(Collectors.toList());
                model.addAttribute("Products", matches);

                model.addAttribute("notPage",0);
                return "listar";
            }
        }else if(opcion ==2)
        {


                System.out.println("entre a opcion 2 MAS VENDIDOS ");
                int page=0;

                Pageable pageRequest= PageRequest.of(page,10);


                Page<vw_b2barticulos_row> products=productoService.findAllByListaOrderByCantidadDesc(usuario.getListaPreciosEsp(),pageRequest);
                PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?", products);
                model.addAttribute("Products", products);
                model.addAttribute("page", pageRender);
            model.addAttribute("notPage",0);
                return "listar";


        }
        return "redirect:/cart";

    }





}
