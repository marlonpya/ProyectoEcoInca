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
public class UsuarioCertificacion extends RealmObject {
    public static final String TAG = UsuarioSectorEmpresarial.class.getSimpleName();
    public static final String ID = "id";

    @PrimaryKey
    private long id;
    @Required
    private String descripcion;

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(UsuarioCertificacion.class).max(ID);
        return number == null ? 0 : number.intValue() + 1;
    }

    public static void crearCertificacion(String certificacion) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        UsuarioCertificacion item = realm.createObject(UsuarioCertificacion.class);
        item.setId(getUltimoId());
        item.setDescripcion(certificacion);
        realm.copyToRealm(item);
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, item.toString());
    }

    public static void crearCertificacion(ArrayList<String> certificacion) {
        Realm realm = Realm.getDefaultInstance();
        for (int i = 0; i < certificacion.size(); i++ ) {
            realm.beginTransaction();
            UsuarioCertificacion item = realm.createObject(UsuarioCertificacion.class);
            item.setId(getUltimoId());
            item.setDescripcion(certificacion.get(i));
            realm.copyToRealm(item);
            realm.commitTransaction();
            Log.d(TAG, item.toString());
        }
        realm.close();
    }

    public static ArrayList<String> getSectoresIndustriales() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<String> certificaciones = new ArrayList<>();
        RealmResults<UsuarioCertificacion> certificaciones_realm = realm.where(UsuarioCertificacion.class).findAll();
        for (UsuarioCertificacion industria : certificaciones_realm) {
            certificaciones.add(industria.getDescripcion());
        }
        return certificaciones;
    }

    public static void limpiarCertificacion() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(UsuarioCertificacion.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

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
