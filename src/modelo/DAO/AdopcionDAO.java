package modelo.DAO;

import modelo.POJO.Adopcion;

import java.util.ArrayList;
/**
 * Interfaz DAO con las operaciones de adopcion en la base de datos.
 *
 * @author Jaime
 * @version 1.0
 */
public interface AdopcionDAO {
    void añadirAdopcion();
    void actualizarAdopcion();
    /** @return lista con todas las adopciones registradas */
    ArrayList<Adopcion> listarAdopciones();
    /**
     * @param idAdoptante ID del adoptante a consultar
     * @return lista con las adopciones de ese adoptante
     */
    ArrayList<Adopcion> listarPorAdoptante(int idAdoptante);
}
