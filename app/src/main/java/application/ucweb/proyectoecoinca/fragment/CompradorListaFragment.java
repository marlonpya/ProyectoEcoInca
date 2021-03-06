package application.ucweb.proyectoecoinca.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.adapter.EmpresaAdapter;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.Usuario;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * FRAGMENT QUE GUARDA TODOS VENDEDORES QUE LO SIGUEN
 */
public class CompradorListaFragment extends Fragment {
    public static final String TAG = CompradorListaFragment.class.getSimpleName();
    @BindView(R.id.rrvListaEmpresas) RealmRecyclerView recyclerView;
    @BindView(R.id.layout_fragment_comprador_lista) LinearLayout layout;
    private Realm realm;
    private RealmChangeListener realmChangeListener;
    private EmpresaAdapter adapter;
    private RealmResults<Empresa> lista;

    public CompradorListaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comprador_lista, container, false);
        ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                iniciarRRV();
            }
        };
        realm.addChangeListener(realmChangeListener);
        //cargarRRV();
        return view;
    }

    private void iniciarRRV() {
        /*lista_empresas = realm.where(Empresa.class).equalTo(Empresa.TIPO_EMPRESA, Empresa.E_CONTACTO)

                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_VENDEDOR).equalTo(Empresa.TIPO_MATCH,Empresa.M_ESPERA)
                .or()
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_AMBOS).equalTo(Empresa.TIPO_MATCH,Empresa.M_ESPERA).findAll();*/

        Usuario usuario = Usuario.getUsuario();
        if (usuario.getTipo_empresa() != Empresa.N_AMBOS) {
            lista = realm.where(Empresa.class).equalTo(Empresa.TIPO_MATCH, Empresa.M_ESPERA).findAll();
        } else {
            lista = realm.where(Empresa.class).equalTo(Empresa.POSICION, Empresa.IZQUIERDA).equalTo(Empresa.TIPO_MATCH, Empresa.M_ESPERA).findAll();
        }
        if (!lista.isEmpty()) {
            adapter = new EmpresaAdapter(getActivity(), lista);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        iniciarRRV();
        Log.d(TAG, lista.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }
}
