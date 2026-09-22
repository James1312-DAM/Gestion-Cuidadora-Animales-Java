package modelo.POJO;

import modelo.DAO.AdopcionDAO;
import modelo.DAO.ConfiguracionBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Adopcion implements AdopcionDAO {

    private int id;
    private Usuario usuario;
    private Animal animal;
    private LocalDate fechaAdopcion;
    private String estado;

    /**
     * Clase que representa una solicitud de adopcion entre un usuario y un animal.
     * Implementa AdopcionDAO para las operaciones con la base de datos.
     *
     * @author Jaime
     * @version 1.0
     */
    public Adopcion(int id, Usuario usuario, Animal animal,
                    LocalDate fechaAdopcion, String estado) {
        this.id = id;
        this.usuario = usuario;
        this.animal = animal;
        this.fechaAdopcion = fechaAdopcion;
        this.estado = estado;
    }

    /** Inserta la adopcion en la tabla ADOPCION de la base de datos */
    @Override
    public void añadirAdopcion() {
        String sql = "INSERT INTO ADOPCION (USUARIO_ID, ANIMAL_ID, " +
                "FECHA_ADOPCION, ESTADO) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, this.usuario.getId());
            sentencia.setInt(2, this.animal.getId());
            sentencia.setDate(3, java.sql.Date.valueOf(this.fechaAdopcion));
            sentencia.setString(4, this.estado);

            sentencia.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Actualiza el estado de la adopcion en la base de datos */
    @Override
    public void actualizarAdopcion() {
        String sql = "UPDATE ADOPCION SET ESTADO = ? WHERE ID = ?";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, this.estado);
            sentencia.setInt(2, this.id);

            sentencia.executeUpdate();
            System.out.println("Adopcion actualizada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** @return lista con todas las adopciones registradas en la BD */
    @Override
    public ArrayList<Adopcion> listarAdopciones() {
        String sql = "SELECT * FROM ADOPCION";
        ArrayList<Adopcion> listaAdopciones = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            ResultSet resultados = sentencia.executeQuery();

            while (resultados.next()) {
                listaAdopciones.add(construirAdopcion(resultados));
            }

            resultados.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAdopciones;
    }

    /**
     * @param idAdoptante ID del adoptante a consultar
     * @return lista con las adopciones de ese adoptante
     */
    @Override
    public ArrayList<Adopcion> listarPorAdoptante(int idAdoptante) {
        String sql = "SELECT * FROM ADOPCION WHERE USUARIO_ID = ?";
        ArrayList<Adopcion> listaAdopciones = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idAdoptante);
            ResultSet resultados = sentencia.executeQuery();

            while (resultados.next()) {
                listaAdopciones.add(construirAdopcion(resultados));
            }

            resultados.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAdopciones;
    }

    /**
     * Construye un objeto Adopcion a partir de un ResultSet.
     *
     * @param resultados ResultSet con los datos de la adopcion
     * @return objeto Adopcion construido
     * @throws SQLException si hay un error al leer el ResultSet
     */
    private Adopcion construirAdopcion(ResultSet resultados) throws SQLException {
        int id = resultados.getInt("ID");
        int usuarioId = resultados.getInt("USUARIO_ID");
        int animalId = resultados.getInt("ANIMAL_ID");
        LocalDate fechaAdopcion = resultados.getDate("FECHA_ADOPCION").toLocalDate();
        String estado = resultados.getString("ESTADO");

        Usuario usuario = buscarUsuarioPorId(usuarioId);
        Animal animal = buscarAnimalPorId(animalId);

        return new Adopcion(id, usuario, animal, fechaAdopcion, estado);
    }

    /**
     * Busca un usuario en la BD por su ID.
     *
     * @param id ID del usuario a buscar
     * @return objeto Adoptante con los datos del usuario, o null si no existe
     */
    private Usuario buscarUsuarioPorId(int id) {
        String sql = "SELECT * FROM USUARIO WHERE ID = ?";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            ResultSet resultados = sentencia.executeQuery();

            if (resultados.next()) {
                String nombreUsuario = resultados.getString("NOMBRE_USUARIO");
                String contrasena = resultados.getString("CONTRASENA");
                String correo = resultados.getString("CORREO_ELECTRONICO");
                String telefono = resultados.getString("TELEFONO");
                LocalDate fechaRegistro = resultados.getDate("FECHA_REGISTRO").toLocalDate();
                resultados.close();
                return new Adoptante(id, nombreUsuario, contrasena,
                        correo, telefono, fechaRegistro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Busca un animal en la BD por su ID.
     *
     * @param id ID del animal a buscar
     * @return objeto Animal (Perro o Gato), o null si no existe
     */
    private Animal buscarAnimalPorId(int id) {
        String sql = "SELECT * FROM ANIMAL WHERE ID = ?";

        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            ResultSet resultados = sentencia.executeQuery();

            if (resultados.next()) {
                Animal animal = Animal.construirAnimal(resultados);
                resultados.close();
                return animal;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //* Getters y Setters*/
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }

    public LocalDate getFechaAdopcion() { return fechaAdopcion; }
    public void setFechaAdopcion(LocalDate fechaAdopcion) { this.fechaAdopcion = fechaAdopcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    /** @return cadena con los datos de la adopcion */
    @Override
    public String toString() {
        return "----- DATOS DE LA ADOPCION -----\n" +
                "Id --> " + id +
                "\nUsuario --> " + usuario.getNombreUsuario() +
                "\nAnimal --> " + animal.getNombreAnimal() +
                "\nFecha adopcion --> " + fechaAdopcion +
                "\nEstado --> " + estado +
                "\n----------";
    }
}