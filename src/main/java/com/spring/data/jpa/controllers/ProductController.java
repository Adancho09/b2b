package com.spring.data.jpa.controllers;

import com.spring.data.jpa.Paginator.PageRender;
import com.spring.data.jpa.models.dao.IUsuarioDao;

import com.spring.data.jpa.models.entity.CardD;
import com.spring.data.jpa.models.entity.Usuario;
import com.spring.data.jpa.models.entity.artCaracteristicas;
import com.spring.data.jpa.models.entity.vw_articulosBR_row;

import com.spring.data.jpa.service.*;
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
   private iArtCarService artCarService;


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
    public List getDetallesArticulo(@PathVariable(value="sku") String sku) {

        List<artCaracteristicas> articuloDetalle = artCarService.getDetallesArticulo(sku);
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
    @RequestMapping(value="/BuscarArticulo/{codigo}", method = RequestMethod.GET)
    public List BusquedaArticulo(@PathVariable(value="codigo") String codigo) {
    	
        List<vw_articulosBR_row> busquedaArticulo=articuloService.findByCriterio(codigo);
    	return busquedaArticulo;
    }







}
