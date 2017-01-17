package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import application.ucweb.proyectoecoinca.model.Usuario;
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

        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_EMPRESARIAL);
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_PAIS);
    }

    private ArrayList<Buscar> getListaBusqueda() {
        lista_busqueda = new ArrayList<>();
        lista_busqueda.add(new Buscar(getString(R.string.empresarial), R.drawable.imagen_row_buscar_industria));
        lista_busqueda.add(new Buscar(getString(R.string.b_pais), R.drawable.imagen_row_buscar_pais));
        return lista_busqueda;
    }

    @OnClick(R.id.btnBuscar)
    public void buscar() {
        if (ConexionBroadcastReceiver.isConnected()) {
            if (Usuario.getUsuario().isPlus() || Usuario.getUsuario().getCantidad_busqueda() <= 7) {
                if (validarBusqueda()) {
                    requestBusquedaSimple();
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.app_name)
                        .setMessage(R.string.m_usuario_no_plus)
                        .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(BuscarActivity.this, PlusActivity.class));
                            }
                        })
                        .setNegativeButton(R.string.cancelar, null)
                        .show();
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
                        Empresa.eliminarPorTipoEmpresa(Empresa.E_BUSQUEDA);
                        try {
                            JSONObject jData = new JSONObject(s);
                            Log.d(TAG, jData.toString());
                            JSONArray jArray = jData.getJSONArray("data");
                            for (int i = 0; i < jArray.length(); i++) {
                                Empresa empresa = new Empresa();
<<<<<<< HEAD
                                empresa.setId(Empresa.getUltimoId());
=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
                                empresa.setId_server(jArray.getJSONObject(i).getInt("EMP_ID"));
                                empresa.setNombre(jArray.getJSONObject(i).getString("EMP_NOMBRE"));
                                empresa.setTipo_negocio(jArray.getJSONObject(i).getInt("EMP_TIPO"));
                                empresa.setImagen(jArray.getJSONObject(i).getString("EMP_IMAGEN"));
                                empresa.setPdf(jArray.getJSONObject(i).getString("EMP_PDF"));
                                empresa.setDescripcion(jArray.getJSONObject(i).getString("EMP_DESCRIPCION"));
                                empresa.setCiudad(jArray.getJSONObject(i).getString("EMP_CIUDAD"));
                                empresa.setPais(jArray.getJSONObject(i).getString("EMP_PAIS"));
                                empresa.setAnio_f(jArray.getJSONObject(i).getString("EMP_ANIO_FUNDACION"));
                                empresa.setTipo_match(Empresa.M_DESCONOCIDO);
                                empresa.setId_match(Empresa.ID_MACTH_DEFAULT);
                                empresa.setTipo_empresa(Empresa.E_BUSQUEDA);
                                Empresa.registrarEmpresa(empresa);
                            }
                            Log.d(TAG, String.valueOf(jData.getInt("cantbusqueda")));
                            Usuario.aumentarCantidadBusqueda(jData.getInt("cantbusqueda"));
                            hidepDialog(pDialog);
                            if (jData.getBoolean("status")) startActivity(new Intent(BuscarActivity.this, BuscarResultadoListaActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hidepDialog(pDialog);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hidepDialog(pDialog);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                try {
                    params = getHashBusqueda();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
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
        pDialog.setCancelable(false);
    }

    private HashMap<String, String> getHashBusqueda() throws JSONException {
        HashMap<String, String> hashMap = new HashMap<>();
        JSONArray jsonArray = new JSONArray();

        JSONObject jCriterio = new JSONObject();
        jCriterio.put("criterio", et_busqueda.getText().toString().trim());
        jCriterio.put("tipo", Usuario.getUsuario().getTipo_empresa());
        jCriterio.put("idempresa", Usuario.getUsuario().getId_empresa());
        jsonArray.put(jCriterio);

        JSONArray jEmpresarial = new JSONArray();
        for (int i = 0; i < BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL).size(); i++) {
            JSONObject jOEmpresarial = new JSONObject();
            jOEmpresarial.put("empresarial", BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL).get(i));
            jEmpresarial.put(jOEmpresarial);
        }
        jsonArray.put(jEmpresarial);

        JSONArray jPais = new JSONArray();
        for (int j = 0; j < BuscarDetalle.getMarcados(BuscarDetalle.TIPO_PAIS).size(); j++) {
            JSONObject jOPais = new JSONObject();
            jOPais.put("pais", BuscarDetalle.getMarcados(BuscarDetalle.TIPO_PAIS).get(j));
            jPais.put(jOPais);
        }
        jsonArray.put(jPais);

        hashMap.put("buscarEmpresa",jsonArray.toString());
        Log.d(TAG, hashMap.toString());
        return hashMap;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_EMPRESARIAL);
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_PAIS);
    }
}
