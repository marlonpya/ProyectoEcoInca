package application.ucweb.proyectoecoinca.model;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by ucweb02 on 09/11/2016.
 */
@RealmClass
public class Usuario extends RealmObject {
    public static final String TAG = Usuario.class.getSimpleName();

    public static final String ID = "id";
    @PrimaryKey
    private long id;
    private int id_empresa;
    @Required
    private String nombre_empresa;
    @Required
    private String pais;
    @Required
    private String ciudad;
    @Required
    private String email_empresa;
    @Required
    private String anio_fundacion;
    @Required
    private String descripcion;
    private int tipo_empresa;
    @Required
    private String imagen_empresa;
    @Required
    private String nombre_contacto;
    @Required
    private String apellido_contacto;
    @Required
    private String cargo_contacto;
    @Required
    private String telefono;
    @Required
    private String celular;
    @Required
    private String email_contacto;
    @Required
    private String web;
    @Required
    private String linkedin;
    private boolean plus;
    private boolean sesion;
    private int cantidad_busqueda;

    public static void iniciarSesion(Usuario usuario) {
        Realm realm = Realm.getDefaultInstance();
        Usuario user = realm.where(Usuario.class).equalTo(ID, 1).findFirst();
        realm.beginTransaction();
        if (user == null) {
            Usuario user2 = realm.createObject(Usuario.class);
            user2.setId(1);
            user2.setId_empresa(usuario.getId_empresa());
            user2.setTipo_empresa(usuario.getTipo_empresa());
            user2.setImagen_empresa(usuario.getImagen_empresa());
            user2.setNombre_empresa(usuario.getNombre_empresa());
            user2.setPais(usuario.getPais());
            user2.setCiudad(usuario.getCiudad());
            user2.setEmail_empresa(usuario.getEmail_empresa());
            user2.setAnio_fundacion(usuario.getAnio_fundacion());
            user2.setDescripcion(usuario.getDescripcion());
            user2.setNombre_contacto(usuario.getNombre_contacto());
            user2.setApellido_contacto(usuario.getApellido_contacto());
            user2.setCargo_contacto(usuario.getCargo_contacto());
            user2.setTelefono(usuario.getTelefono());
            user2.setCelular(usuario.getCelular());
            user2.setEmail_contacto(usuario.getEmail_contacto());
            user2.setWeb(usuario.getWeb());
            user2.setLinkedin(usuario.getLinkedin());
            user2.setPlus(usuario.isPlus());
            user2.setSesion(true);
            user2.setCantidad_busqueda(usuario.getCantidad_busqueda());
            realm.copyToRealmOrUpdate(user2);
            Log.d(TAG, user2.toString());
        } else {
            user.setId(1);
            user.setId_empresa(usuario.getId_empresa());
            user.setTipo_empresa(usuario.getTipo_empresa());
            user.setImagen_empresa(usuario.getImagen_empresa());
            user.setNombre_empresa(usuario.getNombre_empresa());
            user.setPais(usuario.getPais());
            user.setCiudad(usuario.getCiudad());
            user.setEmail_empresa(usuario.getEmail_empresa());
            user.setAnio_fundacion(usuario.getAnio_fundacion());
            user.setDescripcion(usuario.getDescripcion());
            user.setNombre_contacto(usuario.getNombre_contacto());
            user.setApellido_contacto(usuario.getApellido_contacto());
            user.setCargo_contacto(usuario.getCargo_contacto());
            user.setTelefono(usuario.getTelefono());
            user.setCelular(usuario.getCelular());
            user.setEmail_contacto(usuario.getEmail_contacto());
            user.setWeb(usuario.getWeb());
            user.setLinkedin(usuario.getLinkedin());
            user.setPlus(usuario.isPlus());
            user.setSesion(true);
            user.setCantidad_busqueda(usuario.getCantidad_busqueda());
            Log.d(TAG, user.toString());
        }
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, "iniciarSesion()");
    }

    public static void actualizarCantidadBusqueda(int cantidad) {
        Realm realm = Realm.getDefaultInstance();
        Usuario usuario = realm.where(Usuario.class).equalTo(ID, 1).findFirst();
        realm.beginTransaction();
        usuario.setId(usuario.getId());
        usuario.setCantidad_busqueda(cantidad);
        realm.copyFromRealm(usuario);
        Log.d(TAG, "actualizarCantidadBusqueda"+usuario.getNombre_empresa());
        realm.commitTransaction();
        realm.close();
    }

    public static void cerrarSesion() {
        Realm realm = Realm.getDefaultInstance();
        Usuario user = realm.where(Usuario.class).equalTo(ID, 1).findFirst();
        realm.beginTransaction();
        user.setId(1);
        user.setId_empresa(0);
        user.setTipo_empresa(0);
        user.setImagen_empresa("");
        user.setNombre_empresa("");
        user.setPais("");
        user.setCiudad("");
        user.setEmail_empresa("");
        user.setAnio_fundacion("");
        user.setDescripcion("");
        user.setNombre_contacto("");
        user.setApellido_contacto("");
        user.setCargo_contacto("");
        user.setTelefono("");
        user.setCelular("");
        user.setEmail_contacto("");
        user.setWeb("");
        user.setLinkedin("");
        user.setPlus(false);
        user.setSesion(false);
        user.setCantidad_busqueda(0);
        realm.commitTransaction();
        realm.close();
    }

    public static Usuario getUsuario() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Usuario.class).equalTo(ID, 1).findFirst();
    }

    public static void aumentarCantidadBusqueda(int cantidad) {
        if (cantidad > Usuario.getUsuario().getCantidad_busqueda()) {
            Realm realm = Realm.getDefaultInstance();
            Usuario usuario = getUsuario();
            realm.beginTransaction();
            usuario.setCantidad_busqueda(cantidad);
            realm.commitTransaction();
            realm.close();
        }
    }

    public static void actualizarUsuarioPlus(boolean valor) {
        Realm realm = Realm.getDefaultInstance();
        Usuario usuario = getUsuario();
        realm.beginTransaction();
        usuario.setPlus(valor);
        realm.commitTransaction();
        realm.close();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public int getTipo_empresa() {
        return tipo_empresa;
    }

    public void setTipo_empresa(int tipo_empresa) {
        this.tipo_empresa = tipo_empresa;
    }

    public String getImagen_empresa() {
        return imagen_empresa;
    }

    public void setImagen_empresa(String imagen_empresa) {
        this.imagen_empresa = imagen_empresa;
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

    public String getAnio_fundacion() {
        return anio_fundacion;
    }

    public void setAnio_fundacion(String anio_fundacion) {
        this.anio_fundacion = anio_fundacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmail_empresa() {
        return email_empresa;
    }

    public void setEmail_empresa(String email_empresa) {
        this.email_empresa = email_empresa;
    }

    public String getNombre_contacto() {
        return nombre_contacto;
    }

    public void setNombre_contacto(String nombre_contacto) {
        this.nombre_contacto = nombre_contacto;
    }

    public String getApellido_contacto() {
        return apellido_contacto;
    }

    public void setApellido_contacto(String apellido_contacto) {
        this.apellido_contacto = apellido_contacto;
    }

    public String getCargo_contacto() {
        return cargo_contacto;
    }

    public void setCargo_contacto(String cargo_contacto) {
        this.cargo_contacto = cargo_contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail_contacto() {
        return email_contacto;
    }

    public void setEmail_contacto(String email_contacto) {
        this.email_contacto = email_contacto;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public boolean isPlus() {
        return plus;
    }

    public void setPlus(boolean plus) {
        this.plus = plus;
    }

    public boolean isSesion() {
        return sesion;
    }

    public void setSesion(boolean sesion) {
        this.sesion = sesion;
    }

    public int getCantidad_busqueda() {
        return cantidad_busqueda;
    }

    public void setCantidad_busqueda(int cantidad_busqueda) {
        this.cantidad_busqueda = cantidad_busqueda;
    }
}
