package application.ucweb.proyectoecoinca.model;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by ucweb02 on 05/12/2016.
 */

public class UsuarioExtras extends RealmObject {
    public static final String TAG = UsuarioExtras.class.getSimpleName();
    public static final String ID = "id";

    public static final int T_CERTIFICACION = 0;
    public static final int T_PRODUCTO      = 1;
    public static final int T_EMPRESARIAL   = 2;

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(UsuarioCertificacion.class).max(ID);
        return number == null ? 0 : number.intValue() + 1;
    }

    @PrimaryKey
    private long id;
    @Required
    private String descripcion;
    private int tipo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
