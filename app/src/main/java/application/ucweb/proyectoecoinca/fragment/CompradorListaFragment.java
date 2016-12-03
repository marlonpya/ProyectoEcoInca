package application.ucweb.proyectoecoinca.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.adapter.EmpresaAdapter;
import application.ucweb.proyectoecoinca.model.Empresa;
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
    private Realm realm;
    private EmpresaAdapter adapter;
    private RealmResults<Empresa> lista_empresas;

    public CompradorListaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comprador_lista, container, false);
        ButterKnife.bind(this, view);

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

}
