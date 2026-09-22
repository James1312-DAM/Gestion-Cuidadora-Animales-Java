package modelo.POJO;

import modelo.DAO.ConfiguracionBD;

import java.sql.*;
import java.time.LocalDate;

/**
 * Clase final que representa un gato de la protectora.
 * Hereda de Animal.
 *
 * @author Jaime
 * @version 1.0
 */
public final class Gato extends Animal {

    private boolean convivePerros;

    public Gato(int id, String nombreAnimal, String raza,
                EstadoAnimal estado, LocalDate fechaIngreso,
                String descripcion, boolean convivePerros) {
        super(id, nombreAnimal, raza, estado, fechaIngreso, descripcion);
        this.convivePerros = convivePerros;
    }

    /** @return sonido del gato: Miau */
    @Override
    public String getSonido() {
        return "Miau";
    }

    /** Inserta el gato en la tabla ANIMAL*/
    @Override
    public void añadirAnimal() {
        String sql = "INSERT INTO ANIMAL (NOMBRE_ANIMAL, RAZA, ESTADO, " +
                "TIPO_ANIMAL, FECHA_INGRESO, DESCRIPCION, CONVIVE_PERROS) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, this.nombreAnimal);
            sentencia.setString(2, this.raza);
            sentencia.setString(3, this.estado.name());
            sentencia.setString(4, "GATO");
            sentencia.setDate(5, java.sql.Date.valueOf(this.fechaIngreso));
            sentencia.setString(6, this.descripcion);
            sentencia.setInt(7, this.convivePerros ? 1 : 0);

            sentencia.executeUpdate();
            System.out.println("Gato añadido correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /** @return true si el gato convive bien con perros */
    public boolean isConvivePerros() {
        return convivePerros;
    }
    /** @param convivePerros nuevo valor */
    public void setConvivePerros(boolean convivePerros) {
        this.convivePerros = convivePerros;
    }

    /** @return cadena con los datos del gato */
    @Override
    public String toString() {
        return "----- DATOS DEL GATO -----\n" +
                "Id --> " + id +
                "\nNombre --> " + nombreAnimal +
                "\nRaza --> " + raza +
                "\nEstado --> " + estado +
                "\nFecha ingreso --> " + fechaIngreso +
                "\nDescripcion --> " + descripcion +
                "\nConvive con perros --> " + convivePerros +
                "\nSonido --> " + getSonido() +
                "\n----------";
    }
}