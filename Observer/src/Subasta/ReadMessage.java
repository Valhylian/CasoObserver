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
        while (true) {

            try {
                Paquete paquete = (Paquete) dis.readObject();

                if (paquete.asunto.equals("info")) {
                    clientePantalla = new Oferente_GUI(dis,dos);
                    clientePantalla.init();
                    
                }

            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
