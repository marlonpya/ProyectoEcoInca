package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

import application.ucweb.proyectoecoinca.adapter.SeguirAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.model.detalle.Certificado;
import application.ucweb.proyectoecoinca.model.detalle.Producto;
import application.ucweb.proyectoecoinca.model.detalle.SectorIndustrial;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;

public class VamosAlNegocioActivity extends BaseActivity {
    public static final String TAG = VamosAlNegocioActivity.class.getSimpleName();
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.tl_seguir) TabLayout tab_layout;
    @BindView(R.id.vp_seguir) ViewPager pager;
    private SeguirAdapter adapter;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vamos_al_negocio);
        iniciarLayout();

        if (ConexionBroadcastReceiver.isConnected()) {
            if (Usuario.getUsuario().getTipo_empresa() != Empresa.N_AMBOS) {
                requestMisSeguidores();
            } else {
                requestMisSeguidorestipo2();
            }
        }
        tab_layout.addTab(tab_layout.newTab());
        tab_layout.addTab(tab_layout.newTab());
        setupTabLayout();
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        if (Usuario.getUsuario().getTipo_empresa() == Empresa.N_VENDEDOR) {
            adapter = new SeguirAdapter(getSupportFragmentManager(), 1, Empresa.N_VENDEDOR);
            pager.setAdapter(adapter);

            tab_layout.getTabAt(1).select();
            LinearLayout tabStrip = ((LinearLayout)tab_layout.getChildAt(0));
            for(int i = 0; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
            pager.setCurrentItem(0);
            pager.setOnTouchListener(null);
        } else if (Usuario.getUsuario().getTipo_empresa() == Empresa.N_COMPRADOR) {
            adapter = new SeguirAdapter(getSupportFragmentManager(), 1, Empresa.N_COMPRADOR);
            pager.setAdapter(adapter);

            tab_layout.getTabAt(0).select();
            LinearLayout tabStrip = ((LinearLayout)tab_layout.getChildAt(0));
            for(int i = 0; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
            pager.setCurrentItem(1);
            pager.setOnTouchListener(null);
        } else {
            adapter = new SeguirAdapter(getSupportFragmentManager(), tab_layout.getTabCount(), Empresa.N_AMBOS);
            pager.setAdapter(adapter);
            //tab_layout.setupWithViewPager(pager);
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
    }

    private void requestMisSeguidorestipo2() {
        BaseActivity.showDialog(pDialog);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_MIS_SEGUIDORES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            boolean status = jsonObject.getBoolean("status");
                            JSONArray jSeguido = jsonObject.getJSONArray("dataseguido");
                            JSONArray jSeguidor = jsonObject.getJSONArray("dataseguidor");
                            listadoNoContacto(jSeguido, status);
                            listadoNoContacto(jSeguidor, status);
                            /*JSONArray jData2 = jsonObject.getJSONArray("dataseguidor");
                            for (int i = 0; i < jData2.length(); i++) {
                                Empresa empresa = new Empresa();
                                empresa.setId(Empresa.getUltimoId());
                                empresa.setId_server(jData2.getJSONObject(i).getInt("EMP_ID"));
                                empresa.setNombre(jData2.getJSONObject(i).getString("EMP_NOMBRE"));
                                empresa.setTipo_negocio(jData2.getJSONObject(i).getInt("EMP_TIPO"));
                                empresa.setImagen(jData2.getJSONObject(i).getString("EMP_IMAGEN"));
                                empresa.setCiudad(jData2.getJSONObject(i).getString("EMP_CIUDAD"));
                                empresa.setPais(jData2.getJSONObject(i).getString("EMP_PAIS"));
                                empresa.setAnio_f(jData2.getJSONObject(i).getString("EMP_ANIO_FUNDACION"));
                                empresa.setDescripcion(jData2.getJSONObject(i).getString("EMP_DESCRIPCION"));
                                empresa.setTipo_match(Empresa.M_ESPERA);
                                empresa.setTipo_empresa(Empresa.E_CONTACTO);
                                empresa.setId_match(jData2.getJSONObject(i).getInt("SEG_ID"));
                                empresa.setPosicion(Empresa.DERECHA);
                                empresa.setWeb(jData2.getJSONObject(i).getString("CON_WEB_SITE"));
                                empresa.setTelefono1(jData2.getJSONObject(i).getString("CON_TELEFONO"));
                                empresa.setTelefono2(jData2.getJSONObject(i).getString("CON_CELULAR"));
                                empresa.setCorreo1(jData2.getJSONObject(i).getString("EMP_EMAIL"));
                                empresa.setCorreo2(jData2.getJSONObject(i).getString("CON_EMAIL"));
                                Empresa.registrarEmpresa(empresa);
                                Log.d(TAG, empresa.toString());

                                final int idEmpresa = jData2.getJSONObject(i).getInt("EMP_ID");
                                JSONObject jExtra = jData2.getJSONObject(i);
                                if (jExtra.names().getString(i).equals("CERTIFICADO_INDUSTRIA")) {

                                    if (jExtra.has("CERTIFICADO")) {
                                        JSONArray jCertificado = jExtra.getJSONArray("CERTIFICADO");
                                        if (jCertificado != null && jCertificado.length() >= 0) {
                                            Certificado.delete(idEmpresa);
                                            if (jCertificado.length() > 0) {
                                                for (int k = 0; k < jCertificado.length(); k++) {
                                                    Certificado.createOrUpdate(jCertificado.getJSONObject(k).getString("CER_NOMBRE"), idEmpresa);
                                                }
                                            }
                                        }
                                    }

                                    JSONArray jIndustria = jExtra.getJSONArray("INDUSTRIAL");
                                    if (jIndustria != null && jIndustria.length() >= 0) {
                                        SectorIndustrial.delete(idEmpresa);
                                        if (jIndustria.length() > 0) {
                                            for (int k = 0; k < jIndustria.length(); k++) {
                                                SectorIndustrial.createOrUpdate(jIndustria.getJSONObject(k).getString("SECIND_NOMBRE"), idEmpresa);
                                            }
                                        }
                                    }

                                    JSONArray jProducto = jExtra.getJSONArray("PRODUCTOS");
                                    if (jProducto != null && jProducto.length() >= 0) {
                                        Producto.delete(idEmpresa);
                                        if (jProducto.length() > 0) {
                                            for (int k = 0; k > jProducto.length(); k++) {
                                                Producto.createOrUpdate(jProducto.getJSONObject(k).getString("PRO_NOMBRE"), idEmpresa);
                                            }
                                        }
                                    }
                                }
                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.toString(), e);
                        }
                        BaseActivity.hidepDialog(pDialog);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        BaseActivity.hidepDialog(pDialog);
                        VolleyLog.e(volleyError.toString(), volleyError);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("idempresa", String.valueOf(Usuario.getUsuario().getId_empresa()));
                params.put("idtipoempresa", String.valueOf(Usuario.getUsuario().getTipo_empresa()));
                return params;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    private void listadoNoContacto(JSONArray jData, boolean status) throws JSONException {
        if (status) {
            for (int i = 0; i < jData.length(); i++) {
                Empresa empresa = new Empresa();

                empresa.setId(Empresa.getUltimoId());
                empresa.setId_server(jData.getJSONObject(i).getInt("EMP_ID"));
                empresa.setNombre(jData.getJSONObject(i).getString("EMP_NOMBRE"));
                empresa.setTipo_negocio(jData.getJSONObject(i).getInt("EMP_TIPO"));
                empresa.setImagen(jData.getJSONObject(i).getString("EMP_IMAGEN"));
                empresa.setCiudad(jData.getJSONObject(i).getString("EMP_CIUDAD"));
                empresa.setPais(jData.getJSONObject(i).getString("EMP_PAIS"));
                empresa.setAnio_f(jData.getJSONObject(i).getString("EMP_ANIO_FUNDACION"));
                empresa.setDescripcion(jData.getJSONObject(i).getString("EMP_DESCRIPCION"));
                empresa.setTipo_match(Empresa.M_ESPERA);
                empresa.setTipo_empresa(Empresa.E_CONTACTO);
                empresa.setId_match(jData.getJSONObject(i).getInt("SEG_ID"));
                empresa.setPosicion(Empresa.IZQUIERDA);
                empresa.setWeb(jData.getJSONObject(i).getString("CON_WEB_SITE"));
                empresa.setTelefono1(jData.getJSONObject(i).getString("CON_TELEFONO"));
                empresa.setTelefono2(jData.getJSONObject(i).getString("CON_CELULAR"));
                empresa.setCorreo1(jData.getJSONObject(i).getString("EMP_EMAIL"));
                empresa.setCorreo2(jData.getJSONObject(i).getString("CON_EMAIL"));
                Empresa.registrarEmpresa(empresa);

                final int idEmpresa = jData.getJSONObject(i).getInt("EMP_ID");
                JSONObject jExtra = jData.getJSONObject(i);
                if (jExtra.names().getString(i).equals("CERTIFICADO_INDUSTRIA")) {
                    JSONArray jCertificado = jExtra.getJSONArray("CERTIFICADO");
                    if (jCertificado != null && jCertificado.length() >= 0) {
                        Certificado.delete(idEmpresa);
                        if (jCertificado.length() > 0) {
                            for (int k = 0; k < jCertificado.length(); k++) {
                                Certificado.createOrUpdate(jCertificado.getJSONObject(k).getString("CER_NOMBRE"), idEmpresa);
                            }
                        }
                    }

                    JSONArray jIndustria = jExtra.getJSONArray("INDUSTRIAL");
                    if (jIndustria != null && jIndustria.length() >= 0) {
                        SectorIndustrial.delete(idEmpresa);
                        if (jIndustria.length() > 0) {
                            for (int k = 0; k < jIndustria.length(); k++) {
                                SectorIndustrial.createOrUpdate(jIndustria.getJSONObject(k).getString("SECIND_NOMBRE"), idEmpresa);
                            }
                        }
                    }

                    JSONArray jProducto = jExtra.getJSONArray("PRODUCTOS");
                    if (jProducto != null && jProducto.length() >= 0) {
                        Producto.delete(idEmpresa);
                        if (jProducto.length() > 0) {
                            for (int k = 0; k > jProducto.length(); k++) {
                                Producto.createOrUpdate(jProducto.getJSONObject(k).getString("PRO_NOMBRE"), idEmpresa);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setupTabLayout() {
        TextView customTab1 = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.textview_tab_fragment_perfil, null);
        TextView customTab2 = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.textview_tab_fragment_perfil, null);
        switch (Usuario.getUsuario().getTipo_empresa()) {
            case Empresa.N_COMPRADOR : customTab2.setTextColor(getResources().getColor(R.color.plomo));   break;
            case Empresa.N_VENDEDOR : customTab1.setTextColor(getResources().getColor(R.color.plomo));    break;
        }
        customTab1.setText(R.string.soy_comprador);
        tab_layout.getTabAt(0).setCustomView(customTab1);
        customTab2.setText(R.string.soy_vendedor);
        tab_layout.getTabAt(1).setCustomView(customTab2);
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_vamos_al_negocio));
        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.app_name);
        pDialog.setMessage(getString(R.string.m_busqueda));
        pDialog.setCancelable(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void requestMisSeguidores() {
        BaseActivity.showDialog(pDialog);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_MIS_SEGUIDORES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jData = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jData.length(); i++) {
                                Empresa empresa = new Empresa();
                                empresa.setId(Empresa.getUltimoId());
                                empresa.setId_server(jData.getJSONObject(i).getInt("EMP_ID"));
                                empresa.setNombre(jData.getJSONObject(i).getString("EMP_NOMBRE"));
                                empresa.setTipo_negocio(jData.getJSONObject(i).getInt("EMP_TIPO"));
                                empresa.setImagen(jData.getJSONObject(i).getString("EMP_IMAGEN"));
                                empresa.setCiudad(jData.getJSONObject(i).getString("EMP_CIUDAD"));
                                empresa.setPais(jData.getJSONObject(i).getString("EMP_PAIS"));
                                empresa.setAnio_f(jData.getJSONObject(i).getString("EMP_ANIO_FUNDACION"));
                                empresa.setDescripcion(jData.getJSONObject(i).getString("EMP_DESCRIPCION"));
                                empresa.setTipo_match(Empresa.M_ESPERA);
                                empresa.setTipo_empresa(Empresa.E_CONTACTO);
                                empresa.setId_match(jData.getJSONObject(i).getInt("SEG_ID"));
                                empresa.setPosicion(Empresa.getPos(jData.getJSONObject(i).getInt("EMP_TIPO")));
                                empresa.setWeb(jData.getJSONObject(i).getString("CON_WEB_SITE"));
                                empresa.setTelefono1(jData.getJSONObject(i).getString("CON_TELEFONO"));
                                empresa.setTelefono2(jData.getJSONObject(i).getString("CON_CELULAR"));
                                empresa.setCorreo1(jData.getJSONObject(i).getString("EMP_EMAIL"));
                                empresa.setCorreo2(jData.getJSONObject(i).getString("CON_EMAIL"));
                                Empresa.registrarEmpresa(empresa);

                                final int idEmpresa = jData.getJSONObject(i).getInt("EMP_ID");
                                JSONObject jExtra = jData.getJSONObject(i);

                                    if (jExtra.names().getString(i).equals("CERTIFICADO_INDUSTRIA_PRODUCTOS")) {
                                        JSONObject jDetalleEmpresa = jExtra.getJSONObject("CERTIFICADO_INDUSTRIA_PRODUCTOS");
                                        Log.d(TAG, jDetalleEmpresa.toString());

                                        JSONArray jCertificado = jDetalleEmpresa.getJSONArray("CERTIFICADO");
                                        if (jCertificado != null && jCertificado.length() >= 0) {
                                            Certificado.delete(idEmpresa);
                                            if (jCertificado.length() > 0) {
                                                for (int k = 0; k < jCertificado.length(); k++) {
                                                    Certificado.createOrUpdate(jCertificado.getJSONObject(k).getString("CER_NOMBRE"), idEmpresa);
                                                }
                                            }
                                        }


                                        JSONArray jIndustrial = jDetalleEmpresa.getJSONArray("INDUSTRIAL");
                                        if (jIndustrial != null && jIndustrial.length() >= 0) {
                                            SectorIndustrial.delete(idEmpresa);
                                            if (jIndustrial.length() > 0) {
                                                for (int k = 0; k < jIndustrial.length(); k++) {
                                                    SectorIndustrial.createOrUpdate(jIndustrial.getJSONObject(k).getString("SECIND_NOMBRE"), idEmpresa);
                                                }
                                            }
                                        }


                                        JSONArray jProducto = jDetalleEmpresa.getJSONArray("PRODUCTOS");
                                        if (jProducto != null && jProducto.length() >= 0) {
                                            Producto.delete(idEmpresa);
                                            if (jProducto.length() > 0) {
                                                for (int k = 0; k < jProducto.length(); k ++) {
                                                    Producto.createOrUpdate(jProducto.getJSONObject(k).getString("PRO_NOMBRE"), idEmpresa);
                                                }
                                            }
                                        }
                                        break;
                                    }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.toString(), e);
                        }
                        BaseActivity.hidepDialog(pDialog);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.e(volleyError.toString(), volleyError);
                        BaseActivity.hidepDialog(pDialog);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("idempresa", String.valueOf(Usuario.getUsuario().getId_empresa()));
                params.put("idtipoempresa", String.valueOf(Usuario.getUsuario().getTipo_empresa()));
                return params;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }
}
