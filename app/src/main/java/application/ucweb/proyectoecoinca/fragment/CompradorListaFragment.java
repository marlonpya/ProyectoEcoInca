package application.ucweb.proyectoecoinca.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.adapter.EmpresaAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * FRAGMENT QUE GUARDA TODOS VENDEDORES QUE LO SIGUEN
 */
public class CompradorListaFragment extends Fragment {
    public static final String TAG = CompradorListaFragment.class.getSimpleName();
    @BindView(R.id.rrvListaEmpresas) RealmRecyclerView recyclerView;
    @BindView(R.id.layout_fragment_comprador_lista) LinearLayout layout;
    private Realm realm;
    private EmpresaAdapter adapter;
    private RealmResults<Empresa> lista_empresas;
    private ProgressDialog pDialog;

    public CompradorListaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comprador_lista, container, false);
        ButterKnife.bind(this, view);
        iniciarPDialog();

        if (ConexionBroadcastReceiver.isConnected()) requestMisSeguidores();

        cargarRRV();
        return view;
    }

    private void cargarRRV() {
        realm = Realm.getDefaultInstance();
        lista_empresas = realm.where(Empresa.class).findAll();
        adapter = new EmpresaAdapter(getActivity(), lista_empresas, true, true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void requestMisSeguidores() {
        BaseActivity.showDialog(pDialog);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_MIS_SEGUIDORES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        /*try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jData = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jData.length(); i++) {
                                Empresa empresa = new Empresa();
                                empresa.setId(Empresa.getUltimoId());
                                empresa.setId_server(jData.getJSONObject(i).getInt(""));
                                empresa.setTipo_negocio(jData.getJSONObject(i).getInt(""));
                                empresa.setTipo_match(jData.getJSONObject(i).getInt(""));
                                empresa.setTipo_empresa(jData.getJSONObject(i).getInt(""));
                                empresa.setPdf(jData.getJSONObject(i).getString(""));
                                empresa.setAnio_f(jData.getJSONObject(i).getString(""));
                                empresa.setCiudad(jData.getJSONObject(i).getString(""));
                                empresa.setDescripcion(jData.getJSONObject(i).getString(""));
                                empresa.setImagen(jData.getJSONObject(i).getString(""));
                                Empresa.registrarEmpresa(empresa);
                                Log.d(TAG, empresa.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                        BaseActivity.hidepDialog(pDialog);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        BaseActivity.hidepDialog(pDialog);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("idempresa", String.valueOf(Usuario.getUsuario().getId()));
                params.put("idtipoempresa", String.valueOf(Usuario.getUsuario().getTipo_empresa()));
                return params;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    private void iniciarPDialog() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle(R.string.app_name);
        pDialog.setMessage(getString(R.string.m_busqueda));
        pDialog.setCancelable(false);
    }

}
