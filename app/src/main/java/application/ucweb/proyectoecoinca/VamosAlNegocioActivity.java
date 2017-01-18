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


        if(ConexionBroadcastReceiver.isConnected()) {
            if (Usuario.getUsuario().getTipo_empresa() != 2) {
                requestMisSeguidores();
            }else{
                requestMisSeguidorestipo2();
            }
        }

        /*if(ConexionBroadcastReceiver.isConnected()) requestMisSeguidores();*/


        tab_layout.addTab(tab_layout.newTab());
        tab_layout.addTab(tab_layout.newTab());
        setupTabLayout();
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        if (Usuario.getUsuario().getTipo_empresa() == Empresa.N_VENDEDOR) {
            adapter = new SeguirAdapter(getSupportFragmentManager(), tab_layout.getTabCount(), Empresa.N_VENDEDOR);
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
            adapter = new SeguirAdapter(getSupportFragmentManager(), tab_layout.getTabCount(), Empresa.N_COMPRADOR);
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
                            JSONArray jData = jsonObject.getJSONArray("dataseguido");
                            for (int i = 0; i < jData.length(); i++) {
                                Empresa empresa = new Empresa();
                                /*int id_empresa = jData.getJSONObject(i).getInt("SEG_ID_SEGUIDOR") != Usuario.getUsuario().getId_empresa() ? jData.getJSONObject(i).getInt("SEG_ID_SEGUIDOR") : jData.getJSONObject(i).getInt("SEG_ID_SEGUIDO");
                                int tipo_empresa = id_empresa*/

                                empresa.setId(Empresa.getUltimoId());
                                empresa.setId_server(jData.getJSONObject(i).getInt("EMP_ID"));
                                empresa.setNombre(jData.getJSONObject(i).getString("EMP_NOMBRE"));
                                empresa.setTipo_negocio(jData.getJSONObject(i).getInt("EMP_TIPO"));
                                empresa.setImagen(jData.getJSONObject(i).getString("EMP_IMAGEN"));
                                empresa.setCiudad(jData.getJSONObject(i).getString("EMP_CIUDAD"));
                                empresa.setPais(jData.getJSONObject(i).getString("EMP_PAIS"));
                                empresa.setAnio_f(jData.getJSONObject(i).getString("EMP_ANIO_FUNDACION"));
                                empresa.setDescripcion(jData.getJSONObject(i).getString("EMP_DESCRIPCION"));
                                empresa.setPdf(jData.getJSONObject(i).getString("EMP_PDF"));
                                empresa.setTipo_match(Empresa.M_ESPERA);
                                empresa.setTipo_empresa(Empresa.E_CONTACTO);
                                empresa.setId_match(jData.getJSONObject(i).getInt("SEG_ID"));
                                Empresa.registrarEmpresa(empresa);
                                Log.d(TAG, empresa.toString());
                            }

                            JSONArray jData2 = jsonObject.getJSONArray("dataseguidor");
                            for (int i = 0; i < jData2.length(); i++) {
                                Empresa empresa = new Empresa();
                                /*int id_empresa = jData.getJSONObject(i).getInt("SEG_ID_SEGUIDOR") != Usuario.getUsuario().getId_empresa() ? jData.getJSONObject(i).getInt("SEG_ID_SEGUIDOR") : jData.getJSONObject(i).getInt("SEG_ID_SEGUIDO");
                                int tipo_empresa = id_empresa*/
                                empresa.setId(Empresa.getUltimoId());
                                empresa.setId_server(jData2.getJSONObject(i).getInt("EMP_ID"));
                                empresa.setNombre(jData2.getJSONObject(i).getString("EMP_NOMBRE"));
                                empresa.setTipo_negocio(jData2.getJSONObject(i).getInt("EMP_TIPO"));
                                empresa.setImagen(jData2.getJSONObject(i).getString("EMP_IMAGEN"));
                                empresa.setCiudad(jData2.getJSONObject(i).getString("EMP_CIUDAD"));
                                empresa.setPais(jData2.getJSONObject(i).getString("EMP_PAIS"));
                                empresa.setAnio_f(jData2.getJSONObject(i).getString("EMP_ANIO_FUNDACION"));
                                empresa.setDescripcion(jData2.getJSONObject(i).getString("EMP_DESCRIPCION"));
                                empresa.setPdf(jData2.getJSONObject(i).getString("EMP_PDF"));
                                empresa.setTipo_match(Empresa.M_ESPERA);
                                empresa.setTipo_empresa(Empresa.E_CONTACTO);
                                empresa.setId_match(jData2.getJSONObject(i).getInt("SEG_ID"));
                                Empresa.registrarEmpresa(empresa);
                                Log.d(TAG, empresa.toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        BaseActivity.hidepDialog(pDialog);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
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

                                /*int id_empresa = jData.getJSONObject(i).getInt("SEG_ID_SEGUIDOR") != Usuario.getUsuario().getId_empresa() ? jData.getJSONObject(i).getInt("SEG_ID_SEGUIDOR") : jData.getJSONObject(i).getInt("SEG_ID_SEGUIDO");
                                int tipo_empresa = id_empresa*/

                                empresa.setId_server(jData.getJSONObject(i).getInt("EMP_ID"));
                                empresa.setNombre(jData.getJSONObject(i).getString("EMP_NOMBRE"));
                                empresa.setTipo_negocio(jData.getJSONObject(i).getInt("EMP_TIPO"));
                                empresa.setImagen(jData.getJSONObject(i).getString("EMP_IMAGEN"));
                                empresa.setCiudad(jData.getJSONObject(i).getString("EMP_CIUDAD"));
                                empresa.setPais(jData.getJSONObject(i).getString("EMP_PAIS"));
                                empresa.setAnio_f(jData.getJSONObject(i).getString("EMP_ANIO_FUNDACION"));
                                empresa.setDescripcion(jData.getJSONObject(i).getString("EMP_DESCRIPCION"));
                                empresa.setPdf(jData.getJSONObject(i).getString("EMP_PDF"));
                                empresa.setTipo_match(Empresa.M_ESPERA);
                                empresa.setTipo_empresa(Empresa.E_CONTACTO);
                                empresa.setId_match(jData.getJSONObject(i).getInt("SEG_ID"));
                                Empresa.registrarEmpresa(empresa);
                                Log.d(TAG, empresa.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        BaseActivity.hidepDialog(pDialog);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
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
