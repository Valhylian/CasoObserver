package Subasta;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Subastador extends JDialog {
    private JTextField txt_Producto;
    private JTextField txt_Nick;
    private JSpinner Sp_price;
    private JButton Btn_conectarse;
    private JPanel JPanel_Subastador;
    public static DataOutputStream dos;
    final static int ServerPort = 1234;
    public  Subasta subasta = null;

    public static void main(String[] args) throws IOException {
        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, ServerPort);

        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());

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
                //MANDE UN MENSAJE DICEINDO LISTO
                try {
                    String producto = txt_Producto.getText();
                    String nickname = txt_Nick.getText();
                    int price = (Integer) Sp_price.getValue();

                    Producto producto1 = new Producto(producto,null);
                    subasta  = new Subasta(nickname, price, price, producto1);
                    JSONObject jsonEnviado = new JSONObject();
                    jsonEnviado.put("asunto", "Pincipal");
                    String mess = jsonEnviado.toString();
                    dos.writeUTF(mess);

                    Btn_conectarse.setEnabled(false);

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });

        setVisible(true);
    }

}
