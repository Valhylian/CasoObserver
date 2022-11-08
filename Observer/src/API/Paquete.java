package API;

import java.io.Serializable;

public class Paquete implements Serializable {
    public String asunto;
    public Object contenido;

    public Paquete (String _asunto, Object _contenido){
        this.asunto = _asunto;
        this.contenido = _contenido;
    }
}
