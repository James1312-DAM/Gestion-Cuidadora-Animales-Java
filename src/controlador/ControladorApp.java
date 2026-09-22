package controlador;

import java.sql.*;
import modelo.DAO.ConfiguracionBD;
import modelo.POJO.*;
import vista.Ventana;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Clase principal de la aplicacion. Actua como controlador en el patron MVC.
 * Gestiona la logica de negocio y coordina la vista con los modelos.
 *
 * @author Jaime
 * @version 1.0
 */
public class ControladorApp {

    // private VistaTextual vista; — sustituida por la ventana grafica
    private static Ventana ventana;
    private String nombreUsuarioActivo;

    /**
     * Metodo principal. Carga el driver Oracle e inicia la aplicacion.
     *
     * @param args argumentos de la linea de comandos
     */
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver Oracle cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: Driver Oracle no encontrado.");
            e.printStackTrace();
            return;
        }

        ControladorApp controlador = new ControladorApp();

        // Version textual — sustituida por la ventana grafica
        // controlador.vista = new VistaTextual(controlador);
        // boolean seguir = true;
        // while (seguir) {
        //     try {
        //         controlador.vista.menuLogin();
        //         seguir = false;
        //     } catch (UsuarioNoEncontradoException e) {
        //         System.err.println("\nERROR: " + e.getMessage());
        //     } catch (AnimalNoDisponibleException e) {
        //         System.err.println("\nERROR: " + e.getMessage());
        //     } catch (Exception e) {
        //         System.err.println("\nERROR desconocido: " + e.getMessage());
        //         e.printStackTrace();
        //     }
        // }

        // Version grafica
        ventana = new Ventana(controlador);
    }

    /**
     * Cambia el panel visible en la ventana principal.
     *
     * @param nuevaVista nuevo panel a mostrar
     */
    public void cambiarVista(JPanel nuevaVista) {
        ventana.setContentPane(nuevaVista);
        ventana.revalidate();
        ventana.repaint();
    }

    /**
     * Comprueba las credenciales del usuario y redirige al menu correspondiente.
     *
     * @param nombreUsuario nombre de usuario introducido
     * @param contrasena contrasena introducida
     * @throws UsuarioNoEncontradoException si el usuario no existe en la BD
     * @throws AnimalNoDisponibleException si se produce durante la sesion
     */
    public void comprobarCredenciales(String nombreUsuario, String contrasena)
            throws UsuarioNoEncontradoException, AnimalNoDisponibleException {

        Adoptante adoptante = new Adoptante(0, nombreUsuario, contrasena,
                null, null, LocalDate.now());

        if (adoptante.existeUsuario()) {
            String contrasenaReal = adoptante.obtenerContraseña();
            if (contrasenaReal != null && contrasenaReal.equals(contrasena)) {
                System.out.println("\nCredenciales correctas. Bienvenido, " + nombreUsuario);
                nombreUsuarioActivo = nombreUsuario;

                // Version textual
                // vista.menuAdoptante(nombreUsuario);

                // Version grafica
                cambiarVista(new vista.PanelAdoptante(this, nombreUsuario).getPanelAdoptante());
            } else {
                // Version textual
                // System.err.println("\nContrasena incorrecta.");
                // vista.menuLogin();

                // Version grafica
                JOptionPane.showMessageDialog(null,
                        "Contrasena incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        Empleado empleado = new Empleado(0, nombreUsuario, contrasena,
                null, null, LocalDate.now(), 0);

        if (empleado.existeUsuario()) {
            String contrasenaReal = empleado.obtenerContraseña();
            if (contrasenaReal != null && contrasenaReal.equals(contrasena)) {
                System.out.println("\nCredenciales correctas. Bienvenido, " + nombreUsuario);
                nombreUsuarioActivo = nombreUsuario;

                // Version textual
                // vista.menuEmpleado(nombreUsuario);

                // Version grafica
                cambiarVista(new vista.PanelEmpleado(this, nombreUsuario).getPanelEmpleado());
            } else {
                // Version textual
                // System.err.println("\nContrasena incorrecta.");
                // vista.menuLogin();

                // Version grafica
                JOptionPane.showMessageDialog(null,
                        "Contrasena incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        Administrador admin = new Administrador(0, nombreUsuario, contrasena,
                null, null, LocalDate.now(), 0);

        if (admin.existeUsuario()) {
            String contrasenaReal = admin.obtenerContraseña();
            if (contrasenaReal != null && contrasenaReal.equals(contrasena)) {
                System.out.println("\nCredenciales correctas. Bienvenido, " + nombreUsuario);
                nombreUsuarioActivo = nombreUsuario;

                // Version textual
                // vista.menuAdministrador(nombreUsuario);

                // Version grafica
                cambiarVista(new vista.PanelAdmin(this, nombreUsuario).getPanelAdmin());
            } else {
                // Version textual
                // System.err.println("\nContrasena incorrecta.");
                // vista.menuLogin();

                // Version grafica
                JOptionPane.showMessageDialog(null,
                        "Contrasena incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        throw new UsuarioNoEncontradoException("El usuario '" + nombreUsuario + "' no existe.");
    }

    /**
     * Valida los datos y registra un nuevo adoptante en la BD.
     *
     * @param nombreUsuario nombre de usuario elegido
     * @param contrasena contrasena elegida
     * @param correo correo electronico
     * @param telefono telefono
     * @throws UsuarioNoEncontradoException si se produce durante el registro
     * @throws AnimalNoDisponibleException si se produce durante el registro
     */
    public void registrarAdoptante(String nombreUsuario, String contrasena,
                                   String correo, String telefono)
            throws UsuarioNoEncontradoException, AnimalNoDisponibleException {

        boolean correoValido = correo.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        if (!correoValido) {
            // Version textual
            // System.err.println("\nERROR: El correo electronico no tiene un formato valido.");
            // vista.menuLogin();

            // Version grafica
            JOptionPane.showMessageDialog(null,
                    "El correo electronico no tiene un formato valido.\nEjemplo: usuario@gmail.com",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean telefonoValido = telefono.matches("[0-9]{9}");
        if (!telefonoValido) {
            // Version textual
            // System.err.println("\nERROR: El telefono no tiene un formato valido.");
            // vista.menuLogin();

            // Version grafica
            JOptionPane.showMessageDialog(null,
                    "El telefono debe contener exactamente 9 digitos numericos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Adoptante adoptante = new Adoptante(0, nombreUsuario, contrasena,
                correo, telefono, LocalDate.now());

        if (adoptante.existeUsuario()) {
            // Version textual
            // System.err.println("\nEl nombre de usuario ya existe.");
            // vista.menuLogin();

            // Version grafica
            JOptionPane.showMessageDialog(null,
                    "El nombre de usuario ya existe.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            adoptante.añadirUsuario();

            // Version textual
            // System.out.println("\nUsuario registrado correctamente. Inicia sesion.");
            // vista.menuLogin();

            // Version grafica
            JOptionPane.showMessageDialog(null,
                    "Usuario registrado correctamente. Inicia sesion.",
                    "Registro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * @return lista con todos los animales disponibles
     */
    public ArrayList<Animal> verAnimalesDisponibles() {
        Animal animal = new Perro(0, null, null,
                EstadoAnimal.DISPONIBLE, LocalDate.now(), null, false);

        // Version textual
        // ArrayList<Animal> lista = animal.listarDisponibles();
        // if (lista.isEmpty()) {
        //     System.out.println("\nNo hay animales disponibles actualmente.");
        // } else {
        //     System.out.println("\n--- Animales disponibles ---");
        //     for (Animal a : lista) { System.out.println(a); }
        // }

        // Version grafica — devuelve la lista para mostrarla en el panel
        return animal.listarDisponibles();
    }

    /**
     * @return lista con todos los animales registrados
     */
    public ArrayList<Animal> verTodosAnimales() {
        Animal animal = new Perro(0, null, null,
                EstadoAnimal.DISPONIBLE, LocalDate.now(), null, false);

        // Version textual
        // ArrayList<Animal> lista = animal.listarAnimales();
        // if (lista.isEmpty()) {
        //     System.out.println("\nNo hay animales registrados.");
        // } else {
        //     System.out.println("\n--- Todos los animales ---");
        //     for (Animal a : lista) { System.out.println(a); }
        // }

        // Version grafica — devuelve la lista para mostrarla en el panel
        return animal.listarAnimales();
    }

    /**
     * Inserta un nuevo animal en la BD.
     *
     * @param tipoAnimal 1 para perro, 2 para gato
     * @param nombre nombre del animal
     * @param raza raza del animal
     * @param descripcion descripcion del animal
     * @param convive true si convive con la otra especie
     */
    public void añadirAnimal(int tipoAnimal, String nombre, String raza,
                             String descripcion, boolean convive) {

        // Version textual
        // String[] datosComunes = vista.pedirDatosAnimalComun();
        // String[] datosPerro = vista.pedirDatosPerro();
        // String[] datosGato = vista.pedirDatosGato();

        // Version grafica — recibe los datos directamente como parametros
        if (tipoAnimal == 1) {
            Perro perro = new Perro(0, nombre, raza, EstadoAnimal.DISPONIBLE,
                    LocalDate.now(), descripcion, convive);
            perro.añadirAnimal();
            registrarRegistrosLIB("Añadido perro: " + nombre);
        } else {
            Gato gato = new Gato(0, nombre, raza, EstadoAnimal.DISPONIBLE,
                    LocalDate.now(), descripcion, convive);
            gato.añadirAnimal();
            registrarRegistrosLIB("Añadido gato: " + nombre);
        }
        JOptionPane.showMessageDialog(null,
                "Animal añadido correctamente.",
                "Añadido", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Elimina un animal de la BD si no tiene adopciones asociadas.
     *
     * @param idAnimal ID del animal a eliminar
     */
    public void eliminarAnimal(int idAnimal) {
        String sql = "SELECT COUNT(*) FROM ADOPCION WHERE ANIMAL_ID = ?";
        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, idAnimal);
            ResultSet resultados = sentencia.executeQuery();
            if (resultados.next() && resultados.getInt(1) > 0) {
                // Version textual
                // System.err.println("\nERROR: No se puede eliminar el animal porque tiene adopciones asociadas.");

                // Version grafica
                JOptionPane.showMessageDialog(null,
                        "No se puede eliminar el animal porque tiene adopciones asociadas.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                resultados.close();
                return;
            }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        Perro perro = new Perro(idAnimal, null, null,
                EstadoAnimal.DISPONIBLE, LocalDate.now(), null, false);
        perro.eliminarAnimal();
        registrarRegistrosLIB("Eliminado animal con id: " + idAnimal);

        // Version grafica
        JOptionPane.showMessageDialog(null,
                "Animal eliminado correctamente.",
                "Eliminado", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Solicita una adopcion para un adoptante sobre un animal disponible.
     *
     * @param nombreUsuario nombre del adoptante
     * @param idAnimal ID del animal a adoptar
     * @throws AnimalNoDisponibleException si el animal no existe o no esta disponible
     */
    public void solicitarAdopcion(String nombreUsuario, int idAnimal)
            throws AnimalNoDisponibleException {

        String sql = "SELECT ID FROM USUARIO WHERE NOMBRE_USUARIO = ?";
        int idAdoptante = 0;
        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, nombreUsuario);
            ResultSet resultados = sentencia.executeQuery();
            if (resultados.next()) { idAdoptante = resultados.getInt("ID"); }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (idAdoptante == 0) {
            // Version textual
            // System.err.println("ERROR: No se encontro el usuario en la base de datos.");

            // Version grafica
            JOptionPane.showMessageDialog(null,
                    "No se encontro el usuario en la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String estadoAnimal = null;
        boolean existeAnimal = false;
        String sqlAnimal = "SELECT ESTADO FROM ANIMAL WHERE ID = ?";
        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sqlAnimal)) {
            sentencia.setInt(1, idAnimal);
            ResultSet resultados = sentencia.executeQuery();
            if (resultados.next()) {
                existeAnimal = true;
                estadoAnimal = resultados.getString("ESTADO");
            }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        if (!existeAnimal) {
            throw new AnimalNoDisponibleException(
                    "No existe ningun animal con id: " + idAnimal);
        }
        if (!estadoAnimal.equals("DISPONIBLE")) {
            throw new AnimalNoDisponibleException(
                    "El animal con id " + idAnimal + " no esta disponible.");
        }

        Adoptante adoptante = new Adoptante(idAdoptante, nombreUsuario, null,
                null, null, LocalDate.now());
        Perro animal = new Perro(idAnimal, null, null,
                EstadoAnimal.EN_ADOPCION, LocalDate.now(), null, false);
        Adopcion adopcion = new Adopcion(0, adoptante, animal, LocalDate.now(), "PENDIENTE");
        adopcion.añadirAdopcion();
        animal.actualizarEstado();

        // Version textual
        // System.out.println("\nSolicitud enviada correctamente.");

        // Version grafica
        JOptionPane.showMessageDialog(null,
                "Solicitud enviada correctamente.",
                "Adopcion", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @param nombreUsuario nombre del adoptante
     * @return lista con las adopciones del adoptante
     */
    public ArrayList<Adopcion> verMisAdopciones(String nombreUsuario) {
        String sql = "SELECT ID FROM USUARIO WHERE NOMBRE_USUARIO = ?";
        int idAdoptante = 0;
        try (Connection conexion = DriverManager.getConnection(
                ConfiguracionBD.URL, ConfiguracionBD.USUARIO, ConfiguracionBD.CONTRASENA);
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, nombreUsuario);
            ResultSet resultados = sentencia.executeQuery();
            if (resultados.next()) { idAdoptante = resultados.getInt("ID"); }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Adoptante adoptante = new Adoptante(idAdoptante, nombreUsuario, null,
                null, null, LocalDate.now());
        Adopcion adopcion = new Adopcion(0, adoptante, null, LocalDate.now(), null);

        // Version textual
        // ArrayList<Adopcion> lista = adopcion.listarPorAdoptante(idAdoptante);
        // if (lista.isEmpty()) {
        //     System.out.println("\nNo tienes adopciones registradas.");
        // } else {
        //     System.out.println("\n--- Mis adopciones ---");
        //     for (Adopcion a : lista) { System.out.println(a); }
        // }

        // Version grafica — devuelve la lista para mostrarla en el panel
        return adopcion.listarPorAdoptante(idAdoptante);
    }

    /**
     * @return lista con todas las adopciones
     */
    public ArrayList<Adopcion> verTodasAdopciones() {
        Adopcion adopcion = new Adopcion(0, null, null, LocalDate.now(), null);

        // Version textual
        // ArrayList<Adopcion> lista = adopcion.listarAdopciones();
        // if (lista.isEmpty()) {
        //     System.out.println("\nNo hay adopciones registradas.");
        // } else {
        //     System.out.println("\n--- Todas las adopciones ---");
        //     for (Adopcion a : lista) { System.out.println(a); }
        // }

        // Version grafica — devuelve la lista para mostrarla en el panel
        return adopcion.listarAdopciones();
    }

    /**
     * @return lista con todos los adoptantes
     */
    public ArrayList<Adoptante> verTodosAdoptantes() {
        Adoptante adoptante = new Adoptante(0, null, null, null, null, LocalDate.now());

        // Version textual
        // ArrayList<Adoptante> lista = adoptante.listarAdoptantes();
        // if (lista.isEmpty()) {
        //     System.out.println("\nNo hay adoptantes registrados.");
        // } else {
        //     System.out.println("\n--- Todos los adoptantes ---");
        //     for (Adoptante a : lista) { System.out.println(a); }
        // }

        // Version grafica — devuelve la lista para mostrarla en el panel
        return adoptante.listarAdoptantes();
    }

    /**
     * Gestiona una adopcion aprobandola o rechazandola.
     *
     * @param idAdopcion ID de la adopcion
     * @param aprobar true para aprobar, false para rechazar
     */
    public void gestionarAdopcion(int idAdopcion, boolean aprobar) {

        // Version textual
        // Scanner sc = new Scanner(System.in);
        // System.out.print("Introduce el ID de la adopcion a gestionar: ");
        // int idAdopcion = sc.nextInt();
        // System.out.println("1. Aprobar");
        // System.out.println("2. Rechazar");
        // int opcion = sc.nextInt();

        // Version grafica — recibe el id y la decision directamente como parametros
        Adopcion adopcion = new Adopcion(0, null, null, LocalDate.now(), null);
        ArrayList<Adopcion> lista = adopcion.listarAdopciones();

        Adopcion adopcionSeleccionada = null;
        for (Adopcion a : lista) {
            if (a.getId() == idAdopcion) {
                adopcionSeleccionada = a;
                break;
            }
        }

        if (adopcionSeleccionada == null) {
            JOptionPane.showMessageDialog(null,
                    "No se encontro la adopcion con ese ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (aprobar) {
            adopcionSeleccionada.setEstado("APROBADA");
            adopcionSeleccionada.actualizarAdopcion();
            Animal animal = adopcionSeleccionada.getAnimal();
            animal.setEstado(EstadoAnimal.ADOPTADO);
            animal.actualizarEstado();
            JOptionPane.showMessageDialog(null,
                    "Adopcion aprobada correctamente.",
                    "Aprobada", JOptionPane.INFORMATION_MESSAGE);
        } else {
            adopcionSeleccionada.setEstado("RECHAZADA");
            adopcionSeleccionada.actualizarAdopcion();
            Animal animal = adopcionSeleccionada.getAnimal();
            animal.setEstado(EstadoAnimal.DISPONIBLE);
            animal.actualizarEstado();
            JOptionPane.showMessageDialog(null,
                    "Adopcion rechazada. Animal vuelve a estar disponible.",
                    "Rechazada", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Crea un nuevo empleado en la BD.
     *
     * @param nombreUsuario nombre de usuario del empleado
     * @param contrasena contrasena del empleado
     * @param correo correo electronico del empleado
     * @param telefono telefono del empleado
     * @param salario salario del empleado
     */
    public void crearEmpleado(String nombreUsuario, String contrasena, String correo,
                              String telefono, double salario) {
        Empleado empleado = new Empleado(0, nombreUsuario, contrasena,
                correo, telefono, LocalDate.now(), salario);
        if (empleado.existeUsuario()) {
            // Version textual
            // System.err.println("\nEl nombre de usuario ya existe.");

            // Version grafica
            JOptionPane.showMessageDialog(null,
                    "El nombre de usuario ya existe.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            empleado.añadirEmpleado();

            // Version textual
            // System.out.println("\nEmpleado creado correctamente.");

            // Version grafica
            JOptionPane.showMessageDialog(null,
                    "Empleado creado correctamente.",
                    "Creado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Registra una operacion en el fichero registros.txt.
     *
     * @param accion descripcion de la operacion realizada
     */
    private void registrarRegistrosLIB(String accion) {
        RegistrosLIB registrosLIB = new RegistrosLIB(LocalDateTime.now(), nombreUsuarioActivo, accion);
        registrosLIB.escribirLog();
    }

    /** @return nombre del usuario activo */
    public String getNombreUsuarioActivo() {
        return nombreUsuarioActivo;
    }
}