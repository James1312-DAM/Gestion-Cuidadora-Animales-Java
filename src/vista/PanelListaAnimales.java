package vista;

import controlador.ControladorApp;
import modelo.POJO.Animal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelListaAnimales {

    private JButton volverButton;
    private JLabel listaLabel;
    private JPanel panelListaAnimales;

    public PanelListaAnimales(ControladorApp controlador, ArrayList<Animal> lista, String rolUsuario) {

        StringBuilder sb = new StringBuilder("<html><body style='padding: 10px; font-family: Arial; font-size: 12px;'>");
        if (lista.isEmpty()) {
            sb.append("No hay animales registrados.");
        } else {
            for (Animal a : lista) {
                sb.append("ID: ").append(a.getId())
                        .append(" | Nombre: ").append(a.getNombreAnimal())
                        .append(" | Raza: ").append(a.getRaza())
                        .append(" | Estado: ").append(a.getEstado())
                        .append(" | Fecha ingreso: ").append(a.getFechaIngreso())
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

    public JPanel getPanelListaAnimales() {
        return panelListaAnimales;
    }
}