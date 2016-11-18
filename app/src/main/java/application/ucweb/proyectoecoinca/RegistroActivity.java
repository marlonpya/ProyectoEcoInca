package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Preferencia;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import me.originqiu.library.EditTag;
import me.originqiu.library.MEditText;

public class RegistroActivity extends BaseActivity {
    public static final String TAG = RegistroActivity.class.getSimpleName();
    @BindView(R.id.layout_activity_registro) LinearLayout layout;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.ivRegistroImagenSubir) ImageView imagen_subir;
    @BindView(R.id.icono) ImageView icono;
    @BindView(R.id.ll_btn__pais) LinearLayout btn_pais;
    @BindView(R.id.ll_btn_ciudad) LinearLayout btn_ciudad;

    @BindView(R.id.et_nombre_registro) EditText et_nombre;
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
    @BindView(R.id.tv_titulo_producto) TextView titulo_producto;
    @BindView(R.id.et_telefono_contacto_registro) TextView et_tlf_oficina;
    @BindView(R.id.et_movil_contacto_registro) TextView et_tlf;
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
    @BindColor(R.color.celeste) int CELESTE;
    private ProgressDialog pDialog;
    private static int VALOR = 1;
    private int tipo_usuario = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        iniciarLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        et_sec_empresarial.setText(Preferencia.getIndustria(this));
        et_certificado.setText(Preferencia.getCertificado(this));
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEditText.getText().length() > 0) { titulo_producto.setTextColor(CELESTE); }
                else { titulo_producto.setTextColor(Color.parseColor("#FF808080")); }
            }
        });
    }

    @OnClick(R.id.btnSiguienteRegistro)
    public void siguienteRegistro() {
        if (validarRegistroEmpresa()) {

        }
    }

    @OnClick(R.id.fabRegistroAgregarImagen)
    public void agregarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, VALOR);
    }

    @OnClick(R.id.btnSectorIndustrial)
    public void irADetalleSectorIndustrial() {
        startActivity(new Intent(this, RegistroDetalleListaActivity.class).putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, 4));
    }

    @OnClick(R.id.btnCertificado)
    public void irADetalleCertificado() {
        startActivity(new Intent(this, RegistroDetalleListaActivity.class).putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, 6));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VALOR && resultCode == RESULT_OK && data !=null) {
            Uri image = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(image, filePath, null, null, null);
            cursor.moveToFirst();

            int index = cursor.getColumnIndex(filePath[0]);
            String picturePath = cursor.getString(index);
            cursor.close();
            imagen_subir.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @OnClick(R.id.ll_btn__pais)
    public void dialogoPais() {
        new AlertDialog.Builder(this)
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
        new AlertDialog.Builder(this)
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

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.registrarse_min));

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Enviando..");
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
        if (imageView.getId() == R.id.iv_vendedor_registro) make(1);
        if (imageView.getId() == R.id.iv_ambos_registro) make(2);
    }

    public void make(int i) {
        iv_comprador.setBackground(drw_comprador_opaco);
        iv_vendedor.setBackground(drw_vendedor_opaco);
        iv_ambos.setBackground(drw_ambos_opaco);
        switch (i){
            case 0: iv_comprador.setBackground(drw_comprador); tipo_usuario = 0; break;
            case 1: iv_vendedor.setBackground(drw_vendedor); tipo_usuario = 1; break;
            case 2: iv_ambos.setBackground(drw_ambos); tipo_usuario = 2; break;
        }
    }

    private Map<String, String> crearHashMap(
            final String imagen,
            final String pais,
            final String ciudad,
            final String email,
            final String tipo_usuario,
            final String anio_fundacion,
            final String descripcion,
            final String[] sector_empresarial,
            final String[] productos,
            final String[] certificaciones,
            final String nombre,
            final String apellido,
            final String cargo,
            final String telefono,
            final String celular,
            final String email_usuario,
            final String website,
            final String linkedin) throws JSONException {
        Map<String, String> parametros = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imagen", imagen);
        jsonObject.put("pais", pais);
        jsonObject.put("ciudad", ciudad);
        jsonObject.put("email", email);
        jsonObject.put("tipo_usuario", tipo_usuario);
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
        jsonArray.put(jsonObject);
        for (String sector_emp : sector_empresarial) {
            JSONObject json_sector_emp = new JSONObject();
            json_sector_emp.put("sector_emp", sector_emp);
            jsonArray.put(json_sector_emp);
        }
        for (String producto : productos) {
            JSONObject json_productos = new JSONObject();
            json_productos.put("producto", producto);
            jsonArray.put(json_productos);
        }
        for (String certificacion : certificaciones) {
            JSONObject json_certificacion = new JSONObject();
            json_certificacion.put("certificacion", certificacion);
            jsonArray.put(json_certificacion);
        }
        parametros.put("registrarEmpresa", jsonArray.toString());
        return parametros;
    }

    private boolean validarRegistroEmpresa() {
        //agregar todos los diccionarios
        boolean resultado = false;
        boolean resultado_imagen = false;
        if (imagen_subir.getDrawable() == null) {Toast.makeText(this, "ingrese una imagen", Toast.LENGTH_SHORT).show(); resultado_imagen= true;}
        if (!et_nombre.getText().toString().trim().equals("") ||
                !et_pais.getText().toString().trim().equals("") ||
                !et_ciudad.getText().toString().trim().equals("") ||
                !et_email.getText().toString().trim().equals("") ||
                tipo_usuario != -1 ||
                !et_anio_f.getText().toString().trim().equals("") ||
                !et_sec_empresarial.getText().toString().trim().equals("") ||
                et_producto.getTagList().size() != 0 ||
                !et_certificado.getText().toString().trim().equals("") ||
                !et_nombre_contacto_registro.getText().toString().equals("") ||
                !et_apellido.getText().toString().trim().equals("") ||
                !et_cargo_contacto_registro.getText().toString().trim().equals("") ||
                !et_tlf_oficina.getText().toString().trim().equals("") ||
                !et_tlf.getText().toString().trim().equals("") ||
                !et_email_contacto.getText().toString().trim().equals("") ||
                !et_website.getText().toString().trim().equals("") ||
                !et_linkedin.getText().toString().trim().equals("")) { resultado = true;
        } else { Toast.makeText(this, "ingrese todos los campos", Toast.LENGTH_SHORT).show(); }
        return (resultado && resultado_imagen);
    }

    /*private void requestRegistrarEmpresa() {
        if (ConexionBroadcastReceiver.isConnected()) {
            StringRequest request = new StringRequest(
                    Constantes.URL_REGISTRAR_USUARIO,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            crearHashMap(imagen_subir.)
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }
            );
        } else { ConexionBroadcastReceiver.showSnack(layout); }
    }*/

}
