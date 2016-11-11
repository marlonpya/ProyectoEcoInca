package application.ucweb.proyectoecoinca;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import io.realm.Realm;
public class MiPerfilEmpresaActivity extends BaseActivity {
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.iv_fondo_mi_perfil_empresa) ImageView fondo;
    @BindView(R.id.iv_perfil_empresa) ImageView imagen_empresa;
    @BindView(R.id.tv_nombre_empresa) TextView nombre_empresa;
    private long id;
    private Realm realm;
    private Empresa empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil_empresa);
        iniciarLayout();
        recibirId();
    }

    private void recibirId() {
        if (getIntent().hasExtra(Constantes.L_ID_EMPRESA)) {
            id = getIntent().getLongExtra(Constantes.L_ID_EMPRESA, -1);
            realm = Realm.getDefaultInstance();
            empresa = realm.where(Empresa.class).equalTo(Empresa.ID, id).findFirst();
            usarGlideCircular(this, empresa.getImagen(), imagen_empresa);
            nombre_empresa.setText(empresa.getNombre());
        }
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_mi_perfil));
        usarGlide(this, R.drawable.fondo_iniciar_sesion, fondo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
