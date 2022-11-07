package API;

import API.ClientHandler;
import Subasta.Subasta;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends AbstractObservable{
    private static Server server;
    private final int PORT = 1234;
    private static ServerSocket ss;
    private static ArrayList<Object> auxiliar = new ArrayList<>();

    private Server() throws IOException {
        this.ss = new ServerSocket(PORT);
    }

    public static Server getInstance() throws IOException {
        if (server == null) {
            server = new Server();
        }
        return server;
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
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Creating a new handler for this client...");

            // Create a new handler object for handling this request.
            ClientHandler mtch = new ClientHandler(s,dis, dos, 0,server);

            Thread t = new Thread(mtch);

            System.out.println("Adding this client to active client list");
            // add this client to active clients list
            server.addObserver(mtch);
            // start the thread.
            t.start();
        }
    }

}
