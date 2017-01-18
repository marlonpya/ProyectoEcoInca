package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Util;
import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
public class MiPerfilEmpresaActivity extends BaseActivity {
    public static final String TAG = MiPerfilEmpresaActivity.class.getSimpleName();
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.iv_fondo_mi_perfil_empresa) ImageView fondo;
    @BindView(R.id.iv_perfil_empresa) ImageView imagen_empresa;
    @BindView(R.id.tv_nombre_empresa) TextView nombre_empresa;
    @BindView(R.id.layout_activity_mi_perfil_empresa) RelativeLayout layout;
    @BindView(R.id.tv_ciudad_empresa) TextView tvCiudad;
    @BindView(R.id.tv_pais_empresa) TextView tvPais;
    @BindView(R.id.tv_anio_f_empresa) TextView tvAnioF;
    @BindView(R.id.tv_descripcion_empresa) TextView tvDescripcion;
    @BindView(R.id.btnVamosHacerNegocio) Button btnVamosHacerNegocio;
    private ProgressDialog pDialog;
    private long id_intent;
    private Realm realm;
    private Empresa empresa;
    private String idempresa = "";
    private String idtipoempresa = "";
    private String idempresaseguido = "";
    private String nombreempresa = "";
    private String pdfempresa = "";
    private String idmatch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil_empresa);
        iniciarLayout();

        recibirId();
        nombreempresa = empresa.getNombre();
        idempresa = String.valueOf(Usuario.getUsuario().getId_empresa());
        idtipoempresa = String.valueOf(Usuario.getUsuario().getTipo_empresa());
        idempresaseguido = String.valueOf(empresa.getId_server());
        pdfempresa = empresa.getPdf();
        idmatch = String.valueOf(empresa.getId_match());
        if (getIntent().hasExtra(Constantes.B_DESACTIVAR_HACER_NEGOCIO))  btnVamosHacerNegocio.setEnabled(false);
    }

    private void recibirId() {
        if (getIntent().hasExtra(Constantes.L_ID_EMPRESA)) {
            id_intent = getIntent().getLongExtra(Constantes.L_ID_EMPRESA, -1);
            realm = Realm.getDefaultInstance();
            empresa = realm.where(Empresa.class).equalTo(Empresa.ID, id_intent).findFirst();
            usarGlideCircular(this, empresa.getImagen(), imagen_empresa);
            nombre_empresa.setText(empresa.getNombre());
            tvCiudad.setText(empresa.getCiudad());
            tvPais.setText(empresa.getPais());
            tvAnioF.setText(empresa.getAnio_f());
            tvDescripcion.setText(empresa.getDescripcion());
        }
    }

    @OnClick(R.id.btnVamosHacerNegocio)
    public void vamosHacerNegocio() {
        if (empresa.getTipo_empresa() == Empresa.E_BUSQUEDA) {
            switch (Empresa.identificarEmpresaContacto(empresa.getTipo_match())) {
                case Empresa.M_DESCONOCIDO  : requestVamosHacerNegocio(); break;
                case Empresa.M_RECHAZADO    : requestVamosHacerNegocio(); break;
                case Empresa.M_ACEPTADO     : mostrarMensaje(Empresa.M_ACEPTADO); break;
                case Empresa.M_ESPERA       : mostrarMensaje(Empresa.M_ACEPTADO); break;
            }
        } else if (empresa.getTipo_empresa() == Empresa.E_CONTACTO && empresa.getTipo_match() == Empresa.M_ESPERA) {
            requestAceptarNegocio();
        }
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
                                JSONArray jData = jsonObject.getJSONArray("data");
                                if (jData.getJSONObject(0).getBoolean("status")) {
                                    btnVamosHacerNegocio.setEnabled(true);
                                    hidepDialog(pDialog);
                                    new AlertDialog.Builder(MiPerfilEmpresaActivity.this)
                                            .setTitle(R.string.app_name)
                                            .setMessage(getString(R.string.m_solicitud_ok))
                                            .setPositiveButton(R.string.aceptar, null)
                                            .show();
                                } else {
                                    new AlertDialog.Builder(MiPerfilEmpresaActivity.this)
                                            .setTitle(R.string.app_name)
                                            .setMessage(getString(R.string.m_solicitud_error))
                                            .setPositiveButton(R.string.aceptar, null)
                                            .show();
                                }
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
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idempresa", idempresa);
                    params.put("idtipoempresa", idtipoempresa);
                    params.put("idempresaseguido", idempresaseguido);
                    return params;
                }
            };
            Configuracion.getInstance().addToRequestQueue(request, TAG);
        } else {
            ConexionBroadcastReceiver.showSnack(layout, this);
        }
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

                                    btnVamosHacerNegocio.setEnabled(false);
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
        } else { ConexionBroadcastReceiver.showSnack(layout, this); }
    }

    @OnClick(R.id.btnVerPerfil)
    public void descargarPDFEmpresa() {
        if (empresa.getPdf().isEmpty()) Toast.makeText(this, R.string.m_error_pdf, Toast.LENGTH_SHORT).show();
        else new DownloadFile().execute(pdfempresa, Util.getRutaPDF(pdfempresa));
    }

    public void downloadFile(String fileUrl, File directory) {
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
    }

    private class DownloadFile extends AsyncTask<String, Void, Void>{

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
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_mi_perfil));
        usarGlide(this, R.drawable.fondo_iniciar_sesion, fondo);

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
}
