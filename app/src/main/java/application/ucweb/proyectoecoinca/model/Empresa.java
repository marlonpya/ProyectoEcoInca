package application.ucweb.proyectoecoinca.model;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by ucweb02 on 06/10/2016.
 */
public class Empresa extends RealmObject{
    public static final String TAG = Empresa.class.getSimpleName();
    public static final String ID = "id";
    public static final String TIPO_EMPRESA = "tipo_empresa";

    //tipo_negocio
    public static final int N_COMPRADOR   = 0;
    public static final int N_VENDEDOR    = 1;
    public static final int N_AMBOS       = 2;

    //tipo_empresa
    public static final int E_BUSQUEDA     = 0;
    public static final int E_CONTACTO     = 1;

    @PrimaryKey
    private long id;
    @Required
    private String nombre;
    private int tipo_negocio;
    private String imagen;
    private int tipo_empresa;

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(Empresa.class).max(ID);
        return number == null ? 0 : number.intValue() + 1;
    }

    public static void eliminarPorTipoEmpresa(int tipo_empresa) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Empresa> empresas = realm.where(Empresa.class).equalTo(TIPO_EMPRESA, tipo_empresa).findAll();
        empresas.deleteAllFromRealm();
        realm.close();
    }

    public static void registrarEmpresa(Empresa emp, int tipo_empresa) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Empresa empresa = realm.createObject(Empresa.class);
        empresa.setId(getUltimoId());
        empresa.setNombre(emp.getNombre());
        empresa.setTipo_negocio(emp.getTipo_negocio());
        empresa.setImagen(emp.getImagen());
        empresa.setTipo_empresa(tipo_empresa);
        realm.copyToRealm(empresa);
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, empresa.toString());
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

    public int getTipo_empresa() {
        return tipo_empresa;
    }

    public void setTipo_empresa(int tipo_empresa) {
        this.tipo_empresa = tipo_empresa;
    }

    public int getTipo_negocio() {
        return tipo_negocio;
    }

    public void setTipo_negocio(int tipo_negocio) {
        this.tipo_negocio = tipo_negocio;
    }
}
