package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import application.ucweb.proyectoecoinca.adapter.SlideAdapter;
import application.ucweb.proyectoecoinca.apis.FacebookA;
import application.ucweb.proyectoecoinca.apis.LinkedinA;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Buscar;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;

public class InicioActivity extends BaseActivity {
    public static final String TAG = InicioActivity.class.getSimpleName();
    @BindView(R.id.vp_inicio) ViewPager viewPager;
    @BindView(R.id.llhtml) LinearLayout linearLayout;
    @BindView(R.id.tv_registrarse_inicio) TextView texto;
    private ProgressDialog pDialog;

    private LayoutInflater inflater;
    private SlideAdapter adapter;
    private TextView[] dots;
    private int[] layouts = new int[]{
            R.layout.vista1,
            R.layout.vista2,
            R.layout.vista3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        iniciarPDialog();
        texto.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        addBottomDots(0);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adapter = new SlideAdapter(inflater, this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(listener);

        //INICIAR DATOS DE BD REALM !!!!!!!
        if(BuscarDetalle.getUltimoId() == 0 ) {
            BuscarDetalle.cargarPais();
        } //SESION PARA DESARROLLO, NO TE OLVIDES DE BORRARLO D:

        BuscarDetalle.cargarEmpresarial(this);
        BuscarDetalle.cargarCertificaciones(this);
        verificarSesion();
    }

    private void iniciarPDialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.app_name);
        pDialog.setMessage(getString(R.string.m_actualizando));
        pDialog.setCancelable(false);
    }

    @OnClick(R.id.btnIrALogin)
    public void irALogin() {
        startActivity(new Intent(this, IniciarSesionActivity.class));
    }

    @OnClick(R.id.btnIrARegistrar)
    public void irARegistrar() {
        startActivity(new Intent(this, RegistroActivity.class));
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        linearLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#A0A0A0"));
            linearLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.WHITE);
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            addBottomDots(position);
        }

        @Override
        public void onPageSelected(int position) { }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };

    /**
     * VALIDAMOS LAS SESIONES FACEBOOK, LINKEDIN O ECO-INCA
     */
    private void verificarSesion() {
        boolean sesion = false;
        if (Usuario.getUsuario() != null) { sesion = Usuario.getUsuario().isSesion(); }
        if (FacebookA.iniciado() || LinkedinA.iniciado(this) || sesion) {
            Intent intent = new Intent(this, PrincipalActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void requestPaisDepartamentos() {
        hidepDialog(pDialog);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_PAISES_DEPARTAMENTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            Realm realm = Realm.getDefaultInstance();
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jData1 = jsonObject.getJSONArray("data");
                            JSONArray jData2 = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jData1.length(); i++) {
                                int id = BuscarDetalle.getUltimoId();
                                realm.beginTransaction();
                                BuscarDetalle pais = realm.createObject(BuscarDetalle.class);
                                pais.setId(id);
                                pais.setDescripcion(jData1.getJSONObject(i).getString("pais"));
                                pais.setTipo(BuscarDetalle.TIPO_PAIS);
                                realm.copyFromRealm(pais);
                                realm.commitTransaction();

                                for (int j = 0; j < jData2.length(); i ++) {
                                    realm.beginTransaction();
                                    BuscarDetalle departamento = realm.createObject(BuscarDetalle.class);
                                    departamento.setId(BuscarDetalle.getUltimoId());
                                    departamento.setDescripcion(jData2.getJSONObject(i).getString("departamento"));
                                    departamento.setTipo(BuscarDetalle.TIPO_DEPARTAMENTO);
                                    departamento.setDepartamento_fk(id);
                                    realm.copyFromRealm(departamento);
                                    realm.commitTransaction();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }
}
