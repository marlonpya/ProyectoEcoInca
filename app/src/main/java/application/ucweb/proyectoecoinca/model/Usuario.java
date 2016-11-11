package application.ucweb.proyectoecoinca.model;

import android.util.Log;

import java.util.Date;

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
    @Required
    private String nombre_empresa;
    @Required
    private String tipo_empresa;
    @Required
    private String imagen_empresa;
    @Required
    private String ciudad;
    @Required
    private String pais;
    @Required
    private Date anio_fundacion;
    @Required
    private String descripcion;

    public static void iniciarSesionFB(Usuario usuario) {
        Realm realm = Realm.getDefaultInstance();
        Usuario user = realm.where(Usuario.class).equalTo(ID, 1).findFirst();
        realm.beginTransaction();
        if (user == null) {
            Usuario user2 = realm.createObject(Usuario.class);
            user2.setId(1);
            user2.setNombre_empresa(usuario.getNombre_empresa());
            user2.setTipo_empresa(usuario.getTipo_empresa());
            user2.setImagen_empresa(usuario.getImagen_empresa());
            user2.setCiudad(usuario.getCiudad());
            user2.setPais(usuario.getPais());
            user2.setAnio_fundacion(usuario.getAnio_fundacion());
            user2.setDescripcion(usuario.getDescripcion());
            realm.copyToRealmOrUpdate(user2);
            Log.d(TAG, user2.toString());
        } else {
            user.setId(1);
            user.setNombre_empresa(usuario.getNombre_empresa());
            user.setTipo_empresa(usuario.getTipo_empresa());
            user.setImagen_empresa(usuario.getImagen_empresa());
            user.setCiudad(usuario.getCiudad());
            user.setPais(usuario.getPais());
            user.setAnio_fundacion(usuario.getAnio_fundacion());
            user.setDescripcion(usuario.getDescripcion());
            Log.d(TAG, user.toString());
        }
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, "iniciarSesionFB()");
    }

    public static Usuario getUsuario() {
        Realm realm = Realm.getDefaultInstance();
        Usuario usuario = realm.where(Usuario.class).equalTo(ID, 1).findFirst();
        if (usuario != null) {
            return usuario;
        } else{
            Log.e(TAG, "getUsuario: USUARIO NULLO");
            return null;
        }
    }

    public static void sesionDesarrollo() {
        Realm realm = Realm.getDefaultInstance();
        Usuario user = realm.where(Usuario.class).equalTo(ID, 1).findFirst();
        if (user == null) {
            realm.beginTransaction();
            Usuario usuario = realm.createObject(Usuario.class);
            usuario.setId(1);
            usuario.setNombre_empresa("ECO INCA");
            usuario.setTipo_empresa("Comprador");
            usuario.setImagen_empresa("http://www.ecoinca.com/site/templates/eco_inca_b/images/logo.jpg");
            usuario.setCiudad("Lima");
            usuario.setPais("Perú");
            usuario.setAnio_fundacion(new Date(12, 0, 0));
            usuario.setDescripcion("blkgdklsdglsdgkllsdgljljljllljksdgllsdglsglnsgjl");
            realm.copyToRealmOrUpdate(usuario);
            realm.commitTransaction();
            Log.d(TAG, usuario.toString());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getTipo_empresa() {
        return tipo_empresa;
    }

    public void setTipo_empresa(String tipo_empresa) {
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

    public Date getAnio_fundacion() {
        return anio_fundacion;
    }

    public void setAnio_fundacion(Date anio_fundacion) {
        this.anio_fundacion = anio_fundacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
