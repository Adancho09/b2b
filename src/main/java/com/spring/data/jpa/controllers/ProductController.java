package com.spring.data.jpa.controllers;


import com.spring.data.jpa.Paginator.PageRender;
import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.entity.CardD;
import com.spring.data.jpa.models.entity.Usuario;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Controller
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

    @ResponseBody
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
    	
        List<String> articulos=articuloService.findByTodo();
    	
    	return articulos;
    }
    @RequestMapping(value="/listar",method = RequestMethod.GET)
    public String listar(@RequestParam Map<String,String> requestParams,Model model){

    	String username;
    	
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
    		username = ((UserDetails) principal).getUsername();
    	 }
    	 else {
    		username = "00000";
    	 }
         
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


        String category=requestParams.get("category");
        String subcategory=requestParams.get("subcategory");
        String fam=requestParams.get("fam");
        String subfam=requestParams.get("subfam");
        int filtro =1;

        if(requestParams.get("filtro")==null)
        {
          filtro =0;
        }


        if(category==null && requestParams.get("filtro")!=null){

            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,50);
            String company=requestParams.get("company");
            Page<vw_b2barticulos_row> products = productoService.findByFabricanteAndListaFiltro(company ,usuario.getListaPreciosEsp(),pageRequest);
            List<String> categories=productoService.findByCategoriaL(usuario.getListaPreciosEsp(),category);

            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?company="+company, products);
            model.addAttribute("categories",categories);
            model.addAttribute("category",category);
            model.addAttribute("Products", products);
            model.addAttribute("page", pageRender);
            model.addAttribute("company",company);
            return "listar";
        }

        if(category==null){
            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,6);
            String company=requestParams.get("company");
            Page<vw_b2barticulos_row> products = productoService.findByFabricanteAndLista(company ,usuario.getListaPreciosEsp(),pageRequest);
            List<String> categories=productoService.findByCategoriaL(usuario.getListaPreciosEsp(),category);

            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?company="+company, products);
            model.addAttribute("categories",categories);
            model.addAttribute("category",category);
            model.addAttribute("Products", products);
            model.addAttribute("page", pageRender);
            model.addAttribute("company",company);
            return "listar";
        }

        if(category!=null && subcategory==null){
            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,12);


            Page<vw_b2barticulos_row> products=productoService.findByCategoriaAndLista(category,usuario.getListaPreciosEsp(),pageRequest);
            List<String> subCat=productoService.findByGrupo(category,usuario.getListaPreciosEsp());
            System.out.println(subCat);
            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?category="+category, products);
            model.addAttribute("subcat",subCat);
            model.addAttribute("Products", products);
            model.addAttribute("category",category);
            model.addAttribute("page", pageRender);
            return "listar";
        }


        if(subcategory!=null && fam==null){

            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,12);


            Page<vw_b2barticulos_row> products=productoService.findByCategoriaAndGrupoAndLista(category,subcategory,usuario.getListaPreciosEsp(),pageRequest);
            List<String> familia1 =productoService.findByCategoriaAndGrupo(category,subcategory,usuario.getListaPreciosEsp());

            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?category="+category+"&subcategory="+subcategory, products);
            model.addAttribute("familiaList",familia1);
            model.addAttribute("subcat",subcategory);
            model.addAttribute("Products", products);
            model.addAttribute("category",category);
            model.addAttribute("page", pageRender);
            return "listar";
        }
        if(fam!=null && subfam==null){

            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,12);


            Page<vw_b2barticulos_row> products = productoService.findByCategoriaAndGrupoAndFamiliaAndLista(category,subcategory,fam,usuario.getListaPreciosEsp(),pageRequest);
            List<String> familia = productoService.findByCategoriaAndGrupoAndFamilia(category,subcategory,fam,usuario.getListaPreciosEsp());

            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?category="+category+"&subcategory="+subcategory+"&fam="+fam, products);
            model.addAttribute("familiaList",familia);
            model.addAttribute("subcat",subcategory);
            model.addAttribute("famParam",fam);
            model.addAttribute("Products", products);
            model.addAttribute("category",category);
            model.addAttribute("page", pageRender);

            return "listar";
        }
        if(subfam!=null){

            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,6);


            Page<vw_b2barticulos_row> products=productoService.findByCategoriaAndGrupoAndFamiliaAndCanalAndLista(category,subcategory,fam,subfam,usuario.getListaPreciosEsp(),pageRequest);
            List<String> subfamilia =productoService.findByCategoriaAndGrupoAndFamilia(category,subcategory,fam,usuario.getListaPreciosEsp());

            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?category="+category, products);
            model.addAttribute("subfamilia",subfamilia);
            model.addAttribute("subcat",subcategory);
            model.addAttribute("fam",fam);
            model.addAttribute("Products", products);
            model.addAttribute("category",category);
            model.addAttribute("page", pageRender);
            return "listar";
        }


        return "listar";

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
