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

                if (paquete.asunto.equals("AddSocio")){
                    window.meterseLinea("Se unio el cliente: "+paquete.informacion);
                }

                else if (paquete.asunto.equals("push_subasta")){
                    //add oferta
                    Oferta oferta = new Oferta(subastador.subasta.ofertas.size(),paquete.sourceAux,paquete.source,paquete.precioPush);
                    subastador.subasta.ofertas.add(oferta);
                    String info = "Oferta: "+oferta.indexOferta+" Cliente: "+oferta.name_ofertante + " Puja: "+oferta.dineroOfertado;
                    window.meterseLinea(info);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
