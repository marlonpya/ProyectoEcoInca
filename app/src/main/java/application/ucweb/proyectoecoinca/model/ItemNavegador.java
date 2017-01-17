package application.ucweb.proyectoecoinca.model;

/**
 * Created by ucweb02 on 03/10/2016.
 */
public class ItemNavegador {
    private int icono;
    private String titulo;

    public ItemNavegador(String titulo, int icono) {
        this.icono = icono;
        this.titulo = titulo;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
