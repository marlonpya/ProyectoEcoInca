package application.ucweb.proyectoecoinca.model;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ucweb02 on 12/10/2016.
 */
public class BuscarDetalle extends RealmObject {
    //TIPO: 1 = PRODUCTO, 2 = SERVICIO, 3 = COMPAÑÍA, 4 = INDUSTRIA, 5 = PAÍS, 6 = CERTIFICACION
    public static final String TAG = BuscarDetalle.class.getSimpleName();
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

    public static void cargarProducto() {
        Realm realm = Realm.getDefaultInstance();
        String[] array = {"Chia", "Papa", "Arroz", "Quinua", "Maca", "Quaker"};
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

    public static void cargarServicio() {
        Realm realm = Realm.getDefaultInstance();
        String[] array = {"Electricidad", "Combustibles", "Restaurante", "Transporte", "Comercio", "Ocio"};
        for(String nombre : array) {
            realm.beginTransaction();
            BuscarDetalle item = realm.createObject(BuscarDetalle.class);
            item.setId(getUltimoId());
            item.setDescripcion(nombre.toUpperCase());
            item.setTipo(2);
            item.setSeleccionado(false);
            realm.copyToRealm(item);
            realm.commitTransaction();
            Log.d(TAG, item.toString());
        }
        realm.close();
    }

    public static void cargarIndustria() {
        Realm realm = Realm.getDefaultInstance();
        String[] array = {"Agro Industria", "Alimentos y Bebidas", "Hotelería", "Servicios", "Industria textil y confecciones", "Ropa industrial y accesorios", "Calzado", "Madera", "Minería", "Construcción", "Equipamiento y mobiliario en general", "Metalmecánica", "Otros" };
        for(String nombre : array) {
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
        realm.close();
    }

    public static void cargarPais() {
        Realm realm = Realm.getDefaultInstance();
        String[] array = {"Alemania", "Peru", "Estados Unidos", "Bolivia", "Ecuador", "Mexico", "Colombia", "Venezuela", "Arabia", "Argentina", "Guatemala"};
        for(String nombre : array) {
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
        realm.close();
    }

    public static void cargarCertificaciones() {
        Realm realm = Realm.getDefaultInstance();
        String[] array = {"BPM", "BRC", "FAIR TRADE", "GLUTEN FREE", "HACCP", "Halal", "ISO", "Kosher", "NON GMO", "RAINFOREST ALLIANCE", "UEBT"};
        for(String nombre : array) {
            realm.beginTransaction();
            BuscarDetalle item = realm.createObject(BuscarDetalle.class);
            item.setId(getUltimoId());
            item.setDescripcion(nombre.toUpperCase());
            item.setTipo(6);
            item.setSeleccionado(false);
            realm.copyToRealm(item);
            realm.commitTransaction();
            Log.d(TAG, item.toString());
        }
        realm.close();
    }

}
