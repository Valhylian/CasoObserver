package API;

import RedSocial.CelebridadS;
import RedSocial.Post;
import Subasta.Subasta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable, IObserver, Serializable{
    private Socket client;
    final ObjectInputStream dis;
    final ObjectOutputStream dos;
    public int id;
    public Server server;

    @Override
    public void notifyObserver(Paquete paquete) throws IOException {
        dos.reset();
        dos.writeObject(paquete);
    }


    public ClientHandler(Socket client, ObjectInputStream dis, ObjectOutputStream dos, int id) throws IOException {
        this.client = client;
        this.dis = dis;
        this.dos = dos;
        this.id = id;
        this.server = Server.getInstance();
    }

    @Override
    public void run(){
        String received;
        try {
            Paquete paqueteEnviado = new Paquete("Cliente Conectado",null);
            dos.writeObject(paqueteEnviado);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        while (true) {
            try {
                // receive the string;
                Paquete objectoRecibido = (Paquete) dis.readUnshared();
                System.out.println(objectoRecibido);

                if(objectoRecibido.asunto.equals("logout")){
                    break;
                }
                //Llega un nuevo cliente (SubastadorS)
                if(objectoRecibido.asunto.equals("Prueeeeba")){
                    dos.writeObject(new Paquete("listo",server.Observables));
                }

                //Se crea nueva subasta (Subastador_GUI)
                if (objectoRecibido.asunto.equals("Principal")) {
                    server.addPaquete(objectoRecibido.contenido);
                    //obtener referencia del principal
                    server.addPrincipal(this);
                    //dos.writeObject(new Paquete("listo",server.Observables));
                    //notificar nueva subasta a todos
                    server.notifyAllObservers(new Paquete("info",server.Observables));
                }

                if (objectoRecibido.asunto.equals("Cliente")) {
                    server.addObserver(this);
                    dos.writeObject(new Paquete("setId",Integer.toString(id), Tipos.SUBASTA));
                    dos.writeObject(new Paquete("info",server.Observables));
                }
                //Nos asociamos a una nueva subasta (Oferente_GUI)
                if (objectoRecibido.asunto.equals("Asociarse")){
                    int index = server.buscarObservable_nombre(objectoRecibido.informacion, objectoRecibido.tipo);

                    if (objectoRecibido.tipo == Tipos.SUBASTA){
                        Subasta subasta = (Subasta) server.Observables.get(index);
                        subasta.addObserver(server.observers.get(objectoRecibido.source));
                        //Agregar cliente a subasta
                        Paquete msg = new Paquete("AddSocio",objectoRecibido.sourceAux, Tipos.SUBASTA);
                        server.notifyPrincipal(index,msg);
                        dos.writeObject(new Paquete("info_subasta",subasta));
                    }

                    //dos.writeObject(new Paquete("listo",server.Observables));

                }
                //Pushamos un precio para una subasta (Oferente_GUI)
                if (objectoRecibido.asunto.equals("push_subasta")) {
                    int index = server.buscarObservable_nombre(objectoRecibido.informacion, objectoRecibido.tipo);

                    if (objectoRecibido.tipo == Tipos.SUBASTA){
                        Subasta subasta = (Subasta) server.Observables.get(index);
                        //Paquete msg = new Paquete("nuevo_push",objectoRecibido.sourceAux);
                        server.notifyPrincipal(index,objectoRecibido);
                        //dos.writeObject(new Paquete("push_aceptado",subasta));
                    }
                }
                else if (objectoRecibido.asunto.equals("actualizacion")) {
                    System.out.println("llega aca?");
                    System.out.printf(objectoRecibido.informacion);
                    //ACTUALIZAR TODA LA SUBASTA MENOS LOS SUSCRITOS
                    Subasta newSubasta = (Subasta) objectoRecibido.contenido;
                    System.out.println("nombre new: "+newSubasta.name);
                    int index = server.buscarObservable_nombre(newSubasta.name, Tipos.SUBASTA);
                    Subasta subastaSever = (Subasta) server.Observables.get(index);
                    System.out.println("nombre: "+subastaSever.name);
                    subastaSever.indexGanador = newSubasta.indexGanador;
                    subastaSever.lastOfert = newSubasta.lastOfert;
                    subastaSever.estado = newSubasta.estado;
                    Paquete notificarOferta = new Paquete("notificacion",objectoRecibido.informacion);
                    subastaSever.notifyAllObservers(notificarOferta);
                    server.notifyAllObservers(new Paquete("info",server.Observables));

                }else if (objectoRecibido.asunto.equals("actualizacionCelebridad")) {
                    //ACTUALIZAR TODA LA SUBASTA MENOS LOS SUSCRITOS
                    CelebridadS celebridadS = (CelebridadS) objectoRecibido.contenido;
                    int index = server.buscarObservable_nombre(celebridadS.name, Tipos.CELEBRIDAD);

                    CelebridadS celebridadServer = (CelebridadS) server.Observables.get(index);
                    celebridadServer.estado = celebridadS.estado;

                    Paquete notificarOferta = new Paquete("notificacion",objectoRecibido.informacion);
                    celebridadServer.notifyAllObservers(notificarOferta);
                    server.notifyAllObservers(new Paquete("info",server.Observables));
                }


                else if (objectoRecibido.asunto.equals("terminada")) {
                    //ACTUALIZAR TODA LA SUBASTA MENOS LOS SUSCRITOS
                    Subasta newSubasta = (Subasta) objectoRecibido.contenido;
                    int index = server.buscarObservable_nombre(newSubasta.name, Tipos.SUBASTA);
                    Subasta subastaSever = (Subasta) server.Observables.get(index);
                    subastaSever.indexGanador = newSubasta.indexGanador;
                    subastaSever.lastOfert = newSubasta.lastOfert;
                    subastaSever.estado = newSubasta.estado;

                    Paquete notificarOferta = new Paquete("notificacion",objectoRecibido.informacion);
                    subastaSever.notifyAllObservers(notificarOferta);

                    server.notifyAllObservers(new Paquete("info",server.Observables));

                    String msg = subastaSever.name+" le informa: "+ objectoRecibido.sourceAux;
                    int indexGanador = newSubasta.indexGanador;
                    server.notifyObserver_Index(indexGanador,new Paquete("notificacion", msg));
                }


                if (objectoRecibido.asunto.equals("NuevaCelebridad")) {
                    server.addPaquete(objectoRecibido.contenido);
                    server.addPrincipal(this);
                   // dos.writeObject(new Paquete("listo",server.Observables));
                    //notificar nueva celebridad a todos
                    server.notifyAllObservers(new Paquete("info",server.Observables));


                }else if (objectoRecibido.asunto.equals("PostCelebridad")) {
                    Post post = (Post) objectoRecibido.contenido;
                    String texto = "---------------\nLa celebridad "+post.posterName + " notifica #"+post.id+" : " + post.texto;
                    int index = server.buscarObservable_nombre(post.posterName, Tipos.CELEBRIDAD);
                    CelebridadS celebridad = (CelebridadS) server.Observables.get(index);

                    Paquete paquete = new Paquete("postNuevo",texto);
                    celebridad.notifyAllObservers(paquete);
                    //dos.writeObject(new Paquete("push_aceptado",subasta));
                }else if (objectoRecibido.asunto.equals("NuevoUsuario")) {

                    System.out.println("Llega usuario!");
                    server.addObserver(this);
                    dos.writeObject(new Paquete("setId",Integer.toString(id), Tipos.CELEBRIDAD));
                    dos.writeObject(new Paquete("info",server.Observables));

                }

                // Seguimos a una Celebridad
                else if (objectoRecibido.asunto.equals("FollowRequest")){
                    int index = server.buscarObservable_nombre(objectoRecibido.informacion, objectoRecibido.tipo);

                    if (objectoRecibido.tipo == Tipos.CELEBRIDAD){
                        CelebridadS celebridad = (CelebridadS) server.Observables.get(index);
                        //agrega a celebridad el clientHandler de seguidor buscando en observers a ese ID del SeguidorS
                        celebridad.addObserver(server.observers.get(objectoRecibido.source));
                        Paquete msg = new Paquete("AddSocio",objectoRecibido.sourceAux, Tipos.CELEBRIDAD);
                        server.notifyPrincipal(index,msg);
                        dos.writeObject(new Paquete("info_celebridad",celebridad));
                    }

                }else if (objectoRecibido.asunto.equals("like")){
                    int index = server.buscarObservable_nombre(objectoRecibido.informacion, objectoRecibido.tipo);

                    if (objectoRecibido.tipo == Tipos.CELEBRIDAD){
                        CelebridadS celebridad = (CelebridadS) server.Observables.get(index);
                        //agrega a celebridad el clientHandler de seguidor buscando en observers a ese ID del SeguidorS

                        server.notifyPrincipal(index,objectoRecibido);
                    }

                }else if (objectoRecibido.asunto.equals("notificacion")){
                    int index = server.buscarObservable_nombre(objectoRecibido.sourceAux, objectoRecibido.tipo);


                    if (objectoRecibido.tipo == Tipos.CELEBRIDAD){
                        CelebridadS celebridad =(CelebridadS) server.Observables.get(index);
                        celebridad.notifyAllObservers(objectoRecibido);
                    }

                }







            } catch (IOException e) {

                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}



