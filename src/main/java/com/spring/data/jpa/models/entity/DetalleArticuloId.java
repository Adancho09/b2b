package com.spring.data.jpa.models.entity;

import java.io.Serializable;

public class DetalleArticuloId implements Serializable {
	
	private String articulo;
    private double renglon;

    // default constructor

    public DetalleArticuloId(String articulo, double renglon) {
        this.articulo = articulo;
        this.renglon = renglon;
    }
    public DetalleArticuloId() {
    	
    }

}

    
