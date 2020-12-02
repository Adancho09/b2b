package com.spring.data.jpa.models.dao;

import com.spring.data.jpa.models.entity.Agente;
import org.springframework.data.repository.CrudRepository;

public interface IAgenteDao extends CrudRepository<Agente,String> {

    Agente findByAgente(String agente);
}
