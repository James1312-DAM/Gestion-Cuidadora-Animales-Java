package modelo.POJO;

import modelo.DAO.ConfiguracionBD;
import modelo.DAO.EmpleadoDAO;

import java.sql.*;
import java.time.LocalDate;

/**
 * Clase que representa a un empleado de la protectora.
 * Hereda de Usuario e implementa EmpleadoDAO.
 *
 * @author Jaime
 * @version 1.0
 */
public class Empleado extends Usuario implements EmpleadoDAO {

    protected double salario;

    public Empleado(int id, String nombreUsuario, String contrasena,
                    String correoElectronico, String telefono,
                    LocalDate fechaRegistro, double salario) {
        super(id, nombreUsuario, contrasena, correoElectronico,
                telefono, fechaRegistro, TipoUsuario.EMPLEADO);
        this.salario = salario;
    }

    /** @return TipoUsuario.EMPLEADO */
    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.EMPLEADO;
    }

    /** Inserta el empleado en la tabla USUARIO de la base de datos */
    @Override
    public void añadirEmpleado() {
        String sql = "INSERT INTO USUARIO (NOMBRE_USUARIO, CONTRASENA, " +
                "CORREO_ELECTRONICO, TELEFONO, FECHA_REGISTRO, TIPO, SALARIO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, this.nombreUsuario);
            sentencia.setString(2, this.contraseña);
            sentencia.setString(3, this.correoElectronico);
            sentencia.setString(4, this.telefono);
            sentencia.setDate(5, java.sql.Date.valueOf(this.fechaRegistro));
            sentencia.setString(6, this.tipo.name());
            sentencia.setDouble(7, this.salario);

            sentencia.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Getters y Setters */
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    /** @return cadena con los datos del empleado */
    @Override
    public String toString() {
        return "----- DATOS DEL EMPLEADO -----\n" +
                "Id --> " + id +
                "\nNombre usuario --> " + nombreUsuario +
                "\nCorreo electronico --> " + correoElectronico +
                "\nTelefono --> " + telefono +
                "\nFecha registro --> " + fechaRegistro +
                "\nSalario --> " + salario +
                "\n----------";
    }
}