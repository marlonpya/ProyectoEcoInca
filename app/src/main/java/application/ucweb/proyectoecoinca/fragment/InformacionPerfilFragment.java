package application.ucweb.proyectoecoinca.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformacionPerfilFragment extends Fragment {
    public static final String TAG = InformacionPerfilFragment.class.getSimpleName();
    @BindView(R.id.tv_perfil_ciudad) TextView ciudad;
    @BindView(R.id.tv_perfil_pais) TextView pais;
    @BindView(R.id.tv_perfil_anio_fundacion) TextView anio_fundacion;
    @BindView(R.id.tv_perfil_descripcion) TextView descripcion;

    public InformacionPerfilFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informacion_perfil, container, false);
        ButterKnife.bind(this, view);

        instanciarSesion();
        return view;
    }

    private void instanciarSesion() {
        Usuario usuario = Usuario.getUsuario();
        ciudad.setText(usuario.getCiudad());
        pais.setText(usuario.getPais());
        anio_fundacion.setText(usuario.getAnio_fundacion());
        descripcion.setText(usuario.getDescripcion());
    }

}
