package com.spring.data.jpa.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ArtCaracteristicas")
public class artCaracteristicas implements Serializable {
	
	@Id
	private String articulo;
	private BigDecimal renglon;
	private String orden;
	private String caracteristicas;
	private String descripcion;
	private String tipo;
	private String filtro;
	private String estatus;

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getArticulo() {
		return articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}

	public BigDecimal getRenglon() {
		return renglon;
	}

	public void setRenglon(BigDecimal renglon) {
		this.renglon = renglon;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getCaracteriticas() {
		return caracteristicas;
	}

	public void setCaracteriticas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public artCaracteristicas() {
	}

	private static final long serialVersionUID = 1l;

}
