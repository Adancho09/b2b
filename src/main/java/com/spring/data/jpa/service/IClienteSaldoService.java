package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.clienteSaldo;

public interface IClienteSaldoService {

    clienteSaldo findByCliente(String cliente);

}
