package RedSocial;

import API.Paquete;
import API.Tipos;
import Subasta.SubastadorS;
import Subasta.Subastador_GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;

public class ReadMessage_Celebridad extends Thread {
    ObjectInputStream dis;
    ObjectOutputStream dos;
    CelebridadS celebridad;
    Celebridad_GUI window;

    public ReadMessage_Celebridad(ObjectInputStream dis, ObjectOutputStream dos, CelebridadS _celebridad) {
        super();
        this.dis = dis;
        this.dos = dos;
        this.celebridad = _celebridad;
    }


    @Override
    public void run() {
        window = new Celebridad_GUI(dis, dos, celebridad);
        window.init();

        while (true) {

            try {

                Paquete paquete = (Paquete) dis.readObject();

                if (paquete.asunto.equals("AddSocio")) {
                    celebridad.cantidadSubs++;
                    window.meterseLinea("Se unio el cliente: " + paquete.informacion);
                    //Validar si ya llego a un multiplo de 10
                    if (celebridad.cantidadSubs % 10 == 0) {
                        String msg = "------\n" + celebridad.name + " le notifica: llegué a " + celebridad.cantidadSubs + " followers";
                        Paquete paquete1 = new Paquete("notificacion", msg, celebridad.name, null, Tipos.CELEBRIDAD);
                        dos.writeObject(paquete1);
                    }
                } else if (paquete.asunto.equals("push_subasta")) {
                    //add oferta
                    window.meterseLinea("Cliente: " + paquete.sourceAux + "pushea: " + paquete.precioPush);
                } else if (paquete.asunto.equals("like")) {
                    int indexPost = paquete.precioPush;

                    if (indexPost < celebridad.postRealizados.size()) {
                        Post post = celebridad.postRealizados.get(indexPost);
                        post.likes++;
                        //Validar si ya llego a un multiplo de 10
                        if (post.likes % 10 == 0) {
                            System.out.println("ya soy un multiplo de 10");
                            String msg = "------\n" + celebridad.name + " le notifica: llegué a " + post.likes + " de likes en mi post #" + post.id + ": " + post.texto;
                            Paquete paquete1 = new Paquete("notificacion", msg, celebridad.name, null, Tipos.CELEBRIDAD);
                            dos.writeObject(paquete1);
                        }

                        window.meterseLinea("---------\n" + paquete.sourceAux + " le dió like a su post #" + indexPost + ": " + paquete.informacion + "\nCant de likes: " + post.likes);
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}