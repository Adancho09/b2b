package com.spring.data.jpa.models.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.spring.data.jpa.models.entity.vw_articulosBR_row;

@Qualifier("articuloBean")
public interface IArticuloDao extends CrudRepository<vw_articulosBR_row,String> {
	
	    List<vw_articulosBR_row> findAll();

}
