package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
<<<<<<< HEAD
import android.view.MenuItem;
=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
<<<<<<< HEAD
import com.android.volley.DefaultRetryPolicy;
=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

 public class ComprarPlusActivity extends BaseActivity {
    public static final String TAG = ComprarPlusActivity.class.getSimpleName();
    @BindView(R.id.activity_comprar_plus) LinearLayout layout;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.tv_tipo_pago_plus) TextView tv_tipo_plus;
    @BindView(R.id.tv_empresa_plus) TextView tv_nombre;
    @BindView(R.id.tv_correo_plus) TextView tv_correo;
    @BindView(R.id.tv_direccion_plus) TextView tv_direccion;
    @BindView(R.id.et_nombre_plus) EditText et_nombre;
    @BindView(R.id.et_apellido_plus) EditText et_apellido;
    @BindView(R.id.et_numero_tarjeta) EditText et_numero;
    @BindView(R.id.et_vencimiento) EditText et_vencimiento;
    @BindView(R.id.et_cvc) EditText et_cvc;
    public static final String PAGO1 = "1 Mes $1.99";
    public static final String PAGO2 = "3 Mes $5.99";
    public static final String PAGO3 = "12 Mes $12.99";
    private ProgressDialog pDialog;
    private int id_tipo;
    private String direccion;
    private String ciudad;
    private int id_empresa;
    private String telefono;
    private String empresa;
<<<<<<< HEAD
    private String correo;
    private int estado;
=======
    private String nombre;
    private String apellido;
    private String correo;
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
    private static boolean aceptar_termino = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar_plus);
        iniciarLayout();
        id_tipo = getIntent().getIntExtra(Constantes.I_TIPO_PLUS, -1);
        switch (id_tipo) {
            case 1 : tv_tipo_plus.setText(PAGO1); break;
            case 2 : tv_tipo_plus.setText(PAGO2); break;
            case 3 : tv_tipo_plus.setText(PAGO3); break;
        }
<<<<<<< HEAD
        direccion   = "default";
=======
        direccion   = Usuario.getUsuario().getPais();
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
        ciudad      = Usuario.getUsuario().getCiudad();
        id_empresa  = Usuario.getUsuario().getId_empresa();
        telefono    = Usuario.getUsuario().getCelular();
        empresa     = Usuario.getUsuario().getNombre_empresa();
<<<<<<< HEAD
        /*nombre      = Usuario.getUsuario().getNombre_contacto();*/
      /*  apellido    = Usuario.getUsuario().getApellido_contacto();*/
        correo      = Usuario.getUsuario().getEmail_contacto();
        estado      = Usuario.getUsuario().isPlus() ? 1 : 0;
=======
        nombre      = Usuario.getUsuario().getNombre_contacto();
        apellido    = Usuario.getUsuario().getApellido_contacto();
        correo      = Usuario.getUsuario().getEmail_contacto();
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
        tv_nombre.setText(empresa);
        tv_correo.setText(correo);
        tv_direccion.setText(direccion);
        et_vencimiento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_vencimiento.length() == 2) et_vencimiento.append("/");
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @OnClick(R.id.bntComprar)
    public void comprarPlus() {
<<<<<<< HEAD
        String nombre = et_nombre.getText().toString().trim();
        String apellido = et_apellido.getText().toString().trim();
=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
        if (ConexionBroadcastReceiver.isConnected()) {
            if (aceptar_termino) {
                if (validar()) {
                    String m_exp = et_vencimiento.getText().toString().substring(0, 2);
                    String a_exp = et_vencimiento.getText().toString().substring(3, 7);
                    culqi(correo,
                            nombre,
                            apellido,
                            Long.parseLong(et_numero.getText().toString().trim()),
                            Integer.parseInt(et_cvc.getText().toString()),
                            m_exp,
<<<<<<< HEAD
                            Integer.parseInt(a_exp),
                            estado);
=======
                            Integer.parseInt(a_exp));
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
                }
            } else {
                Toast.makeText(this, R.string.termino_error, Toast.LENGTH_SHORT).show();
            }
        } else {
            ConexionBroadcastReceiver.showSnack(layout, this);
        }
    }

    @OnClick(R.id.tvTerminosCondiciones)
    public void dialogoTerminosYCondiciones() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialogo_terminos_y_condiciones, null);
        /*TextView textView = (TextView) view.findViewById(R.id.tvDTerminosCondiciones);
        textView.setText(getString(R.string.politica));*/
        new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(R.string.terminos_y_condiciones_texto)
                .setPositiveButton(R.string.aceptar, null)
                .show();
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.obten_liason_plus));
        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.app_name);
        pDialog.setMessage(getString(R.string.genera_compra_plus));
        pDialog.setCancelable(false);
    }

    private void culqi(
            String correo_electronico,
            final String nombre,
            final String apellido,
            long numero,
            int cvv,
            String m_exp,
<<<<<<< HEAD
            int a_exp,
            final int estado) {
=======
            int a_exp) {
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
        Log.d(TAG, correo_electronico);
        Log.d(TAG, nombre);
        Log.d(TAG, apellido);
        Log.d(TAG, String.valueOf(numero));
        Log.d(TAG, String.valueOf(cvv));
        Log.d(TAG, String.valueOf(m_exp));
        Log.d(TAG, String.valueOf(a_exp));
        showDialog(pDialog);

        Map<String, Object> jCarta = new HashMap<>();
        jCarta.put("correo_electronico", correo_electronico);
        jCarta.put("nombre", nombre);
        jCarta.put("apellido", apellido);
        jCarta.put("numero", numero);
        jCarta.put("cvv", cvv);
        jCarta.put("m_exp", m_exp);
        jCarta.put("a_exp", a_exp);
        jCarta.put("guardar", true);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://integ-pago.culqi.com/api/v1/tokens",
                new JSONObject(jCarta),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String token = jsonObject.getString("id");
                            requestRespuestaPago(String.valueOf(id_tipo),
                                    getString(R.string.comprar),
                                    direccion,
                                    ciudad,
                                    String.valueOf(id_empresa),
                                    telefono,
<<<<<<< HEAD
                                    et_nombre.getText().toString().trim(),
                                    et_apellido.getText().toString().trim(),
                                    correo,
                                    token,
                                    estado);
=======
                                    nombre,
                                    apellido,
                                    correo,
                                    token);
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hidepDialog(pDialog);
                        }
                        Log.d(TAG, jsonObject.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, volleyError.toString());
                        hidepDialog(pDialog);
<<<<<<< HEAD

=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Content-type", "application/json");
                header.put("Authorization", "Bearer " + Constantes.CODIGO_COMERCIO);
                return header;
            }
        };
<<<<<<< HEAD
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    private void requestRespuestaPago(
            final String idTipopaquete,
            final String descripcion,
            final String direccion,
            final String ciudad,
            final String idUsuario,
            final String telefono,
            final String nombre,
            final String apellido,
            final String correo,
<<<<<<< HEAD
            final String token,
            final int estado) {
=======
            final String token) {
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
        Log.d(TAG, "idTipopaquete_"+idTipopaquete);
        Log.d(TAG, "descripcion_"+descripcion);
        Log.d(TAG, "direccion_"+direccion);
        Log.d(TAG, "ciudad_"+ciudad);
        Log.d(TAG, "idUsuario_"+idUsuario);
        Log.d(TAG, "telefono_"+telefono);
        Log.d(TAG, "nombre_"+nombre);
        Log.d(TAG, "apellido_"+apellido);
        Log.d(TAG, "correo_"+correo);
        Log.d(TAG, "token_"+token);
<<<<<<< HEAD
        Log.d(TAG, "usuarioPlus_"+estado);
=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_PAGO_PLUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getBoolean("status")) {
                                Usuario.actualizarUsuarioPlus(true);
                                new AlertDialog.Builder(ComprarPlusActivity.this)
                                        .setTitle(R.string.app_name)
                                        .setMessage(getString(R.string.m_plus_ok))
                                        .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                onBackPressed();
                                            }
                                        })
                                        .show();
                            } else if (jsonObject.getInt("codigo") == -2) {
                                new AlertDialog.Builder(ComprarPlusActivity.this)
                                        .setTitle(R.string.app_name)
                                        .setMessage(getString(R.string.m_plus_error))
                                        .setPositiveButton(R.string.aceptar, null)
                                        .show();
                            }
                            Log.d(TAG, jsonObject.toString());
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
                        VolleyLog.e(volleyError.toString());
                        hidepDialog(pDialog);
<<<<<<< HEAD

=======
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("idTipopaquete", idTipopaquete);
                params.put("descripcion", descripcion);
                params.put("direccion", direccion);
                params.put("ciudad", ciudad);
                params.put("idUsuario", idUsuario);
                params.put("telefono", telefono);
                params.put("nombre", nombre);
                params.put("Apellido", apellido);
                params.put("correo", "marlon.arteaga.m@hotmail.com");
                params.put("token", token);
<<<<<<< HEAD
                params.put("tipousuarioplus",String.valueOf(estado));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
=======
                return params;
            }
        };
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    private boolean validar() {
        boolean resultado = false;
        if (!et_nombre.getText().toString().isEmpty() && !et_apellido.getText().toString().isEmpty() &&
                !et_numero.getText().toString().isEmpty() && !et_vencimiento.getText().toString().isEmpty() &&
                !et_cvc.getText().toString().isEmpty()) {
            if (et_nombre.getText().length() >= 5 && et_apellido.getText().length() >= 5 &&
                    et_numero.getText().length() >= 5 && et_vencimiento.getText().length() == 7 ) {
                resultado = true;
            } else {
                Log.d(TAG, "et_nombre_"+String.valueOf(et_nombre.getText().length()));
                Log.d(TAG, "et_apellido_"+String.valueOf(et_apellido.getText().length()));
                Log.d(TAG, "et_numero_"+String.valueOf(et_numero.getText().length()));
                Log.d(TAG, "et_vencimiento_"+String.valueOf(et_vencimiento.getText().length()));
                Log.d(TAG, "et_cvc_"+String.valueOf(et_cvc.getText().length()));
                Toast.makeText(this, R.string.m_ingrese_todos_mas_5, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.m_ingrese_todos_campos, Toast.LENGTH_SHORT).show();
        }
        return resultado;
    }

    @OnCheckedChanged(R.id.rbTerminos)
    public void chekearRadioButtton(boolean estado) {
        if (estado) aceptar_termino = true;
        else aceptar_termino = false;
    }
<<<<<<< HEAD

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         if (item.getItemId() == android.R.id.home) onBackPressed();
         return super.onOptionsItemSelected(item);
     }
 }
=======
}
>>>>>>> b7a067e29cabae4a5c3a5aed2f5102b0dbea98a8
