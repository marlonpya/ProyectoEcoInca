package application.ucweb.proyectoecoinca.model;

import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by ucweb02 on 30/11/2016.
 */

@RealmClass
public class UsuarioProducto extends RealmObject {
    public static final String TAG = UsuarioProducto.class.getSimpleName();
    public static final String ID = "id";

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(UsuarioProducto.class).max(ID);
        return number == null ? 0 : number.intValue() + 1;
    }

    public static void crearProducto(String producto) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        UsuarioProducto item = realm.createObject(UsuarioProducto.class);
        item.setId(getUltimoId());
        item.setDescripcion(producto);
        realm.copyToRealm(item);
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, item.toString());
    }

    public static void eliminarProductos() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(UsuarioProducto.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static ArrayList<String> getProductos() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<String> productos = new ArrayList<>();
        RealmResults<UsuarioProducto> productos_realm = realm.where(UsuarioProducto.class).findAll();
        for (UsuarioProducto industria : productos_realm) {
            productos.add(industria.getDescripcion());
        }
        return productos;
    }

    @PrimaryKey
    private long id;
    @Required
    private String descripcion;

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
}
