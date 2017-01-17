package application.ucweb.proyectoecoinca;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import application.ucweb.proyectoecoinca.adapter.BuscarDetalleAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class BuscarDetalleListaActivity extends BaseActivity {
    public static final String TAG = BuscarDetalleListaActivity.class.getSimpleName();
    @BindView(R.id.rrvBusquedaDetalle) RealmRecyclerView realmRecyclerView;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    private Realm realm;
    private RealmResults<BuscarDetalle> lista;
    private BuscarDetalleAdapter adapter;
    private int tipo_busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_detalle_lista);
        iniciarLayout();

        iniciarRRV();
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.resultados_busqueda));
    }

    private void iniciarRRV() {
        tipo_busqueda = getIntent().getIntExtra(Constantes.POSICION_I_DETALLE_BUSCAR, 0);
        Log.d(TAG, "tipo_busqueda_"+String.valueOf(tipo_busqueda));
        realm = Realm.getDefaultInstance();
        lista = realm.where(BuscarDetalle.class).equalTo(BuscarDetalle.BUSDET_TIPO, tipo_busqueda).findAll();
        adapter = new BuscarDetalleAdapter(this, lista, true, true);
        realmRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
