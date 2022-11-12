package API;

import API.ClientHandler;
import RedSocial.CelebridadS;
import Subasta.Subasta;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends AbstractObservable{
    private static Server server;
    private final int PORT = 1234;
    private static ServerSocket ss;
    public ArrayList<Object> Observables = new ArrayList<>();

    private Server() throws IOException {
        super();
        this.ss = new ServerSocket(PORT);
    }

    public static Server getInstance() throws IOException {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public void addPaquete(Object nuevoPaquete){
        server.Observables.add(nuevoPaquete);
    }


    public static void main(String[] args) throws IOException {

        // server is listening on port 1234
        Server server = getInstance();

        Socket s;

        // running infinite loop for getting
        // client request
        while (true) {
            // Accept the incoming request
            s = ss.accept();

            System.out.println("New client request received : " + s);
            // obtain input and output streams
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());

            System.out.println("Creating a new handler for this client...");
            // Create a new handler object for handling this request.
            ClientHandler mtch = new ClientHandler(s,dis, dos, server.observers.size());

            Thread t = new Thread(mtch);

            t.start();
        }
    }

    public int buscarObservable_nombre(String nombre, Tipos tipo){
        System.out.println("llega el nombre "+nombre);
        if(tipo == Tipos.SUBASTA){
            for(int i=0; i<Observables.size(); i++) {
                Subasta subasta = (Subasta) Observables.get(i);
                String name = subasta.name;
                if (name.equals(nombre)){
                    System.out.println("entra");
                    return i;
                }
             }
        }
        else if(tipo == Tipos.CELEBRIDAD){
            for(int i=0; i<Observables.size(); i++) {
                CelebridadS celebridad = (CelebridadS) Observables.get(i);
                if (celebridad.name.equals(nombre)){
                    return i;
                }
            }
        }

        return 0;
    }

}
