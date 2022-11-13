package RedSocial;

import API.AbstractObservable;
import API.ClientHandler;
import API.Paquete;
import Subasta.Estado;


import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class CelebridadS extends AbstractObservable implements Serializable {

    final static int ServerPort = 1234;

    //public Subasta subasta = null;
    public String name;
    public int nivel;
    public Estado estado;
    public int cantidadSubs = 0;
    public ArrayList<Post> postRealizados = new ArrayList<>();

    public CelebridadS(String name,int nivel) {
        this.name = name;
        this.nivel = nivel;
        this.estado = Estado.DEFAULT;
    }

    public static void main(String[] args) throws IOException {
        System.out.printf("NICKNAME:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String nickname = br.readLine();

        // getting localhost ip
        InetAddress ip = null;

        try {
            ip = InetAddress.getByName("localhost");
            // establish the connection
            Socket s = new Socket(ip, 1234);

            CelebridadS celebridad = new CelebridadS(nickname,0);

            // obtaining input and out streams
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());

            ReadMessage_Celebridad readMessageCelebridad = new ReadMessage_Celebridad(dis, dos, celebridad);

            readMessageCelebridad.start();

            Paquete paqueteEnviado = new Paquete("NuevaCelebridad",celebridad);
            dos.writeObject(paqueteEnviado);

        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Server inactivo", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server inactivo", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(ex);
        }

    }


}
