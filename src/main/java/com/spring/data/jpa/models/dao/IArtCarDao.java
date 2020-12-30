package com.spring.data.jpa.models.dao;

import com.spring.data.jpa.models.entity.ArtCarApk;
import com.spring.data.jpa.models.entity.artCaracteristicas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IArtCarDao extends CrudRepository<artCaracteristicas,ArtCarApk> {
    @Query(value = "select * from artCaracteristicas where Articulo =?1",nativeQuery = true)
    List<artCaracteristicas> getDetallesArticulo(String sku);
}
