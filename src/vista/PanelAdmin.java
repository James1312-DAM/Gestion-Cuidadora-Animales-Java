package vista;

import controlador.ControladorApp;
import modelo.POJO.Animal;
import modelo.POJO.Adopcion;
import modelo.POJO.Adoptante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelAdmin {

    private JButton verAnimalesButton;
    private JButton añadirAnimalButton;
    private JButton eliminarAnimalButton;
    private JButton verAdopcionesButton;
    private JButton verAdoptantesButton;
    private JButton crearEmpleadoButton;
    private JButton cerrarSesionButton;
    private JPanel panelAdmin;

    public PanelAdmin(ControladorApp controlador, String nombreUsuario) {

        verAnimalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Animal> lista = controlador.verTodosAnimales();
                 controlador.cambiarVista(new PanelListaAnimales(controlador, lista, "admin").getPanelListaAnimales());
            }
        });

        añadirAnimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cambiarVista(new PanelRegistrarAnimal(controlador, "admin").getPanelRegistrarAnimal());
            }
        });

        eliminarAnimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog(null, "Introduce el ID del animal a eliminar:");
                if (idStr == null) return;
                try {
                    int idAnimal = Integer.parseInt(idStr);
                    int confirmacion = JOptionPane.showConfirmDialog(null,
                            "¿Seguro que quieres eliminar el animal con ID " + idAnimal + "?",
                            "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirmacion == 0) {
                        controlador.eliminarAnimal(idAnimal);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "El ID debe ser un numero.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        verAdopcionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Adopcion> lista = controlador.verTodasAdopciones();
                 controlador.cambiarVista(new PanelListaAdopciones(controlador, lista, "admin").getPanelListaAdopciones());
            }
        });

        verAdoptantesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Adoptante> lista = controlador.verTodosAdoptantes();
                 controlador.cambiarVista(new PanelListaAdoptantes(controlador, lista).getPanelListaAdoptantes());
            }
        });

        crearEmpleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cambiarVista(new PanelRegistrarEmpleado(controlador).getPanelRegistrarEmpleado());
            }
        });

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cambiarVista(new PanelLogin(controlador).getPanelLogin());
            }
        });
    }

    public JPanel getPanelAdmin() {
        return panelAdmin;
    }
}