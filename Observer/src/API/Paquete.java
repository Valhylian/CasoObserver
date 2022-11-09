package API;

import java.io.Serializable;
import java.net.Socket;

public class Paquete implements Serializable {
    public String asunto;
    public Object contenido;
    public String informacion;
    public Tipos tipo;
    public int source;
    public String sourceAux;
    public int precioPush;



    public Paquete (String _asunto, Object _contenido){
        this.asunto = _asunto;
        this.contenido = _contenido;
        this.informacion = null;
    }

    public Paquete (String asunto, String info, Tipos tipo, int socket,String sourceAux){
        this.asunto=asunto;
        this.informacion = info;
        this.tipo = tipo;
        this.source = socket;
        this.sourceAux = sourceAux;
    }
    public Paquete (String asunto, String info, Tipos tipo){
        this.asunto=asunto;
        this.informacion = info;
        this.tipo = tipo;
    }
    //Cliente oferente hace push a subasta
    public Paquete (String _asunto, String nombreSubasta,int idOferente, String nombreOferente, int precioPush,Tipos tipo){
        this.asunto = _asunto;
        this.informacion = nombreSubasta; //MUY IMPORTANTE!
        this.source = idOferente;
        this.sourceAux = nombreOferente;
        this.precioPush = precioPush;
        this.tipo = tipo;
    }
    //Celebridad postea
    public Paquete (String _asunto, String nombreCelebridad, String texto){
        this.asunto = _asunto;
        this.informacion = nombreCelebridad; //MUY IMPORTANTE!
        this.sourceAux = texto;

    }


}
