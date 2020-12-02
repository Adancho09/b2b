package com.spring.data.jpa.models.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_b2bventaclienteanual")
public class ClienteVenta {

    @Id
    private String cliente;
    private double enero;
    private double febrero;
    private double marzo;
    private double abril;
    private double mayo;
    private double junio;
    private double julio;
    private double agosto;
    private double septiembre;
    private double octubre;
    private double noviembre;
    private double diciembre;



    public String getCliente() {
        return cliente;
    }

    public double getSeptiembre() {
        return septiembre;
    }

    public void setSeptiembre(double septiembre) {
        this.septiembre = septiembre;
    }

    public double getOctubre() {
        return octubre;
    }

    public void setOctubre(double octubre) {
        this.octubre = octubre;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getEnero() {
        return enero;
    }

    public void setEnero(double enero) {
        this.enero = enero;
    }

    public double getFebrero() {
        return febrero;
    }

    public void setFebrero(double febrero) {
        this.febrero = febrero;
    }

    public double getMarzo() {
        return marzo;
    }

    public void setMarzo(double marzo) {
        this.marzo = marzo;
    }

    public double getAbril() {
        return abril;
    }

    public void setAbril(double abril) {
        this.abril = abril;
    }

    public double getMayo() {
        return mayo;
    }

    public void setMayo(double mayo) {
        this.mayo = mayo;
    }

    public double getJunio() {
        return junio;
    }

    public void setJunio(double junio) {
        this.junio = junio;
    }

    public double getJulio() {
        return julio;
    }

    public void setJulio(double julio) {
        this.julio = julio;
    }

    public double getAgosto() {
        return agosto;
    }

    public void setAgosto(double agosto) {
        this.agosto = agosto;
    }

    public double getNoviembre() {
        return noviembre;
    }

    public void setNoviembre(double noviembre) {
        this.noviembre = noviembre;
    }

    public double getDiciembre() {
        return diciembre;
    }

    public void setDiciembre(double diciembre) {
        this.diciembre = diciembre;
    }




}
