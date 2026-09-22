package modelo.DAO;

import modelo.POJO.Adoptante;

import java.util.ArrayList;
/**
 * Interfaz DAO con las operaciones especificas de adoptante en la base de datos.
 *
 * @author Jaime
 * @version 1.0
 */
public interface AdoptanteDAO {
    /** @return lista con todos los adoptantes registrados */
    ArrayList<Adoptante> listarAdoptantes();
}
