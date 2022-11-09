package API;

import java.io.Serializable;
import java.net.Socket;

public class Paquete implements Serializable {
    public String asunto;
    public Object contenido;
    public String informacion;
    public Tipos tipo;
    public String source;


    public Paquete (String _asunto, Object _contenido){
        this.asunto = _asunto;
        this.contenido = _contenido;
        this.informacion = null;
    }

    public Paquete (String asunto, String info, Tipos tipo, String socket){
        this.asunto=asunto;
        this.informacion = info;
        this.tipo = tipo;
        this.source = socket;
    }
    public Paquete (String asunto, String info, Tipos tipo){
        this.asunto=asunto;
        this.informacion = info;
        this.tipo = tipo;
    }


}
