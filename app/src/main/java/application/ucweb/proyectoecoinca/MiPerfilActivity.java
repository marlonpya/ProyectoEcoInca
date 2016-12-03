package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.ucweb.proyectoecoinca.adapter.TabMiPerfilAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.fragment.InformacionPerfilEditarFragment;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import application.ucweb.proyectoecoinca.model.UsuarioCertificacion;
import application.ucweb.proyectoecoinca.model.UsuarioSectorEmpresarial;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Preferencia;
import butterknife.BindView;
import butterknife.OnClick;
import me.originqiu.library.EditTag;

public class MiPerfilActivity extends BaseActivity {
    public static final String TAG = MiPerfilActivity.class.getSimpleName();
    @BindView(R.id.tab_layout) TabLayout tab_layout;
    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.iv_fondo_mi_perfil) ImageView fondo;
    private TabMiPerfilAdapter adapter;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        iniciarLayout();

        tab_layout.addTab(tab_layout.newTab());
        tab_layout.addTab(tab_layout.newTab());
        setupTabLayout();
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new TabMiPerfilAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    @OnClick(R.id.btnEditarPerfil)
    public void editarPerfil()  {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.m_confirmar_actualizacion)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestEditarPerfil();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }

    private Map<String, String> crearHashMap() throws JSONException {
        Fragment fragment = getSupportFragmentManager().findFragmentById(InformacionPerfilEditarFragment.ID);
        if (fragment != null) {
            String nombre_empresa   = ((EditText)fragment.getView().findViewById(R.id.et_nombre_editar)).getText().toString();
            String pais             = ((EditText)fragment.getView().findViewById(R.id.et_pais_editar)).getText().toString();
            String ciudad           = ((EditText)fragment.getView().findViewById(R.id.et_ciudad_editar)).getText().toString();
            String email            = ((EditText)fragment.getView().findViewById(R.id.et_email_editar)).getText().toString();
            String anio_f           = ((EditText)fragment.getView().findViewById(R.id.et_anio_fundacion_editar)).getText().toString();
            String descripcion_emp  = ((EditText)fragment.getView().findViewById(R.id.et_descripcion_editar)).getText().toString();
            String nombre_contaco   = ((EditText)fragment.getView().findViewById(R.id.et_nombre_contacto_editar)).getText().toString();
            String apellido         = ((EditText)fragment.getView().findViewById(R.id.et_apellido_editar)).getText().toString();
            String cargo            = ((EditText)fragment.getView().findViewById(R.id.et_cargo_contacto_editar)).getText().toString();
            String telefono         = ((EditText)fragment.getView().findViewById(R.id.et_telefono_contacto_editar)).getText().toString();
            String celular          = ((EditText)fragment.getView().findViewById(R.id.et_movil_contacto_editar)).getText().toString();
            String email_contacto   = ((EditText)fragment.getView().findViewById(R.id.et_email_contacto_editar)).getText().toString();
            String web              = ((EditText)fragment.getView().findViewById(R.id.et_website_contacto_editar)).getText().toString();
            String linkedin         = ((EditText)fragment.getView().findViewById(R.id.et_linkedin_contacto_editar)).getText().toString();

            List<String> productos  = ((EditTag)fragment.getView().findViewById(R.id.et_sector_producto_editar)).getTagList();

            Map<String, String> hashmap = new HashMap<>();
            JSONArray jsonArray = new JSONArray();
            JSONObject jEmpresa = new JSONObject();
            jEmpresa.put("nombre_empresa", nombre_empresa);
            jEmpresa.put("pais", pais);
            jEmpresa.put("ciudad", ciudad);
            jEmpresa.put("email", email);
            jEmpresa.put("anio_fundacion", anio_f);
            jEmpresa.put("descripcion", descripcion_emp);
            jEmpresa.put("nombre", nombre_contaco);
            jEmpresa.put("apellido", apellido);
            jEmpresa.put("cargo", cargo);
            jEmpresa.put("telefono", telefono);
            jEmpresa.put("celular", celular);
            jEmpresa.put("email_usuario", email_contacto);
            jEmpresa.put("website", web);
            jEmpresa.put("linkedin", linkedin);
            jsonArray.put(jEmpresa);

            JSONArray jSectorEmpresarial = new JSONArray();
            ArrayList<String> array_sector = UsuarioSectorEmpresarial.getSectoresEmpresariales();
            if (BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL).size() != 0) array_sector = BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL);
            for (String sector_emp : array_sector) {
                JSONObject json_sector_emp = new JSONObject();
                json_sector_emp.put("sector_empresarial", sector_emp);
                jSectorEmpresarial.put(json_sector_emp);
            }
            jsonArray.put(jSectorEmpresarial);

            JSONArray jProducto = new JSONArray();
            for (String producto : productos) {
                JSONObject json_producto = new JSONObject();
                json_producto.put("producto", producto);
                jProducto.put(json_producto);
            }
            jsonArray.put(jProducto);

            JSONArray jCertificacion = new JSONArray();
            ArrayList<String> array_certificacion = UsuarioCertificacion.getSectoresIndustriales();
            if (BuscarDetalle.getMarcados(BuscarDetalle.TIPO_CERTIFICACIONES).size() !=0) array_certificacion = BuscarDetalle.getMarcados(BuscarDetalle.TIPO_CERTIFICACIONES);
            for(String certificacion : array_certificacion) {
                JSONObject json_certificacion = new JSONObject();
                json_certificacion.put("certificado", certificacion);
                jCertificacion.put(json_certificacion);
            }
            jsonArray.put(jCertificacion);
            hashmap.put("actualizarEmpresa", jsonArray.toString());
            Log.d(TAG, hashmap.toString());
            return hashmap;
        }
        return null;
    }

    private void requestEditarPerfil() {
        showDialog(pDialog);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_ACTUALIZAR_EMPRESA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            JSONObject jData = new JSONObject(s);
                            Log.d(TAG, jData.toString());
                            hidepDialog(pDialog);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hidepDialog(pDialog);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hidepDialog(pDialog);
                        VolleyLog.d(volleyError.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                try {
                    param = crearHashMap();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return param;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    private void setupTabLayout() {
        TextView customTab1 = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.textview_tab_fragment_perfil, null);
        TextView customTab2 = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.textview_tab_fragment_perfil, null);
        customTab1.setText(R.string.informacion);
        tab_layout.getTabAt(0).setCustomView(customTab1);
        customTab2.setText(R.string.editar);
        tab_layout.getTabAt(1).setCustomView(customTab2);
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_mi_perfil));
        usarGlide(this, R.drawable.fondo_iniciar_sesion, fondo);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.app_name);
        pDialog.setMessage(getString(R.string.m_actualizando));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Preferencia(this).setActualizar_certificacion(false);
        new Preferencia(this).setActualizar_sector_empresarial(false);
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_EMPRESARIAL);
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_CERTIFICACIONES);
    }
}
