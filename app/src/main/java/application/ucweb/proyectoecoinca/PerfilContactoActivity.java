package application.ucweb.proyectoecoinca;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.detalle.Certificado;
import application.ucweb.proyectoecoinca.model.detalle.Producto;
import application.ucweb.proyectoecoinca.model.detalle.SectorIndustrial;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmList;

public class PerfilContactoActivity extends BaseActivity {
    public static final String TAG = MiPerfilEmpresaActivity.class.getSimpleName();
    @BindView(R.id.con_toolbar) Toolbar toolbar;
    @BindView(R.id.layout_activity_perfil_contacto) CoordinatorLayout layout;
    @BindView(R.id.iv_perfil_empresa) ImageView iv_perfil_empresa;
    @BindView(R.id.tv_descripcion_empresa) TextView tv_descripcion;
    @BindView(R.id.tv_nombre_empresa) TextView tv_nombre;
    @BindView(R.id.tv_ciudad_empresa) TextView tv_ciudad;
    @BindView(R.id.tv_pais_empresa) TextView tv_pais;
    @BindView(R.id.tv_sector_empresarial_empresa) TextView tv_sector_emp;
    @BindView(R.id.tv_productos) TextView tv_productos;
    @BindView(R.id.tv_certificados) TextView tv_certificados;
    @BindView(R.id.tv_anio_fundacion) TextView tv_anio_fundacion;
    @BindView(R.id.tv_web) TextView tv_web;
    @BindView(R.id.tv_telefono) TextView tv_telefono;
    @BindView(R.id.tv_correo) TextView tv_correo;
    private Realm realm;
    private Empresa empresa;
    private int id_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_activity_perfil_contacto);

        realm = Realm.getDefaultInstance();
        iniciarLayout();
        intentRealm();
    }

    private void intentRealm() {
        id_intent = getIntent().getIntExtra(Constantes.L_ID_EMPRESA, -1);
        empresa = realm.where(Empresa.class).equalTo(Empresa.ID, id_intent).findFirst();
        if (empresa != null) {
            Log.d(TAG, empresa.toString());
            usarGlide(this, empresa.getImagen(), iv_perfil_empresa);
            tv_descripcion.setText(empresa.getDescripcion().isEmpty() && empresa.getDescripcion() == null ? "-" : empresa.getDescripcion());
            tv_nombre.setText(empresa.getNombre().isEmpty() && empresa.getNombre() == null ? "-" : empresa.getNombre());
            tv_ciudad.setText(empresa.getCiudad().isEmpty() && empresa.getCiudad() == null ? "-" : empresa.getCiudad());
            tv_pais.setText(empresa.getPais().isEmpty() && empresa.getPais() == null ? "-" : empresa.getPais());

            String sector_emp = "";
            RealmList<SectorIndustrial> sectorIndustrials = empresa.getSectorIndustriales();
            for (int i = 0; i < sectorIndustrials.size(); i++) {
                sector_emp += sectorIndustrials.get(i).getDescripcion() + "\n";
            }
            tv_sector_emp.setText(sector_emp.isEmpty() ? "-" : sector_emp);

            String producto = "";
            RealmList<Producto> productos = empresa.getProductos();
            for (int i = 0; i < productos.size(); i++) {
                producto += " " + productos.get(i).getDescripcion() + ",";
                if (i + 1 == producto.length()) producto += ".";
            }
            tv_productos.setText(producto.isEmpty() ? "-" : producto);

            String certificado = "";
            RealmList<Certificado> certificados = empresa.getCertificados();
            for (int i = 0; i < certificados.size(); i++) {
                certificado += certificados.get(i).getDescripcion() + "\n";
            }
            tv_certificados.setText(certificado.isEmpty() ? "-" : certificado);
            tv_anio_fundacion.setText(empresa.getAnio_f().isEmpty() && empresa.getAnio_f() == null ? "-" : empresa.getAnio_f());
            tv_web.setText(empresa.getWeb().trim().isEmpty() || empresa.getWeb() == null ? "-" : empresa.getWeb());
            if (empresa.getTelefono1() != null && !empresa.getTelefono1().isEmpty() && empresa.getTelefono2() != null && !empresa.getTelefono2().isEmpty()) {
                tv_telefono.setText(empresa.getTelefono1() + "\n" + empresa.getTelefono2());
            } else if (empresa.getTelefono1() != null && !empresa.getTelefono1().isEmpty()) {
                tv_telefono.setText(empresa.getTelefono1());
            } else if (empresa.getTelefono2() != null && !empresa.getTelefono2().isEmpty()) {
                tv_telefono.setText(empresa.getTelefono1());
            }

            if (empresa.getCorreo1() != null && !empresa.getCorreo1().isEmpty() && empresa.getCorreo2() != null && !empresa.getTelefono2().isEmpty()) {
                tv_correo.setText(empresa.getCorreo1() + "\n" + empresa.getCorreo2());
            } else if (empresa.getCorreo1() != null && !empresa.getCorreo1().isEmpty()) {
                tv_correo.setText(empresa.getCorreo1());
            } else if (empresa.getCorreo2() != null && !empresa.getCorreo2().isEmpty()) {
                tv_correo.setText(empresa.getCorreo2());
            }
        }
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_mi_perfil));
        toolbar.setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
