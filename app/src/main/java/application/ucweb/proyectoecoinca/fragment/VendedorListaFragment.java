package application.ucweb.proyectoecoinca.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.adapter.EmpresaResultadoAdapter;
import application.ucweb.proyectoecoinca.model.Empresa;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * FRAGMENT QUE GUARDA TODOS LOS COMPRADORES A LOS QUE SIGUES
 */
public class VendedorListaFragment extends Fragment {

    public static final String TAG = VendedorListaFragment.class.getSimpleName();

    @BindView(R.id.rrvListaSeguirVendedor) RealmRecyclerView realmRecyclerView;
    private Realm realm;
    private RealmChangeListener realmChangeListener;
    private RealmResults<Empresa> lista;
    private EmpresaResultadoAdapter adapter;

    public VendedorListaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendedor_lista, container, false);
        ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();
        //iniciarRRV();
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                iniciarRRV();
            }
        };
        realm.addChangeListener(realmChangeListener);
        return view;
    }

    private void iniciarRRV() {
        /*lista = realm.where(Empresa.class).equalTo(Empresa.TIPO_EMPRESA, Empresa.E_CONTACTO)
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_COMPRADOR).equalTo(Empresa.TIPO_MATCH, Empresa.M_ESPERA)
                .or()
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_AMBOS).equalTo(Empresa.TIPO_MATCH,Empresa.M_ESPERA).findAll();*/
        lista = realm.where(Empresa.class).equalTo(Empresa.POSICION, Empresa.DERECHA).equalTo(Empresa.TIPO_MATCH, Empresa.M_ESPERA).findAll();
        if (!lista.isEmpty()) {
            adapter = new EmpresaResultadoAdapter(getActivity(), lista);
            realmRecyclerView.setAdapter(adapter);
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
