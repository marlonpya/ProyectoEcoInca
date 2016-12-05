package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.ucweb.proyectoecoinca.adapter.BuscarAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Buscar;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        iniciarLayout();
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
            if (validarBusqueda()) {
                if (!BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL).isEmpty() &&
                        !BuscarDetalle.getMarcados(BuscarDetalle.TIPO_PAIS).isEmpty()) {
                    Log.d(TAG, "empresarial y pais");
                }else if (!BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL).isEmpty()) {
                    Log.d(TAG, "empresarial");
                } else if (!BuscarDetalle.getMarcados(BuscarDetalle.TIPO_PAIS).isEmpty()) {
                    Log.d(TAG, "pais");
                } else {
                    requestBusquedaSimple();
                    Log.d(TAG, "por defecto");
                }
            }
        } else {
            ConexionBroadcastReceiver.showSnack(layout, this);
        }
    }

    private void requestBusquedaSimple() {
        showDialog(pDialog);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_BUSQUEDA_SIMPLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            JSONObject jData = new JSONObject(s);
                            JSONArray jArray = jData.getJSONArray("resultado");
                            for (int i = 0; i < jArray.length(); i++) {
                                Empresa empresa = new Empresa();
                                empresa.setNombre(jArray.getJSONObject(i).getString("nombre"));
                                empresa.setTipo_negocio(jArray.getJSONObject(i).getInt("tipo_negocio"));
                                empresa.setImagen(jArray.getJSONObject(i).getString("imagen"));
                                Empresa.registrarEmpresa(empresa, Empresa.E_BUSQUEDA);
                            }
                            //eliminar los anteriores empresas
                            //if (jData.getBoolean("status")) startActivity(new Intent(BuscarActivity.this, BuscarResultadoListaActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    protected void onDestroy() {
        super.onDestroy();
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_EMPRESARIAL);
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_PAIS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private HashMap<String, String> getHashBusqueda()throws JSONException {
        HashMap<String, String> hashMap = new HashMap<>();
        JSONArray jsonArray = new JSONArray();

        JSONObject jCriterio = new JSONObject();
        jCriterio.put("criterio", et_busqueda.getText().toString().trim());
        jsonArray.put(jCriterio);

        JSONArray jEmpresarial = new JSONArray();
        for (int i = 0; i < BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL).size(); i++) {
            JSONObject jOEmpresarial = new JSONObject();
            jOEmpresarial.put("empresarial", BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL).get(i));
            jEmpresarial.put(jOEmpresarial);
        }
        jEmpresarial.put(jsonArray);

        JSONArray jPais = new JSONArray();
        for (int j = 0; j < BuscarDetalle.getMarcados(BuscarDetalle.TIPO_PAIS).size(); j++) {
            JSONObject jOPais = new JSONObject();
            jOPais.put("pais", BuscarDetalle.getMarcados(BuscarDetalle.TIPO_PAIS).get(j));
            jPais.put(jOPais);
        }
        jPais.put(jsonArray);

        hashMap.put("buscar",jsonArray.toString());
        return hashMap;
    }
}
