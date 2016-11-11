package application.ucweb.proyectoecoinca.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ucweb02 on 06/10/2016.
 */
public class Empresa extends RealmObject{
    // COMPRADOR = 0, VENDEDOR = 1, AMBOS = 2
    public static final String ID = "id";

    @PrimaryKey
    private long id;
    private String nombre;
    private int imagen;
    private int tipo;

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(Empresa.class).max(ID);
        return number == null ? 0 : number.intValue() + 1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
