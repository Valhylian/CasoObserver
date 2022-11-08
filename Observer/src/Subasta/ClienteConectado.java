package Subasta;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClienteConectado extends JDialog{
    public JComboBox comboBoxSubastas;
    private JButton accederButton;
    public JPanel panelClienteConectado;



    public ClienteConectado(JFrame parent) {
        super(parent);
        setTitle("Cliente Conectado");
        setContentPane(panelClienteConectado);
        setMinimumSize(new Dimension(480, 474));
        setModal(true);
        setLocationRelativeTo(parent);

    }

    public void actualizarScreen(ArrayList<Object> infoSubasta){
        comboBoxSubastas.setEditable(true);
        DefaultComboBoxModel favourites = new DefaultComboBoxModel();
        for (Object subastaIterator:infoSubasta){
            Subasta subasta = (Subasta) subastaIterator;
            favourites.addElement(subasta.name);
        }

        panelClienteConectado.removeAll();
        panelClienteConectado.revalidate();
        panelClienteConectado.repaint();
    }

    public void openGUI (){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClienteConectado(null).setVisible(true);
            }
        });

    }
}
