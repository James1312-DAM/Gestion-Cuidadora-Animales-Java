package vista;

import controlador.ControladorApp;
import modelo.POJO.Adopcion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelGestionarAdopciones {

    private JPanel panelGestionarAdopciones;
    private JLabel listaLabel;
    private JButton aprobarButton;
    private JButton rechazarButton;
    private JButton volverButton;
    private JComboBox idComboBox;

    public PanelGestionarAdopciones(ControladorApp controlador, String rolUsuario) {

        ArrayList<Adopcion> lista = controlador.verTodasAdopciones();
        StringBuilder sb = new StringBuilder("<html>");

        if (lista.isEmpty()) {
            sb.append("No hay adopciones pendientes.");
        } else {
            for (Adopcion a : lista) {
                if (a.getEstado().equals("PENDIENTE")) {
                    idComboBox.addItem(a.getId());
                    sb.append("ID: ").append(a.getId())
                            .append(" | Usuario: ").append(a.getUsuario().getNombreUsuario())
                            .append(" | Animal: ").append(a.getAnimal().getNombreAnimal())
                            .append(" | Estado: ").append(a.getEstado())
                            .append("<br>");
                }
            }
        }
        sb.append("</html>");
        listaLabel.setText(sb.toString());

        aprobarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null,
                            "No hay adopciones pendientes.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idAdopcion = (int) idComboBox.getSelectedItem();
                controlador.gestionarAdopcion(idAdopcion, true);
                controlador.cambiarVista(new PanelGestionarAdopciones(controlador, rolUsuario).getPanelGestionarAdopciones());
            }
        });

        rechazarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null,
                            "No hay adopciones pendientes.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idAdopcion = (int) idComboBox.getSelectedItem();
                controlador.gestionarAdopcion(idAdopcion, false);
                controlador.cambiarVista(new PanelGestionarAdopciones(controlador, rolUsuario).getPanelGestionarAdopciones());
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rolUsuario.equals("empleado")) {
                    controlador.cambiarVista(new PanelEmpleado(controlador,
                            controlador.getNombreUsuarioActivo()).getPanelEmpleado());
                } else {
                    controlador.cambiarVista(new PanelAdmin(controlador,
                            controlador.getNombreUsuarioActivo()).getPanelAdmin());
                }
            }
        });
    }

    public JPanel getPanelGestionarAdopciones() {
        return panelGestionarAdopciones;
    }
}