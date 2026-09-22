package modelo.DAO;

import modelo.POJO.Animal;

import java.util.ArrayList;
/**
 * Interfaz DAO con las operaciones de animal en la base de datos.
 *
 * @author Jaime
 * @version 1.0
 */
public interface AnimalDAO {
    void añadirAnimal();
    void actualizarEstado();
    void eliminarAnimal();
    /** @return lista con todos los animales registrados */
    ArrayList<Animal> listarAnimales();
    /** @return lista con los animales en estado DISPONIBLE */
    ArrayList<Animal> listarDisponibles();
}
