package API;

import Subasta.Subasta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable, IObserver{
    private Socket client;
    final DataInputStream dis;
    final DataOutputStream dos;
    public IObserver observable;
    public int id;
    public Server server;

    @Override
    public void notifyObserver(String command, Object source) {

    }

    public ClientHandler(Socket client, DataInputStream dis, DataOutputStream dos, int id,
                         Server server) throws IOException {
        this.client = client;
        this.dis = dis;
        this.dos = dos;
        this.id = id;
        this.server = server;
    }

    @Override
    public void run(){
        String received;
        try {
            JSONObject jsonEnviado = new JSONObject();
            jsonEnviado.put("asunto", "setNombre");
            String mess = jsonEnviado.toString();
            this.dos.writeUTF(mess);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        while (true) {
            try {
                // receive the string
                received = dis.readUTF();
                System.out.println(received);

                if(received.equals("logout")){
                    break;
                }

                JSONParser parser = new JSONParser();
                JSONObject json;

                try {
                    json = (JSONObject) parser.parse(received);
                    Object asunto = json.get("asunto");
                    String asuntoStr = asunto.toString();

                    if (asuntoStr.equals("Pincipal")) {
                        this.id = server.addPrincipal(this);
                        System.out.println("agregado");
                    }

                    if (asuntoStr.equals("Cliente")) {
                        for (IObserver cliente : Server.observers){
                            cliente.notifyObserver("Send",this);
                        }
                        this.id = server.addObserver(this);
                        System.out.println("agregado");
                    }

                    if (asuntoStr.equals("Send")){

                    }


                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (IOException e) {

                e.printStackTrace();
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



