package com.spring.data.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.data.jpa.models.dao.IArticuloDao;
import com.spring.data.jpa.models.entity.vw_articulosBR_row;

@Service
public class ArticuloServiceImpl implements IArticuloService{

	@Autowired
    @Qualifier("articuloBean")
    private IArticuloDao articuloDao;

	@Override
	@Transactional(readOnly = true)
	public List<vw_articulosBR_row> findAll() {
		return articuloDao.findAll();
	}
}
