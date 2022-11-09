package Subasta;

import API.Paquete;
import Subasta.Subasta;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SubastadorS {

    final static int ServerPort = 1234;

    public Subasta subasta = null;
    private String name;

    public SubastadorS(String name) {
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
            SubastadorS subastador = new SubastadorS(nickname);
            // obtaining input and out streams
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());

            ReadMessage_Subastador readMessageSubastador = new ReadMessage_Subastador(dis, dos, subastador);
            readMessageSubastador.start();

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
