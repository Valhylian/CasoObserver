package RedSocial;

import API.Paquete;
import Subasta.SubastadorS;
import Subasta.Subastador_GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;

public class ReadMessage_Celebridad extends Thread{
    ObjectInputStream dis;
    ObjectOutputStream dos;
    CelebridadS celebridad;
    Celebridad_GUI window;

    public ReadMessage_Celebridad(ObjectInputStream dis, ObjectOutputStream dos,CelebridadS _celebridad) {
        super();
        this.dis = dis;
        this.dos = dos;
        this.celebridad = _celebridad;
    }


    @Override
    public void run() {
        window = new Celebridad_GUI(dis,dos,celebridad);
        window.init();

        while (true) {

            try {

                Paquete paquete = (Paquete) dis.readObject();

                if (paquete.asunto.equals("listo")) {
                    System.out.println("ha sido verificado como celebridad! ");

                }

                if (paquete.asunto.equals("Asociarse")) {
                    System.out.println("Vale craaack");

                }
                if (paquete.asunto.equals("AddSocio")){
                    window.meterseLinea("Se unio el cliente: "+paquete.informacion);

                }

                if (paquete.asunto.equals("push_subasta")){
                    //add oferta

                    window.meterseLinea("Cliente: "+paquete.sourceAux+ "pushea: "+paquete.precioPush);

                }

            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}