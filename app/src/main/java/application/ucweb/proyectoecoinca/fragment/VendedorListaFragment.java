package application.ucweb.proyectoecoinca.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
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
import io.realm.RealmResults;

/**
 * FRAGMENT QUE GUARDA TODOS LOS COMPRADORES A LOS QUE SIGUES
 */
public class VendedorListaFragment extends Fragment {
<<<<<<< HEAD
    public static final String TAG = VendedorListaFragment.class.getSimpleName();
=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
    @BindView(R.id.rrvListaSeguirVendedor) RealmRecyclerView realmRecyclerView;
    private Realm realm;
    private RealmResults<Empresa> lista;
    private EmpresaResultadoAdapter adapter;

    public VendedorListaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendedor_lista, container, false);
        ButterKnife.bind(this, view);

        iniciarRRV();
        return view;
    }

    private void iniciarRRV() {
        realm = Realm.getDefaultInstance();
        lista = realm.where(Empresa.class).equalTo(Empresa.TIPO_EMPRESA, Empresa.E_CONTACTO)
<<<<<<< HEAD
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_COMPRADOR).equalTo(Empresa.TIPO_MATCH, Empresa.M_ESPERA)
                .or()
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_AMBOS).equalTo(Empresa.TIPO_MATCH,Empresa.M_ESPERA).findAll();
=======
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_COMPRADOR)
                .or()
                .equalTo(Empresa.TIPO_NEGOCIO, Empresa.N_AMBOS).findAll();
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8

        adapter = new EmpresaResultadoAdapter(getActivity(), lista, true, true);
        realmRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
<<<<<<< HEAD

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        Log.d(TAG, lista.toString());
    }
=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
}
