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
public class UsuarioSectorIndustrial extends RealmObject {
    public static final String TAG = UsuarioSectorIndustrial.class.getSimpleName();
    public static final String ID = "id";

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(UsuarioSectorIndustrial.class).max(ID);
        return number == null ? 0 : number.intValue() + 1;
    }

    public static void crearSectorIndustrial(String industria) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        UsuarioSectorIndustrial item = realm.createObject(UsuarioSectorIndustrial.class);
        item.setId(getUltimoId());
        item.setDescripcion(industria);
        realm.copyToRealm(item);
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, item.toString());
    }

    public static void eliminarSectoresIndustriales() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(UsuarioSectorIndustrial.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static ArrayList<String> getSectoresIndustriales() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<String> sectorIndustriales = new ArrayList<>();
        RealmResults<UsuarioSectorIndustrial> sectorIndustriales_realm = realm.where(UsuarioSectorIndustrial.class).findAll();
        for (UsuarioSectorIndustrial industria : sectorIndustriales_realm) {
            sectorIndustriales.add(industria.getDescripcion());
        }
        return sectorIndustriales;
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
