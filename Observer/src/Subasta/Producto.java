package Subasta;

import java.awt.*;
import java.io.Serializable;
import java.net.Socket;

public class  Producto implements Serializable {
    String name;
    String descripcion;
    Image imagen;


    public Producto(String nombre, String descripcion, Image imagen){
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.name = name;
    }

}
