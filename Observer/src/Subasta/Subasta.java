package Subasta;

import API.AbstractObservable;

public class Subasta extends AbstractObservable {
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
