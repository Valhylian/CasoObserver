package Subasta;

import API.AbstractObservable;

import java.io.Serializable;
import java.util.ArrayList;

public class Subasta extends AbstractObservable implements Serializable {
    public String name;
    public int lastOfert;
    public int inicio;
    public int finalProgramado;
    public Producto producto;
    public int indexGanador;
    public Estado estado = Estado.DEFAULT;
    //ARRAYLIST OFERTAS   --NAME PERSONA --INDEX PERSONA -- INDEX OFERTA --PUSH
    public ArrayList<Oferta> ofertas = new ArrayList<>();

    public Subasta(String name, int lastOfert,Producto producto){
        super();
        this.name = "Subasta de: "+name;
        this.lastOfert = lastOfert;
        this.inicio = producto.precioInicial;
        this.producto = producto;
        this.indexGanador = -1;
        this.finalProgramado = producto.precioFinal;
        this.estado = Estado.DEFAULT;
    }

    public Subasta(String name, int lastOfert,Producto producto, int indexGanador, Estado estado){
        super();
        this.name = name;
        this.lastOfert = lastOfert;
        this.inicio = producto.precioInicial;
        this.producto = producto;
        this.indexGanador = indexGanador;
        this.finalProgramado = producto.precioFinal;
        this.estado = estado;
    }

    public void addOferta(String name_ofertante,int index_ofertante,int dineroOfertado){
        Oferta oferta = new Oferta(ofertas.size(),name_ofertante,index_ofertante,dineroOfertado);
        ofertas.add(oferta);
    }

}
