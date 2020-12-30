package com.spring.data.jpa.service;

import com.spring.data.jpa.models.dao.IArtCarDao;
import com.spring.data.jpa.models.dao.IArticuloDao;
import com.spring.data.jpa.models.entity.artCaracteristicas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtCarServiceImp implements iArtCarService {

    @Autowired
    private IArtCarDao artCarDao;
    @Override
    @Transactional(readOnly = true)
    public List<artCaracteristicas> getDetallesArticulo(String sku) {
        return artCarDao.getDetallesArticulo(sku);
    }
}
