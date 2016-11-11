package application.ucweb.proyectoecoinca;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import application.ucweb.proyectoecoinca.adapter.SectorIndustrialAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Preferencia;
import butterknife.BindView;
import butterknife.OnClick;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class RegistroDetalleListaActivity extends BaseActivity {
    public static final String TAG = RegistroDetalleListaActivity.class.getSimpleName();
    @BindView(R.id.rrvListaDetalleRegistro) RealmRecyclerView realmRecyclerView;
    private Realm realm;
    private SectorIndustrialAdapter adapter;
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
        adapter = new SectorIndustrialAdapter(this, lista, true, true);
        realmRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btnAceptarDetalleListaRegistrar)
    public void aceptarDetalleListaRegistrar() { onBackPressed(); }

    @Override
    public void onBackPressed() {
        String buffer = "";
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).isSeleccionado()) {
                buffer += "- "+lista.get(i).getDescripcion().toUpperCase()+ "\n";
            }
        }
        switch (id_intent){
            case 1: Preferencia.setProducto(buffer, this); break;
            case 2: Preferencia.setServicio(buffer, this); break;
            case 4: Preferencia.setIndustria(buffer, this); break;
            case 6: Preferencia.setCertificado(buffer, this); break;
        }
        Log.d(TAG, buffer);
        Log.d(TAG, lista.toString());
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
