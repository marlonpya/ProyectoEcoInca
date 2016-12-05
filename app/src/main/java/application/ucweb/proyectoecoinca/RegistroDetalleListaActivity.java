package application.ucweb.proyectoecoinca;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import application.ucweb.proyectoecoinca.adapter.BuscarDetalleAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class RegistroDetalleListaActivity extends BaseActivity {
    public static final String TAG = RegistroDetalleListaActivity.class.getSimpleName();
    @BindView(R.id.rrvListaDetalleRegistro) RealmRecyclerView realmRecyclerView;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    private Realm realm;
    private BuscarDetalleAdapter adapter;
    private RealmResults<BuscarDetalle> lista;
    private int id_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_detalle_lista);
        id_intent = getIntent().getIntExtra(Constantes.POSICION_I_DETALLE_BUSCAR, -1);
        iniciarRRV();
    }

    private void iniciarRRV() {
        realm = Realm.getDefaultInstance();
        lista = realm.where(BuscarDetalle.class).equalTo(BuscarDetalle.BUSDET_TIPO, id_intent).findAll();
        adapter = new BuscarDetalleAdapter(this, lista, true, true);
        realmRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setToolbarSon(toolbar, this, getString(R.string.resultados_busqueda));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
