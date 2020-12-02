package com.spring.data.jpa.service;

import com.spring.data.jpa.models.dao.IAgenteDao;
import com.spring.data.jpa.models.entity.Agente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgenteServiceImp implements IAgenteService{

    @Autowired
    private IAgenteDao agenteDao;


    @Override
    public Agente findByAgente(String agente) {
        return agenteDao.findByAgente(agente);
    }
}
