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
                if (paquete.asunto.equals("Aceptado")) {
                }






            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
