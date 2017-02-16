package application.ucweb.proyectoecoinca.model.detalle;

import application.ucweb.proyectoecoinca.model.Empresa;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ucweb03 on 15/02/2017.
 */

public class Certificado extends RealmObject {

    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static void createOrUpdate(String certificado, int idEmpresa) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Certificado insert_certificado = realm.createObject(Certificado.class);
        insert_certificado.setDescripcion(certificado);

        Empresa empresa = realm.where(Empresa.class).equalTo(Empresa.ID_SERVER, idEmpresa).findFirst();
        empresa.getCertificados().add(insert_certificado);
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
