package application.ucweb.proyectoecoinca;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;

import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.ucweb.proyectoecoinca.apis.FacebookA;
import application.ucweb.proyectoecoinca.apis.LinkedinA;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Buscar;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Preferencia;
import application.ucweb.proyectoecoinca.util.Util;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import me.originqiu.library.EditTag;
import me.originqiu.library.MEditText;

public class RegistroActivity extends BaseActivity {
    private static final String TAG = RegistroActivity.class.getSimpleName();
    @BindView(R.id.layout_activity_registro) LinearLayout layout;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.ivRegistroImagenSubir) ImageView imagen_subir;
    @BindView(R.id.icono) ImageView icono;
    @BindView(R.id.ll_btn__pais) LinearLayout btn_pais;
    @BindView(R.id.ll_btn_ciudad) LinearLayout btn_ciudad;

    @BindView(R.id.et_nombre_registro) EditText et_nombre_empresa;
    @BindView(R.id.et_apellido_registro) EditText et_apellido;
    @BindView(R.id.et_pais_registro) EditText et_pais;
    @BindView(R.id.et_ciudad_registro) EditText et_ciudad;
    @BindView(R.id.et_email_registro) EditText et_email;
    @BindView(R.id.et_anio_fundacion_registro) EditText et_anio_f;
    @BindView(R.id.et_descripcion_emp_registro) EditText et_descripcion_e;
    @BindView(R.id.et_sector_industrial_registro) EditText et_sec_empresarial;
    @BindView(R.id.et_sector_producto_registro) EditTag et_producto;
    @BindView(R.id.et_nombre_contacto_registro) TextView et_nombre_contacto_registro;
    @BindView(R.id.et_cargo_contacto_registro) TextView et_cargo_contacto_registro;
    @BindView(R.id.et_certificado_registro) EditText et_certificado;
    @BindView(R.id.et_contrasenia_registro) EditText et_contrasenia;
    @BindView(R.id.tv_titulo_producto) TextView titulo_producto;
    @BindView(R.id.et_telefono_contacto_registro) TextView et_telefono_oficina;
    @BindView(R.id.et_movil_contacto_registro) TextView et_celular;
    @BindView(R.id.et_mail_contacto_registro) TextView et_email_contacto;
    @BindView(R.id.et_website_contacto_registro) TextView et_website;
    @BindView(R.id.et_linkedin_contacto_registro) TextView et_linkedin;
    @BindView(R.id.met_editag) MEditText mEditText;
    @BindView(R.id.iv_comprador_registro) ImageView iv_comprador;
    @BindView(R.id.iv_vendedor_registro) ImageView iv_vendedor;
    @BindView(R.id.iv_ambos_registro) ImageView iv_ambos;
    @BindDrawable(R.drawable.icono_comprador_registro) Drawable drw_comprador;
    @BindDrawable(R.drawable.icono_vendedor_registro) Drawable drw_vendedor;
    @BindDrawable(R.drawable.icono_ambos_registro) Drawable drw_ambos;
    @BindDrawable(R.drawable.icono_comprador_registro_opaco) Drawable drw_comprador_opaco;
    @BindDrawable(R.drawable.icono_vendedor_registro_opaco) Drawable drw_vendedor_opaco;
    @BindDrawable(R.drawable.icono_ambos_registro_opaco) Drawable drw_ambos_opaco;
    @BindView(R.id.textView2) TextView texto_subir_logo;
    @BindColor(R.color.celeste) int CELESTE;
    private static final int REQUEST_WRITE_STORAGE = 112;
    public static final int WRITE_PERMISSION = 0x01;
    private ProgressDialog pDialog;
    private Preferencia preferencia;
    private static int VALOR = 1;
    private int TIPO_EMPRESA = -1;
    private String imagen_base = "";
    private String codigo = "";
    private Realm realm;
    private boolean red_social;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        iniciarLayout();
        realm = Realm.getDefaultInstance();
        preferencia = new Preferencia(this);
        red_social = getIntent().getBooleanExtra(Constantes.B_RED_SOCIAL_INICIAR_SESION, false);

        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_CERTIFICACIONES);
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_EMPRESARIAL);
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_PAIS);

        if (red_social) {
            String nombre = getIntent().getStringExtra(Constantes.S_NOMBRE_INICIAR_SESION);
            String email = getIntent().getStringExtra(Constantes.S_EMAIL_INICIAR_SESION);
            String apellido = getIntent().getStringExtra(Constantes.S_APE_INICIAR_SESION);
            et_email.setText(email);
            et_email.setEnabled(false);
            et_nombre_contacto_registro.setText(nombre);
            et_apellido.setText(apellido);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        InicioActivity.requestPais(pDialog, this, layout);
        BuscarDetalle.cargarEmpresarial(this);
        BuscarDetalle.cargarCertificaciones(this);
        generarMarcados(et_sec_empresarial, BuscarDetalle.TIPO_EMPRESARIAL);
        generarMarcados(et_certificado, BuscarDetalle.TIPO_CERTIFICACIONES);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEditText.getText().length() > 0) titulo_producto.setTextColor(CELESTE);
                else titulo_producto.setTextColor(Color.parseColor("#FF808080"));
            }
        });
    }

    private void requestDepartamento(final String codigo_pais) {
        Log.d(TAG, codigo_pais);
        if (ConexionBroadcastReceiver.isConnected()) {
            showDialog(pDialog);
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constantes.URL_DEPARTAMENTOS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d(TAG, s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jData = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jData.length(); i++) {
                                    BuscarDetalle.cargarDepartamento(jData.getJSONObject(i).getString("name"), codigo_pais);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            hidepDialog(pDialog);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            VolleyLog.e(volleyError.toString());
                            hidepDialog(pDialog);
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idcountries", codigo_pais);
                    return params;
                }
            };
            Configuracion.getInstance().addToRequestQueue(request, TAG);
        } else ConexionBroadcastReceiver.showSnack(layout, this);
    }

    @OnClick(R.id.btnSiguienteRegistro)
    public void siguienteRegistro() {
        if (ConexionBroadcastReceiver.isConnected()) {
            if (validarRegistroEmpresa()) requestRegistrarEmpresa();
        } else
            ConexionBroadcastReceiver.showSnack(layout, this);
    }

    @OnClick(R.id.fabRegistroAgregarImagen)
    public void agregarImagen() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
            else
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), VALOR);
        } else
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), VALOR);
    }

    @OnClick(R.id.btnSectorEmpresarial)
    public void irADetalleSectorIndustrial() {
        startActivity(new Intent(this, RegistroDetalleListaActivity.class)
                .putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, BuscarDetalle.TIPO_EMPRESARIAL));
    }

    @OnClick(R.id.btnCertificado)
    public void irADetalleCertificado() {
        startActivity(new Intent(this, RegistroDetalleListaActivity.class)
                .putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, BuscarDetalle.TIPO_CERTIFICACIONES));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, R.string.permiso_imagen, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VALOR && resultCode == RESULT_OK && data != null) {
            Uri imagen_selecionada = data.getData();
            String mi_path = Util.getPath(imagen_selecionada, getApplicationContext());
            Log.d(TAG, mi_path);
            Bitmap fotobitmap = BitmapFactory.decodeFile(mi_path);
            if (fotobitmap != null) {
                int alto = fotobitmap.getWidth();
                int ancho = fotobitmap.getHeight();
                Log.d(TAG, "alto =" + String.valueOf(alto) + "ancho= " + String.valueOf(ancho));
                if (alto > 1025 || ancho > 1025) {
                    imagen_base = "";
                    texto_subir_logo.setText(R.string.subir_logo);
                    imagen_subir.setImageResource(0);
                    Toast.makeText(getApplicationContext(), getString(R.string.regla_imagenes), Toast.LENGTH_LONG).show();
                } else {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    fotobitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    fotobitmap.recycle();
                    byte[] bytes_local = outputStream.toByteArray();
                    String imagen_encode = Base64.encodeToString(bytes_local, Base64.DEFAULT);
                    Log.d(TAG, "imagen_encode\n" + imagen_encode);
                    imagen_subir.setImageURI(imagen_selecionada);
                    texto_subir_logo.setText("");

                    imagen_base = imagen_encode;
                }
            }
        }
    }

    @OnClick(R.id.ll_btn__pais)
    public void dialogoPais() {
        realm = Realm.getDefaultInstance();
        final RealmResults<BuscarDetalle> paises = realm.where(BuscarDetalle.class).equalTo(BuscarDetalle.BUSDET_TIPO, BuscarDetalle.TIPO_PAIS).findAll();
        final List<String> nombre_paises = new ArrayList<>();
        for (int i = 0; i < paises.size(); i++) {
            nombre_paises.add(paises.get(i).getDescripcion());
        }
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(nombre_paises.toArray(new String[nombre_paises.size()]), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_pais.setText(nombre_paises.get(which));
                        codigo = paises.get(which).getId_server();
                        dialog.dismiss();
                        et_ciudad.setText("");
                        if (realm.where(BuscarDetalle.class)
                                .equalTo(BuscarDetalle.BUSDET_TIPO, BuscarDetalle.TIPO_DEPARTAMENTO)
                                .equalTo(BuscarDetalle.BUSDET_DEPARTAMENTO_FK, codigo)
                                .findFirst() == null)
                            requestDepartamento(codigo);
                    }
                })
                .create()
                .show();
    }

    @OnClick(R.id.ll_btn_ciudad)
    public void dialogoCiudad() {
        realm = Realm.getDefaultInstance();
        if (!realm.where(BuscarDetalle.class)
                .equalTo(BuscarDetalle.BUSDET_TIPO, BuscarDetalle.TIPO_DEPARTAMENTO)
                .equalTo(BuscarDetalle.BUSDET_DEPARTAMENTO_FK, codigo).findAll().isEmpty()) {

            RealmResults<BuscarDetalle> departamentos = realm.where(BuscarDetalle.class)
                    .equalTo(BuscarDetalle.BUSDET_TIPO, BuscarDetalle.TIPO_DEPARTAMENTO)
                    .equalTo(BuscarDetalle.BUSDET_DEPARTAMENTO_FK, codigo).findAll();

            final List<String> nombres_departamentos = new ArrayList<>();
            for (int i = 0; i < departamentos.size(); i++) {
                nombres_departamentos.add(departamentos.get(i).getDescripcion());
            }
            if (!codigo.isEmpty()) {
                new AlertDialog.Builder(this)
                        .setSingleChoiceItems(nombres_departamentos.toArray(new String[nombres_departamentos.size()]), -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                et_ciudad.setText(nombres_departamentos.get(which));
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        }
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.registrarse_min));

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage(getString(R.string.m_actualizando));
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.iv_comprador_registro, R.id.iv_vendedor_registro, R.id.iv_ambos_registro})
    public void elegirTipoUsuario(ImageView imageView) {
        if (imageView.getId() == R.id.iv_comprador_registro) make(0);
        else if (imageView.getId() == R.id.iv_vendedor_registro) make(1);
        else if(imageView.getId() == R.id.iv_ambos_registro) make(2);
    }

    public void make(int i) {
        iv_comprador.setBackground(drw_comprador_opaco);
        iv_vendedor.setBackground(drw_vendedor_opaco);
        iv_ambos.setBackground(drw_ambos_opaco);
        switch (i){
            case 0: iv_comprador.setBackground(drw_comprador); TIPO_EMPRESA = 0; break;
            case 1: iv_vendedor.setBackground(drw_vendedor); TIPO_EMPRESA = 1; break;
            case 2: iv_ambos.setBackground(drw_ambos); TIPO_EMPRESA = 2; break;
        }
    }

    private Map<String, String> crearHashMap(
            final String imagen,
            final String nombre_empresa,
            final String pais,
            final String ciudad,
            final String email,
            final String contrasenia,
            final String tipo_empresa,
            final String anio_fundacion,
            final String descripcion,
            final ArrayList<String> sector_empresarial,
            final List<String> productos,
            final ArrayList<String> certificaciones,
            final String nombre,
            final String apellido,
            final String cargo,
            final String telefono,
            final String celular,
            final String email_usuario,
            final String website,
            final String linkedin) throws JSONException {
        final String jsonArrayNombre = "registrarEmpresa";
        Map<String, String> param = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imagen", imagen);
        jsonObject.put("nombre_empresa", nombre_empresa);
        jsonObject.put("pais", pais);
        jsonObject.put("ciudad", ciudad);
        jsonObject.put("email", email);
        jsonObject.put("contrasenia", contrasenia);
        jsonObject.put("tipo_empresa", tipo_empresa);
        jsonObject.put("anio_fundacion", anio_fundacion);
        jsonObject.put("descripcion", descripcion);
        jsonObject.put("nombre", nombre);
        jsonObject.put("apellido", apellido);
        jsonObject.put("cargo", cargo);
        jsonObject.put("telefono", telefono);
        jsonObject.put("celular", celular);
        jsonObject.put("email_usuario", email_usuario);
        jsonObject.put("website", website);
        jsonObject.put("linkedin", linkedin);
        jsonObject.put("dispositivo", "android"); //<-- Agregado !
        jsonObject.put("token", preferencia.getTokenFcm()); //<-- Agregado !
        jsonArray.put(jsonObject);
        JSONArray jsonArrayEmpresarial = new JSONArray();
        for (String sector_emp : sector_empresarial) {
            JSONObject json_sector_emp = new JSONObject();
            json_sector_emp.put("sector_emp", sector_emp);
            jsonArrayEmpresarial.put(json_sector_emp);
        }
        jsonArray.put(jsonArrayEmpresarial);

        JSONArray jsonArrayProductos = new JSONArray();
        for (String producto : productos) {
            JSONObject json_productos = new JSONObject();
            json_productos.put("producto", producto);
            jsonArrayProductos.put(json_productos);
        }
        jsonArray.put(jsonArrayProductos);

        JSONArray jsonArrayCertificaciones = new JSONArray();
        for (String certificacion : certificaciones) {
            JSONObject json_certificacion = new JSONObject();
            json_certificacion.put("certificacion", certificacion);
            jsonArrayCertificaciones.put(json_certificacion);
        }
        jsonArray.put(jsonArrayCertificaciones);
        param.put(jsonArrayNombre, jsonArray.toString());
        Log.d(TAG, "parámetros_"+param.toString());
        return param;
    }

    private boolean validarRegistroEmpresa() {
        //agregar todos los diccionarios
        boolean resultado = false;
        if (!et_nombre_empresa.getText().toString().trim().isEmpty() &&
                !et_pais.getText().toString().trim().isEmpty() &&
                !et_ciudad.getText().toString().trim().isEmpty() &&
                !et_email.getText().toString().trim().isEmpty() &&
                TIPO_EMPRESA != -1 &&
                !et_anio_f.getText().toString().trim().isEmpty() &&
                !et_sec_empresarial.getText().toString().trim().isEmpty() &&
                et_producto.getTagList().size() > 0 &&
                !et_nombre_contacto_registro.getText().toString().isEmpty() &&
                !et_apellido.getText().toString().trim().isEmpty() &&
                !et_cargo_contacto_registro.getText().toString().trim().isEmpty() &&
                !et_telefono_oficina.getText().toString().trim().isEmpty() &&
                !et_celular.getText().toString().trim().isEmpty() &&
                !et_email_contacto.getText().toString().trim().isEmpty()) resultado = true;

        else Toast.makeText(getApplicationContext(), R.string.m_ingrese_todos_campos, Toast.LENGTH_SHORT).show();
        return resultado;
    }

    private void requestRegistrarEmpresa() {
        if (ConexionBroadcastReceiver.isConnected()) {
            showDialog(pDialog);
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constantes.URL_REGISTRAR_USUARIO,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d(TAG, s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                Log.d(TAG, jsonObject.toString());
                                if (jsonObject.getBoolean("status"))
                                    IniciarSesionActivity.requestIniciarSesion(et_email, et_contrasenia, pDialog, RegistroActivity.this, preferencia.getTokenFcm());
                                else {
                                    int codigo = jsonObject.getInt("codigo");
                                    mostrarMensaje(codigo);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            hidepDialog(pDialog);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            VolleyLog.d(volleyError.toString());
                            hidepDialog(pDialog);
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    try {
                        params = crearHashMap(imagen_base,
                                    et_nombre_empresa.getText().toString().trim(),
                                    et_pais.getText().toString().trim(),
                                    et_ciudad.getText().toString().trim(),
                                    et_email.getText().toString().trim(),
                                    et_contrasenia.getText().toString().trim(),
                                    String.valueOf(TIPO_EMPRESA),
                                    et_anio_f.getText().toString().trim(),
                                    et_descripcion_e.getText().toString().trim(),
                                    BuscarDetalle.getMarcados(BuscarDetalle.TIPO_EMPRESARIAL),
                                    et_producto.getTagList(),
                                    BuscarDetalle.getMarcados(BuscarDetalle.TIPO_CERTIFICACIONES),
                                    et_nombre_contacto_registro.getText().toString(),
                                    et_apellido.getText().toString(),
                                    et_cargo_contacto_registro.getText().toString(),
                                    et_telefono_oficina.getText().toString(),
                                    et_celular.getText().toString(),
                                    et_email_contacto.getText().toString(),
                                    et_website.getText().toString(),
                                    et_linkedin.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "DATA_ENVIADA"+params.toString());
                    return params;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Configuracion.getInstance().addToRequestQueue(request, TAG);
        } else { ConexionBroadcastReceiver.showSnack(layout, this); }
    }

    private void mostrarMensaje(int tipo) {
        String extra = "";
        switch (tipo) {
            case -10    : extra = getString(R.string.m_registro_10); break;
            case -9     : extra = getString(R.string.m_registro_9); break;
            case -8     : extra = getString(R.string.m_registro_8); break;
            case -7     : extra = getString(R.string.m_registro_7); break;
            case -6     : extra = getString(R.string.m_registro_6); break;
            case -5     : extra = getString(R.string.m_registro_5); break;
            case -4     : extra = getString(R.string.m_registro_4); break;
            case -3     : extra = getString(R.string.m_registro_3); break;
            case -2     : extra = getString(R.string.m_registro_2); break;
        }
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(extra)
                .setPositiveButton(R.string.aceptar, null)
                .show();
    }

    private static void generarMarcados(EditText editText, int tipo) {
        String generado = "";
        ArrayList<String> marcados = BuscarDetalle.getMarcados(tipo);
        for (int i = 0; i < marcados.size(); i++) {
            generado += ("- " + marcados.get(i) + "\n");
        }
        if (!generado.equals("- \n") && marcados.size() > 0) editText.setText(generado);
        else editText.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_CERTIFICACIONES);
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_EMPRESARIAL);
        BuscarDetalle.desmarcar(BuscarDetalle.TIPO_PAIS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (FacebookA.iniciado()) FacebookA.cerrarSesion();
        if (LinkedinA.iniciado(this)) LinkedinA.cerrarSesion(this);
    }
}