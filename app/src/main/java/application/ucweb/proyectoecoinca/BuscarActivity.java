package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import application.ucweb.proyectoecoinca.adapter.BuscarAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Buscar;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Preferencia;
import butterknife.BindView;
import butterknife.OnClick;

public class BuscarActivity extends BaseActivity {
    public static final String TAG = BuscarActivity.class.getSimpleName();
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.rvBusqueda) RecyclerView recyclerView;
    @BindView(R.id.et_descripcion_de_busqueda) EditText et_busqueda;
    @BindView(R.id.layout_activity_buscar) LinearLayout layout;
    private ArrayList<Buscar> lista_busqueda;
    private BuscarAdapter adapter;
    private ProgressDialog pDialog;
    private Preferencia preferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        iniciarLayout();

        preferencia = new Preferencia(this);
    }

    private ArrayList<Buscar> getListaBusqueda() {
        lista_busqueda = new ArrayList<>();
        lista_busqueda.add(new Buscar(getString(R.string.industria), R.drawable.imagen_row_buscar_industria));
        lista_busqueda.add(new Buscar(getString(R.string.b_pais), R.drawable.imagen_row_buscar_pais));
        return lista_busqueda;
    }

    @OnClick(R.id.btnBuscar)
    public void buscar() {
        if(ConexionBroadcastReceiver.isConnected()) {
//            if (preferencia.isBusqueda_pais()) { if (validarBusqueda()) requestBusquedaSimple(); }

        } else {
            ConexionBroadcastReceiver.showSnack(layout, this);
        }
        startActivity(new Intent(this, BuscarResultadoListaActivity.class));
    }

    private void requestBusquedaSimple() {
        showDialog(pDialog);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_BUSQUEDA_SIMPLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return super.getParams();
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    private boolean validarBusqueda() {
        if (!et_busqueda.getText().toString().isEmpty()) {
            return true;
        } else {
            Toast.makeText(this, R.string.m_ingrese_todos_campos, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_buscar));
        adapter = new BuscarAdapter(this, getListaBusqueda());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.app_name);
        pDialog.setMessage(getString(R.string.m_busqueda));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
