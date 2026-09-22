package modelo.POJO;

import modelo.DAO.AdoptanteDAO;
import modelo.DAO.ConfiguracionBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase que representa a un adoptante del sistema.
 * Hereda de Usuario e implementa AdoptanteDAO.
 *
 * @author Jaime
 * @version 1.0
 */
public class Adoptante extends Usuario implements AdoptanteDAO {

    public Adoptante(int id, String nombreUsuario, String contrasena,
                     String correoElectronico, String telefono,
                     LocalDate fechaRegistro) {
        super(id, nombreUsuario, contrasena, correoElectronico,
                telefono, fechaRegistro, TipoUsuario.ADOPTANTE);
    }

    /** @return TipoUsuario.ADOPTANTE */
    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.ADOPTANTE;
    }

    /** @return lista con todos los adoptantes registrados en la BD */
    @Override
    public ArrayList<Adoptante> listarAdoptantes() {
        String sql = "SELECT * FROM USUARIO WHERE TIPO = 'ADOPTANTE'";
        ArrayList<Adoptante> listaAdoptantes = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            ResultSet resultados = sentencia.executeQuery();

            while (resultados.next()) {
                int id = resultados.getInt("ID");
                String nombreUsuario = resultados.getString("NOMBRE_USUARIO");
                String contrasena = resultados.getString("CONTRASENA");
                String correo = resultados.getString("CORREO_ELECTRONICO");
                String telefono = resultados.getString("TELEFONO");
                LocalDate fechaRegistro = resultados.getDate("FECHA_REGISTRO").toLocalDate();

                listaAdoptantes.add(new Adoptante(id, nombreUsuario, contrasena,
                        correo, telefono, fechaRegistro));
            }

            resultados.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAdoptantes;
    }

    /** @return cadena con los datos del adoptante */
    @Override
    public String toString() {
        return "----- DATOS DEL ADOPTANTE -----\n" +
                "Id --> " + id +
                "\nNombre usuario --> " + nombreUsuario +
                "\nCorreo electronico --> " + correoElectronico +
                "\nTelefono --> " + telefono +
                "\nFecha registro --> " + fechaRegistro +
                "\n----------";
    }
}