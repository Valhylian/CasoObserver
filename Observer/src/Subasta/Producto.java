package Subasta;

import java.awt.*;
import java.io.Serializable;
import java.net.Socket;

public class  Producto implements Serializable {
    String name;
    String descripcion;
    Image imagen;
    int precioInicial;
    int precioFinal;


    public Producto(String nombre, String descripcion, Image imagen, int precioInicial, int precioFinal){
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.name = nombre;
        this.precioInicial = precioInicial;
        this.precioFinal = precioFinal;
    }

}
