package Subasta;

import API.Paquete;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteS {

    public static void main(String[] args) throws IOException {
        System.out.printf("NICKNAME:");
        Scanner userInput = new Scanner(System.in);
        // getting localhost ip
        InetAddress ip = null;
        try {
            Oferente oferente = new Oferente("DEFAULT");

            ip = InetAddress.getByName("localhost");
            // establish the connection
            Socket s = new Socket(ip, 1234);

            // obtaining input and out streams

            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());

            ReadMessage readMessage = new ReadMessage(null,dis,dos);
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
