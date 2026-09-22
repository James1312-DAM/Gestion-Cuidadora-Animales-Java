/*package vista;

import controlador.ControladorApp;
import modelo.POJO.AnimalNoDisponibleException;
import modelo.POJO.UsuarioNoEncontradoException;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Clase que gestiona la interfaz textual de la aplicacion.
 * Muestra los menus por consola y recoge la entrada del usuario.
 *
 * @author Jaime
 * @version 1.0

public class VistaTextual {

    private Scanner sc;
    private ControladorApp controlador;

    public VistaTextual(ControladorApp controlador) {
        this.sc = new Scanner(System.in);
        this.controlador = controlador;
    }

    /**
     * Muestra el menu de login y gestiona el inicio de sesion y registro.
     *
     * @throws UsuarioNoEncontradoException si el usuario no existe en la BD
     * @throws AnimalNoDisponibleException si el animal no esta disponible

    public void menuLogin() throws UsuarioNoEncontradoException, AnimalNoDisponibleException {
        System.out.println("\n***Bienvenido a la Protectora de Animales***");
        System.out.println("¿Qué desea hacer?");
        System.out.println("1. Iniciar sesion");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");
        System.out.print("Elija una opcion: ");

        int opcion = 0;
        boolean repetir;

        do {
            repetir = false;
            try {
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.print("Nombre de usuario: ");
                        String nombreUsuario = sc.nextLine();
                        System.out.print("Contrasena: ");
                        String contrasena = sc.nextLine();
                        controlador.comprobarCredenciales(nombreUsuario, contrasena);
                        break;
                    case 2:
                        System.out.print("Nombre de usuario: ");
                        String nuevoNombre = sc.nextLine();
                        System.out.print("Contrasena: ");
                        String nuevaContrasena = sc.nextLine();
                        System.out.print("Correo electronico: ");
                        String correo = sc.nextLine();
                        System.out.print("Telefono: ");
                        String telefono = sc.nextLine();
                        controlador.registrarAdoptante(nuevoNombre, nuevaContrasena,
                                correo, telefono);
                        break;
                    case 3:
                        System.out.println("Gracias por usar la aplicacion.");
                        return;
                    default:
                        System.err.println("Opcion incorrecta. Intentelo de nuevo.");
                        repetir = true;
                }
            } catch (UsuarioNoEncontradoException | AnimalNoDisponibleException e) {
                throw e;
            }catch (InputMismatchException e) {
                System.err.println("ERROR: Debes introducir un numero valido.");
                sc.nextLine();
                repetir = true;
            }
            catch (Exception e) {
                System.err.println("ERROR: Introduce un numero valido.");
                sc.nextLine();
                repetir = true;
            }
        } while (repetir);
    }

    /**
     * Muestra el menu del adoptante y gestiona sus opciones.
     *
     * @param nombreUsuario nombre del adoptante que ha iniciado sesion
     * @throws AnimalNoDisponibleException si el animal no esta disponible
     * @throws UsuarioNoEncontradoException si el usuario no existe en la BD

    public void menuAdoptante(String nombreUsuario) throws AnimalNoDisponibleException, UsuarioNoEncontradoException {
        System.out.println("\n*** Menu Adoptante — Usuario: " + nombreUsuario + " ***");

        int opcion = 0;
        boolean salir = false;

        while (!salir) {
            try {
                mostrarMenuAdoptante();
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        controlador.verAnimalesDisponibles();
                        break;
                    case 2:
                        System.out.print("Introduce el ID del animal a adoptar: ");
                        int idAnimal = sc.nextInt();
                        sc.nextLine();
                        controlador.solicitarAdopcion(nombreUsuario, idAnimal);
                        break;
                    case 3:
                        controlador.verMisAdopciones(nombreUsuario);
                        break;
                    case 4:
                        salir = true;
                        menuLogin();
                        break;
                    default:
                        System.err.println("Opcion incorrecta.");
                }
            } catch (AnimalNoDisponibleException | UsuarioNoEncontradoException e) {
                throw e;
            } catch (Exception e) {
                System.err.println("ERROR: Introduce un numero valido.");
                sc.nextLine();
            }
        }
    }

    /** Muestra las opciones del menu adoptante
    private void mostrarMenuAdoptante() {
        System.out.println("\n--- Menu Adoptante ---");
        System.out.println("1. Ver animales disponibles");
        System.out.println("2. Solicitar adopcion");
        System.out.println("3. Ver mis adopciones");
        System.out.println("4. Cerrar sesion");
        System.out.print("Elija una opcion: ");
    }

    /**
     * Muestra el menu del empleado y gestiona sus opciones.
     *
     * @param nombreUsuario nombre del empleado que ha iniciado sesion
     * @throws UsuarioNoEncontradoException si el usuario no existe en la BD
     * @throws AnimalNoDisponibleException si el animal no esta disponible

    public void menuEmpleado(String nombreUsuario) throws UsuarioNoEncontradoException, AnimalNoDisponibleException {
        System.out.println("\n*** Menu Empleado — Usuario: " + nombreUsuario + " ***");

        int opcion = 0;
        boolean salir = false;

        while (!salir) {
            try {
                mostrarMenuEmpleado();
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        controlador.verTodosAnimales();
                        break;
                    case 2:
                        System.out.println("Tipo de animal:");
                        System.out.println("1. Perro");
                        System.out.println("2. Gato");
                        int tipoAnimal = sc.nextInt();
                        sc.nextLine();
                        controlador.añadirAnimal(tipoAnimal);
                        break;
                    case 3:
                        System.out.print("Introduce el ID del animal: ");
                        int idEliminar = sc.nextInt();
                        sc.nextLine();
                        controlador.eliminarAnimal(idEliminar);
                        break;
                    case 4:
                        controlador.gestionarAdopciones(nombreUsuario);
                        break;
                    case 5:
                        salir = true;
                        menuLogin();
                        break;
                    default:
                        System.err.println("Opcion incorrecta.");
                }
            } catch (UsuarioNoEncontradoException | AnimalNoDisponibleException e) {
                throw e;
            } catch (Exception e) {
                System.err.println("ERROR: Introduce un numero valido.");
                sc.nextLine();
            }
        }
    }

    /** Muestra las opciones del menu empleado
    private void mostrarMenuEmpleado() {
        System.out.println("\n--- Menu Empleado ---");
        System.out.println("1. Ver todos los animales");
        System.out.println("2. Añadir animal");
        System.out.println("3. Eliminar animal");
        System.out.println("4. Gestionar adopciones");
        System.out.println("5. Cerrar sesion");
        System.out.print("Elija una opcion: ");
    }

    /**
     * Muestra el menu del administrador y gestiona sus opciones.
     *
     * @param nombreUsuario nombre del administrador que ha iniciado sesion
     * @throws UsuarioNoEncontradoException si el usuario no existe en la BD
     * @throws AnimalNoDisponibleException si el animal no esta disponible

    public void menuAdministrador(String nombreUsuario) throws UsuarioNoEncontradoException, AnimalNoDisponibleException {
        System.out.println("\n*** Menu Administrador — Usuario: " + nombreUsuario + " ***");

        int opcion = 0;
        boolean salir = false;

        while (!salir) {
            try {
                mostrarMenuAdministrador();
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        controlador.verTodosAnimales();
                        break;
                    case 2:
                        System.out.println("Tipo de animal:");
                        System.out.println("1. Perro");
                        System.out.println("2. Gato");
                        int tipoAnimal = sc.nextInt();
                        sc.nextLine();
                        controlador.añadirAnimal(tipoAnimal);
                        break;
                    case 3:
                        System.out.print("Introduce el ID del animal: ");
                        int idEliminar = sc.nextInt();
                        sc.nextLine();
                        controlador.eliminarAnimal(idEliminar);
                        break;
                    case 4:
                        controlador.verTodasAdopciones();
                        break;
                    case 5:
                        controlador.verTodosAdoptantes();
                        break;
                    case 6:
                        System.out.print("Nombre de usuario: ");
                        String nombre = sc.nextLine();
                        System.out.print("Contrasena: ");
                        String contrasena = sc.nextLine();
                        System.out.print("Correo electronico: ");
                        String correo = sc.nextLine();
                        System.out.print("Telefono: ");
                        String telefono = sc.nextLine();
                        System.out.print("Salario: ");
                        double salario = sc.nextDouble();
                        sc.nextLine();
                        controlador.crearEmpleado(nombre, contrasena,
                                correo, telefono, salario);
                        break;
                    case 7:
                        salir = true;
                        menuLogin();
                        break;
                    default:
                        System.err.println("Opcion incorrecta.");
                }
            } catch (UsuarioNoEncontradoException | AnimalNoDisponibleException e) {
                throw e;
            } catch (Exception e) {
                System.err.println("ERROR: Introduce un numero valido.");
                sc.nextLine();
            }
        }
    }
    /** Muestra las opciones del menu administrador
    private void mostrarMenuAdministrador() {
        System.out.println("\n--- Menu Administrador ---");
        System.out.println("1. Ver todos los animales");
        System.out.println("2. Añadir animal");
        System.out.println("3. Eliminar animal");
        System.out.println("4. Ver todas las adopciones");
        System.out.println("5. Ver todos los adoptantes");
        System.out.println("6. Crear empleado");
        System.out.println("7. Cerrar sesion");
        System.out.print("Elija una opcion: ");
    }

    /**
     * Solicita los datos comunes de un animal.
     *
     * @return array con nombre, raza y descripcion

    public String[] pedirDatosAnimalComun() {
        System.out.print("Nombre del animal: ");
        String nombre = sc.nextLine();
        System.out.print("Raza: ");
        String raza = sc.nextLine();
        System.out.print("Descripcion: ");
        String descripcion = sc.nextLine();
        return new String[]{nombre, raza, descripcion};
    }
    /**
     * Solicita los datos especificos de un perro.
     *
     * @return array con conviveGatos

    public String[] pedirDatosPerro() {
        System.out.print("¿Convive con gatos? (si/no): ");
        String conviveGatos = sc.nextLine();
        return new String[]{conviveGatos};
    }
    /**
     * Solicita los datos especificos de un gato.
     *
     * @return array con convivePerros

    public String[] pedirDatosGato() {
        System.out.print("¿Convive con perros? (si/no): ");
        String convivePerros = sc.nextLine();
        return new String[]{convivePerros};
    }
}

 */