package vista;

import controlador.ControladorApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelLogin {

    private JButton iniciarSesionButton;
    private JButton registrarseButton;
    private JButton salirButton;
    private JPasswordField contrasenaField;
    private JTextField usuarioField;
    private JPanel panelLogin;
    private JLabel logoLabel;

    public PanelLogin(ControladorApp controlador) {

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = usuarioField.getText();
                String contrasena = new String(contrasenaField.getPassword());

                if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Debes introducir usuario y contraseña.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    controlador.comprobarCredenciales(nombreUsuario, contrasena);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cambiarVista(new PanelRegistrarUsuario(controlador).getPanelRegistrarUsuario());
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Seguro que quieres salir?", "Salir",
                        JOptionPane.YES_NO_OPTION);
                if (confirmacion == 0) {
                    System.exit(0);
                }
            }
        });
    }

    public JPanel getPanelLogin() {
        return panelLogin;
    }

}