package application.ucweb.proyectoecoinca.model;

import android.content.Context;
import android.util.Log;

import application.ucweb.proyectoecoinca.R;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ucweb02 on 12/10/2016.
 */
public class BuscarDetalle extends RealmObject {
    //TIPO: 1 = PRODUCTO, 2 = SERVICIO, 3 = COMPAÑÍA, 4 = INDUSTRIA, 5 = PAÍS, 6 = CERTIFICACION
    public static final String TAG = BuscarDetalle.class.getSimpleName();
    public static final String BUSDET_ID = "id";
    public static final String BUSDET_TIPO = "tipo";

    @PrimaryKey
    private long id;
    private String descripcion;
    private int tipo;
    private boolean seleccionado;

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(BuscarDetalle.class).max("id");
        return number == null ? 0 : number.intValue() + 1;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    //PAIS 1 ; CIUDAD; 2; PRODUCTOS 3; EMPRESARIAL 4; CERTIFICACIONES 5;
    public static void cargarPais() {
        Realm realm = Realm.getDefaultInstance();
        String[] array = {"Alemania", "Peru", "Estados Unidos", "Bolivia", "Ecuador", "Mexico", "Colombia", "Venezuela", "Arabia", "Argentina", "Guatemala"};
        for(String nombre : array) {
            realm.beginTransaction();
            BuscarDetalle item = realm.createObject(BuscarDetalle.class);
            item.setId(getUltimoId());
            item.setDescripcion(nombre.toUpperCase());
            item.setTipo(1);
            item.setSeleccionado(false);
            realm.copyToRealm(item);
            realm.commitTransaction();
            Log.d(TAG, item.toString());
        }
        realm.close();
    }

    public static void cargarProducto() {
        Realm realm = Realm.getDefaultInstance();
        String[] array = {"Chia", "Papa", "Arroz", "Quinua", "Maca", "Quaker"};
        for(String nombre : array) {
            realm.beginTransaction();
            BuscarDetalle item = realm.createObject(BuscarDetalle.class);
            item.setId(getUltimoId());
            item.setDescripcion(nombre.toUpperCase());
            item.setTipo(3);
            item.setSeleccionado(false);
            realm.copyToRealm(item);
            realm.commitTransaction();
            Log.d(TAG, item.toString());
        }
        realm.close();
    }

    public static void cargarEmpresarial(Context context) {
        Realm realm = Realm.getDefaultInstance();
        String[] array = context.getResources().getStringArray(R.array.sectores_empresariales);
        Number number = realm.where(BuscarDetalle.class).equalTo(BUSDET_TIPO, 4).max(BUSDET_ID);
        if (number == null || number.intValue() == 0) {
            for (String nombre : array) {
                realm.beginTransaction();
                BuscarDetalle item = realm.createObject(BuscarDetalle.class);
                item.setId(getUltimoId());
                item.setDescripcion(nombre.toUpperCase());
                item.setTipo(4);
                item.setSeleccionado(false);
                realm.copyToRealm(item);
                realm.commitTransaction();
                Log.d(TAG, item.toString());
            }
        } else {
            RealmResults<BuscarDetalle> results = realm.where(BuscarDetalle.class).equalTo(BUSDET_TIPO, 4).findAll();
            for (int i = 0; i < results.size(); i++) {
                realm.beginTransaction();
                BuscarDetalle item = results.get(i);
                item.setId(item.getId());
                item.setDescripcion(array[i]);
                realm.commitTransaction();
                Log.d(TAG, item.toString());
            }
        }
        realm.close();
    }

    public static void cargarCertificaciones(Context context) {
        Realm realm = Realm.getDefaultInstance();
        String[] array = context.getResources().getStringArray(R.array.certificaciones);
        Number number = realm.where(BuscarDetalle.class).equalTo(BUSDET_TIPO, 5).max(BUSDET_ID);
        if (number == null || number.intValue() == 0) {
            for (String nombre : array) {
                realm.beginTransaction();
                BuscarDetalle item = realm.createObject(BuscarDetalle.class);
                item.setId(getUltimoId());
                item.setDescripcion(nombre.toUpperCase());
                item.setTipo(5);
                item.setSeleccionado(false);
                realm.copyToRealm(item);
                realm.commitTransaction();
                Log.d(TAG, item.toString());
            }
        } else {
            RealmResults<BuscarDetalle> results = realm.where(BuscarDetalle.class).equalTo(BUSDET_TIPO, 5).findAll();
            for (int i = 0; i < results.size(); i++) {
                realm.beginTransaction();
                BuscarDetalle item = results.get(i);
                item.setId(item.getId());
                item.setDescripcion(array[i]);
                realm.commitTransaction();
                Log.d(TAG, item.toString());
            }
        }

        realm.close();
    }

}
