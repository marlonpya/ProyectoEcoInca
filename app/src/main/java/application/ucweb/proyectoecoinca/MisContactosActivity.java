package application.ucweb.proyectoecoinca;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import application.ucweb.proyectoecoinca.adapter.EmpresaResultadoAdapter;
import application.ucweb.proyectoecoinca.adapter.MisContactosAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Empresa;
import butterknife.BindView;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MisContactosActivity extends BaseActivity {
    @BindView(R.id.rrvListaMisContactos) RealmRecyclerView recyclerView;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    private Realm realm;
    private RealmResults<Empresa> lista_empresas;
    private MisContactosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_contactos);
        iniciarLayout();
        iniciarRRV();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setOnRefreshListener(new RealmRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    private void iniciarRRV() {
        realm = Realm.getDefaultInstance();
        lista_empresas = realm.where(Empresa.class).findAll();
        adapter = new MisContactosAdapter(this, lista_empresas, true, true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_contactos));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
