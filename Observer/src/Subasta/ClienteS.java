package Subasta;

import API.Paquete;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteS {

    public static void main(String[] args) throws IOException {
        System.out.printf("NICKNAME:");
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        String nickname = br.readLine();

        // getting localhost ip
        InetAddress ip = null;
        try {

            ip = InetAddress.getByName("localhost");
            // establish the connection
            Socket s = new Socket(ip, 1234);
            Oferente oferente = new Oferente(nickname);
            // obtaining input and out streams
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());

            ReadMessage readMessage = new ReadMessage(oferente,dis,dos);
            readMessage.start();

            Paquete paqueteEnviado = new Paquete("Cliente",null);
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
