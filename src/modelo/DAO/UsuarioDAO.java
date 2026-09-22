package modelo.DAO;
/**
 * Interfaz DAO con las operaciones basicas de usuario en la base de datos.
 *
 * @author Jaime
 * @version 1.0
 */

public interface UsuarioDAO {
    /** @return true si el usuario existe en la BD, false si no */
    boolean existeUsuario();
    /** @return la contrasena del usuario o null si no existe */
    String obtenerContraseña();
    /** Inserta el usuario en la base de datos */
    void añadirUsuario();
}
