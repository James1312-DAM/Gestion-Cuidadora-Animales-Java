package modelo.POJO;

import modelo.DAO.ConfiguracionBD;
import modelo.DAO.UsuarioDAO;

import java.sql.*;
import java.time.LocalDate;
/**
 * Clase abstracta que representa un usuario del sistema.
 * Es la clase base de Adoptante, Empleado y Administrador.
 *
 * @author Jaime
 * @version 1.0
 */
public abstract class Usuario implements UsuarioDAO {

    protected int id;
    protected String nombreUsuario;
    protected String contraseña;
    protected String correoElectronico;
    protected String telefono;
    protected LocalDate fechaRegistro;
    protected TipoUsuario tipo;
    /**
     * @param id identificador unico del usuario
     * @param nombreUsuario nombre de usuario para el login
     * @param contraseña contraseña del usuario
     * @param correoElectronico correo electronico del usuario
     * @param telefono telefono de contacto
     * @param fechaRegistro fecha en que se registro
     * @param tipo tipo de usuario segun TipoUsuario
     */
    public Usuario(int id, String nombreUsuario, String contraseña,
                   String correoElectronico, String telefono,
                   LocalDate fechaRegistro, TipoUsuario tipo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.fechaRegistro = LocalDate.now();
        this.tipo = tipo;
    }
    /**
     * Devuelve el tipo de usuario.
     * Cada subclase lo implementa devolviendo su TipoUsuario correspondiente.
     *
     * @return el TipoUsuario de la subclase
     */

    public abstract TipoUsuario getTipo();

    /** @return true si el usuario existe en la BD con ese nombre y tipo */
    @Override
    public boolean existeUsuario() {
        String sql = "SELECT * FROM USUARIO WHERE NOMBRE_USUARIO = ? AND TIPO = ?";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, this.nombreUsuario);
            sentencia.setString(2, this.tipo.name());
            ResultSet resultados = sentencia.executeQuery();

            if (resultados.next()) {
                resultados.close();
                return true;
            } else {
                resultados.close();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** @return la contrasena del usuario desde la BD, o null si no existe */
    @Override
    public String obtenerContraseña() {
        String sql = "SELECT CONTRASENA FROM USUARIO WHERE NOMBRE_USUARIO = ?";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, this.nombreUsuario);
            ResultSet resultados = sentencia.executeQuery();

            if (resultados.next()) {
                String contrasena = resultados.getString("CONTRASENA");
                resultados.close();
                return contrasena;
            } else {
                resultados.close();
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Inserta el usuario en la tabla USUARIO de la base de datos */
    @Override
    public void añadirUsuario() {
        String sql = "INSERT INTO USUARIO (NOMBRE_USUARIO, CONTRASENA, " +
                "CORREO_ELECTRONICO, TELEFONO, FECHA_REGISTRO, TIPO) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, this.nombreUsuario);
            sentencia.setString(2, this.contraseña);
            sentencia.setString(3, this.correoElectronico);
            sentencia.setString(4, this.telefono);
            sentencia.setDate(5, java.sql.Date.valueOf(this.fechaRegistro));
            sentencia.setString(6, this.tipo.name());

            sentencia.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Getters y Setters*/
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }

    /** @return cadena con los datos del usuario */
    @Override
    public String toString() {
        return "----- DATOS DEL USUARIO -----\n" +
                "Id --> " + id +
                "\nNombre usuario --> " + nombreUsuario +
                "\nCorreo electronico --> " + correoElectronico +
                "\nTelefono --> " + telefono +
                "\nTipo --> " + tipo +
                "\n----------";
    }
}