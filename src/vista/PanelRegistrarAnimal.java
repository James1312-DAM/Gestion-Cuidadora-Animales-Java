package vista;

import controlador.ControladorApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRegistrarAnimal {

    private JPanel panelRegistrarAnimal;
    private JTextField nombreField;
    private JTextField razaField;
    private JTextField descripcionField;
    private JComboBox tipoAnimalCombo;
    private JButton registrarButton;
    private JButton volverButton;
    private JCheckBox perroCheckBox;
    private JCheckBox gatoCheckBox;

    public PanelRegistrarAnimal(ControladorApp controlador, String rolUsuario) {

        tipoAnimalCombo.addItem("Perro");
        tipoAnimalCombo.addItem("Gato");

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                String raza = razaField.getText();
                String descripcion = descripcionField.getText();
                int tipoAnimal = tipoAnimalCombo.getSelectedIndex() + 1;
                boolean convive = perroCheckBox.isSelected() || gatoCheckBox.isSelected();

                if (nombre.isEmpty() || raza.isEmpty() || descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Todos los campos son obligatorios.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                controlador.añadirAnimal(tipoAnimal, nombre, raza, descripcion, convive);

                if (rolUsuario.equals("empleado")) {
                    controlador.cambiarVista(new PanelEmpleado(controlador,
                            controlador.getNombreUsuarioActivo()).getPanelEmpleado());
                } else {
                    controlador.cambiarVista(new PanelAdmin(controlador,
                            controlador.getNombreUsuarioActivo()).getPanelAdmin());
                }
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

    public JPanel getPanelRegistrarAnimal() {
        return panelRegistrarAnimal;
    }
}