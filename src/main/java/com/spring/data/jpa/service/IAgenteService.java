package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.Agente;

public interface IAgenteService {

    Agente findByAgente(String agente);

}
