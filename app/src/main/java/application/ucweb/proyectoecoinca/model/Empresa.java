package application.ucweb.proyectoecoinca.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by ucweb02 on 06/10/2016.
 */
public class Empresa extends RealmObject{
    // COMPRADOR = 0, VENDEDOR = 1, AMBOS = 2
    public static final String ID = "id";
    public static final String COMODIN = "comodin";

    public static final int COMPRADOR   = 0;
    public static final int VENDEDOR    = 1;
    public static final int AMBOS       = 2;

    public static final int DEFAULT     = 0;
    public static final int BUSQUEDA    = 1;

    @PrimaryKey
    private long id;
    @Required
    private String nombre;
    @Required
    private String imagen;
    private int tipo;
    private int comodin;

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(Empresa.class).max(ID);
        return number == null ? 0 : number.intValue() + 1;
    }

    private void eliminarEmpresas(int tipo) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Empresa> empresas = realm.where(Empresa.class).equalTo(COMODIN, tipo).findAll();
        empresas.deleteAllFromRealm();
        realm.close();
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getComodin() {
        return comodin;
    }

    public void setComodin(int comodin) {
        this.comodin = comodin;
    }
}
