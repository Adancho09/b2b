package com.spring.data.jpa.models.entity;

import java.io.Serializable;
import java.util.Objects;

public class ArtCarApk implements Serializable {

    private String articulo;

    private int renglon;

    public ArtCarApk (String articulo, int renglon) {
        this.articulo = articulo;
        this.renglon = renglon;
    }

    public ArtCarApk () {

    }

    //Getters and setters are omitted for brevity


    public int getRenglon() {
        return renglon;
    }

    public void setRenglon(int renglon) {
        this.renglon = renglon;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        ArtCarApk  pk = (ArtCarApk ) o;
        return Objects.equals( articulo, pk.articulo ) &&
                Objects.equals( renglon, pk.renglon);
    }

    @Override
    public int hashCode() {
        return Objects.hash( articulo,renglon );
    }
}
