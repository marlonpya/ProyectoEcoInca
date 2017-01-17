package application.ucweb.proyectoecoinca;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import application.ucweb.proyectoecoinca.adapter.MisContactosAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MisContactosActivity extends BaseActivity {
    public static final String TAG = MisContactosActivity.class.getSimpleName();
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
<<<<<<< HEAD
        requestContactos();
=======

>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
        iniciarRRV();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setOnRefreshListener(new RealmRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
<<<<<<< HEAD
                requestContactos();
=======

>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
            }
        });
    }

    private void requestContactos() {
        recyclerView.setRefreshing(true);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_CONTACTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            JSONObject jData = new JSONObject(s);
<<<<<<< HEAD
                            if(jData.getBoolean("status")){
                                JSONArray jArray = jData.getJSONArray("data");
                                for (int i = 0; i < jArray.length(); i++) {
                                    Empresa empresa = new Empresa();
                                    empresa.setId(Empresa.getUltimoId());
                                    empresa.setId_server(jArray.getJSONObject(i).getInt("EMP_ID"));
                                    empresa.setNombre(jArray.getJSONObject(i).getString("EMP_NOMBRE"));
                                    empresa.setTipo_negocio(jArray.getJSONObject(i).getInt("EMP_TIPO"));
                                    empresa.setImagen(jArray.getJSONObject(i).getString("EMP_IMAGEN"));
                                    empresa.setPdf(jArray.getJSONObject(i).getString("EMP_PDF"));
                                    empresa.setDescripcion(jArray.getJSONObject(i).getString("EMP_DESCRIPCION"));
                                    empresa.setCiudad(jArray.getJSONObject(i).getString("EMP_CIUDAD"));
                                    empresa.setPais(jArray.getJSONObject(i).getString("EMP_PAIS"));
                                    empresa.setAnio_f(jArray.getJSONObject(i).getString("EMP_ANIO_FUNDACION"));
                                    empresa.setTipo_match(Empresa.M_ACEPTADO);
                                    empresa.setId_match(Empresa.ID_MACTH_DEFAULT);
                                    Empresa.registrarEmpresa(empresa);
                                }
                                adapter.notifyDataSetChanged();
                                recyclerView.setRefreshing(false);
                            }
=======
                            JSONArray jArray = jData.getJSONArray("data");
                            for (int i = 0; i < jArray.length(); i++) {
                                Empresa empresa = new Empresa();
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
                                Empresa.registrarEmpresa(empresa);
                            }
                            recyclerView.setRefreshing(false);
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
                        } catch (JSONException e) {
                            e.printStackTrace();
                            recyclerView.setRefreshing(false);
                        }
<<<<<<< HEAD

=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.d(volleyError.getMessage());
                        recyclerView.setRefreshing(false);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
<<<<<<< HEAD
                params.put("idempresa", String.valueOf(Usuario.getUsuario().getId_empresa()));
                params.put("idtipo",String.valueOf(Usuario.getUsuario().getTipo_empresa()));
=======
                params.put("id_empresa", String.valueOf(Usuario.getUsuario().getId_empresa()));
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
                return params;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    private void iniciarRRV() {
        realm = Realm.getDefaultInstance();
        lista_empresas = realm.where(Empresa.class).equalTo(Empresa.TIPO_EMPRESA, Empresa.E_CONTACTO)
                .or()
                .equalTo(Empresa.TIPO_MATCH, Empresa.M_ACEPTADO).findAll();
        adapter = new MisContactosAdapter(this, lista_empresas, true, true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
<<<<<<< HEAD

        Log.d(TAG, lista_empresas.toString());
=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
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
