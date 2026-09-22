package modelo.POJO;
/**
 * Excepcion propia que se lanza cuando un usuario no existe en la base de datos.
 * Se usa en el proceso de login cuando el nombre de usuario no coincide con ningun registro.
 *
 * @author Jaime
 * @version 1.0
 */
public class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
}
