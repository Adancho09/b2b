package com.spring.data.jpa.models.entity;
import org.hibernate.annotations.Subselect;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "vw_articulosbr" )
public class vw_articulosBR_row implements Serializable {

	@Id
    private String articulo;

    private String fabricante;

    private String categoria;

    private String grupo;

    private String familia;

    private String canal;
    private String lista;
    
    private String descripcion;

    
    private int real_qty;
    private int real_qty3;

    private int  cantidad;

    private double  importe;
    
    private BigDecimal Precio;
   
    @Column(name = "nombrecorto")
    private String nombrecorto;

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public String getNombrecorto() {
		return nombrecorto;
	}

	public void setNombrecorto(String nombrecorto) {
		this.nombrecorto = nombrecorto;
	}

	public String getCanal() {
        return canal;
    }

    public void setCanal(String subFamilia) {
        this.canal = subFamilia;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String subCategoria) {
        this.grupo = subCategoria;
    }

    public BigDecimal getPrecio() {

        return Precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public void setPrecio(BigDecimal precio) {
        Precio = precio;
    }


    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
    this.familia=familia;}

    public int getReal_qty() {
        return real_qty;
    }

    public void setReal_qty(int disponible) {
        this.real_qty = disponible;
    }


    public int getReal_qty3() {
        return real_qty3;
    }

    public void setReal_qty3(int disponible) {
        this.real_qty3 = disponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }


    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricator) {
        this.fabricante = fabricator;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public vw_articulosBR_row() {
    }



    private static final long serialVersionUID=1l;

	
}


