package vista;

import controlador.ControladorApp;
import modelo.POJO.Animal;
import modelo.POJO.Adopcion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelAdoptante {

    private JButton verAnimalesButton;
    private JButton cerrarSesionButton;
    private JButton verMisAdopcionesButton;
    private JButton solicitarAdopcionButton;
    private JPanel panelAdoptante;

    public PanelAdoptante(ControladorApp controlador, String nombreUsuario) {

        verAnimalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Animal> lista = controlador.verAnimalesDisponibles();
                controlador.cambiarVista(new PanelListaAnimales(controlador, lista, "adoptante").getPanelListaAnimales());
            }
        });

        solicitarAdopcionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog(null, "Introduce el ID del animal:");
                if (idStr == null) return;
                try {
                    int idAnimal = Integer.parseInt(idStr);
                    controlador.solicitarAdopcion(nombreUsuario, idAnimal);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "El ID debe ser un numero.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        verMisAdopcionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Adopcion> lista = controlador.verMisAdopciones(nombreUsuario);
                controlador.cambiarVista(new PanelListaAdopciones(controlador, lista, "adoptante").getPanelListaAdopciones());
            }
        });

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cambiarVista(new PanelLogin(controlador).getPanelLogin());
            }
        });
    }

    public JPanel getPanelAdoptante() {
        return panelAdoptante;
    }
}