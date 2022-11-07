package API;

import API.ClientHandler;
import Subasta.Subasta;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<ClientHandler> clientes = new ArrayList<>();
    public static ArrayList<ClientHandler> principales = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(1234);

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
            ClientHandler mtch = new ClientHandler(s,dis, dos, 0);

            Thread t = new Thread(mtch);

            System.out.println("Adding this client to active client list");
            // add this client to active clients list
            clientes.add(mtch);
            // start the thread.
            t.start();
        }
    }

}
