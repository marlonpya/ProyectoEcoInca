package application.ucweb.proyectoecoinca.model.detalle;

import application.ucweb.proyectoecoinca.model.Empresa;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by ucweb03 on 15/02/2017.
 */

public class SectorIndustrial extends RealmObject {

    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static void createOrUpdate(String sector, int idEmpresa) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        SectorIndustrial insert_sector = realm.createObject(SectorIndustrial.class);
        insert_sector.setDescripcion(sector);

        Empresa empresa = realm.where(Empresa.class).equalTo(Empresa.ID_SERVER, idEmpresa).findFirst();
        empresa.getSectorIndustriales().add(insert_sector);
        realm.commitTransaction();
        realm.close();
    }

    public static void delete(int idEmpresa) {
        Realm realm = Realm.getDefaultInstance();
        Empresa empresa = realm.where(Empresa.class).equalTo(Empresa.ID_SERVER, idEmpresa).findFirst();
        RealmList<SectorIndustrial> sectorIndustriales = empresa.getSectorIndustriales();
        realm.beginTransaction();
        sectorIndustriales.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }
}
