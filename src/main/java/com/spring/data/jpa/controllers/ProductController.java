package com.spring.data.jpa.controllers;


import com.spring.data.jpa.Paginator.PageRender;
import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.entity.CardD;
import com.spring.data.jpa.models.entity.Usuario;
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

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
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
    	
    	 String username;
    	 
    	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	 if(principal instanceof UserDetails) {
    		username = ((UserDetails) principal).getUsername();
    	 }
    	 else {
    		username = "00000";
    	 }
         
         Usuario usuario = usuarioDao.findByCliente(username);
    	
        List<String> categories=productoService.findByCategoriaL(usuario.getListaPreciosEsp(),"");
    	
    	return categories;
    }

    @RequestMapping(value="/listarArticulos", method = RequestMethod.GET)
    public List listarArticulos() {
    	
    	 String username;
    	 
    	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	 if(principal instanceof UserDetails) {
    		username = ((UserDetails) principal).getUsername();
    	 }
    	 else {
    		username = "00000";
    	 }
         
         Usuario usuario = usuarioDao.findByCliente(username);
    	
        List<vw_articulosBR_row> articulos=articuloService.findByTodo();
    	
    	return articulos;
    }

//obtencion de articulos por Categoria
    @RequestMapping(value="/findByCat/{cat}", method = RequestMethod.GET)
    public    List<vw_articulosBR_row> getByCat(@PathVariable(value="cat") String categoria) {


        String username;
        System.out.println(categoria);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        else {
            username = "00000";
        }
        Usuario usuario = usuarioDao.findByCliente(username);
        List<vw_articulosBR_row> artsByCat=articuloService.findAllByCategoria(categoria);
        return artsByCat;
    }

    @RequestMapping(value="/findAllBy/{cat}/{sub}", method = RequestMethod.GET)
    public    List<vw_articulosBR_row> findAllBy(@PathVariable(value="cat") String categoria,@PathVariable(value="sub") String sub) {


        String username;
        System.out.println(categoria);


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        else {
            username = "00000";
        }
        Usuario usuario = usuarioDao.findByCliente(username);
        List<vw_articulosBR_row> artsByCat=new ArrayList<>();

        if(sub!=null)
        {
            //System.out.println("SubCat");
           artsByCat=articuloService.findAllByCategoriaAndSub(categoria,sub);

        }else
        {

            artsByCat  =articuloService.findAllByCategoria(categoria);


        }

        return artsByCat;
    }

    @RequestMapping(value="/findAllBy/{cat}/{sub}/{fam}", method = RequestMethod.GET)
    public    List<vw_articulosBR_row> findAllByfam(@PathVariable(value="cat") String categoria,@PathVariable(value="sub") String sub,@PathVariable(value="fam") String fam) {


        String username;
        //System.out.println(categoria);


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        else {
            username = "00000";
        }
        Usuario usuario = usuarioDao.findByCliente(username);



            System.out.println("fam");
        List<vw_articulosBR_row> artsByCat=articuloService.findAllByCategoriaAndSubAndFam(categoria,sub,fam);

            return artsByCat;




    }

    @RequestMapping(value="/listarCategorias", method = RequestMethod.GET)
    public List listarCategoriasBalam() {
    	
    	 String username;
    	 
    	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	 if(principal instanceof UserDetails) {
    		username = ((UserDetails) principal).getUsername();
    	 }
    	 else {
    		username = "00000";
    	 }
         
         Usuario usuario = usuarioDao.findByCliente(username);
    	
        List<String> categorias=articuloService.findByCategoria();
    	
    	return categorias;
    }

    @RequestMapping(value="/listarSubcategorias/{categoria}", method = RequestMethod.GET)
    public List listarSubCategoriasBalam(@PathVariable(value="categoria") String categoria) {
    	
    	 String username;
    	 
    	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	 if(principal instanceof UserDetails) {
    		username = ((UserDetails) principal).getUsername();
    	 }
    	 else {
    		username = "00000";
    	 }
         
         Usuario usuario = usuarioDao.findByCliente(username);
    	
        List<String> subcategorias=articuloService.findBySubcategoria(categoria);
    	
    	return subcategorias;
    }

    @RequestMapping(value="/listarFamilias/{categoria}/{subcategoria}", method = RequestMethod.GET)
    public List listarFamiliaBalam(@PathVariable(value="categoria") String categoria, @PathVariable(value="subcategoria") String subcategoria) {
    	
    	 String username;
    	 
    	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	 if(principal instanceof UserDetails) {
    		username = ((UserDetails) principal).getUsername();
    	 }
    	 else {
    		username = "00000";
    	 }
         
         Usuario usuario = usuarioDao.findByCliente(username);
    	
        List<String> familias=articuloService.findByFamilia(categoria,subcategoria);
    	
    	return familias;
    }
    @RequestMapping(value="/articulo/{codigo}", method = RequestMethod.GET)
    public vw_articulosBR_row DetalleArticulo(@PathVariable(value="codigo") String codigo) {
    	
    	 String username;
    	 
    	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	 if(principal instanceof UserDetails) {
    		username = ((UserDetails) principal).getUsername();
    	 }
    	 else {
    		username = "00000";
    	 }
         System.out.println(codigo);
         Usuario usuario = usuarioDao.findByCliente(username);
    	
        vw_articulosBR_row detalleArticulo=articuloService.findByArticulo(codigo);
    	System.out.println(detalleArticulo.getArticulo());
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
