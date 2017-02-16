package application.ucweb.proyectoecoinca.model.detalle;

import application.ucweb.proyectoecoinca.model.Empresa;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by ucweb03 on 15/02/2017.
 */

public class Producto extends RealmObject {

    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static void createOrUpdate(String producto, int idEmpresa) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Producto insert_producto = realm.createObject(Producto.class);
        insert_producto.setDescripcion(producto);

        Empresa empresa = realm.where(Empresa.class).equalTo(Empresa.ID_SERVER, idEmpresa).findFirst();
        empresa.getProductos().add(insert_producto);
        realm.commitTransaction();
        realm.close();
    }

    public static void delete(int idEmpresa) {
        Realm realm = Realm.getDefaultInstance();
        Empresa empresa = realm.where(Empresa.class).equalTo(Empresa.ID_SERVER, idEmpresa).findFirst();
        RealmList<Certificado> certificados = empresa.getCertificados();
        realm.beginTransaction();
        certificados.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }
}
