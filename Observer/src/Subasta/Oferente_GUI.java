package Subasta;

import API.Paquete;
import API.Tipos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Oferente_GUI {
    private JFrame frame;
    private JTextField textField;
    private JComboBox comboBox;
    private JComboBox comboBoxAsociadas;
    private JButton btn_ofetar;
    private JButton btn_asociar;
    private JTextArea consola;
    private JSpinner sp_oferta;
    private static JPanel panel_1 = new JPanel();


    private static JLabel titulo = new JLabel("Jugador...");
    JTextArea txtrTurnoDelJugador = new JTextArea();
    JPanel panel;

    /**
     * Launch the application.
     */



    public void init() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //PantallaSecundaria window = new PantallaSecundaria(jugador);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Oferente_GUI(ObjectInputStream dis, ObjectOutputStream dos,  Oferente oferente) {
        initialize();
        btn_asociar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //ENVIAR ASOCIARSE A X SUBASTA
                try {
                    String nombreSubasta = (String) comboBox.getSelectedItem();
                    Paquete msg = new Paquete("Asociarse",nombreSubasta, Tipos.SUBASTA, oferente.id,oferente.nickname);
                    dos.writeObject(msg);


                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        btn_ofetar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //pujar a subasta
                try {
                    String nombreSubasta = (String) comboBoxAsociadas.getSelectedItem();
                    int precioPush = (Integer) sp_oferta.getValue();
                    Paquete msg = new Paquete("push_subasta",nombreSubasta,oferente.id,oferente.nickname,precioPush, API.Tipos.SUBASTA);
                    dos.writeObject(msg);


                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 813, 727);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        consola  = new JTextArea();
        JScrollPane scroll = new JScrollPane (consola,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(21,100,300,300);
        frame.getContentPane().add(scroll);


        comboBox = new JComboBox();
        comboBox.setBounds(21,20,252,30);
        frame.getContentPane().add(comboBox);

        btn_ofetar = new JButton("OFERTAR");
        btn_ofetar.setBounds(500, 290, 150,30);
        frame.getContentPane().add(btn_ofetar);

        btn_asociar = new JButton("ASOCIAR");
        btn_asociar.setBounds(21, 60, 150,30);
        frame.getContentPane().add(btn_asociar);



        comboBoxAsociadas = new JComboBox();
        comboBoxAsociadas.setBounds(500,200,252,30);
        frame.getContentPane().add(comboBoxAsociadas);

        sp_oferta = new JSpinner();
        sp_oferta.setBounds(500,250,252,30);
        frame.getContentPane().add(sp_oferta);

    }

    public void actInterfaz(ArrayList<Object> info){
   
        for (Object subasta: info){
            Subasta subastaAux = (Subasta) subasta;
            comboBox.addItem(subastaAux.name);
        }
        frame.getContentPane().repaint();
    }



    public void actSubscritas (Subasta info){

        comboBoxAsociadas.addItem(info.name);
        frame.getContentPane().repaint();
    }

    public void meterseLinea (String info){

        consola.append("\n"+info);
        frame.getContentPane().repaint();
    }
}

