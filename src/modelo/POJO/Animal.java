package modelo.POJO;

import modelo.DAO.AnimalDAO;
import modelo.DAO.ConfiguracionBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Clase abstracta que representa un animal de la protectora.
 * Es la clase base de Perro y Gato.
 *
 * @author Jaime
 * @version 1.0
 */
public abstract class Animal implements AnimalDAO {

    protected int id;
    protected String nombreAnimal;
    protected String raza;
    protected EstadoAnimal estado;
    protected LocalDate fechaIngreso;
    protected String descripcion;

    public Animal(int id, String nombreAnimal, String raza,
                  EstadoAnimal estado, LocalDate fechaIngreso, String descripcion) {
        this.id = id;
        this.nombreAnimal = nombreAnimal;
        this.raza = raza;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el sonido del animal.
     * Cada subclase lo implementa con su sonido correspondiente.
     *
     * @return sonido del animal como String
     */
    public abstract String getSonido();

    /** Inserta el animal en la tabla ANIMAL de la base de datos */
    @Override
    public void añadirAnimal() {
        String sql = "INSERT INTO ANIMAL (NOMBRE_ANIMAL, RAZA, ESTADO, " +
                "TIPO_ANIMAL, FECHA_INGRESO, DESCRIPCION) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, this.nombreAnimal);
            sentencia.setString(2, this.raza);
            sentencia.setString(3, this.estado.name());
            sentencia.setString(4, this.getClass().getSimpleName().toUpperCase());
            sentencia.setDate(5, java.sql.Date.valueOf(this.fechaIngreso));
            sentencia.setString(6, this.descripcion);

            sentencia.executeUpdate();
            System.out.println("Animal añadido correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Actualiza unicamente el estado del animal en la base de datos */
    public void actualizarEstado() {
        String sql = "UPDATE ANIMAL SET ESTADO = ? WHERE ID = ?";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, this.estado.name());
            sentencia.setInt(2, this.id);

            sentencia.executeUpdate();
            System.out.println("Estado del animal actualizado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Elimina el animal de la base de datos */
    @Override
    public void eliminarAnimal() {
        String sql = "DELETE FROM ANIMAL WHERE ID = ?";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, this.id);
            sentencia.executeUpdate();
            System.out.println("Animal eliminado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** @return lista con todos los animales registrados en la BD */
    @Override
    public ArrayList<Animal> listarAnimales() {
        String sql = "SELECT * FROM ANIMAL";
        ArrayList<Animal> listaAnimales = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            ResultSet resultados = sentencia.executeQuery();

            while (resultados.next()) {
                listaAnimales.add(construirAnimal(resultados));
            }

            resultados.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAnimales;
    }

    /** @return lista con los animales en estado DISPONIBLE */
    @Override
    public ArrayList<Animal> listarDisponibles() {
        String sql = "SELECT * FROM ANIMAL WHERE ESTADO = 'DISPONIBLE'";
        ArrayList<Animal> listaAnimales = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            ResultSet resultados = sentencia.executeQuery();

            while (resultados.next()) {
                listaAnimales.add(construirAnimal(resultados));
            }

            resultados.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAnimales;
    }

    /**
     * Construye un objeto Perro o Gato segun el campo TIPO_ANIMAL del ResultSet.
     *
     * @param resultados ResultSet con los datos del animal
     * @return objeto Animal (Perro o Gato)
     * @throws SQLException si hay un error al leer el ResultSet
     */
    public static Animal construirAnimal(ResultSet resultados) throws SQLException {
        int id = resultados.getInt("ID");
        String nombreAnimal = resultados.getString("NOMBRE_ANIMAL");
        String raza = resultados.getString("RAZA");
        EstadoAnimal estado = EstadoAnimal.valueOf(resultados.getString("ESTADO"));
        LocalDate fechaIngreso = resultados.getDate("FECHA_INGRESO").toLocalDate();
        String descripcion = resultados.getString("DESCRIPCION");
        String tipoAnimal = resultados.getString("TIPO_ANIMAL");

        if (tipoAnimal.equals("PERRO")) {
            boolean conviveGatos = resultados.getInt("CONVIVE_GATOS") == 1;
            return new Perro(id, nombreAnimal, raza, estado,
                    fechaIngreso, descripcion, conviveGatos);
        } else {
            boolean convivePerros = resultados.getInt("CONVIVE_PERROS") == 1;
            return new Gato(id, nombreAnimal, raza, estado,
                    fechaIngreso, descripcion, convivePerros);
        }
    }

    /** Getters y Setters*/
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreAnimal() { return nombreAnimal; }
    public void setNombreAnimal(String nombreAnimal) { this.nombreAnimal = nombreAnimal; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public EstadoAnimal getEstado() { return estado; }
    public void setEstado(EstadoAnimal estado) { this.estado = estado; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /** @return cadena con los datos del animal */
    @Override
    public String toString() {
        return "----- DATOS DEL ANIMAL -----\n" +
                "Id --> " + id +
                "\nNombre --> " + nombreAnimal +
                "\nRaza --> " + raza +
                "\nEstado --> " + estado +
                "\nFecha ingreso --> " + fechaIngreso +
                "\nDescripcion --> " + descripcion +
                "\n----------";
    }
}