package Subasta;

import java.util.ArrayList;
import API.ClientHandler;
public class Subasta {
    String name;
    int lastOfert;
    int inicio;
    Producto producto;
    ArrayList<ClientHandler> asociados = new ArrayList<ClientHandler>();

    public Subasta(String name, int lastOfert, int inicio, Producto producto){
        this.name = name;
        this.lastOfert = lastOfert;
        this.inicio = inicio;
        this.producto = producto;
    }
}
