package application.ucweb.proyectoecoinca.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import application.ucweb.proyectoecoinca.BuscarActivity;
import application.ucweb.proyectoecoinca.MisContactosActivity;
import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.VamosAlNegocioActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    public InicioFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btnIrABuscar)
    public void irABuscar() { startActivity(new Intent(getActivity().getApplicationContext(), BuscarActivity.class)); }

    @OnClick(R.id.btnIrANegocio)
    public void irANegocio() { startActivity(new Intent(getActivity().getApplicationContext(), VamosAlNegocioActivity.class)); }

    @OnClick(R.id. btnIrAMiPerfil)
    public void irAMiPerfil() {startActivity(new Intent(getActivity().getApplicationContext(), MisContactosActivity.class)); }

}
