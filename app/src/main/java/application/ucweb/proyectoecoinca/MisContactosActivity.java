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
import application.ucweb.proyectoecoinca.model.detalle.Certificado;
import application.ucweb.proyectoecoinca.model.detalle.Producto;
import application.ucweb.proyectoecoinca.model.detalle.SectorIndustrial;
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
                /*if (Usuario.getUsuario().getTipo_empresa() == Empresa.N_AMBOS)
                    requestContactosAmbos();
                 else
                    requestContactos();*/
                requestContactos();
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
                            boolean status = jObject.getBoolean("status");
                            if (status) {
                                JSONArray jData = jObject.getJSONArray("data");
                                Empresa.eliminarContactos();
                                listadoContactos(jData);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.toString(), e);
                        }
                        adapter.notifyDataSetChanged();
                        recyclerView.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        recyclerView.setRefreshing(false);
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
    }

    private void requestContactosAmbos() {
        recyclerView.setRefreshing(true);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_CONTACTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                JSONArray jSeguidor = jsonObject.getJSONArray("dataseguido");
                                JSONArray jSeguido = jsonObject.getJSONArray("dataseguidor");
                                Empresa.eliminarContactos();
                                listadoContactos(jSeguidor);
                                listadoContactos(jSeguido);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.toString(), e);
                        }
                        recyclerView.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        recyclerView.setRefreshing(false);
                        VolleyLog.e(volleyError.toString(), volleyError);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idempresa", String.valueOf(Usuario.getUsuario().getId_empresa()));
                params.put("idtipo", String.valueOf(Usuario.getUsuario().getTipo_empresa()));
                Log.d(TAG, params.toString());
                return params;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    private void listadoContactos(JSONArray jSeguidor) throws JSONException {
        if (jSeguidor != null && jSeguidor.length() >= 0) {
            for (int i = 0; i < jSeguidor.length(); i++) {
                JSONObject jEmpresa = jSeguidor.getJSONObject(i);
                Empresa empresa = new Empresa();
                empresa.setId(Empresa.getUltimoId());
                empresa.setId_server(jEmpresa.getInt("EMP_ID"));
                empresa.setNombre(jEmpresa.getString("EMP_NOMBRE"));
                empresa.setTipo_empresa(jEmpresa.getInt("EMP_TIPO"));
                empresa.setImagen(jEmpresa.getString("EMP_IMAGEN"));
                empresa.setDescripcion(jEmpresa.getString("EMP_DESCRIPCION"));
                empresa.setCiudad(jEmpresa.getString("EMP_CIUDAD"));
                empresa.setPais(jEmpresa.getString("EMP_PAIS"));
                empresa.setAnio_f(jEmpresa.getString("EMP_ANIO_FUNDACION"));
                empresa.setTipo_match(Empresa.M_ACEPTADO);
                empresa.setId_match(Empresa.ID_MACTH_DEFAULT);
                empresa.setTipo_empresa(Empresa.E_CONTACTO);
                empresa.setTelefono1(jEmpresa.getString("CON_TELEFONO"));
                empresa.setTelefono2(jEmpresa.getString("CON_CELULAR"));
                empresa.setCorreo1(jEmpresa.getString("EMP_EMAIL"));
                empresa.setCorreo2(jEmpresa.getString("CON_EMAIL"));
                empresa.setWeb(jEmpresa.getString("CON_WEB_SITE"));
                Empresa.registrarEmpresa(empresa);

                final int idEmpresa = jEmpresa.getInt("EMP_ID");
                JSONObject jExtra = jSeguidor.getJSONObject(i).getJSONObject("CERTIFICADO_INDUSTRIA_PRODUCTOS");
                //if (jExtra.names().getString(i).equals("CERTIFICADO_INDUSTRIA_PRODUCTOS")) {

                    JSONArray jCertificado = jExtra.getJSONArray("CERTIFICADO");
                    if (jCertificado != null && jCertificado.length() >= 0) {
                        Certificado.delete(idEmpresa);
                        if (jCertificado.length() > 0) {
                            for (int j = 0; j < jCertificado.length(); j++) {
                                Certificado.createOrUpdate(jCertificado.getJSONObject(j).getString("CER_NOMBRE"), idEmpresa);
                            }
                        }
                    }

                    JSONArray jIndustria = jExtra.getJSONArray("INDUSTRIAL");
                    if (jIndustria != null && jIndustria.length() >= 0) {
                        SectorIndustrial.delete(idEmpresa);
                        if (jIndustria.length() > 0) {
                            for (int j = 0; j < jIndustria.length(); j++) {
                                SectorIndustrial.createOrUpdate(jIndustria.getJSONObject(j).getString("SECIND_NOMBRE"), idEmpresa);
                            }
                        }
                    }

                    JSONArray jProducto = jExtra.getJSONArray("PRODUCTOS");
                    if (jProducto != null && jProducto.length() >= 0) {
                        Producto.delete(idEmpresa);
                        if (jProducto.length() > 0) {
                            for (int j = 0; j < jProducto.length(); j++) {
                                Producto.createOrUpdate(jProducto.getJSONObject(j).getString("PRO_NOMBRE"), idEmpresa);
                            }
                        }
                    }
                //}
            }
        }
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
