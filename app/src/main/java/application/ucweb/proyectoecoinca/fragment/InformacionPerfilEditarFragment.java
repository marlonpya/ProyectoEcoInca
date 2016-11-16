package application.ucweb.proyectoecoinca.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformacionPerfilEditarFragment extends Fragment {
    public static final String TAG = InformacionPerfilEditarFragment.class.getSimpleName();
    @BindView(R.id.et_pais_registro) EditText et_pais;
    @BindView(R.id.et_ciudad_registro) EditText et_ciudad;
    @BindView(R.id.ll_btn__pais) LinearLayout flecha_pais;
    @BindView(R.id.ll_btn_ciudad) LinearLayout flecha_ciudad;
    @BindView(R.id.btnSectorIndustrial) LinearLayout flecha_sec_industria;
    //@BindView(R.id.btnServicio) LinearLayout flecha_servicio;
    @BindView(R.id.btnCertificado) LinearLayout flecha_certificado;

    public InformacionPerfilEditarFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_registrarse_sin_opcion, container, false);
        ButterKnife.bind(this, view);
        iniciarLayout();

        return view;
    }

    @OnClick(R.id.ll_btn__pais)
    public void dialogoPais() {
        new AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(Constantes.getPaises(), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_pais.setText(Constantes.getPaises()[which]);
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @OnClick(R.id.ll_btn_ciudad)
    public void dialogoCiudad() {
        new AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(Constantes.ARRAY_DEPARTAMENTOS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_ciudad.setText(Constantes.ARRAY_DEPARTAMENTOS[which]);
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void iniciarLayout() {
        LinearLayout linearLayouts[] = {flecha_pais, flecha_ciudad, flecha_sec_industria, flecha_certificado};
        for (LinearLayout linearLayout : linearLayouts){
            linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.efecto_plomo_transparente));
        }
    }
}
