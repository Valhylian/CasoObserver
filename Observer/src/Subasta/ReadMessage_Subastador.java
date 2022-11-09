package Subasta;

import API.Paquete;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ReadMessage_Subastador extends Thread{
    ObjectInputStream dis;
    ObjectOutputStream dos;

    public ReadMessage_Subastador(ObjectInputStream dis, ObjectOutputStream dos) {
        super();
        this.dis = dis;
        this.dos = dos;
    }


    @Override
    public void run() {


        while (true) {

            try {
                Paquete paquete = (Paquete) dis.readObject();

                if (paquete.asunto.equals("Asociarse")) {
                    System.out.println("Vale craaack");

                }

            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
