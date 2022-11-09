package Subasta;

import API.AbstractObservable;

import java.io.Serializable;

public class Subasta extends AbstractObservable implements Serializable {
    public String name;
    int lastOfert;
    int inicio;
    Producto producto;
    //ARRAYLIST OFERTAS   --NAME PERSONA --INDEX PERSONA -- INDEX OFERTA --PUSH

    public Subasta(String name, int lastOfert, int inicio, Producto producto){
        this.name = name;
        this.lastOfert = lastOfert;
        this.inicio = inicio;
        this.producto = producto;
    }
}
