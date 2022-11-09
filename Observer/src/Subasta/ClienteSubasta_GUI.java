package Subasta;

import API.Paquete;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteSubasta_GUI extends JDialog {
    public  JPanel panel_cliente;
    public  JButton Btn_conectar;
    public  JComboBox comboBox1;
    public  JButton btn_Asociar;
    public  JTextField text_nick;
    final  int ServerPort = 1234;
    public static ObjectOutputStream dos;
    public static ObjectInputStream dis;

    public static Oferente oferente;

    public static void main(String[] args) throws IOException {
        ClienteSubasta_GUI window = new ClienteSubasta_GUI(null);
    }

    public ClienteSubasta_GUI(JFrame parent) {
        super(parent);
        setTitle("Cliente Subasta");
        setContentPane(panel_cliente);
        setMinimumSize(new Dimension(480, 474));
        setModal(true);
        setLocationRelativeTo(parent);

        Btn_conectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // getting localhost ip
                InetAddress ip = null;
                try {


                    ip = InetAddress.getByName("localhost");
                    // establish the connection
                    Socket s = new Socket(ip, ServerPort);
                    oferente = new Oferente(text_nick.getText());

                    // obtaining input and out streams

                    dos = new ObjectOutputStream(s.getOutputStream());
                    dis = new ObjectInputStream(s.getInputStream());
                    Btn_conectar.setEnabled(false);

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
        });

        setVisible(true);
        btn_Asociar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


}
