package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.clienteImportes;
import org.springframework.data.jpa.repository.Query;

public interface IclienteImporteService {
    @Query(value = "select * from vw_b2bfacturacionclienteanual where cliente =?1",nativeQuery = true)
    clienteImportes findAllByClienteFacturacion(String cliente);
    @Query(value = "select * from vw_b2bdevolucionclienteanual where cliente =?1",nativeQuery = true)
    clienteImportes findAllByClienteDev(String cliente);
    @Query(value = "select * from vw_b2bventaclienteanual where cliente =?1",nativeQuery = true)
    clienteImportes findAllByClienteVenta(String cliente);



}
