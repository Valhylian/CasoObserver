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
    private JButton btn_informacion;
    private JTextArea consola;
    private JTextArea infoSubasta;
    private JSpinner sp_oferta;
    private static JPanel panel_1 = new JPanel();
    Oferente oferente;


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
        this.oferente = oferente;
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

        btn_informacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String subasta = (String) comboBox.getSelectedItem();
                int index = getIndexSubasta (subasta);
                if (index != -1){
                    String info = "INFORMACION DE LA SUBASTA\n";
                    Subasta aux = oferente.generales.get(index);
                    info += "Nombre: "+aux.name;
                    info += "\nProducto: "+aux.producto.name;
                    info += "\nDescripcion: "+aux.producto.descripcion;
                    info += "\nPrecio Inicial: "+aux.producto.precioInicial;
                    info += "\nFinal Programado: "+aux.finalProgramado;
                    info += "\nTope: "+aux.lastOfert;
                    infoSubasta.setText(info);
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

        infoSubasta  = new JTextArea();
        JScrollPane scroll = new JScrollPane (infoSubasta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(21,100,300,300);
        frame.getContentPane().add(scroll);

        consola  = new JTextArea();
        JScrollPane scroll2 = new JScrollPane (consola,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll2.setBounds(400,100,300,300);
        frame.getContentPane().add(scroll2);

        comboBox = new JComboBox();
        comboBox.setBounds(21,20,252,30);
        frame.getContentPane().add(comboBox);



        btn_asociar = new JButton("ASOCIAR");
        btn_asociar.setBounds(21, 60, 150,30);
        frame.getContentPane().add(btn_asociar);

        btn_informacion = new JButton("INFORMACION");
        btn_informacion.setBounds(171, 60, 150,30);
        frame.getContentPane().add(btn_informacion);


        comboBoxAsociadas = new JComboBox();
        comboBoxAsociadas.setBounds(21,430,252,30);
        frame.getContentPane().add(comboBoxAsociadas);

        sp_oferta = new JSpinner();
        sp_oferta.setBounds(21,470,252,30);
        frame.getContentPane().add(sp_oferta);

        btn_ofetar = new JButton("OFERTAR");
        btn_ofetar.setBounds(21, 510, 150,30);
        frame.getContentPane().add(btn_ofetar);

    }

    public void actInterfaz(ArrayList<Object> info){
        actualizarGenerales(info);
        comboBox.removeAllItems();
        for (Object subasta: info){
            Subasta subastaAux = (Subasta) subasta;
            //oferente.generales.add(subastaAux);
            if (subastaAux.estado == Estado.DEFAULT){
                comboBox.addItem(subastaAux.name);
            }

        }
        frame.getContentPane().repaint();
    }

    public void actualizarGenerales (ArrayList<Object> info){
        oferente.generales = new ArrayList<>();
        for (Object subasta: info){
            Subasta subastaAux = (Subasta) subasta;
            oferente.generales.add(subastaAux);
        }
    }

    public void actSubscritas (Subasta info){
        comboBoxAsociadas.addItem(info.name);
        frame.getContentPane().repaint();
    }

    public void meterseLinea (String info){

        consola.append("\n"+info);
        frame.getContentPane().repaint();
    }

    public int getIndexSubasta (String name){
        for(int i=0; i<oferente.generales.size(); i++) {
            Subasta subasta = oferente.generales.get(i);
            if (subasta.name.equals(name)){
                return i;
            }
        };
        return -1;
    }
}

