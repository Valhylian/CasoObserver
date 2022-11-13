package RedSocial;


import API.Paquete;
import Subasta.Oferente;
import Subasta.Oferente_GUI;
import Subasta.Subasta;
import java.io.*;
import java.util.ArrayList;

public class ReadMessage_Seguidor extends Thread {
    Seguidor_GUI clientePantalla;
    ObjectInputStream dis;
    ObjectOutputStream dos;
    SeguidorS seguidor;

    public ReadMessage_Seguidor(SeguidorS seguidor, ObjectInputStream dis, ObjectOutputStream dos) {
        super();
        this.seguidor = seguidor;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        clientePantalla = new Seguidor_GUI(dis,dos,seguidor);
        clientePantalla.init();
        while (true) {

            try {
                Paquete paquete = (Paquete) dis.readObject();

                if (paquete.asunto.equals("setId")) {
                    seguidor.id = Integer.parseInt(paquete.informacion) ;

                } else if (paquete.asunto.equals("info")) {
                    clientePantalla.actInterfaz((ArrayList<Object>) paquete.contenido);
                    seguidor.generales = (ArrayList<CelebridadS>) paquete.contenido;

                } else if (paquete.asunto.equals("info_celebridad")) {
                    CelebridadS celebridad = (CelebridadS) paquete.contenido;
                    seguidor.subscritas.add(celebridad);
                    clientePantalla.actSubscritas(celebridad);
                    String muchoTexto = "Suscrito a " + celebridad.name;
                    clientePantalla.meterseLinea(muchoTexto);

                } else if (paquete.asunto.equals("postNuevo")) {
                    clientePantalla.meterseLinea(paquete.informacion);

                }else if (paquete.asunto.equals("notificacion")) {
                    clientePantalla.meterseLinea(paquete.informacion);
                }


            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
