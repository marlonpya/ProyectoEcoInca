package application.ucweb.proyectoecoinca.model;

import android.content.Context;
import android.util.Log;

import application.ucweb.proyectoecoinca.R;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by ucweb02 on 22/11/2016.
 */
@RealmClass
public class RegistroSectorEmpresarial extends RealmObject {
    public static final String TAG = RegistroSectorEmpresarial.class.getSimpleName();
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(RegistroSectorEmpresarial.class).max(ID);
        return number == null ? 0 : number.intValue() + 1;
    }

    public static void actualizarCertificaciones(Context context) {
        Realm realm = Realm.getDefaultInstance();
        RegistroSectorEmpresarial item;
        realm.beginTransaction();
        String[] sectores_empresariales = context.getResources().getStringArray(R.array.sectores_empresariales);
        if (getUltimoId() == 0) {
            for (String sector_empresarial : sectores_empresariales) {
                item = new RegistroSectorEmpresarial();
                item.setId(getUltimoId());
                item.setNombre(sector_empresarial);
                realm.copyFromRealm(item);
                realm.commitTransaction();
                Log.d(TAG, item.toString());
            }
        } else {
            for (int i = 1; i < sectores_empresariales.length; i++) {
                item = realm.where(RegistroSectorEmpresarial.class).equalTo(ID, i).findFirst();
                if (item != null) {
                    realm.beginTransaction();
                    item.setId(i);
                    item.setNombre(sectores_empresariales[i]);
                    realm.commitTransaction();
                    Log.d(TAG, item.toString());
                }
            }
        }
    }

    @PrimaryKey
    private long id;
    @Required
    private String nombre;

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
}
