package application.ucweb.proyectoecoinca;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import application.ucweb.proyectoecoinca.adapter.EmpresaResultadoAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Empresa;
import butterknife.BindView;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class BuscarResultadoListaActivity extends BaseActivity {
    public static final String TAG = BuscarResultadoListaActivity.class.getSimpleName();
    @BindView(R.id.rrvListaBuscar) RealmRecyclerView recyclerView;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.tv_resultados_lista_buscar) TextView cantidad_encontrados;
    private Realm realm;
    private EmpresaResultadoAdapter adapter;
    private RealmResults<Empresa> lista_empresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_resultado_lista);
        iniciarLayout();

        cargarRRV();
    }

    private void cargarRRV() {
        realm = Realm.getDefaultInstance();
        lista_empresas = realm.where(Empresa.class).equalTo(Empresa.TIPO_EMPRESA, Empresa.E_BUSQUEDA).findAll();
        adapter = new EmpresaResultadoAdapter(this, lista_empresas);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cantidad_encontrados.setText(String.valueOf(lista_empresas.size()));
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.resultados_busqueda));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Empresa.eliminarPorTipoEmpresa(Empresa.E_BUSQUEDA);
    }
}
