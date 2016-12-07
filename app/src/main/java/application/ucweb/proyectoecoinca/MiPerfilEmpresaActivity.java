package application.ucweb.proyectoecoinca;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.io.FileInputStream;
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
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
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
    private ProgressDialog pDialog;
    private long id_intent;
    private Realm realm;
    private Empresa empresa;
    String strnombre_empresa = "";
    String strid_empresa ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil_empresa);
        iniciarLayout();

        recibirId();
        strnombre_empresa = empresa.getNombre();
        strid_empresa = String.valueOf(empresa.getId_server());
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
            if (ConexionBroadcastReceiver.isConnected()) {
                hidepDialog(pDialog);
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        Constantes.URL_VAMOS_AL_NEGOCIO,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_empresa", String.valueOf(empresa.getId_server()));
                        return params;
                    }
                };
                Configuracion.getInstance().addToRequestQueue(request, TAG);
            } else {
                ConexionBroadcastReceiver.showSnack(layout, this);
            }
        }
    }

    @OnClick(R.id.btnVerPerfil)
    public void descargarPDFEmpresa() {
        DownloadFile instancia = (DownloadFile) new DownloadFile().execute("http://uc-web.mobi/LIAISON/uploads/50/50pdf.pdf", "50pdf.pdf");
    }

    public static void downloadFile(String fileUrl, File directory) {
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

    }

    private class DownloadFile extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, strnombre_empresa);
            if (folder.mkdir()) {
                File pdfFile = new File(folder, fileName);

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
            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + strnombre_empresa + "/" + strid_empresa+"pdf.pdf");  // -> filename = maven.pdf
            Uri path = Uri.fromFile(pdfFile);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try{
                startActivity(pdfIntent);
            }catch(ActivityNotFoundException e){
                Log.d(TAG, e.getMessage());
                Toast.makeText(MiPerfilEmpresaActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            }
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
