package com.spring.data.jpa.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.spring.data.jpa.models.entity.artCaracteristicas;

public interface IDetalleArticuloDao extends CrudRepository<artCaracteristicas,String> {
	
		@Query(value = "SELECT * FROM artCaracteristicas WHERE articulo = ?1",nativeQuery = true)
		List<artCaracteristicas> getDetallesArticulo(String sku);
		
}
