package Subasta;

import API.Paquete;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class ReadMessage extends Thread {
    Oferente_GUI clientePantalla;
    ObjectInputStream dis;
    ObjectOutputStream dos;
    Oferente oferente;

    public ReadMessage(Oferente oferente, ObjectInputStream dis, ObjectOutputStream dos) {
        super();
        this.oferente = oferente;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {

        clientePantalla = new Oferente_GUI(dis,dos,oferente);
        clientePantalla.init();
        while (true) {

            try {
                Paquete paquete = (Paquete) dis.readObject();

                if (paquete.asunto.equals("setId")) {
                    oferente.id = Integer.parseInt(paquete.informacion) ;
                }

                else if (paquete.asunto.equals("info")) {
                    clientePantalla.actInterfaz((ArrayList<Object>) paquete.contenido);
                    oferente.generales = (ArrayList<Subasta>) paquete.contenido;
                }

                else if (paquete.asunto.equals("info_subasta")) {
                    Subasta subasta = (Subasta) paquete.contenido;
                    oferente.subscritas.add(subasta);
                    clientePantalla.actSubscritas(subasta);
                    String muchoTexto = "Suscrito a " + subasta.name+" "+subasta.producto.name+" "+subasta.producto.descripcion+" Precio: "+Integer.toString(subasta.lastOfert);
                    clientePantalla.meterseLinea(muchoTexto);
                }

                else if (paquete.asunto.equals("notificacion")) {
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
