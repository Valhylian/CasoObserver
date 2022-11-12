package API;

import RedSocial.CelebridadS;
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
    //Esto no debería ser un IObservable?
    public IObserver observable;
    public int id;
    public Server server;

    @Override
    public void notifyObserver(Paquete paquete) throws IOException {
        dos.reset();
        dos.writeObject(paquete);
    }

    @Override
    public Socket returnSocket (){
        return client;
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
                    System.out.printf("ASOCIARSE");
                    System.out.println(objectoRecibido.informacion);
                    int index = server.buscarObservable_nombre(objectoRecibido.informacion, objectoRecibido.tipo);

                    if (objectoRecibido.tipo == Tipos.SUBASTA){
                        Subasta subasta = (Subasta) server.Observables.get(index);
                        System.out.println("INDEX SOCIO: "+objectoRecibido.source + " "+objectoRecibido.sourceAux);
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


                //Se crea nueva celebridad (CelebridadS)
                if (objectoRecibido.asunto.equals("NuevaCelebridad")) {
                    server.addPaquete(objectoRecibido.contenido);

                    server.addPrincipal(this);
                    dos.writeObject(new Paquete("listo",server.Observables));
                    //notificar nueva celebridad a todos

                    //actualizamos interfaz de clientes
                }

                //Celebridad hace un nuevo post (todavia no lo enviamos a sus followers)
                if (objectoRecibido.asunto.equals("PostCelebridad")) {
                    String texto = "Nuevo post celebridad: "+objectoRecibido.informacion+"\n "+objectoRecibido.sourceAux;
                    System.out.println(texto);
                    int index = server.buscarObservable_nombre(objectoRecibido.informacion, Tipos.CELEBRIDAD);

                    CelebridadS celebridad = (CelebridadS) server.Observables.get(index);
                    Paquete paquete = new Paquete("postNuevo",texto);
                    celebridad.notifyAllObservers(paquete);
                    //dos.writeObject(new Paquete("push_aceptado",subasta));

                }

                //Nuevo usuario en la plataforma
                if (objectoRecibido.asunto.equals("NuevoUsuario")) {

                    System.out.println("Llega usuario!");
                    server.addObserver(this);
                    dos.writeObject(new Paquete("Aceptado",null));
                    dos.writeObject(new Paquete("setId",Integer.toString(id), Tipos.CELEBRIDAD));
                    dos.writeObject(new Paquete("info",server.Observables));

                }

                // Seguimos a una Celebridad
                if (objectoRecibido.asunto.equals("FollowRequest")){
                    int index = server.buscarObservable_nombre(objectoRecibido.informacion, objectoRecibido.tipo);

                    if (objectoRecibido.tipo == Tipos.CELEBRIDAD){
                        CelebridadS celebridad = (CelebridadS) server.Observables.get(index);

                        //agrega a celebridad el clientHandler de seguidor buscando en observers a ese ID del SeguidorS
                        celebridad.addObserver(server.observers.get(objectoRecibido.source));


                        Paquete msg = new Paquete("AddSocio",objectoRecibido.sourceAux, Tipos.CELEBRIDAD);
                        server.notifyPrincipal(index,msg);
                        //dos.writeObject(new Paquete("info_subasta",subasta));
                    }

                    //dos.writeObject(new Paquete("listo",server.Observables));

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



