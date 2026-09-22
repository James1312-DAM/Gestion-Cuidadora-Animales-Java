package vista;

import controlador.ControladorApp;
import modelo.POJO.Adoptante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelListaAdoptantes {

    private JLabel listaLabel;
    private JButton volverButton;
    private JPanel panelListaAdoptantes;

    public PanelListaAdoptantes(ControladorApp controlador, ArrayList<Adoptante> lista) {

        StringBuilder sb = new StringBuilder("<html><body style='padding: 10px; font-family: Arial; font-size: 12px;'>");
        if (lista.isEmpty()) {
            sb.append("No hay adoptantes registrados.");
        } else {
            for (Adoptante a : lista) {
                sb.append("ID: ").append(a.getId())
                        .append(" | Usuario: ").append(a.getNombreUsuario())
                        .append(" | Correo: ").append(a.getCorreoElectronico())
                        .append(" | Telefono: ").append(a.getTelefono())
                        .append("<br>");
            }
        }
        sb.append("</body></html>");
        listaLabel.setText(sb.toString());

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cambiarVista(new PanelAdmin(controlador, "").getPanelAdmin());
            }
        });
    }

    public JPanel getPanelListaAdoptantes() {
        return panelListaAdoptantes;
    }
}