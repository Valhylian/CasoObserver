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
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Subastador_GUI {
    private JFrame frame;
    private static JLabel nombreSubastaLabel = new JLabel("Nombre:");
    private static JLabel precioSubastaLabel = new JLabel("Precio:");
    private static JLabel finalSubastaLabel = new JLabel("Final:");
    private static JLabel descripicionSubastaLabel = new JLabel("Descripción:");
    private static JLabel idAceptarSubastaLabel = new JLabel("Id de oferta por aceptar:");
    private JTextField textFieldNombreSubasta;
    private JTextField textFieldMensaje;
    private JTextField textFieldDescripcionSubasta;
    private JSpinner spinnerPrecioSubasta;
    private JSpinner spinnerFinalSubasta;
    private JSpinner spinnerIdAceptarOferta;
    private JTextArea consola;
    private JButton btnAceptarOferta;
    private JButton btnCrearOferta;
    private JButton btnCancelar;
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
                    int finalPrice = (Integer) spinnerFinalSubasta.getValue();

                    Producto producto1 = new Producto(nombreProducto, descripcionProducto, null, price, finalPrice);
                    subastador.subasta = new Subasta(nombreProducto, price, producto1);
                    Paquete nuevaSubasta = new Paquete("Principal", subastador.subasta);

                    dos.writeObject(nuevaSubasta);

                    btnCrearOferta.setEnabled(false);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });

        btnAceptarOferta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MANDE UN MENSAJE DICIENDO LISTO
                try {
                    int indexOferta = (Integer) spinnerIdAceptarOferta.getValue();
                    if (subastador.subasta.ofertas.get(indexOferta) != null) {
                        Oferta oferta = subastador.subasta.ofertas.get(indexOferta);
                        if (oferta.estado == Estado.DEFAULT) {

                            oferta.estado = Estado.APROBADA;
                            subastador.subasta.lastOfert = oferta.dineroOfertado; //actualiza el tope de la oferta
                            subastador.subasta.indexGanador = oferta.index_ofertante;
                            System.out.printf("Index ofertante " + oferta.index_ofertante);
                            //mostrar aprobacion en pantalla
                            String info = "\nOferta ACEPTADA: " + oferta.indexOferta + " Cliente: " + oferta.name_ofertante + " Nuevo tope: " + subastador.subasta.lastOfert;
                            meterseLinea(info);
                            //notificar a todos los subscritos a la suubasta
                            String notificacion = "--------------------\n" + subastador.subasta.name + " notifica: ";
                            notificacion += info;
                            //actualizar Subasta en el server
                            Subasta prueba = new Subasta(subastador.subasta.name, oferta.dineroOfertado, subastador.subasta.producto, oferta.index_ofertante, Estado.DEFAULT);
                            Paquete actualizacion = new Paquete("actualizacion", notificacion, prueba, Tipos.SUBASTA);
                            dos.writeObject(actualizacion);
                        }
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        terminarSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //FINALIZAR SUBASTA
                try {
                    String msgFelicitacion = textFieldMensaje.getText();
                    int ganador = subastador.subasta.indexGanador;
                    if (ganador != -1){
                        String msgFelicidades = textFieldMensaje.getText();
                        String notificacion = "--------------------\n" + subastador.subasta.name + " notifica: ";
                        String info = "\nSUBASTA TERMINADA CON TOPE: "+subastador.subasta.lastOfert;
                        notificacion += info;
                        subastador.subasta.estado = Estado.TERMINADA;
                        Subasta actSub = new Subasta(subastador.subasta.name, subastador.subasta.lastOfert, subastador.subasta.producto, subastador.subasta.indexGanador, Estado.TERMINADA);
                        Paquete msgAct = new Paquete("terminada", notificacion,msgFelicidades,actSub, Tipos.SUBASTA);
                        dos.writeObject(msgAct);
                    }else{
                        System.out.println("NO OFERTARON");
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //FINALIZAR SUBASTA
                try {
                    String notificacion = "--------------------\n" + subastador.subasta.name + " notifica: \nSUBASTA CANCELADA";
                    subastador.subasta.estado = Estado.CANCELADA;
                    Subasta actSub = new Subasta(subastador.subasta.name, subastador.subasta.lastOfert, subastador.subasta.producto, subastador.subasta.indexGanador, Estado.CANCELADA);
                    Paquete msgAct = new Paquete("actualizacion", notificacion,actSub, Tipos.SUBASTA);
                    dos.writeObject(msgAct);

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

        nombreSubastaLabel.setBounds(20, 30, 80, 30);
        frame.getContentPane().add(nombreSubastaLabel);
        descripicionSubastaLabel.setBounds(20, 60, 80, 30);
        frame.getContentPane().add(descripicionSubastaLabel);
        precioSubastaLabel.setBounds(20, 90, 80, 30);
        frame.getContentPane().add(precioSubastaLabel);
        textFieldNombreSubasta = new JTextField();
        textFieldNombreSubasta.setBounds(100, 35, 120, 20);
        frame.getContentPane().add(textFieldNombreSubasta);
        textFieldDescripcionSubasta = new JTextField();
        textFieldDescripcionSubasta.setBounds(100, 65, 120, 20);
        frame.getContentPane().add(textFieldDescripcionSubasta);

        spinnerPrecioSubasta = new JSpinner();
        spinnerPrecioSubasta.setBounds(100, 95, 120, 20);
        frame.getContentPane().add(spinnerPrecioSubasta);

        btnCrearOferta = new JButton("Crear Subasta");
        btnCrearOferta.setBounds(30, 145, 130, 20);
        frame.getContentPane().add(btnCrearOferta);

        finalSubastaLabel.setBounds(20, 120, 130, 20);
        frame.getContentPane().add(finalSubastaLabel);

        spinnerFinalSubasta = new JSpinner();
        spinnerFinalSubasta.setBounds(100, 120, 120, 20);
        frame.getContentPane().add(spinnerFinalSubasta);

        consola = new JTextArea();
        JScrollPane scroll = new JScrollPane(consola, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(21, 200, 300, 300);
        frame.getContentPane().add(scroll);

        idAceptarSubastaLabel.setBounds(30, 510, 200, 30);
        frame.getContentPane().add(idAceptarSubastaLabel);

        spinnerIdAceptarOferta = new JSpinner();
        spinnerIdAceptarOferta.setBounds(200, 515, 130, 20);
        frame.getContentPane().add(spinnerIdAceptarOferta);

        btnAceptarOferta = new JButton("Aceptar Oferta");
        btnAceptarOferta.setBounds(30, 550, 150, 30);
        frame.getContentPane().add(btnAceptarOferta);

        textFieldMensaje = new JTextField("Felicidades broo");
        textFieldMensaje.setBounds(200, 550, 200, 20);
        frame.getContentPane().add(textFieldMensaje);

        terminarSubasta = new JButton("Finalizar subasta");
        terminarSubasta.setBounds(200, 580, 150, 30);
        frame.getContentPane().add(terminarSubasta);

        btnCancelar = new JButton("Cancelar subasta");
        btnCancelar.setBounds(30, 580, 150, 30);
        frame.getContentPane().add(btnCancelar);
    }

    public void meterseLinea(String info) {

        consola.append("\n" + info);
        frame.getContentPane().repaint();
    }

}
