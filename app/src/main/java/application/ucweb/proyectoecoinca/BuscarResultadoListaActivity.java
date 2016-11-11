package application.ucweb.proyectoecoinca;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
        if (Empresa.getUltimoId() == 0) pruebaRRV();
        cargarRRV();
    }

    private void cargarRRV() {
        realm = Realm.getDefaultInstance();
        lista_empresas = realm.where(Empresa.class).findAll();
        adapter = new EmpresaResultadoAdapter(this, lista_empresas, true, true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cantidad_encontrados.setText(String.valueOf(lista_empresas.size()));
    }

    public static void pruebaRRV() {
        int[] array = { R.drawable.icono_ejemplo_empresa01,R.drawable.icono_ejemplo_empresa02,
                R.drawable.icono_ejemplo_empresa03,R.drawable.icono_empresa04,R.drawable.icono_empresa05,
                R.drawable.icono_empresa06, R.drawable.icono_empresa07, R.drawable.icono_empresa08,
                R.drawable.icono_empresa09, R.drawable.icono_empresa10, R.drawable.icono_empresa11};
        int[] tipos = {0,1,2,0,1,2,0,1,2,0,1};
        Realm realm = Realm.getDefaultInstance();
        for (int i = 0; i < array.length; i++) {
            realm.beginTransaction();
            Empresa empresa = realm.createObject(Empresa.class);
            empresa.setId(Empresa.getUltimoId());
            empresa.setNombre("Empresa "+ String.valueOf((i + 1)));
            empresa.setImagen(array[i]);
            empresa.setTipo(tipos[i]);
            realm.copyToRealm(empresa);
            realm.commitTransaction();
            Log.d(TAG, empresa.toString());
        }
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.resultados_busqueda));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
