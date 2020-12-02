package com.spring.data.jpa.models.dao;

import com.spring.data.jpa.models.entity.clienteSaldo;
import org.springframework.data.repository.CrudRepository;

public interface IClienteSaldoDao extends CrudRepository<clienteSaldo,String> {

    clienteSaldo findByCliente(String cliente);

}
