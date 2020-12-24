package com.spring.data.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.data.jpa.models.dao.IArticuloDao;
import com.spring.data.jpa.models.dao.IDetalleArticuloDao;
import com.spring.data.jpa.models.entity.artCaracteristicas;
import com.spring.data.jpa.models.entity.vw_articulosBR_row;

@Service
public class ArticuloServiceImpl implements IArticuloService{

	@Autowired
    private IArticuloDao articuloDao;
	
	@Autowired
    private IDetalleArticuloDao articuloDetalleDao;

    @Override
    @Transactional(readOnly = true)
    public List<vw_articulosBR_row> findAllByLista(String lista) {
        return (List<vw_articulosBR_row>) articuloDao.findAllByLista(lista);
    }

    @Override
    public List<vw_articulosBR_row> findAllByListaFiltro(String lista) {
        return articuloDao.findAllByListaFiltro(lista);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<vw_articulosBR_row> findAllByLista(String lista,Pageable pageable)
    {
        return articuloDao.findAllByLista(lista,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<vw_articulosBR_row> findAllByListaOrderByCantidadDesc(String lista,Pageable pegeable) {
        return articuloDao.findAllByListaOrderByCantidadDesc(lista,pegeable);
    }

    @Override
    public Page<vw_articulosBR_row> findAllByListaAndImporte(double importe, String lista, Pageable pegeable) {
        return articuloDao.findAllByListaAndImporte(importe,lista,pegeable);
    }

    @Override
    public Page<vw_articulosBR_row> findByFabricanteAndListaFiltro(String fabricante, String lista, Pageable pegeable) {
        return articuloDao.findByFabricanteAndListaFiltro(fabricante,lista,pegeable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<vw_articulosBR_row> findByFabricanteAndLista(String fabricante,String lista, Pageable pageable) {
        return  articuloDao.findByFabricanteAndLista(fabricante,lista,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public vw_articulosBR_row findByArticuloAndLista(String articulo,String lista) {
     return articuloDao.findByArticuloAndLista(articulo,lista);
    }

    @Override
    @Transactional(readOnly = true)
    public List<vw_articulosBR_row> findAllByCategoria(String categoria) {
        return articuloDao.findAllByCategoria(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<vw_articulosBR_row> findByCategoriaAndGrupoAndLista(String categoria, String subCategoria,String lista, Pageable pageable) {
        return articuloDao.findByCategoriaAndGrupoAndLista(categoria,  subCategoria,lista, pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<vw_articulosBR_row> findByCategoriaAndGrupoAndFamiliaAndLista(String categoria, String subCategoria,String familia,String lista, Pageable pageable) {
        return articuloDao.findByCategoriaAndGrupoAndFamiliaAndLista(categoria,subCategoria,familia,lista,pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<vw_articulosBR_row> findByCategoriaAndGrupoAndFamiliaAndCanalAndLista(String categoria, String subCategoria,String familia,String canal,String lista, Pageable pageable) {
        return articuloDao.findByCategoriaAndGrupoAndFamiliaAndCanalAndLista(categoria,  subCategoria,familia ,canal,lista, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findByCategoriaL(String lista,String categoria) {
        return articuloDao.findByCategoriaL(lista,categoria);
    }
    @Override
    @Transactional(readOnly = true)
    public List<String> findByGrupo(String grupo,String lista) {
        return articuloDao.findByGrupo(grupo,lista);
    }


    @Override
    @Transactional(readOnly = true)
    public List<String> findByCategoriaAndGrupo(String categoria,String grupo,String lista){
        return articuloDao.findByCategoriaAndGrupo(categoria,grupo,lista);
    }
    @Override
    @Transactional(readOnly = true)
    public List<String> findByCategoriaAndGrupoAndFamilia(String categoria,String grupo,String familia,String lista) {
        return articuloDao.findByCategoriaAndGrupoAndFamilia(categoria,grupo,familia,lista);
    }

    @Override
    public List<String> findAllCategoria(String lista) {
        return null;
    }

    @Override
	@Transactional(readOnly = true)
	public List<vw_articulosBR_row> findByTodo() {
		return articuloDao.findByTodo();
	}

	@Override
	public List<String> findByCategoria() {
		return articuloDao.findByCategoria();
	}

	@Override
	public List<String> findBySubcategoria(String categoria) {
		return articuloDao.findBySubcategoria(categoria);
	}
	@Override
	public List<String> findByFamilia(String categoria, String subcategoria) {
		return articuloDao.findByFamilia(categoria, subcategoria);
	}

    @Override
    public List<vw_articulosBR_row> findAllByCategoriaAndSub(String cat, String sub) {
        return articuloDao.findAllByCategoriaAndSub(cat,sub);
    }

    @Override
    public List<vw_articulosBR_row> findAllByCategoriaAndSubAndFam(String cat, String sub, String fam) {
        return articuloDao.findAllByCategoriaAndSubAndFam(cat,sub,fam);
    }
    @Override
    public vw_articulosBR_row findByArticulo(String codigo) {
        return articuloDao.findByArticulo(codigo);
    }
	@Override
	@Transactional(readOnly = true)
    public List<artCaracteristicas> getDetallesArticulo(String sku) {
        return articuloDetalleDao.getDetallesArticulo(sku);
    }
}
