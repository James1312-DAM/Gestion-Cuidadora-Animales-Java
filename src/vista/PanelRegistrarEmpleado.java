package vista;

import controlador.ControladorApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRegistrarEmpleado {

    private JButton registrarButton;
    private JButton volverButton;
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private JPasswordField repetirContrasenaField;
    private JTextField correoField;
    private JTextField telefonoField;
    private JSpinner salarioSpinner;
    private JPanel panelRegistrarEmpleado;

    public PanelRegistrarEmpleado(ControladorApp controlador) {

        salarioSpinner.setModel(new SpinnerNumberModel(1000, 0, 999999, 100));

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText();
                String contrasena = new String(contrasenaField.getPassword());
                String repetirContrasena = new String(repetirContrasenaField.getPassword());
                String correo = correoField.getText();
                String telefono = telefonoField.getText();
                double salario = ((Number) salarioSpinner.getValue()).doubleValue();

                if (usuario.isEmpty() || contrasena.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Todos los campos son obligatorios.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!contrasena.equals(repetirContrasena)) {
                    JOptionPane.showMessageDialog(null,
                            "Las contrasenas no coinciden.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                controlador.crearEmpleado(usuario, contrasena, correo, telefono, salario);
                controlador.cambiarVista(new PanelAdmin(controlador,
                        controlador.getNombreUsuarioActivo()).getPanelAdmin());
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cambiarVista(new PanelAdmin(controlador,
                        controlador.getNombreUsuarioActivo()).getPanelAdmin());
            }
        });
    }

    public JPanel getPanelRegistrarEmpleado() {
        return panelRegistrarEmpleado;
    }
}