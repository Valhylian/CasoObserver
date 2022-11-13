package RedSocial;

import API.Paquete;
import API.Tipos;
import Subasta.Estado;
import Subasta.Producto;
import Subasta.Subasta;
import Subasta.SubastadorS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class Celebridad_GUI {
    private JFrame frame;
    private static JLabel nombreSubastaLabel = new JLabel("Nombre:");
    private static JLabel descripicionSubastaLabel = new JLabel("Seguidores: 0");

    private JTextField writePost = new JTextField();
    private JTextArea consola = new JTextArea();
    private JButton btnPublicar;
    private JButton btnLogOut;

    public CelebridadS celebridad;

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

    public Celebridad_GUI(ObjectInputStream dis, ObjectOutputStream dos, CelebridadS _celebridad) {
        this.celebridad = _celebridad;
        initialize();

        btnPublicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //hace un nuevo post
                    String textoPost = writePost.getText();
                    String nombrePoster = celebridad.name;
                    Post post = new Post(nombrePoster,textoPost,celebridad.postRealizados.size());
                    celebridad.postRealizados.add(post);
                    consola.append("\nPosteo #"+post.id+": "+post.texto);

                    Paquete nuevoPost = new Paquete("PostCelebridad",post);

                    dos.writeObject(nuevoPost);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String notificacion = "--------------------\n" + celebridad.name + " notifica: \nME RETIRO DE LAS REDES :(";
                    celebridad.estado = Estado.CANCELADA;
                    String name = celebridad.name;

                    Paquete msgAct = new Paquete("actualizacionCelebridad", notificacion,celebridad, Tipos.CELEBRIDAD);
                    dos.reset();
                    dos.writeObject(msgAct);

                    JOptionPane.showMessageDialog(null, "ADIOOS!", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                    btnLogOut.setEnabled(false);
                    btnPublicar.setEnabled(false);

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

        //Nombre celebridad
        nombreSubastaLabel.setBounds(20,30,80,30);
        nombreSubastaLabel.setText(celebridad.name);
        frame.getContentPane().add(nombreSubastaLabel);

        //Cantidad de seguidores
        descripicionSubastaLabel.setBounds(20,80,80,30);
        frame.getContentPane().add(descripicionSubastaLabel);

        //Edit text para escribir post
        writePost.setBounds(20,125,100,75);
        frame.getContentPane().add(writePost);

        consola  = new JTextArea();
        JScrollPane scroll2 = new JScrollPane (consola,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll2.setBounds(150,50,300,300);
        frame.getContentPane().add(scroll2);

        btnPublicar = new JButton("Publicar");
        btnPublicar.setText("Post");
        btnPublicar.setBounds(20, 300, 80,40);
        frame.getContentPane().add(btnPublicar);

        btnLogOut = new JButton("Dar de baja");
        btnLogOut.setBounds(20, 350, 150,40);
        frame.getContentPane().add(btnLogOut);
    }

    public void meterseLinea (String info){
        consola.append("\n"+info);
        frame.getContentPane().repaint();
        descripicionSubastaLabel.setText("Seguidores: "+celebridad.cantidadSubs);
    }

}