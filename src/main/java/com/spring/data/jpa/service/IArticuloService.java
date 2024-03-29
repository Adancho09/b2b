package com.spring.data.jpa.service;
import com.spring.data.jpa.models.entity.artCaracteristicas;
import com.spring.data.jpa.models.entity.vw_articulosBR_row;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface IArticuloService {

	public List<vw_articulosBR_row> findAllByLista(String lista );


	@Query(value = "select * from vw_b2barticulos_rowfiltro  where  lista=?1",nativeQuery = true)
	public List<vw_articulosBR_row> findAllByListaFiltro(String lista );
	public Page<vw_articulosBR_row> findAllByLista(String lista,Pageable pegeable);
	Page<vw_articulosBR_row> findAllByListaOrderByCantidadDesc(String lista,Pageable pegeable);
	@Query(value = "select V from vw_b2barticulos_row V where V.importe <=?1 and V.lista=?2")
	Page<vw_articulosBR_row> findAllByListaAndImporte(double importe , String lista, Pageable pegeable);

	@Query(value = "select * from vw_b2barticulos_rowfiltro  where fabricante =?1 and lista=?2",nativeQuery = true)
	Page<vw_articulosBR_row> findByFabricanteAndListaFiltro(String fabricante , String lista, Pageable pegeable);

	Page<vw_articulosBR_row> findByFabricanteAndLista(String fabricante,String lista,Pageable pageable);
	List<vw_articulosBR_row> findAllByCategoria(String categoria);
	vw_articulosBR_row findByArticuloAndLista(String articulo,String lista);
	Page<vw_articulosBR_row> findByCategoriaAndGrupoAndLista(String categoria,String grupo,String lista,Pageable pageable);
	Page<vw_articulosBR_row> findByCategoriaAndGrupoAndFamiliaAndLista(String categoria,String grupo,String familia,String lista,Pageable pageable);
	Page<vw_articulosBR_row> findByCategoriaAndGrupoAndFamiliaAndCanalAndLista(String categoria,String grupo,String familia,String canal,String lista,Pageable pageable);
	@Query("select categoria from vw_b2barticulos_row where lista =?1  GROUP BY categoria ")
	List<String> findByCategoriaL(String lista,String categoria);
	@Query("select grupo from vw_b2barticulos_row where categoria =?1 and lista =?2 GROUP BY grupo")
	List<String> findByGrupo(String categoria,String lista);
	@Query("select familia from vw_b2barticulos_row  where categoria =?1 and grupo =?2 and lista =?3  GROUP BY familia")
	List<String> findByCategoriaAndGrupo(String categoria,String grupo,String lista);
	@Query("select canal from vw_b2barticulos_row  where categoria =?1 and grupo =?2 and familia =?3 and lista =?4 GROUP BY canal")
	List<String> findByCategoriaAndGrupoAndFamilia(String categoria,String grupo,String familia,String lista);
	@Query("select categoria from vw_b2barticulos_row")
	List<String> findAllCategoria(String lista);
	@Query(value = "select * from vw_articulosbr",nativeQuery = true)
	List<vw_articulosBR_row> findByTodo();
	@Query(value = "select categoria from vw_articulosbr GROUP BY categoria",nativeQuery = true)
	List<String> findByCategoria();
	@Query(value = "select grupo from vw_articulosbr WHERE categoria =?1 GROUP BY grupo",nativeQuery = true)
	List<String> findBySubcategoria(String categoria);
	@Query(value = "select familia from vw_articulosbr WHERE categoria =?1 AND grupo= ?2 GROUP BY grupo",nativeQuery = true)
	List<String> findByFamilia(String categoria, String subcategoria);


	@Query(value = "select * from vw_articulosbr where categoria =?1 and  grupo =?2",nativeQuery = true)
	List<vw_articulosBR_row> findAllByCategoriaAndSub(String cat,String sub);
	@Query(value = "select * from vw_articulosbr where categoria =?1 and  grupo =?2 and familia=?3",nativeQuery = true)
	List<vw_articulosBR_row> findAllByCategoriaAndSubAndFam(String cat,String sub,String fam);
	vw_articulosBR_row findByArticulo(String codigo);

	@Query(value = "SELECT * FROM artCaracteristicas WHERE articulo =?1",nativeQuery = true)
	List<artCaracteristicas> getDetallesArticulo(String sku);

	@Query(value = "select * from vw_articulosbr where descripcion like %?%",nativeQuery = true)
	public List<vw_articulosBR_row> findByCriterio(String codigo);

}
