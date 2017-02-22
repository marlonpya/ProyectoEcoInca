package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.AccessToken;
import com.linkedin.platform.DeepLinkHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.ucweb.proyectoecoinca.apis.FacebookA;
import application.ucweb.proyectoecoinca.apis.LinkedinA;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.model.UsuarioCertificacion;
import application.ucweb.proyectoecoinca.model.UsuarioProducto;
import application.ucweb.proyectoecoinca.model.UsuarioSectorEmpresarial;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Preferencia;
import application.ucweb.proyectoecoinca.util.Util;
import butterknife.BindView;
import butterknife.OnClick;

public class IniciarSesionActivity extends BaseActivity {
    public static final String TAG = IniciarSesionActivity.class.getSimpleName();
    @BindView(R.id.btnLinkedin) Button btnLinkedin;
    @BindView(R.id.iv_fondo_iniciar_sesion) ImageView fondo;
    @BindView(R.id.btnFacebook) LoginButton loginButton;
    @BindView(R.id.layout_iniciar_sesion) RelativeLayout layout;
    @BindView(R.id.tv_usuario_iniciar_sesion) EditText tv_usuario;
    @BindView(R.id.tv_contrasenia_iniciar_sesion) EditText tv_contrasenia;
    private CallbackManager callbackManager;
    private ProgressDialog pDialog;
    private Preferencia preferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        iniciarLayout();
        callbackManager = CallbackManager.Factory.create();
        preferencia = new Preferencia(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FacebookA.iniciado()) {
            Log.d(TAG, String.valueOf(com.facebook.AccessToken.getCurrentAccessToken().getDeclinedPermissions()));
        }
        /*if (LinkedinA.iniciado(this))
            startActivity(new Intent(this, PrincipalActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));*/
    }

    @OnClick(R.id.btnFacebook)
    public void iniciarSesionFB() {
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        obtenerDatos(object);
                        Log.d(TAG, object.toString());
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
                Log.d(TAG, parameters.toString());
                /*Intent intent = new Intent(IniciarSesionActivity.this, PrincipalActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
            }

            @Override
            public void onCancel() { }

            @Override
            public void onError(FacebookException error) { Log.e(TAG, error.toString()); }
        });
    }

    @OnClick(R.id.btnLinkedin)
    public void iniciarSesionLINKEDIN() {
        //Configuracion.generateHashkey(this);
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                getPerfilLinkedin();
            }

            @Override
            public void onAuthError(LIAuthError error) {
                Log.d(TAG, error.toString());
            }
        }, true);
    }

    private void obtenerDatos(JSONObject object) {
        String id_fb = "";
        String foto = "";
        String nombre = "";
        String apellido = "";
        String email = "";
        try {
            id_fb = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id_fb + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                foto = profile_pic.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (object.has("first_name"))
                nombre = object.getString("first_name");
            if (object.has("last_name"))
                apellido = object.getString("last_name");
            if (object.has("email"))
                email = object.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, foto);
        Log.d(TAG, nombre);
        Log.d(TAG, apellido);
        Log.d(TAG, email);

        /*Intent intent = new Intent(this, PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
        if (!email.isEmpty()) { requestIniciarSesionRedSocial(email, nombre, apellido,Constantes.FACEBOOK); }
        else { Toast.makeText(this, R.string.se_requiere_correo, Toast.LENGTH_LONG).show(); FacebookA.cerrarSesion();}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        DeepLinkHelper deepLinkHelper = DeepLinkHelper.getInstance();
        deepLinkHelper.onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btnIngresar)
    public void irAMenuPrincipal() {
        if (ConexionBroadcastReceiver.isConnected()) {
            if (validarIniciarSesion(tv_usuario, tv_contrasenia)) requestIniciarSesion(tv_usuario, tv_contrasenia, pDialog, this, preferencia.getTokenFcm());
        } else {
            ConexionBroadcastReceiver.showSnack(layout, this);
        }
    }

    private void requestIniciarSesionRedSocial(final String email, final String nombre_contacto,final String apellido_contacto ,final String red_social) {
        showDialog(pDialog);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_INICIAR_SESION_2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            JSONObject jUsuario = new JSONObject(s);
                            if (jUsuario.getBoolean("status")) {
                                JSONObject jData = jUsuario.getJSONObject("data");

                                JSONObject jEmpresa = jData.getJSONObject("empresa");
                                JSONArray jSector_Industrial = jData.getJSONArray("sector_industrial");
                                JSONArray jProducto = jData.getJSONArray("producto");
                                JSONArray jCertificado = jData.getJSONArray("certificado");
                                Log.d(TAG, "jEmpresa_" + jEmpresa.toString());
                                Log.d(TAG, "jSector_Industrial" + jSector_Industrial.toString());
                                Log.d(TAG, "jProducto" + jProducto.toString());
                                Log.d(TAG, "jCertificado" + jCertificado.toString());
                                Usuario usuario = new Usuario();
                                usuario.setId_empresa(jEmpresa.getInt("EMP_ID"));
                                usuario.setTipo_empresa(jEmpresa.getInt("EMP_TIPO"));
                                usuario.setImagen_empresa(jEmpresa.getString("EMP_IMAGEN"));
                                usuario.setNombre_empresa(jEmpresa.getString("EMP_NOMBRE"));
                                usuario.setPais(jEmpresa.getString("EMP_PAIS"));
                                usuario.setCiudad(jEmpresa.getString("EMP_CIUDAD"));
                                usuario.setEmail_empresa(jEmpresa.getString("EMP_EMAIL"));
                                usuario.setAnio_fundacion(jEmpresa.getString("EMP_ANIO_FUNDACION"));
                                usuario.setDescripcion(jEmpresa.getString("EMP_DESCRIPCION"));
                                usuario.setNombre_contacto(jEmpresa.getString("CON_NOMBRE"));
                                usuario.setApellido_contacto(jEmpresa.getString("CON_APELLIDO"));
                                usuario.setCargo_contacto(jEmpresa.getString("CON_CARGO"));
                                usuario.setTelefono(jEmpresa.getString("CON_TELEFONO"));
                                usuario.setCelular(jEmpresa.getString("CON_CELULAR"));
                                usuario.setEmail_contacto(jEmpresa.getString("CON_EMAIL"));
                                usuario.setWeb(jEmpresa.getString("CON_WEB_SITE"));
                                usuario.setLinkedin(jEmpresa.getString("CON_LINKED_IN"));
                                usuario.setPlus(jEmpresa.getInt("EMP_TIPO_PLUS") == 1);
                                usuario.setCantidad_busqueda(jEmpresa.getInt("EMP_BUSQUEDA"));
                                Usuario.iniciarSesion(usuario);

                                UsuarioCertificacion.limpiarCertificacion();
                                for (int i = 0; i < jCertificado.length();i ++) {
                                    UsuarioCertificacion.crearCertificacion(jCertificado.getJSONObject(i).getString("CER_NOMBRE"));
                                }

                                UsuarioProducto.limpiarProductos();
                                for (int i = 0; i < jProducto.length(); i++) {
                                    UsuarioProducto.crearProducto(jProducto.getJSONObject(i).getString("PRO_NOMBRE"));
                                }

                                UsuarioSectorEmpresarial.limpiarSectoresEmpresariales();
                                for (int i = 0; i < jSector_Industrial.length(); i++) {
                                    UsuarioSectorEmpresarial.crearSectorEmpresarial(jSector_Industrial.getJSONObject(i).getString("SECIND_NOMBRE"));
                                }
                                hidepDialog(pDialog);
                                startActivity(new Intent(IniciarSesionActivity.this, PrincipalActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                hidepDialog(pDialog);
                                startActivity(new Intent(IniciarSesionActivity.this, RegistroActivity.class)
                                .putExtra(Constantes.B_RED_SOCIAL_INICIAR_SESION, true)
                                .putExtra(Constantes.S_EMAIL_INICIAR_SESION, email)
                                .putExtra(Constantes.S_NOMBRE_INICIAR_SESION, nombre_contacto)
                                .putExtra(Constantes.S_APE_INICIAR_SESION, apellido_contacto));
                            }
                            Log.d(TAG, jUsuario.toString());
                        } catch (JSONException e) {
                            hidepDialog(pDialog);
                            e.printStackTrace();
                            Log.e(TAG, e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.e(volleyError.toString());
                        hidepDialog(pDialog);
                        if (ConexionBroadcastReceiver.isConnected()) requestIniciarSesionRedSocial(email, nombre_contacto, apellido_contacto, red_social);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("dispositivo", "android");
                params.put("redsocial", red_social);
                params.put("token", preferencia.getTokenFcm());
                return params;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    public static void requestIniciarSesion(EditText tv_usuario, EditText tv_contrasenia, final ProgressDialog pDialog, final Context context, final String token) {
        final String txtUsuario = tv_usuario.getText().toString().trim();
        final String txtContrasenia = tv_contrasenia.getText().toString().trim();
        showDialog(pDialog);

        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_INICIAR_SESION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jUsuario = new JSONObject(s);
                            if (jUsuario.getBoolean("status")) {
                                JSONObject jData = jUsuario.getJSONObject("data");

                                JSONObject jEmpresa = jData.getJSONObject("empresa");
                                JSONArray jSector_Industrial = jData.getJSONArray("sector_industrial");
                                JSONArray jProducto = jData.getJSONArray("producto");
                                JSONArray jCertificado = jData.getJSONArray("certificado");
                                Log.d(TAG, "jEmpresa_" + jEmpresa.toString());
                                Log.d(TAG, "jSector_Industrial" + jSector_Industrial.toString());
                                Log.d(TAG, "jProducto" + jProducto.toString());
                                Log.d(TAG, "jCertificado" + jCertificado.toString());
                                Usuario usuario = new Usuario();
                                usuario.setId_empresa(jEmpresa.getInt("EMP_ID"));
                                usuario.setTipo_empresa(jEmpresa.getInt("EMP_TIPO"));
                                usuario.setImagen_empresa(jEmpresa.getString("EMP_IMAGEN"));
                                usuario.setNombre_empresa(jEmpresa.getString("EMP_NOMBRE"));
                                usuario.setPais(jEmpresa.getString("EMP_PAIS"));
                                usuario.setCiudad(jEmpresa.getString("EMP_CIUDAD"));
                                usuario.setEmail_empresa(jEmpresa.getString("EMP_EMAIL"));
                                usuario.setAnio_fundacion(jEmpresa.getString("EMP_ANIO_FUNDACION"));
                                usuario.setDescripcion(jEmpresa.getString("EMP_DESCRIPCION"));
                                usuario.setNombre_contacto(jEmpresa.getString("CON_NOMBRE"));
                                usuario.setApellido_contacto(jEmpresa.getString("CON_APELLIDO"));
                                usuario.setCargo_contacto(jEmpresa.getString("CON_CARGO"));
                                usuario.setTelefono(jEmpresa.getString("CON_TELEFONO"));
                                usuario.setCelular(jEmpresa.getString("CON_CELULAR"));
                                usuario.setEmail_contacto(jEmpresa.getString("CON_EMAIL"));
                                usuario.setWeb(jEmpresa.getString("CON_WEB_SITE"));
                                usuario.setLinkedin(jEmpresa.getString("CON_LINKED_IN"));
                                usuario.setPlus(jEmpresa.getInt("EMP_TIPO_PLUS") == 1);
                                usuario.setCantidad_busqueda(jEmpresa.getInt("EMP_BUSQUEDA"));
                                Usuario.iniciarSesion(usuario);

                                UsuarioCertificacion.limpiarCertificacion();
                                for (int i = 0; i < jCertificado.length();i ++) {
                                    if (jCertificado.getJSONObject(i).has("CER_NOMBRE")) UsuarioCertificacion.crearCertificacion(jCertificado.getJSONObject(i).getString("CER_NOMBRE"));
                                }

                                UsuarioProducto.limpiarProductos();
                                for (int i = 0; i < jProducto.length(); i++) {
                                    if (jProducto.getJSONObject(i).has("PRO_NOMBRE")) UsuarioProducto.crearProducto(jProducto.getJSONObject(i).getString("PRO_NOMBRE"));
                                }

                                UsuarioSectorEmpresarial.limpiarSectoresEmpresariales();
                                for (int i = 0; i < jSector_Industrial.length(); i++) {
                                    if (jSector_Industrial.getJSONObject(i).has("SECIND_NOMBRE")) UsuarioSectorEmpresarial.crearSectorEmpresarial(jSector_Industrial.getJSONObject(i).getString("SECIND_NOMBRE"));
                                }
                                hidepDialog(pDialog);
                                context.startActivity(new Intent(context, PrincipalActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                hidepDialog(pDialog);
                                new AlertDialog.Builder(context)
                                        .setTitle(R.string.app_name)
                                        .setMessage(jUsuario.getString("message"))
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
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", txtUsuario);
                params.put("contrasenia", txtContrasenia);
                params.put("dispositivo", "android");
                params.put("token", token);
                return params;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    private boolean validarIniciarSesion(EditText tv_usuario, EditText tv_contrasenia) {
        boolean resultado = false;
        boolean resultado_contra = true;
        Pattern pattern = Pattern.compile(Util.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(tv_usuario.getText().toString().trim());
        String txtUsuario = tv_usuario.getText().toString().trim();
        String txtContrasenia = tv_contrasenia.getText().toString().trim();
        if (!txtUsuario.isEmpty() || !txtContrasenia.isEmpty()) resultado = true;
        else Toast.makeText(getApplicationContext(), R.string.m_ingrese_todos_campos, Toast.LENGTH_SHORT).show();
        if (!matcher.matches()) { Toast.makeText(this, R.string.correo_invalido, Toast.LENGTH_LONG).show(); resultado_contra = false; }
        return resultado && resultado_contra;
    }

    private void iniciarLayout() {
        usarGlide(this, R.drawable.fondo_iniciar_sesion, fondo);

        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.app_name);
        pDialog.setMessage(getString(R.string.m_cargando_sesion));
        pDialog.setCancelable(false);
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }

    private void getPerfilLinkedin() {
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, Constantes.FETCH_ALL, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse s) {
                try {
                    JSONObject object = s.getResponseDataAsJson();
                    Log.d(TAG, object.toString());
                    String emailAddress = "";
                    String firstName = "";
                    String lastName = "";
                    if (object.has("emailAddress")) emailAddress = object.getString("emailAddress");
                    if (object.has("firstName")) firstName = object.getString("firstName");
                    if (object.has("lastName")) lastName = object.getString("lastName");

                    if (!emailAddress.isEmpty()) { requestIniciarSesionRedSocial(emailAddress, firstName, lastName,Constantes.FACEBOOK); }
                    else { Toast.makeText(IniciarSesionActivity.this, R.string.se_requiere_correo, Toast.LENGTH_LONG).show(); LinkedinA.cerrarSesion(IniciarSesionActivity.this);}

                    /*Intent intent = new Intent(IniciarSesionActivity.this, PrincipalActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);*/
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }

            @Override
            public void onApiError(LIApiError error) {Log.d(TAG, "getPerfilLinkedin"+ error.toString(), error); }
        });
    }
}
