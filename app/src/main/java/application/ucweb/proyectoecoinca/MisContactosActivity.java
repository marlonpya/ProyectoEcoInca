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

        requestContactos();
        realm = Realm.getDefaultInstance();
        iniciarRRV();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setOnRefreshListener(new RealmRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Usuario.getUsuario().getTipo_empresa() == Empresa.N_AMBOS) {

                } else {
                    requestContactos();
                }
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
                            JSONObject jObject = new JSONObject(s);

                            if(jObject.getBoolean("status")) {
                                JSONArray jData = jObject.getJSONArray("data");
                                for (int i = 0; i < jData.length(); i++) {
                                    Empresa empresa = new Empresa();
                                    empresa.setId(Empresa.getUltimoId());
                                    empresa.setId_server(jData.getJSONObject(i).getInt("EMP_ID"));
                                    empresa.setNombre(jData.getJSONObject(i).getString("EMP_NOMBRE"));
                                    empresa.setTipo_negocio(jData.getJSONObject(i).getInt("EMP_TIPO"));
                                    empresa.setImagen(jData.getJSONObject(i).getString("EMP_IMAGEN"));
                                    empresa.setDescripcion(jData.getJSONObject(i).getString("EMP_DESCRIPCION"));
                                    empresa.setCiudad(jData.getJSONObject(i).getString("EMP_CIUDAD"));
                                    empresa.setPais(jData.getJSONObject(i).getString("EMP_PAIS"));
                                    empresa.setAnio_f(jData.getJSONObject(i).getString("EMP_ANIO_FUNDACION"));
                                    empresa.setTipo_match(Empresa.M_ACEPTADO);
                                    empresa.setId_match(Empresa.ID_MACTH_DEFAULT);
                                    empresa.setTipo_empresa(Empresa.E_CONTACTO);
                                    empresa.setPosicion(Empresa.getPos(jData.getJSONObject(i).getInt("EMP_TIPO")));
                                    empresa.setWeb(jData.getJSONObject(i).getString("CON_WEB_SITE"));
                                    empresa.setTelefono1(jData.getJSONObject(i).getString("CON_TELEFONO"));
                                    empresa.setTelefono2(jData.getJSONObject(i).getString("CON_CELULAR"));
                                    empresa.setCorreo1(jData.getJSONObject(i).getString("CON_EMAIL"));
                                    empresa.setCorreo2(jData.getJSONObject(i).getString("CON_WEB_SITE"));
                                    Empresa.registrarEmpresa(empresa);
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, e.toString(), e);
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.d(volleyError.toString(), volleyError);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idempresa", String.valueOf(Usuario.getUsuario().getId_empresa()));
                params.put("idtipo",String.valueOf(Usuario.getUsuario().getTipo_empresa()));
                return params;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
        recyclerView.setRefreshing(false);
    }

    private void requestContactosAmbos() {

    }

    private void iniciarRRV() {
        lista_empresas = realm.where(Empresa.class).equalTo(Empresa.TIPO_MATCH, Empresa.M_ACEPTADO).findAll();
        adapter = new MisContactosAdapter(this, lista_empresas);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d(TAG, lista_empresas.toString());
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_contactos));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
