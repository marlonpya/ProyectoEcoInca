package application.ucweb.proyectoecoinca.model;

/**
 * Created by ucweb02 on 06/10/2016.
 */
public class Buscar {

    private String nombre;
    private int icono;

    public Buscar(String nombre, int icono) {
        this.nombre = nombre;
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }
}
