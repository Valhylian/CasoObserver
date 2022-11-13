package RedSocial;

import API.Paquete;
import API.Tipos;
import Subasta.Estado;
import Subasta.Subasta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Seguidor_GUI {
    private JFrame frame;
    private JTextField textField;
    private JComboBox comboBoxCelebridades;
    private JComboBox comboBoxAsociadas;
    private JButton btn_ofetar;
    private JButton btn_asociar;
    private JTextArea consola;
    private JSpinner spinnerNumero;
    SeguidorS seguidor;

    private static JPanel panel_1 = new JPanel();
    private static JLabel titulo = new JLabel("Jugador...");
    JTextArea txtrTurnoDelJugador = new JTextArea();
    JPanel panel;





    public void init() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Seguidor_GUI(ObjectInputStream dis, ObjectOutputStream dos, SeguidorS seguidor) {
        this.seguidor = seguidor;
        initialize();
        btn_asociar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //ENVIAR ASOCIARSE A CELEBRIDAD
                String nombreCelebridad = (String) comboBoxCelebridades.getSelectedItem();
                for (CelebridadS celebridad : seguidor.subscritas) {
                    if (celebridad.name.equals(nombreCelebridad)) {
                        JOptionPane.showMessageDialog(null, "Ya está subscrito a esa celebridad", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                try {
                    Paquete msg = new Paquete("FollowRequest",nombreCelebridad, Tipos.CELEBRIDAD,seguidor.id,seguidor.nombre);
                    dos.writeObject(msg);


                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        btn_ofetar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreCelebridad = (String) comboBoxAsociadas.getSelectedItem();
                for (CelebridadS celebridad : seguidor.generales) {
                    if (celebridad.name.equals(nombreCelebridad) && celebridad.estado!=Estado.DEFAULT) {
                        JOptionPane.showMessageDialog(null, "La celebridad ya se retiró", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                try {
                    int numPost = (Integer) spinnerNumero.getValue();
                    Paquete msg = new Paquete("like",nombreCelebridad,seguidor.id,seguidor.nombre,numPost, Tipos.CELEBRIDAD);
                    dos.writeObject(msg);
                    JOptionPane.showMessageDialog(null, "Like!", "InfoBox", JOptionPane.INFORMATION_MESSAGE);

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


        comboBoxCelebridades = new JComboBox();
        comboBoxCelebridades.setBounds(21,20,252,30);
        frame.getContentPane().add(comboBoxCelebridades);

        btn_ofetar = new JButton("DAR LIKE");
        btn_ofetar.setBounds(500, 290, 150,30);
        frame.getContentPane().add(btn_ofetar);

        btn_asociar = new JButton("Follow");
        btn_asociar.setBounds(21, 60, 150,30);
        frame.getContentPane().add(btn_asociar);

        comboBoxAsociadas = new JComboBox();
        comboBoxAsociadas.setBounds(500,200,252,30);
        frame.getContentPane().add(comboBoxAsociadas);

        spinnerNumero = new JSpinner();
        spinnerNumero.setBounds(500, 240, 120, 20);
        frame.getContentPane().add(spinnerNumero);



    }

    public void actInterfaz(ArrayList<Object> info){
        actualizarGenerales(info);
        comboBoxCelebridades.removeAllItems();
        for (Object celebridad: info){
            CelebridadS celebridadS = (CelebridadS) celebridad;
            if (celebridadS.estado == Estado.DEFAULT) {
                comboBoxCelebridades.addItem(celebridadS.name);
            }

        }
        frame.getContentPane().repaint();
    }

    public void actualizarGenerales(ArrayList<Object> info) {
        seguidor.generales = new ArrayList<>();
        for (Object celebridad : info) {
            CelebridadS celebridadS = (CelebridadS) celebridad;
            seguidor.generales.add(celebridadS);
        }
    }

    public void actSubscritas (CelebridadS info){

        comboBoxAsociadas.addItem(info.name);
        frame.getContentPane().repaint();
    }

    public void meterseLinea (String info){

        consola.append("\n"+info);
        frame.getContentPane().repaint();
    }
}
