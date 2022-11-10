package API;

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
                Paquete objectoRecibido = (Paquete) dis.readObject();
                System.out.println(objectoRecibido);

                if(objectoRecibido.asunto.equals("logout")){
                    break;
                }
                //Llega un nuevo cliente (SubastadorS / CelebridadS)
                if(objectoRecibido.asunto.equals("Prueeeeba")){
                    System.out.println("llega aca");
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

                //Celebridad hace un nuevo post (todavia no lo enviamos a sus followers)
                if (objectoRecibido.asunto.equals("PostCelebridad")) {
                    System.out.println("Nuevo post celebridad: "+objectoRecibido.informacion+"\n "+objectoRecibido.sourceAux);
                    //
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



