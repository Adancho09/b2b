package com.spring.data.jpa.models.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="vw_b2bclienteinfo")
public class clienteSaldo {
    @Id
    private String cliente;
    private BigDecimal creditolimitemx;
    private BigDecimal saldomn;
    private BigDecimal disponible;

    public clienteSaldo() {
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getCreditolimitemx() {
        return creditolimitemx;
    }

    public void setCreditolimitemx(BigDecimal creditolimitemx) {
        this.creditolimitemx = creditolimitemx;
    }

    public BigDecimal getSaldomn() {
        return saldomn;
    }

    public void setSaldomn(BigDecimal saldomn) {
        this.saldomn = saldomn;
    }

    public BigDecimal getDisponible() {
        return disponible;
    }

    public void setDisponible(BigDecimal disponible) {
        this.disponible = disponible;
    }
}
