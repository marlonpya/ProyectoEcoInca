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
        //iniciarPDialog();

        //if (ConexionBroadcastReceiver.isConnected()) requestMisSeguidores();

        cargarRRV();
        return view;
    }

    private void cargarRRV() {
        realm = Realm.getDefaultInstance();
        lista_empresas = realm.where(Empresa.class).equalTo(Empresa.TIPO_EMPRESA, Empresa.E_CONTACTO)
<<<<<<< HEAD
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_VENDEDOR).equalTo(Empresa.TIPO_MATCH,Empresa.M_ESPERA)
                .or()
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_AMBOS).equalTo(Empresa.TIPO_MATCH,Empresa.M_ESPERA).findAll();
=======
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_VENDEDOR)
                .or()
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_AMBOS).findAll();
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
        adapter = new EmpresaAdapter(getActivity(), lista_empresas, true, true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

<<<<<<< HEAD
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        Log.d(TAG, lista_empresas.toString());
    }

=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
    private void iniciarPDialog() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle(R.string.app_name);
        pDialog.setMessage(getString(R.string.m_busqueda));
        pDialog.setCancelable(false);
    }
}
