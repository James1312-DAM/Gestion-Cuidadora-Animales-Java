package vista;

import controlador.ControladorApp;
import javax.swing.*;

public class Ventana extends JFrame {

    public Ventana(ControladorApp controlador) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setTitle("Protectora de Animales");
        setContentPane(new PanelLogin(controlador).getPanelLogin());
        setLocationRelativeTo(null);
        setVisible(true);
    }
}