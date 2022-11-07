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
import java.net.UnknownHostException;

public class ClienteSubasta_GUI extends JDialog {
    public  JPanel panel_cliente;
    public  JButton Btn_conectar;
    public  JComboBox comboBox1;
    public  JButton btn_Asociar;
    public  JTextField text_nick;
    final  int ServerPort = 1234;
    public static DataOutputStream dos;
    public static DataInputStream dis;

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
                    oferente = new Oferente(text_nick.getText());

                    ip = InetAddress.getByName("localhost");
                    // establish the connection
                    Socket s = new Socket(ip, ServerPort);

                    // obtaining input and out streams
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    dos = new DataOutputStream(s.getOutputStream());
                    dis = new DataInputStream(s.getInputStream());
                    Btn_conectar.setEnabled(false);

                    JSONObject jsonEnviado = new JSONObject();
                    jsonEnviado.put("asunto", "Cliente");
                    String mess = jsonEnviado.toString();
                    dos.writeUTF(mess);


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
