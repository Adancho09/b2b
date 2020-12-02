package com.spring.data.jpa.service;

import com.spring.data.jpa.models.dao.IClienteSaldoDao;
import com.spring.data.jpa.models.entity.clienteSaldo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteSaldoServiceImp implements IClienteSaldoService {

    @Autowired
    private IClienteSaldoDao clienteSaldoDao;


    @Override
    public clienteSaldo findByCliente(String cliente) {
        return clienteSaldoDao.findByCliente(cliente);
    }
}
