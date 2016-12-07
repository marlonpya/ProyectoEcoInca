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
    public static final String ID_SERVER = "id_server";
    public static final String TIPO_EMPRESA = "tipo_empresa";

    //tipo_negocio
    public static final int N_COMPRADOR     = 0;
    public static final int N_VENDEDOR      = 1;
    public static final int N_AMBOS         = 2;

    //tipo_empresa
    public static final int E_BUSQUEDA      = 0;
    public static final int E_CONTACTO      = 1;

    //tipo_match si (tipo_empresa == E_CONTACTO)
    public static final int M_ESPERA        = 0;
    public static final int M_ACEPTADO      = 1;
    public static final int M_RECHAZADO     = 2;
    public static final int M_DESCONOCIDO   =-1;

    @PrimaryKey
    private long id;
    private int id_server;
    @Required
    private String nombre;
    private int tipo_negocio;
    private String imagen;
    private int tipo_empresa;
    private String ciudad;
    private String pais;
    private String anio_f;
    private String descripcion;
    private String pdf;
    private int tipo_match;

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(Empresa.class).max(ID);
        return number == null ? 0 : number.intValue() + 1;
    }

    public static void eliminarPorTipoEmpresa(int tipo_empresa) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Empresa> empresas = realm.where(Empresa.class).equalTo(TIPO_EMPRESA, tipo_empresa).findAll();
        empresas.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public static void registrarEmpresa(Empresa emp, int tipo_empresa) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Empresa empresa = realm.createObject(Empresa.class);
        empresa.setId(getUltimoId());
        empresa.setId_server(emp.getId_server());
        empresa.setNombre(emp.getNombre());
        empresa.setTipo_negocio(emp.getTipo_negocio());
        empresa.setImagen(emp.getImagen());
        empresa.setTipo_empresa(tipo_empresa);
        empresa.setCiudad(emp.getCiudad());
        empresa.setPais(emp.getPais());
        empresa.setAnio_f(emp.getAnio_f());
        empresa.setDescripcion(emp.getDescripcion());
        empresa.setPdf(emp.getPdf());
        empresa.setTipo_match(emp.getTipo_match());
        realm.copyToRealm(empresa);
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, empresa.toString());
    }

    public static int identificarEmpresaContacto(int id_empresa_contacto) {
        int tipo = M_DESCONOCIDO;
        Realm realm = Realm.getDefaultInstance();
        Empresa empresa = realm.where(Empresa.class).equalTo(ID_SERVER, id_empresa_contacto).findFirst();
        if (empresa != null) tipo = empresa.getTipo_match();
        return tipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getId_server() {
        return id_server;
    }

    public void setId_server(int id_server) {
        this.id_server = id_server;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAnio_f() {
        return anio_f;
    }

    public void setAnio_f(String anio_f) {
        this.anio_f = anio_f;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public int getTipo_match() {
        return tipo_match;
    }

    public void setTipo_match(int tipo_match) {
        this.tipo_match = tipo_match;
    }
}
