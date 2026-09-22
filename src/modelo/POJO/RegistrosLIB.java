package modelo.POJO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
/**
 * Clase final que gestiona el registro de operaciones en un fichero de texto.
 * Guarda las altas y bajas de animales realizadas por los empleados en doc/registros.txt.
 *
 * @author Jaime
 * @version 1.0
 */
public final class RegistrosLIB {

    private LocalDateTime fecha;
    private String usuario;
    private String accion;

    /** Ruta del fichero de registros */
    private static final String RUTA = "doc/registros.txt";

    public RegistrosLIB(LocalDateTime fecha, String usuario, String accion) {
        this.fecha = LocalDateTime.now();
        this.usuario = usuario;
        this.accion = accion;
    }

    /**
     * Escribe una linea en el fichero registros.txt.
     * Si el fichero no existe lo crea con una cabecera.
     */
    public void escribirLog() {
        String linea = fecha.toLocalDate() + " " + fecha.toLocalTime().withNano(0) +
                " | " + usuario +
                " | " + accion + "\n";

        try {
            Path archivo = Path.of(RUTA);

            if (!Files.exists(archivo)) {
                Files.createFile(archivo);
                Files.writeString(archivo,
                        "FECHA              | EMPLEADO         | ACCION\n" +
                                "-------------------------------------------------------------\n");
            }

            Files.writeString(archivo, linea, StandardOpenOption.APPEND);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //* Getters y Setters*/
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    /** @return cadena con los datos del registro */
    @Override
    public String toString() {
        return "----- REGISTRO DE ACTIVIDAD -----\n" +
                "Fecha --> " + fecha.toString() +
                "\nUsuario --> " + usuario +
                "\nAccion --> " + accion +
                "\n----------";
    }
}