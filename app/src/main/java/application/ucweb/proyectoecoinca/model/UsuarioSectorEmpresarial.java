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
public class UsuarioSectorEmpresarial extends RealmObject {
    public static final String TAG = UsuarioSectorEmpresarial.class.getSimpleName();
    public static final String ID = "id";

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(UsuarioSectorEmpresarial.class).max(ID);
        return number == null ? 0 : number.intValue() + 1;
    }

    public static void crearSectorEmpresarial(String industria) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        UsuarioSectorEmpresarial item = realm.createObject(UsuarioSectorEmpresarial.class);
        item.setId(getUltimoId());
        item.setDescripcion(industria);
        realm.copyToRealm(item);
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, item.toString());
    }

    public static void crearSectorEmpresarial(ArrayList<String> industria) {
        Realm realm = Realm.getDefaultInstance();
        for (int i = 0; i < industria.size(); i++) {
            realm.beginTransaction();
            UsuarioSectorEmpresarial item = realm.createObject(UsuarioSectorEmpresarial.class);
            item.setId(getUltimoId());
            item.setDescripcion(industria.get(i));
            realm.copyToRealm(item);
            realm.commitTransaction();
            Log.d(TAG, item.toString());
        }
        realm.close();
    }

    public static ArrayList<String> getSectoresEmpresariales() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<String> sectorIndustriales = new ArrayList<>();
        RealmResults<UsuarioSectorEmpresarial> sectorIndustriales_realm = realm.where(UsuarioSectorEmpresarial.class).findAll();
        for (UsuarioSectorEmpresarial industria : sectorIndustriales_realm) {
            sectorIndustriales.add(industria.getDescripcion());
        }
        return sectorIndustriales;
     }

    public static void limpiarSectoresEmpresariales() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(UsuarioSectorEmpresarial.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
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
