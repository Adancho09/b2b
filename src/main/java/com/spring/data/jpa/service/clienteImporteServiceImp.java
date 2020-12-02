package com.spring.data.jpa.service;

import com.spring.data.jpa.models.dao.IclienteImporteDao;
import com.spring.data.jpa.models.entity.clienteImportes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class clienteImporteServiceImp implements IclienteImporteService{

    @Autowired
    private IclienteImporteDao  importeDao;


    @Override
    public clienteImportes findAllByClienteFacturacion(String cliente) {
        return importeDao.findAllByClienteFacturacion(cliente);
    }

    @Override
    public clienteImportes findAllByClienteDev(String cliente) {
        return importeDao.findAllByClienteDev( cliente);
    }

    @Override
    public clienteImportes findAllByClienteVenta(String cliente) {
        return importeDao.findAllByClienteVenta(cliente);
    }
}
