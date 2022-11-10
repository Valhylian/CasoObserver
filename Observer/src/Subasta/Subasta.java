package Subasta;

import API.AbstractObservable;

import java.io.Serializable;
import java.util.ArrayList;

public class Subasta extends AbstractObservable implements Serializable {
    public String name;
    int lastOfert;
    int inicio;
    Producto producto;
    int indexGanador;
    //ARRAYLIST OFERTAS   --NAME PERSONA --INDEX PERSONA -- INDEX OFERTA --PUSH
    public ArrayList<Oferta> ofertas = new ArrayList<>();

    public Subasta(String name, int lastOfert, int inicio, Producto producto){
        this.name = name;
        this.lastOfert = lastOfert;
        this.inicio = inicio;
        this.producto = producto;
        this.indexGanador = -1;
    }

    public void addOferta(String name_ofertante,int index_ofertante,int dineroOfertado){
        Oferta oferta = new Oferta(ofertas.size(),name_ofertante,index_ofertante,dineroOfertado);
        ofertas.add(oferta);
    }
}
