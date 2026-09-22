package vista;

import controlador.ControladorApp;
import modelo.POJO.Adopcion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelListaAdopciones {

    private JButton volverButton;
    private JLabel listaLabel;
    private JPanel panelListaAdopciones;

    public PanelListaAdopciones(ControladorApp controlador, ArrayList<Adopcion> lista, String rolUsuario) {

        StringBuilder sb = new StringBuilder("<html><body style='padding: 10px; font-family: Arial; font-size: 12px;'>");
        if (lista.isEmpty()) {
            sb.append("No hay adopciones registradas.");
        } else {
            for (Adopcion a : lista) {
                sb.append("ID: ").append(a.getId())
                        .append(" | Usuario: ").append(a.getUsuario().getNombreUsuario())
                        .append(" | Animal: ").append(a.getAnimal().getNombreAnimal())
                        .append(" | Estado: ").append(a.getEstado())
                        .append(" | Fecha: ").append(a.getFechaAdopcion())
                        .append("<br>");
            }
        }
        sb.append("</body></html>");
        listaLabel.setText(sb.toString());

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rolUsuario.equals("adoptante")) {
                    controlador.cambiarVista(new PanelAdoptante(controlador, controlador.getNombreUsuarioActivo()).getPanelAdoptante()
                    );
                } else if (rolUsuario.equals("empleado")) {
                    controlador.cambiarVista(new PanelEmpleado(controlador, controlador.getNombreUsuarioActivo()).getPanelEmpleado()
                    );
                } else {
                    controlador.cambiarVista(new PanelAdmin(controlador, controlador.getNombreUsuarioActivo()).getPanelAdmin()
                    );
                }
            }
        });
    }

    public JPanel getPanelListaAdopciones() {
        return panelListaAdopciones;
    }
}