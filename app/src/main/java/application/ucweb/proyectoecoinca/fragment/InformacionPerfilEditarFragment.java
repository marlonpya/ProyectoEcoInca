package application.ucweb.proyectoecoinca.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.RegistroDetalleListaActivity;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.model.UsuarioCertificacion;
import application.ucweb.proyectoecoinca.model.UsuarioProducto;
import application.ucweb.proyectoecoinca.model.UsuarioSectorEmpresarial;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Preferencia;
import application.ucweb.proyectoecoinca.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.originqiu.library.EditTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformacionPerfilEditarFragment extends Fragment {
    public static final String TAG = InformacionPerfilEditarFragment.class.getSimpleName();
    public static int ID = 0;
    @BindView(R.id.ll_btn__pais) LinearLayout flecha_pais;
    @BindView(R.id.ll_btn_ciudad) LinearLayout flecha_ciudad;
    @BindView(R.id.btnSectorEmpresarial) LinearLayout flecha_sec_industria;
    //@BindView(R.id.btnServicio) LinearLayout flecha_servicio;
    @BindView(R.id.btnCertificado) LinearLayout flecha_certificado;

    @BindView(R.id.et_nombre_editar) EditText et_nombre_editar;
    @BindView(R.id.et_pais_editar) EditText et_pais;
    @BindView(R.id.et_ciudad_editar) EditText et_ciudad;
    @BindView(R.id.et_email_editar) EditText et_email;
    @BindView(R.id.et_anio_fundacion_editar) EditText et_anio_fundacion;
    @BindView(R.id.et_descripcion_editar) EditText et_descripcion;
    @BindView(R.id.et_sector_empresarial_editar) EditText et_sector_empresarial;
    @BindView(R.id.et_sector_producto_editar) EditTag et_sector_producto;
    @BindView(R.id.et_certificado_editar) EditText et_certificado;
    @BindView(R.id.et_nombre_contacto_editar) EditText et_nombre_contacto;
    @BindView(R.id.et_apellido_editar) EditText et_apellido;
    @BindView(R.id.et_cargo_contacto_editar) EditText et_cargo_contacto;
    @BindView(R.id.et_telefono_contacto_editar) EditText et_telefono_contacto;
    @BindView(R.id.et_movil_contacto_editar) EditText et_movil_contacto;
    @BindView(R.id.et_email_contacto_editar) EditText et_email_contacto;
    @BindView(R.id.et_website_contacto_editar) EditText et_website_contacto;
    @BindView(R.id.et_linkedin_contacto_editar) EditText et_linkedin_contacto;

    public InformacionPerfilEditarFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_registrarse_sin_opcion, container, false);
        ButterKnife.bind(this, view);
        iniciarLayout();
        instanciarSesion();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (new Preferencia(getActivity()).isActualizar_sector_empresarial() && BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL).size() != 0)
            et_sector_empresarial.setText(Util.generarLista(BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL)));
        if (new Preferencia(getActivity()).isActualizar_certificacion() && BuscarDetalle.getMarcados(BuscarDetalle.TIPO_CERTIFICACIONES).size() != 0)
            et_certificado.setText(Util.generarLista(BuscarDetalle.getMarcados(BuscarDetalle.TIPO_CERTIFICACIONES)));
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

    @OnClick(R.id.btnSectorEmpresarial)
    public void irSectorEmpresarial() {
        new Preferencia(getActivity()).setActualizar_sector_empresarial(true);
        startActivity(new Intent(getActivity().getApplicationContext(), RegistroDetalleListaActivity.class)
                .putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, BuscarDetalle.TIPO_EMPRESARIAL));
    }

    @OnClick(R.id.btnCertificado)
    public void irCertificado() {
        new Preferencia(getActivity()).setActualizar_certificacion(true);
        startActivity(new Intent(getActivity().getApplicationContext(), RegistroDetalleListaActivity.class)
                .putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, BuscarDetalle.TIPO_CERTIFICACIONES));
    }

    private void iniciarLayout() {
        LinearLayout linearLayouts[] = {flecha_pais, flecha_ciudad, flecha_sec_industria, flecha_certificado};
        for (LinearLayout linearLayout : linearLayouts){
            linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.efecto_plomo_transparente));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ID = getId();
    }

    private void instanciarSesion() {
        if (Usuario.getUsuario() != null) {
            Usuario usuario = Usuario.getUsuario();
            et_nombre_editar.setText(usuario.getNombre_empresa());
            et_pais.setText(usuario.getPais());
            et_ciudad.setText(usuario.getCiudad());
            et_email.setText(usuario.getEmail_empresa());
            et_anio_fundacion.setText(usuario.getAnio_fundacion());
            et_descripcion.setText(usuario.getDescripcion());
            et_sector_empresarial.setText(Util.generarLista(UsuarioSectorEmpresarial.getSectoresEmpresariales()));
            et_sector_producto.setTagList(UsuarioProducto.getProductos());
            et_certificado.setText(Util.generarLista(UsuarioCertificacion.getSectoresIndustriales()));
            et_nombre_contacto.setText(usuario.getNombre_contacto());
            et_apellido.setText(usuario.getApellido_contacto());
            et_cargo_contacto.setText(usuario.getCargo_contacto());
            et_telefono_contacto.setText(usuario.getTelefono());
            et_movil_contacto.setText(usuario.getCelular());
            et_email_contacto.setText(usuario.getEmail_contacto());
            et_website_contacto.setText(usuario.getWeb());
            et_linkedin_contacto.setText(usuario.getLinkedin());
        }
    }
}
