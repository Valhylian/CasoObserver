package Subasta;

import API.AbstractObservable;

import java.io.Serializable;

public class Subasta extends AbstractObservable implements Serializable {
    String name;
    int lastOfert;
    int inicio;
    Producto producto;

    public Subasta(String name, int lastOfert, int inicio, Producto producto){
        this.name = name;
        this.lastOfert = lastOfert;
        this.inicio = inicio;
        this.producto = producto;
    }
}
