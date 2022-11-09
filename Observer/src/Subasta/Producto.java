package Subasta;

import java.awt.*;
import java.io.Serializable;

public class  Producto implements Serializable {
    String name;
    Image imagen;

    public Producto(String nombre, Image imagen){
        this.imagen = imagen;
        this.name = name;
    }

}
