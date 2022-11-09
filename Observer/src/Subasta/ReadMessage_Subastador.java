package Subasta;

import API.Paquete;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ReadMessage_Subastador extends Thread{
    ObjectInputStream dis;
    ObjectOutputStream dos;
    SubastadorS subastador;
    Subastador_GUI window;

    public ReadMessage_Subastador(ObjectInputStream dis, ObjectOutputStream dos,SubastadorS subastador) {
        super();
        this.dis = dis;
        this.dos = dos;
        this.subastador = subastador;
    }


    @Override
    public void run() {
        window = new Subastador_GUI(dis,dos,subastador);
        window.init();

        while (true) {

            try {

                Paquete paquete = (Paquete) dis.readObject();

                if (paquete.asunto.equals("listo")) {
                    System.out.println("Vale craaack2");
                }

                if (paquete.asunto.equals("Asociarse")) {
                    System.out.println("Vale craaack");

                }
                if (paquete.asunto.equals("AddSocio")){
                    window.meterseLinea("Se unio el cliente: "+paquete.informacion);

                }

            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
