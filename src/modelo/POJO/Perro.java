package modelo.POJO;

import modelo.DAO.ConfiguracionBD;

import java.sql.*;
import java.time.LocalDate;
/**
 * Clase final que representa un perro de la protectora.
 * Hereda de Animal.
 *
 * @author Jaime
 * @version 1.0
 */
public final class Perro extends Animal {

    private boolean conviveGatos;

    public Perro(int id, String nombreAnimal, String raza,
                 EstadoAnimal estado, LocalDate fechaIngreso,
                 String descripcion, boolean conviveGatos) {
        super(id, nombreAnimal, raza, estado, fechaIngreso, descripcion);
        this.conviveGatos = conviveGatos;
    }

    /** @return sonido del perro: Guau */
    @Override
    public String getSonido() {
        return "Guau";
    }

    /** Inserta el perro en la tabla ANIMAL  */
    @Override
    public void añadirAnimal() {
        String sql = "INSERT INTO ANIMAL (NOMBRE_ANIMAL, RAZA, ESTADO, " +
                "TIPO_ANIMAL, FECHA_INGRESO, DESCRIPCION, CONVIVE_GATOS) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, this.nombreAnimal);
            sentencia.setString(2, this.raza);
            sentencia.setString(3, this.estado.name());
            sentencia.setString(4, "PERRO");
            sentencia.setDate(5, java.sql.Date.valueOf(this.fechaIngreso));
            sentencia.setString(6, this.descripcion);
            sentencia.setInt(7, this.conviveGatos ? 1 : 0);

            sentencia.executeUpdate();
            System.out.println("Perro añadido correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** @return true si el perro convive bien con gatos */
    public boolean isConviveGatos() { return conviveGatos; }
    /** @param conviveGatos nuevo valor */
    public void setConviveGatos(boolean conviveGatos) { this.conviveGatos = conviveGatos; }
    /** @return cadena con los datos del perro */
    @Override
    public String toString() {
        return "----- DATOS DEL PERRO -----\n" +
                "Id --> " + id +
                "\nNombre --> " + nombreAnimal +
                "\nRaza --> " + raza +
                "\nEstado --> " + estado +
                "\nFecha ingreso --> " + fechaIngreso +
                "\nDescripcion --> " + descripcion +
                "\nConvive con gatos --> " + conviveGatos +
                "\nSonido --> " + getSonido() +
                "\n----------";
    }
}