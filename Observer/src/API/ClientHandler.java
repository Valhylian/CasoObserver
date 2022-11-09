package API;

import Subasta.Subasta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable, IObserver{
    private Socket client;
    final ObjectInputStream dis;
    final ObjectOutputStream dos;
    //Esto no debería ser un IObservable?
    public IObserver observable;
    public int id;
    public Server server;

    @Override
    public void notifyObserver(String command, Object source) {

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

                if(objectoRecibido.asunto.equals("Prueeeeba")){
                    System.out.println("llega aca");
                    dos.writeObject(new Paquete("listo",server.Observables));
                }

                if (objectoRecibido.asunto.equals("Principal")) {
                    server.addPaquete(objectoRecibido.contenido);
                    //obtener referencia del principal
                    server.addPrincipal(this);
                    dos.writeObject(new Paquete("listo",server.Observables));
                }

                if (objectoRecibido.asunto.equals("Cliente")) {
                    System.out.println("llegaaa");
                    server.addObserver(this);
                    dos.writeObject(new Paquete("info",server.Observables));
                }

                if (objectoRecibido.asunto.equals("Asociarse")){
                    int index = server.buscarObservable_nombre(objectoRecibido.informacion, objectoRecibido.tipo);
                    //Agregar cliente a subasta

                    Paquete msg = new Paquete("AddSocio","prueba");
                    server.notifyPrincipal(index,msg);
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



