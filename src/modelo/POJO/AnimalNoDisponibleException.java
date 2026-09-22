package modelo.POJO;
/**
 * Excepcion propia que se lanza cuando un animal no esta disponible para adopcion.
 * Se usa al solicitar una adopcion si el animal no existe o su estado no es DISPONIBLE.
 *
 * @author Jaime
 * @version 1.0
 */
public class AnimalNoDisponibleException extends Exception {
    public AnimalNoDisponibleException(String message) {
        super(message);
    }
}
