package application.ucweb.proyectoecoinca;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.DeepLinkHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import butterknife.OnClick;

public class IniciarSesionActivity extends BaseActivity {
    public static final String TAG = IniciarSesionActivity.class.getSimpleName();
    @BindView(R.id.btnLinkedin) Button btnLinkedin;
    @BindView(R.id.iv_fondo_iniciar_sesion) ImageView fondo;
    @BindView(R.id.btnFacebook) LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        iniciarLayout();
        callbackManager = CallbackManager.Factory.create();

    }

    @OnClick(R.id.btnFacebook)
    public void iniciarSesionFB() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        obtenerDatos(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
                Intent intent = new Intent(IniciarSesionActivity.this, PrincipalActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onCancel() { }

            @Override
            public void onError(FacebookException error) { }
        });
    }

    @OnClick(R.id.btnLinkedin)
    public void iniciarSesionLINKEDIN() {
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

    private void obtenerDatos( JSONObject object) {
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

        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
        startActivity(new Intent(this, PrincipalActivity.class));
    }

    private void iniciarLayout() {
        usarGlide(this, R.drawable.fondo_iniciar_sesion, fondo);
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }

    private void getPerfilLinkedin() {
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, Constantes.FETCH_BASIC_INFO, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse s) {
                try {
                    JSONObject object = s.getResponseDataAsJson();
                    Log.d(TAG, object.toString());
                    Intent intent = new Intent(IniciarSesionActivity.this, PrincipalActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }

            @Override
            public void onApiError(LIApiError error) {
                Log.d(TAG, "getPerfilLinkedin"+ error.toString());
            }
        });
    }
}
