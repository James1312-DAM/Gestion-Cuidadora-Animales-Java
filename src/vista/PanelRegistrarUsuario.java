package vista;

import controlador.ControladorApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRegistrarUsuario {

    private JPanel panelRegistrarUsuario;
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private JPasswordField repetirContrasenaField;
    private JTextField correoField;
    private JTextField telefonoField;
    private JButton enviarButton;
    private JButton volverButton;

    public PanelRegistrarUsuario(ControladorApp controlador) {

        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = usuarioField.getText();
                String contrasena = new String(contrasenaField.getPassword());
                String repetirContrasena = new String(repetirContrasenaField.getPassword());
                String correo = correoField.getText();
                String telefono = telefonoField.getText();

                if (nombre.isEmpty() || contrasena.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
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

                try {
                    controlador.registrarAdoptante(nombre, contrasena, correo, telefono);
                    controlador.cambiarVista(new PanelLogin(controlador).getPanelLogin());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cambiarVista(new PanelLogin(controlador).getPanelLogin());
            }
        });
    }

    public JPanel getPanelRegistrarUsuario() {
        return panelRegistrarUsuario;
    }
}