package modelo.POJO;

import java.time.LocalDate;
/**
 * Clase que representa al administrador del sistema.
 * Hereda de Empleado y tiene acceso a todas las funcionalidades de la aplicacion.
 *
 * @author Jaime
 * @version 1.0
 */
public class Administrador extends Empleado {

    public Administrador(int id, String nombreUsuario, String contraseña,
                         String correoElectronico, String telefono,
                         LocalDate fechaRegistro, double salario) {
        super(id, nombreUsuario, contraseña, correoElectronico,
                telefono, fechaRegistro, salario);
        this.tipo = TipoUsuario.ADMIN;
    }

    /** @return TipoUsuario.ADMIN */
    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.ADMIN;
    }

    /** @return cadena con los datos del administrador */
    @Override
    public String toString() {
        return "----- DATOS DEL ADMINISTRADOR -----\n" +
                "Id --> " + id +
                "\nNombre usuario --> " + nombreUsuario +
                "\nCorreo electronico --> " + correoElectronico +
                "\nTelefono --> " + telefono +
                "\nFecha registro --> " + fechaRegistro +
                "\nSalario --> " + salario +
                "\n----------";
    }
}