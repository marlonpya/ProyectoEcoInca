package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import org.xml.sax.helpers.LocatorImpl;

import java.util.HashMap;
import java.util.Map;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.EmpresaSerializable;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.model.detalle.Certificado;
import application.ucweb.proyectoecoinca.model.detalle.Producto;
import application.ucweb.proyectoecoinca.model.detalle.SectorIndustrial;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;

public class MiPerfilEmpresaActivity extends BaseActivity {
    public static final String TAG = MiPerfilEmpresaActivity.class.getSimpleName();
    @BindView(R.id.con_toolbar) Toolbar toolbar;
    @BindView(R.id.layout_activity_mi_perfil_empresa) CoordinatorLayout layout;
    @BindView(R.id.iv_perfil_empresa) ImageView iv_perfil_empresa;
    @BindView(R.id.tv_descripcion_empresa) TextView tv_descripcion;
    @BindView(R.id.tv_nombre_empresa) TextView tv_nombre;
    @BindView(R.id.tv_ciudad_empresa) TextView tv_ciudad;
    @BindView(R.id.tv_pais_empresa) TextView tv_pais;
    @BindView(R.id.tv_sector_empresarial_empresa) TextView tv_sector_emp;
    @BindView(R.id.tv_productos) TextView tv_productos;
    @BindView(R.id.tv_certificados) TextView tv_certificados;
    @BindView(R.id.tv_anio_fundacion) TextView tv_anio_fundacion;
    @BindView(R.id.tv_web) TextView tv_web;
    @BindView(R.id.tv_telefono) TextView tv_telefono;
    @BindView(R.id.tv_correo) TextView tv_correo;
    @BindView(R.id.btnVamosHacerNegocio) FloatingActionButton btnVamosHacerNegocio;
    private ProgressDialog pDialog;
    private int id_intent;
    private EmpresaSerializable empresaSerializable;
    private Empresa empresa;
    private String idmatch = "-1";
    private String idempresaseguido = "-1";
    private Realm realm;
    private boolean isRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_activity_mi_perfil_empresa);
        iniciarLayout();

        isRealm = getIntent().getBooleanExtra(Constantes.EXTRA_IS_REAL, false);
        realm = Realm.getDefaultInstance();
        recibirId();
        if (getIntent().hasExtra(Constantes.B_DESACTIVAR_HACER_NEGOCIO)) btnVamosHacerNegocio.setVisibility(View.GONE);
    }

    private void recibirId() {
        id_intent = getIntent().getIntExtra(Constantes.L_ID_EMPRESA, -1);

        if (getIntent().hasExtra(Constantes.L_ID_EMPRESA) && !isRealm) {
            Log.d(TAG, String.valueOf("ID_INTENT_ " + id_intent));
            requestGetEmpresaId();
        } else if(isRealm){
            empresa = realm.where(Empresa.class).equalTo(Empresa.ID, id_intent).findFirst();
            if (empresa != null) {
                Log.d(TAG, empresa.toString());
                idmatch = String.valueOf(empresa.getId_match());
                usarGlide(MiPerfilEmpresaActivity.this, empresa.getImagen(), iv_perfil_empresa);
                tv_descripcion.setText(empresa.getDescripcion().isEmpty() || empresa.getDescripcion() == null ? "-" : empresa.getDescripcion());
                tv_nombre.setText(empresa.getNombre().isEmpty() || empresa.getNombre() == null ? "-" : empresa.getNombre());
                tv_ciudad.setText(empresa.getCiudad().isEmpty() || empresa.getCiudad() == null ? "-" : empresa.getCiudad());
                tv_pais.setText(empresa.getPais().isEmpty() || empresa.getPais() == null ? "-" : empresa.getPais());

                String sector_emp = "-";
                RealmList<SectorIndustrial> sectorIndustrials = empresa.getSectorIndustriales();
                for (int i = 0; i < sectorIndustrials.size(); i++) {
                    if (!sectorIndustrials.isEmpty()) sector_emp += sectorIndustrials.get(i).getDescripcion() + "\n";
                }
                tv_sector_emp.setText(sector_emp);

                String producto = "-";
                RealmList<Producto> productos = empresa.getProductos();
                for (int i = 0; i < productos.size(); i++) {
                    if (!productos.isEmpty()) producto += productos.get(i).getDescripcion() + "\n";
                }
                tv_productos.setText(producto);

                String certificado = "-";
                RealmList<Certificado> certificados = empresa.getCertificados();
                for (int i = 0; i < certificados.size(); i++) {
                    if (!certificados.isEmpty()) certificado += certificados.get(i).getDescripcion() + "\n";
                }
                tv_certificados.setText(certificado);
                //tv_sector_emp
                //tv_productos
                //tv_certificados
                tv_anio_fundacion.setText(empresa.getAnio_f().isEmpty() || empresa.getAnio_f() == null ? "-" : empresa.getAnio_f());
                tv_web.setText(empresa.getWeb().isEmpty() || empresa.getWeb() == null ? "-" : empresa.getWeb());
                if (empresa.getTelefono1() != null && !empresa.getTelefono1().isEmpty() && empresa.getTelefono2() != null && !empresa.getTelefono2().isEmpty()) {
                    tv_telefono.setText(empresa.getTelefono1() + "\n" + empresa.getTelefono2());
                } else if (empresa.getTelefono1() != null && !empresa.getTelefono1().isEmpty()) {
                    tv_telefono.setText(empresa.getTelefono1());
                } else if (empresa.getTelefono2() != null && !empresa.getTelefono2().isEmpty()) {
                    tv_telefono.setText(empresa.getTelefono1());
                }

                if (empresa.getCorreo1() != null && !empresa.getCorreo1().isEmpty() && empresa.getCorreo2() != null && !empresa.getTelefono2().isEmpty()) {
                    tv_correo.setText(empresa.getCorreo1() + "\n" + empresa.getCorreo2());
                } else if (empresa.getCorreo1() != null && !empresa.getCorreo1().isEmpty()) {
                    tv_correo.setText(empresa.getCorreo1());
                } else if (empresa.getCorreo2() != null && !empresa.getCorreo2().isEmpty()) {
                    tv_correo.setText(empresa.getCorreo2());
                }
            }
        }
    }

    @OnClick(R.id.btnVamosHacerNegocio)
    public void vamosHacerNegocio() {
        Log.d(TAG, "clickFAB");
        /*if (empresaSerializable.getTipo_empresa() == Empresa.E_BUSQUEDA) {
            switch (Empresa.identificarEmpresaContacto(empresaSerializable.getTipo_match())) {
                case Empresa.M_DESCONOCIDO  : requestVamosHacerNegocio(); break;
                case Empresa.M_RECHAZADO    : requestVamosHacerNegocio(); break;
                case Empresa.M_ACEPTADO     : mostrarMensaje(Empresa.M_ACEPTADO); break;
                case Empresa.M_ESPERA       : mostrarMensaje(Empresa.M_ACEPTADO); break;
            }
        } else if (empresaSerializable.getTipo_empresa() == Empresa.E_CONTACTO && empresaSerializable.getTipo_match() == Empresa.M_ESPERA) {
            requestAceptarNegocio();
        }*/
        if (isRealm) requestAceptarNegocio();
        else requestVamosHacerNegocio();
    }

    private void requestGetEmpresaId() {
        if (ConexionBroadcastReceiver.isConnected()) {
            showDialog(pDialog);
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constantes.URL_EMPRESA_X_ID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d(TAG, s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONObject jData = jsonObject.getJSONObject("data");

                                if (jsonObject.getBoolean("status")) {
                                    JSONObject jEmpresa = jData.getJSONArray("empresa").getJSONObject(0);

                                    //int id_server, String nombre, String imagen, String ciudad, String pais, String anio_f, String descripcion, String web, String telefono1, String telefono2, String correo1, String correo2) {
                                    empresaSerializable = new EmpresaSerializable(
                                            jEmpresa.getInt("EMP_ID"),
                                            jEmpresa.getString("EMP_NOMBRE"),
                                            jEmpresa.getString("EMP_IMAGEN"),
                                            jEmpresa.getString("EMP_CIUDAD"),
                                            jEmpresa.getString("EMP_PAIS"),
                                            jEmpresa.getString("EMP_ANIO_FUNDACION"),
                                            jEmpresa.getString("EMP_DESCRIPCION"),
                                            jEmpresa.getString("CON_WEB_SITE"),
                                            jEmpresa.getString("CON_TELEFONO"),
                                            jEmpresa.getString("CON_CELULAR"),
                                            jEmpresa.getString("EMP_EMAIL"),
                                            jEmpresa.getString("CON_EMAIL"),
                                            jEmpresa.getInt("EMP_TIPO")
                                    );
                                    usarGlide(MiPerfilEmpresaActivity.this, empresaSerializable.getImagen(), iv_perfil_empresa);
                                    //Glide.with(MiPerfilEmpresaActivity.this).load(empresaSerializable.getImagen()).fitCenter().override(400, 200).into(iv_perfil_empresa);
                                    tv_descripcion.setText(empresaSerializable.getDescripcion().isEmpty() || empresaSerializable.getDescripcion() == null ? "-" : empresaSerializable.getDescripcion());
                                    tv_nombre.setText(empresaSerializable.getNombre().isEmpty() || empresaSerializable.getNombre() == null ? "-" : empresaSerializable.getNombre());
                                    tv_ciudad.setText(empresaSerializable.getCiudad().isEmpty() || empresaSerializable.getCiudad() == null ? "-" : empresaSerializable.getCiudad());
                                    tv_pais.setText(empresaSerializable.getPais().isEmpty() || empresaSerializable.getPais().isEmpty() ? "-" : empresaSerializable.getPais());

                                    String sector_empresarial = "";
                                    JSONArray jSectorEmpresarial = jData.getJSONArray("sector_empresarial");
                                    if (jSectorEmpresarial != null && jSectorEmpresarial.length() > 0) {
                                        for (int i = 0; i < jSectorEmpresarial.length(); i++) {
                                            sector_empresarial += jSectorEmpresarial.getJSONObject(0).getString("SECIND_NOMBRE");
                                            if (i + 1 != jSectorEmpresarial.length()) sector_empresarial += "\n";
                                        }
                                    } else
                                        sector_empresarial = "-";
                                    tv_sector_emp.setText(sector_empresarial);

                                    String productos = "";
                                    JSONArray jProductos = jData.getJSONArray("productos");
                                    if (jProductos != null && jProductos.length() > 0) {
                                        for (int i = 0; i < jProductos.length(); i++) {
                                            productos += " "+jProductos.getJSONObject(i).getString("PRO_NOMBRE");
                                            if (i + 1 != jProductos.length()) productos += ",";
                                            else productos += ".";
                                        }
                                    } else
                                        productos = "-";
                                    tv_productos.setText(productos);

                                    String certificados = "";
                                    JSONArray jCertificados = jData.getJSONArray("certificados");
                                    if (jCertificados != null && jCertificados.length() > 0) {
                                        for (int i = 0; i < jCertificados.length(); i++) {
                                            certificados += jCertificados.getJSONObject(i).getString("CER_NOMBRE");
                                            if (i + 1 != jCertificados.length()) certificados += "\n";
                                        }
                                    } else
                                        certificados = "-";
                                    tv_certificados.setText(certificados);
                                    tv_anio_fundacion.setText(empresaSerializable.getAnio_f().isEmpty() || empresaSerializable.getAnio_f() == null ? "-" : empresaSerializable.getAnio_f());
                                    tv_web.setText(empresaSerializable.getWeb().isEmpty() || empresaSerializable.getWeb() == null ? "-" : empresaSerializable.getWeb());
                                    tv_telefono.setText(empresaSerializable.getTelefono1() + "\n" + empresaSerializable.getTelefono2());
                                    tv_correo.setText(empresaSerializable.getCorreo1() + "\n" + empresaSerializable.getCorreo2());

                                    idempresaseguido = String.valueOf(empresaSerializable.getId_server());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, e.toString(), e);
                            }
                            hidepDialog(pDialog);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            VolleyLog.e(volleyError.toString(), volleyError);
                            hidepDialog(pDialog);
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id_empresa", String.valueOf(id_intent));
                    return params;
                }
            };
            Configuracion.getInstance().addToRequestQueue(request, TAG);
        } else
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setCancelable(false)
                    .setMessage(R.string.m_no_conneccion_request)
                    .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    })
                    .show();
    }

    private void mostrarMensaje(int tipo) {
        String texto = tipo == Empresa.M_ACEPTADO ? getString(R.string.m_contacto_aceptado) : getString(R.string.m_contacto_espera);
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(texto)
                .setPositiveButton(R.string.aceptar, null)
                .show();
    }

    private void requestVamosHacerNegocio() {
        if (ConexionBroadcastReceiver.isConnected()) {
            showDialog(pDialog);
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constantes.URL_VAMOS_AL_NEGOCIO,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d(TAG, s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                Log.d(TAG, jsonObject.toString());
                                if (jsonObject.getBoolean("status")) {
                                    btnVamosHacerNegocio.setEnabled(true);
                                    hidepDialog(pDialog);
                                    new AlertDialog.Builder(MiPerfilEmpresaActivity.this)
                                            .setTitle(R.string.app_name)
                                            .setMessage(getString(R.string.m_solicitud_ok))
                                            .setPositiveButton(R.string.aceptar, null)
                                            .show();
                                    btnVamosHacerNegocio.setVisibility(View.GONE);
                                } else {
                                    hidepDialog(pDialog);
                                    new AlertDialog.Builder(MiPerfilEmpresaActivity.this)
                                            .setTitle(R.string.app_name)
                                            .setMessage(getString(R.string.m_solicitud_error))
                                            .setPositiveButton(R.string.aceptar, null)
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, e.toString(), e);
                                hidepDialog(pDialog);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            VolleyLog.e(volleyError.toString(), volleyError);
                            hidepDialog(pDialog);
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idempresa", String.valueOf(Usuario.getUsuario().getId_empresa()));
                    params.put("idtipoempresa", String.valueOf(Usuario.getUsuario().getTipo_empresa()));
                    params.put("idempresaseguido", idempresaseguido);
                    Log.d(TAG, params.toString());
                    return params;
                }
            };
            Configuracion.getInstance().addToRequestQueue(request, TAG);
        } else
            ConexionBroadcastReceiver.showSnack(layout, this);
    }

    private void requestAceptarNegocio() {
        if (ConexionBroadcastReceiver.isConnected()) {
            showDialog(pDialog);
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constantes.URL_ACEPTAR_NEGOCIO,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                if (jsonObject.getBoolean("status")) {
                                    hidepDialog(pDialog);
                                    new AlertDialog.Builder(MiPerfilEmpresaActivity.this)
                                            .setTitle(R.string.app_name)
                                            .setMessage(getString(R.string.m_aceptar_negocio_ok))
                                            .setPositiveButton(R.string.aceptar, null)
                                            .show();
                                    Empresa.actualizarMatch(empresa.getId_server(), Empresa.M_ACEPTADO);

                                    btnVamosHacerNegocio.setVisibility(View.GONE);
                                } else {
                                    hidepDialog(pDialog);
                                    new AlertDialog.Builder(MiPerfilEmpresaActivity.this)
                                            .setTitle(R.string.app_name)
                                            .setMessage(getString(R.string.m_aceptar_negocio_error))
                                            .setPositiveButton(R.string.aceptar, null)
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, e.toString(), e);
                                hidepDialog(pDialog);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            VolleyLog.d(volleyError.toString());
                            hidepDialog(pDialog);
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idmatch", idmatch);
                    return params;
                }
            };
            Configuracion.getInstance().addToRequestQueue(request, TAG);
        } else
            ConexionBroadcastReceiver.showSnack(layout, this);
    }

    /*@OnClick(R.id.btnVerPerfil)
    public void descargarPDFEmpresa() {
        if (empresaSerializable.getPdf().isEmpty()) Toast.makeText(this, R.string.m_error_pdf, Toast.LENGTH_SHORT).show();
        else new DownloadFile().execute(pdfempresa, Util.getRutaPDF(pdfempresa));
    }*/

    /*public void downloadFile(String fileUrl, File directory) {
        if (ConexionBroadcastReceiver.isConnected()) {
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(directory);
                int totalSize = urlConnection.getContentLength();

                byte[] buffer = new byte[1024];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ConexionBroadcastReceiver.showSnack(layout, this);
        }
    }*/

    /*private class DownloadFile extends AsyncTask<String, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(pDialog);
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // http://uc-web.mobi/LIAISON/uploads/50/50pdf.pdf
            String fileName = strings[1];  // 50pdf.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
            File folder = new File(extStorageDirectory, nombreempresa);
            if (folder.mkdir()) {
                File pdfFile = new File(folder, fileName);
                Log.d(TAG, String.valueOf(folder));
                try{
                    pdfFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                downloadFile(fileUrl, pdfFile);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            File pdfFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + nombreempresa + "/"+ idempresa + ".pdf");  // -> id debe coincidir !
            Uri path = Uri.fromFile(pdfFile);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent intent = Intent.createChooser(pdfIntent, "Open file");
            Log.d(TAG, String.valueOf(pdfFile));
            try{
                startActivity(intent);
            }catch(ActivityNotFoundException e){
                Log.d(TAG, e.getMessage());
                Toast.makeText(MiPerfilEmpresaActivity.this, R.string.m_error_ver_pdf, Toast.LENGTH_SHORT).show();
            }
            hidepDialog(pDialog);
        }
    }*/

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_mi_perfil));
        toolbar.setTitle("");

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
