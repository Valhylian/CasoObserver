package Subasta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Oferente_GUI {
    private JFrame frame;
    private JTextField textField;
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
    public Oferente_GUI(ObjectInputStream dis, ObjectOutputStream dos) {
        initialize(dis, dos);
    }

    private void initialize(ObjectInputStream dis, ObjectOutputStream dos) {
        frame = new JFrame();
        frame.setBounds(100, 100, 813, 727);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        textField = new JTextField();
        textField.setBounds(21, 581, 252, 30);
        frame.getContentPane().add(textField);
        textField.setColumns(10);



    }
}


