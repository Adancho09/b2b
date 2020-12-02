package com.spring.data.jpa.models.dao;

import com.spring.data.jpa.models.entity.CardD;
import com.spring.data.jpa.models.entity.CardDApk;
import com.spring.data.jpa.models.entity.VentaD;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IVentaDDao extends CrudRepository<VentaD, CardDApk> {

    VentaD save(VentaD venta);
      List<VentaD> findByID(int ID);
    @Query(value = "update saldoU set saldoU =SALDOU+?1 where rama='RESV'and cuenta =?2 and GRUPO =?3",nativeQuery = true)
   void updateSaldoU(int saldoU,String art,int grupo);
}
