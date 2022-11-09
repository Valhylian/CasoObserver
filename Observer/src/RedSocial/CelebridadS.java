package RedSocial;

import API.Paquete;
//import Subasta.Subasta; tal vez import feedCelebridad ? con los msjs de ese feed?

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class CelebridadS {

    final static int ServerPort = 1234;

    //public Subasta subasta = null;
    public String name;

    public CelebridadS(String name) {
        this.name = name;

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

            CelebridadS celebridad = new CelebridadS(nickname);

            // obtaining input and out streams
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());

            ReadMessage_Celebridad readMessageCelebridad = new ReadMessage_Celebridad(dis, dos, celebridad);

            readMessageCelebridad.start();

            Paquete paqueteEnviado = new Paquete("Prueeeeba", null);
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
