package application.ucweb.proyectoecoinca;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import application.ucweb.proyectoecoinca.adapter.EmpresaBusquedaAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.EmpresaSerializable;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;

public class BuscarResultadoListaActivity extends BaseActivity {
    public static final String TAG = BuscarResultadoListaActivity.class.getSimpleName();
    @BindView(R.id.rrvListaBuscar) RecyclerView recyclerView;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.tv_resultados_lista_buscar) TextView cantidad_encontrados;
    private EmpresaBusquedaAdapter adapter;
    private ArrayList<EmpresaSerializable> lista_empresas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_resultado_lista);
        iniciarLayout();

        cargarRRV();
    }

    private void cargarRRV() {
        lista_empresas = (ArrayList<EmpresaSerializable>) getIntent().getSerializableExtra(Constantes.EXTRA_SERIALIZABLE_BUSQUEDA);
        adapter = new EmpresaBusquedaAdapter(this, lista_empresas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
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
