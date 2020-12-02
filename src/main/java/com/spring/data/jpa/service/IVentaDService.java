package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.VentaD;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IVentaDService {
    VentaD save(VentaD venta);

    List<VentaD> findByID(int ID);

    @Query(value = "update saldoU set saldoU =SALDOU+?1 where rama='RESV'and cuenta =?2 and GRUPO =?3 ",nativeQuery = true)
    void updateSaldoU(int saldoU,String art,int grupo);

}
