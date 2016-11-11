package application.ucweb.proyectoecoinca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import application.ucweb.proyectoecoinca.adapter.BuscarAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Buscar;
import butterknife.BindView;
import butterknife.OnClick;

public class BuscarActivity extends BaseActivity {
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.rvBusqueda) RecyclerView recyclerView;
    private ArrayList<Buscar> lista_busqueda;
    private BuscarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        iniciarLayout();
    }

    private ArrayList<Buscar> listaBusqueda() {
        lista_busqueda = new ArrayList<>();
        lista_busqueda.add(new Buscar(getString(R.string.industria), R.drawable.imagen_row_buscar_industria));
        lista_busqueda.add(new Buscar(getString(R.string.b_pais), R.drawable.imagen_row_buscar_pais));
        return lista_busqueda;
    }

    @OnClick(R.id.btnBuscar)
    public void buscar() {
        startActivity(new Intent(this, BuscarResultadoListaActivity.class));
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_buscar));
        adapter = new BuscarAdapter(this, listaBusqueda());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
