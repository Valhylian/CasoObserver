package Subasta;

import API.Paquete;
import API.Server;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;

public class Subastador extends JDialog {
    private JTextField txt_Producto;
    private JTextField txt_Nick;
    private JSpinner Sp_price;
    private JButton Btn_conectarse;
    private JPanel JPanel_Subastador;
    public static ObjectOutputStream dos;
    public static ObjectInputStream dis;
    final static int ServerPort = 1234;
    public  Subasta subasta = null;

    public static void main(String[] args) throws IOException {
        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, ServerPort);

        // obtaining input and out streams
        dos = new ObjectOutputStream(s.getOutputStream());
        dis = new ObjectInputStream(s.getInputStream());
        Subastador window = new Subastador(null);


    }

    public Subastador(JFrame parent) {
        super(parent);
        setTitle("Subastador");
        setContentPane(JPanel_Subastador);
        setMinimumSize(new Dimension(480,474));
        setModal(true);
        setLocationRelativeTo(parent);

        Btn_conectarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MANDE UN MENSAJE DICIENDO LISTO
                try {
                    String producto = txt_Producto.getText();
                    String nickname = txt_Nick.getText();
                    int price = (Integer) Sp_price.getValue();

                    Producto producto1 = new Producto(producto,null);
                    subasta  = new Subasta(nickname, price, price, producto1);
                    Paquete nuevaSubasta = new Paquete("Principal",subasta);

                    dos.writeObject(nuevaSubasta);

                    Btn_conectarse.setEnabled(false);
                    dispose();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });

        setVisible(true);
    }

}
