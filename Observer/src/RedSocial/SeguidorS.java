package RedSocial;

import API.Paquete;
import Subasta.Oferente;
import Subasta.ReadMessage;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class SeguidorS {
    public String nombre;
    SeguidorS(String name){
        this.nombre = name;
    }
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
            SeguidorS seguidor = new SeguidorS(nickname);
            // obtaining input and out streams
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());

            ReadMessage_Seguidor readMessage = new ReadMessage_Seguidor(seguidor,dis,dos);
            readMessage.start();

            Paquete paqueteEnviado = new Paquete("NuevoUsuario",null);
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