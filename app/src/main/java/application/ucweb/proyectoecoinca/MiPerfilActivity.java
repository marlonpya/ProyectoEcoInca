package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.ucweb.proyectoecoinca.adapter.TabMiPerfilAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.fragment.InformacionPerfilEditarFragment;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.model.UsuarioCertificacion;
import application.ucweb.proyectoecoinca.model.UsuarioProducto;
import application.ucweb.proyectoecoinca.model.UsuarioSectorEmpresarial;
import application.ucweb.proyectoecoinca.util.CirculoView;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Util;
import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import me.originqiu.library.EditTag;

public class MiPerfilActivity extends BaseActivity {
    private static final String TAG = MiPerfilActivity.class.getSimpleName();
    @BindView(R.id.layout_activity_mi_perfil) RelativeLayout layout;
    @BindView(R.id.tab_layout) TabLayout tab_layout;
    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.iv_imagen_empresa_mi_perfil) ImageView imagen_empresa;
    @BindView(R.id.tv_nombre_mi_perfil) TextView nombre_empresa;
    private TabMiPerfilAdapter adapter;
    private ProgressDialog pDialog;
    public static final int WRITE_PERMISSION = 0x01;
    private static int VALOR = 1;
    private String imagen_base = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        iniciarLayout();
        mostrarmensaje();
        if (ConexionBroadcastReceiver.isConnected())
            Glide.with(this).load(Usuario.getUsuario().getImagen_empresa()).transform(new CirculoView(this)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imagen_empresa);
        else
            BaseActivity.usarGlideCircular(this, Usuario.getUsuario().getImagen_empresa(), imagen_empresa);
        nombre_empresa.setText(Usuario.getUsuario().getNombre_empresa());

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

    private void mostrarmensaje() {
        String descripcion = "";
        String linkedin = "";
        String web = "";
        String imagen = "";
        if(Usuario.getUsuario() != null) {
            if(Usuario.getUsuario().getDescripcion().isEmpty() || Usuario.getUsuario().getLinkedin().isEmpty() ||
                    Usuario.getUsuario().getWeb().isEmpty() || Usuario.getUsuario().getImagen_empresa().isEmpty()) {

                if(Usuario.getUsuario().getDescripcion().isEmpty()){
                    descripcion = getString(R.string.descripcion_pop) + "\n";
                }
                if(Usuario.getUsuario().getLinkedin().isEmpty()){
                    linkedin = getString(R.string.linkedin_pop) + "\n";
                }
                if( Usuario.getUsuario().getWeb().isEmpty()){
                    web = getString(R.string.web_pop)+ "\n";
                }
                if(validarimagen()){
                    imagen = getString(R.string.imagen_pop);
                }
                new AlertDialog.Builder(this)
                        .setTitle(R.string.app_name)
                        .setMessage(descripcion + linkedin + web + imagen)
                        .setPositiveButton(R.string.aceptar,null)
                        .show();
            }
        }
    }

    private boolean validarimagen() {
        String Imagen = Usuario.getUsuario().getImagen_empresa();
        String ultcaracter = Imagen.substring(Imagen.length() - 1);
        return !ultcaracter.equals("g");
    }

    @OnClick(R.id.iv_imagen_empresa_mi_perfil)
    public void editarImagenValidar() {
        if(Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
             else
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), VALOR);
        }else
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), VALOR);
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
                    imagen_empresa.setImageResource(0);
                    Toast.makeText(getApplicationContext(), getString(R.string.regla_imagenes), Toast.LENGTH_LONG).show();
                } else {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    fotobitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    fotobitmap.recycle();
                    byte[] bytes_local = outputStream.toByteArray();
                    String imagen_encode = Base64.encodeToString(bytes_local, Base64.DEFAULT);
                    Log.d(TAG, "imagen_encode\n" + imagen_encode);
                    //imagen_empresa.setImageURI(imagen_selecionada);
                    Glide.with(this).load(new File(mi_path)).transform(new CirculoView(this)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imagen_empresa);
                    imagen_base = imagen_encode;
                    if (!imagen_base.isEmpty()) editarImagen(String.valueOf(Usuario.getUsuario().getId_empresa()));
                }
            }
        }
    }

    private void editarImagen(final String id){
        if(ConexionBroadcastReceiver.isConnected()) {
            showDialog(pDialog);
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constantes.URL_EDITARIMG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            String path = "";
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                path  = jsonObject.getString("data");
                                hidepDialog(pDialog);
                                if (!path.isEmpty()) {
                                    Realm realm = Realm.getDefaultInstance();
                                    Usuario usuario = realm.where(Usuario.class).equalTo("id", 1).findFirst();
                                    realm.beginTransaction();
                                    usuario.setImagen_empresa("");
                                    usuario.setImagen_empresa(path);
                                    realm.commitTransaction();
                                    realm.close();
                                    Log.d(TAG, usuario.toString());
                                } else new AlertDialog.Builder(MiPerfilActivity.this)
                                        .setTitle(R.string.app_name)
                                        .setMessage(getString(R.string.actualizacion_error))
                                        .show();
                            } catch (JSONException e) {
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
                            new AlertDialog.Builder(MiPerfilActivity.this)
                                    .setTitle(R.string.app_name)
                                    .setMessage(getString(R.string.actualizacion_error))
                                    .show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("imagen",imagen_base);
                    params.put("idempresa", id);
                    return params;
                }
            };
            Configuracion.getInstance().addToRequestQueue(request,TAG);
        }else { ConexionBroadcastReceiver.showSnack(layout, this); }
    }

    @OnClick(R.id.btnEditarPerfil)
    public void editarPerfil()  {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.m_confirmar_actualizacion)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ConexionBroadcastReceiver.isConnected()) {
                            if (validarEditar()) requestEditarPerfil();
                        } else
                            ConexionBroadcastReceiver.showSnack(layout, MiPerfilActivity.this);
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }

    private boolean validarEditar() {
        boolean resultado = false;
        Fragment fragment = getSupportFragmentManager().findFragmentById(InformacionPerfilEditarFragment.ID);
        if (fragment != null) {
            EditTag productos       = (EditTag) fragment.getView().findViewById(R.id.et_sector_producto_editar);
            TextView sector_empresarial = (EditText) fragment.getView().findViewById(R.id.et_sector_empresarial_editar);
            String nombre_empresa   = ((EditText)fragment.getView().findViewById(R.id.et_nombre_editar)).getText().toString();
            String pais             = ((EditText)fragment.getView().findViewById(R.id.et_pais_editar)).getText().toString();
            String ciudad           = ((EditText)fragment.getView().findViewById(R.id.et_ciudad_editar)).getText().toString();
            String email            = ((TextView)fragment.getView().findViewById(R.id.tv_email_editar)).getText().toString();
            String anio_f           = ((EditText)fragment.getView().findViewById(R.id.et_anio_fundacion_editar)).getText().toString();
            String descripcion_emp  = ((EditText)fragment.getView().findViewById(R.id.et_descripcion_editar)).getText().toString();
            String nombre_contaco   = ((EditText)fragment.getView().findViewById(R.id.et_nombre_contacto_editar)).getText().toString();
            String apellido         = ((EditText)fragment.getView().findViewById(R.id.et_apellido_editar)).getText().toString();
            String cargo            = ((EditText)fragment.getView().findViewById(R.id.et_cargo_contacto_editar)).getText().toString();
            String telefono         = ((EditText)fragment.getView().findViewById(R.id.et_telefono_contacto_editar)).getText().toString();
            String celular          = ((EditText)fragment.getView().findViewById(R.id.et_movil_contacto_editar)).getText().toString();
            String email_contacto   = ((EditText)fragment.getView().findViewById(R.id.et_email_contacto_editar)).getText().toString();

            resultado = !productos.getTagList().isEmpty() && !sector_empresarial.getText().toString().isEmpty() &&
                        !nombre_empresa.isEmpty() && !pais.isEmpty() && !ciudad.isEmpty() && !email.isEmpty() &&
                        !anio_f.isEmpty() && !descripcion_emp.isEmpty() && !nombre_contaco.isEmpty() && !apellido.isEmpty() &&
                        !cargo.isEmpty() && !telefono.isEmpty() && !telefono.isEmpty() && !telefono.isEmpty() &&
                        !celular.isEmpty() && !email_contacto.isEmpty() && !email_contacto.isEmpty();
        }
        if (!resultado) Toast.makeText(this, R.string.m_existen_campos_vacíos, Toast.LENGTH_SHORT).show();
        return resultado;
    }

    private Map<String, String> crearHashMap() throws JSONException {
        Fragment fragment = getSupportFragmentManager().findFragmentById(InformacionPerfilEditarFragment.ID);
        if (fragment != null) {
            String nombre_empresa   = ((EditText)fragment.getView().findViewById(R.id.et_nombre_editar)).getText().toString();
            String pais             = ((EditText)fragment.getView().findViewById(R.id.et_pais_editar)).getText().toString();
            String ciudad           = ((EditText)fragment.getView().findViewById(R.id.et_ciudad_editar)).getText().toString();
            String email            = ((TextView)fragment.getView().findViewById(R.id.tv_email_editar)).getText().toString();
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
            UsuarioSectorEmpresarial.limpiarSectoresEmpresariales();
            UsuarioSectorEmpresarial.crearSectorEmpresarial(array_sector);
            for (String sector_emp : array_sector) {
                JSONObject json_sector_emp = new JSONObject();
                json_sector_emp.put("sector_empresarial", sector_emp);
                jSectorEmpresarial.put(json_sector_emp);
            }
            jsonArray.put(jSectorEmpresarial);

            UsuarioProducto.limpiarProductos();
            UsuarioProducto.crearProducto(productos);
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
            UsuarioCertificacion.limpiarCertificacion();
            UsuarioCertificacion.crearCertificacion(array_certificacion);
            for(String certificacion : array_certificacion) {
                JSONObject json_certificacion = new JSONObject();
                json_certificacion.put("certificado", certificacion);
                jCertificacion.put(json_certificacion);
            }
            jsonArray.put(jCertificacion);
            hashmap.put("actualizarEmpresa", jsonArray.toString());
            Log.d(TAG, hashmap.toString());
            Usuario usuario_viejo = Usuario.getUsuario();
            Usuario usuario = new Usuario();
            usuario.setId(usuario_viejo.getId());
            usuario.setId_empresa(usuario_viejo.getId_empresa());
            usuario.setTipo_empresa(usuario_viejo.getTipo_empresa());
            usuario.setImagen_empresa(usuario_viejo.getImagen_empresa());
            usuario.setNombre_empresa(nombre_empresa);
            usuario.setPais(pais);
            usuario.setCiudad(ciudad);
            usuario.setEmail_empresa(email);
            usuario.setAnio_fundacion(anio_f);
            usuario.setDescripcion(descripcion_emp);
            usuario.setNombre_contacto(nombre_contaco);
            usuario.setApellido_contacto(apellido);
            usuario.setCargo_contacto(cargo);
            usuario.setTelefono(telefono);
            usuario.setCelular(celular);
            usuario.setEmail_contacto(email_contacto);
            usuario.setWeb(web);
            usuario.setLinkedin(linkedin);
            usuario.setPlus(usuario_viejo.isPlus());
            usuario.setSesion(true);
            usuario.setCantidad_busqueda(usuario_viejo.getCantidad_busqueda());
            Usuario.iniciarSesion(usuario);
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
                            hidepDialog(pDialog);
                            if (jData.getBoolean("status")) {
                                new AlertDialog.Builder(MiPerfilActivity.this)
                                        .setTitle(R.string.app_name)
                                        .setMessage(getString(R.string.actualizacion_ok))
                                        .show();
                            } else new AlertDialog.Builder(MiPerfilActivity.this)
                                    .setTitle(R.string.app_name)
                                    .setMessage(getString(R.string.actualizacion_error))
                                    .show();

                            Log.d(TAG, jData.toString());
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
                        new AlertDialog.Builder(MiPerfilActivity.this)
                                .setTitle(R.string.app_name)
                                .setMessage(getString(R.string.actualizacion_error))
                                .show();
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
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
        //usarGlide(this, R.drawable.fondo_iniciar_sesion, fondo);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.app_name);
        pDialog.setMessage(getString(R.string.m_actualizando));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
