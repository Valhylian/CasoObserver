package Subasta;

import API.Paquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Subastador_GUI {
    private JFrame frame;
    private static JLabel nombreSubastaLabel = new JLabel("Nombre:");
    private static JLabel precioSubastaLabel = new JLabel("Precio:");
    private static JLabel descripicionSubastaLabel = new JLabel("Descripción:");
    private static JLabel idAceptarSubastaLabel = new JLabel("Id de oferta por aceptar:");
    private JTextField textFieldNombreSubasta;
    private JTextField textFieldDescripcionSubasta;
    private JSpinner spinnerPrecioSubasta;
    private JSpinner spinnerIdAceptarOferta;
    private JTextArea consola;
    private JButton btnAceptarOferta;
    private JButton btnCrearOferta;
    private JButton terminarSubasta;
    private static JLabel labelOfertasEntrantes = new JLabel("Ofertas entrantes");

    public SubastadorS subastador;




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

    public Subastador_GUI(ObjectInputStream dis, ObjectOutputStream dos, SubastadorS subastador) {
        initialize();

        btnCrearOferta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MANDE UN MENSAJE DICIENDO LISTO
                try {
                    String descripcionProducto = textFieldDescripcionSubasta.getText();
                    String nombreProducto = textFieldNombreSubasta.getText();
                    int price = (Integer) spinnerPrecioSubasta.getValue();

                    Producto producto1 = new Producto(nombreProducto,descripcionProducto,null);
                    subastador.subasta  = new Subasta(nombreProducto, price, price, producto1);
                    Paquete nuevaSubasta = new Paquete("Principal",subastador.subasta);

                    dos.writeObject(nuevaSubasta);

                    btnCrearOferta.setEnabled(false);
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

        nombreSubastaLabel.setBounds(20,30,80,30);
        frame.getContentPane().add(nombreSubastaLabel);
        descripicionSubastaLabel.setBounds(20,60,80,30);
        frame.getContentPane().add(descripicionSubastaLabel);
        precioSubastaLabel.setBounds(20,90,80,30);
        frame.getContentPane().add(precioSubastaLabel);
        textFieldNombreSubasta = new JTextField();
        textFieldNombreSubasta.setBounds(100,35,120,20);
        frame.getContentPane().add(textFieldNombreSubasta);
        textFieldDescripcionSubasta = new JTextField();
        textFieldDescripcionSubasta.setBounds(100,65,120,20);
        frame.getContentPane().add(textFieldDescripcionSubasta);
        spinnerPrecioSubasta = new JSpinner();
        spinnerPrecioSubasta.setBounds(100,95,120,20);
        frame.getContentPane().add(spinnerPrecioSubasta);
        btnCrearOferta = new JButton("Crear Oferta");
        btnCrearOferta.setBounds(30,125,130,20);
        frame.getContentPane().add(btnCrearOferta);

        consola  = new JTextArea();
        JScrollPane scroll = new JScrollPane (consola,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(21,200,300,300);
        frame.getContentPane().add(scroll);

        idAceptarSubastaLabel.setBounds(30,510,200,30);
        frame.getContentPane().add(idAceptarSubastaLabel);

        spinnerIdAceptarOferta = new JSpinner();
        spinnerIdAceptarOferta.setBounds(200,515,130,20);
        frame.getContentPane().add(spinnerIdAceptarOferta);


        btnAceptarOferta = new JButton("Aceptar oferta");
        btnAceptarOferta.setBounds(30, 550, 150,30);
        frame.getContentPane().add(btnAceptarOferta);
        terminarSubasta = new JButton("Finalizar subasta");
        terminarSubasta.setBounds(200, 550, 150,30);
        frame.getContentPane().add(terminarSubasta);
    }



}
