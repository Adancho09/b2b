package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.artCaracteristicas;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface iArtCarService {
    @Query(value = "select * from artCaracteristicas where Articulo =?1 ",nativeQuery = true)
    List<artCaracteristicas> getDetallesArticulo(String sku);
}
