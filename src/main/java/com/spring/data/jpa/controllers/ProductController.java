package com.spring.data.jpa.controllers;


import com.spring.data.jpa.Paginator.PageRender;
import com.spring.data.jpa.models.dao.IUsuarioDao;

import com.spring.data.jpa.models.entity.vw_articulosBR_row;

import com.spring.data.jpa.service.IArticuloService;
import com.spring.data.jpa.service.ICardService;
import com.spring.data.jpa.service.ICartService;
import com.spring.data.jpa.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://store.balamrush.com.mx", maxAge = 3600)
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
    	 

         

    	
        List<String> categories=productoService.findByCategoriaL("1","");
    	
    	return categories;
    }

    @RequestMapping(value="/listarArticulos", method = RequestMethod.GET)
    public List listarArticulos() {
    	

    	
        List<vw_articulosBR_row> articulos=articuloService.findByTodo();
    	
    	return articulos;
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






            System.out.println("fam");
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
    	
    	 String username;

    	
        List<String> subcategorias=articuloService.findBySubcategoria(categoria);
    	
    	return subcategorias;
    }

    @RequestMapping(value="/listarFamilias/{categoria}/{subcategoria}", method = RequestMethod.GET)
    public List listarFamiliaBalam(@PathVariable(value="categoria") String categoria, @PathVariable(value="subcategoria") String subcategoria) {
    	
    	 String username;
    	 

        List<String> familias=articuloService.findByFamilia(categoria,subcategoria);
    	
    	return familias;
    }
    @RequestMapping(value="/articulo/{codigo}", method = RequestMethod.GET)
    public vw_articulosBR_row DetalleArticulo(@PathVariable(value="codigo") String codigo) {

        vw_articulosBR_row detalleArticulo=articuloService.findByArticulo(codigo);
    	System.out.println(detalleArticulo.getArticulo());
    	return detalleArticulo;
    }






}
