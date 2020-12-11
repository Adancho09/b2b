package com.spring.data.jpa.service;
import com.spring.data.jpa.models.entity.vw_articulosBR_row;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface IArticuloService {

		List<vw_articulosBR_row> findAll();

}
