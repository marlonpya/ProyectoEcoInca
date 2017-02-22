package application.ucweb.proyectoecoinca.model;

import android.util.Log;

import application.ucweb.proyectoecoinca.model.detalle.Certificado;
import application.ucweb.proyectoecoinca.model.detalle.Producto;
import application.ucweb.proyectoecoinca.model.detalle.SectorIndustrial;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by ucweb02 on 06/10/2016.
 */
public class Empresa extends RealmObject{
    public static final String TAG = Empresa.class.getSimpleName();
    public static final String ID           = "id";
    public static final String ID_SERVER = "id_server";
    public static final String TIPO_NEGOCIO = "tipo_negocio";
    public static final String TIPO_EMPRESA = "tipo_empresa";
    public static final String TIPO_MATCH   = "tipo_match";
    public static final String POSICION     = "posicion";

    //tipo_negocio
    public static final int N_COMPRADOR         = 0;
    public static final int N_VENDEDOR          = 1;
    public static final int N_AMBOS             = 2;

    //tipo_empresa
    public static final int E_BUSQUEDA          = 0;
    public static final int E_CONTACTO          = 1;

    //tipo_match si (tipo_empresa == E_CONTACTO)
    public static final int M_ESPERA            = 0;
    public static final int M_ACEPTADO          = 1;
    public static final int M_RECHAZADO         = 2;
    public static final int M_DESCONOCIDO       = -1;

    public static final int ID_MACTH_DEFAULT    = -1;

    //posicion
    public static final int IZQUIERDA           = 0; //VENDEDORES
    public static final int DERECHA             = 1; //COMPRADORES

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
    private int tipo_match;
    private int id_match;
    private int posicion;
    private String web;
    private String telefono1;
    private String telefono2;
    private String correo1;
    private String correo2;
    private RealmList<Certificado> certificados;
    private RealmList<Producto> productos;
    private RealmList<SectorIndustrial> sectorIndustriales;

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

    public static int getPos(int posicion) {
        int resultado;
        Usuario usuario = Usuario.getUsuario();
        if (usuario.getTipo_empresa() == Empresa.N_COMPRADOR) {
            if (posicion == Empresa.N_VENDEDOR || posicion == Empresa.N_AMBOS) resultado = IZQUIERDA;
            else resultado = DERECHA;
        } else if (usuario.getTipo_empresa() == Empresa.N_VENDEDOR) {
            if (posicion == Empresa.N_COMPRADOR || posicion == Empresa.N_AMBOS) resultado = DERECHA;
            else resultado = IZQUIERDA;
        } else {
            //Empresa.N_AMBOS
            resultado = posicion;
        }
        return resultado;
    }

    public static void eliminarContactos() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Empresa> empresas = realm.where(Empresa.class).equalTo(Empresa.TIPO_MATCH, Empresa.M_ACEPTADO).findAll();
                empresas.deleteAllFromRealm();
            }
        });
        /*RealmResults<Empresa> empresas = realm.where(Empresa.class).equalTo(Empresa.TIPO_MATCH, Empresa.M_ACEPTADO).findAll();
        realm.beginTransaction();
        empresas.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();*/
    }

    public static void eliminarNoContactos() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Empresa> empresas = realm.where(Empresa.class).notEqualTo(Empresa.TIPO_MATCH, Empresa.M_ACEPTADO).findAll();
                empresas.deleteAllFromRealm();
            }
        });
        /*RealmResults<Empresa> empresas = realm.where(Empresa.class).notEqualTo(Empresa.TIPO_MATCH, Empresa.M_ACEPTADO).findAll();
        realm.beginTransaction();
        empresas.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();*/
    }

    public static void registrarEmpresa(Empresa emp) {
        Realm realm = Realm.getDefaultInstance();
        Empresa empresa = realm.where(Empresa.class).equalTo(ID_SERVER, emp.getId_server()).findFirst();
        realm.beginTransaction();
        if (empresa == null) {
            Empresa emp_new = realm.createObject(Empresa.class);
            emp_new.setId(Empresa.getUltimoId());
            emp_new.setId_server(emp.getId_server());
            emp_new.setNombre(emp.getNombre());
            emp_new.setAnio_f(emp.getAnio_f());
            emp_new.setCiudad(emp.getCiudad());
            emp_new.setDescripcion(emp.getDescripcion());
            emp_new.setImagen(emp.getImagen());
            emp_new.setPais(emp.getPais());
            emp_new.setTipo_empresa(emp.getTipo_empresa());
            emp_new.setTipo_match(emp.getTipo_match());
            emp_new.setTipo_negocio(emp.getTipo_negocio());
            emp_new.setId_match(emp.getId_match());
            emp_new.setPosicion(emp.getPosicion());
            emp_new.setWeb(emp.getWeb());
            emp_new.setTelefono1(emp.getTelefono1());
            emp_new.setTelefono2(emp.getTelefono2());
            emp_new.setCorreo1(emp.getCorreo1());
            emp_new.setCorreo2(emp.getCorreo2());
            realm.copyToRealmOrUpdate(emp_new);
            Log.d(TAG, emp_new.toString());
        } else {
            empresa.setId(emp.getId());
            empresa.setId_server(emp.getId_server());
            empresa.setNombre(emp.getNombre());
            empresa.setAnio_f(emp.getAnio_f());
            empresa.setCiudad(emp.getCiudad());
            empresa.setDescripcion(emp.getDescripcion());
            empresa.setImagen(emp.getImagen());
            empresa.setPais(emp.getPais());
            empresa.setTipo_empresa(emp.getTipo_empresa());
            empresa.setTipo_match(emp.getTipo_match());
            empresa.setTipo_negocio(emp.getTipo_negocio());
            empresa.setId_match(emp.getId_match());
            empresa.setPosicion(emp.getPosicion());
            empresa.setWeb(emp.getWeb());
            empresa.setTelefono1(emp.getTelefono1());
            empresa.setTelefono2(emp.getTelefono2());
            empresa.setCorreo1(emp.getCorreo1());
            empresa.setCorreo2(emp.getCorreo2());
            Log.d(TAG, empresa.toString());
        }
        realm.commitTransaction();
        Log.d(TAG, "registrarEmpresa_"+emp.getNombre());
        realm.close();
    }

    public static int identificarEmpresaContacto(int id_empresa_contacto) {
        int tipo = M_DESCONOCIDO;
        Realm realm = Realm.getDefaultInstance();
        Empresa empresa = realm.where(Empresa.class).equalTo(ID_SERVER, id_empresa_contacto).findFirst();
        if (empresa != null) tipo = empresa.getTipo_match();
        return tipo;
    }

    public static void eliminarEmpresa(int id_empresa) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Empresa empresas = realm.where(Empresa.class).equalTo(ID_SERVER, id_empresa).findFirst();
        Log.d(TAG, "eliminarEmpresa_" +empresas.getNombre());
        empresas.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public static void actualizarMatch(long id, int match) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Empresa empresa = realm.where(Empresa.class).equalTo(ID_SERVER, id).findFirst();
        empresa.setTipo_match(match);
        realm.commitTransaction();
        realm.close();
    }

    public static void limpiarEmpresa() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Empresa> empresas = realm.where(Empresa.class).findAll();
        empresas.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
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

    public int getTipo_match() {
        return tipo_match;
    }

    public void setTipo_match(int tipo_match) {
        this.tipo_match = tipo_match;
    }

    public int getId_match() {
        return id_match;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getCorreo1() {
        return correo1;
    }

    public void setCorreo1(String correo1) {
        this.correo1 = correo1;
    }

    public String getCorreo2() {
        return correo2;
    }

    public void setCorreo2(String correo2) {
        this.correo2 = correo2;
    }

    public RealmList<Certificado> getCertificados() {
        return certificados;
    }

    public void setCertificados(RealmList<Certificado> certificados) {
        this.certificados = certificados;
    }

    public RealmList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(RealmList<Producto> productos) {
        this.productos = productos;
    }

    public RealmList<SectorIndustrial> getSectorIndustriales() {
        return sectorIndustriales;
    }

    public void setSectorIndustriales(RealmList<SectorIndustrial> sectorIndustriales) {
        this.sectorIndustriales = sectorIndustriales;
    }
}
