package application.ucweb.proyectoecoinca.apis;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Preferencia;

/**
 * Created by ucweb02 on 07/12/2016.
 */

public class FcmInstanceIdListener extends FirebaseInstanceIdService {
    public static final String TAG = FcmInstanceIdListener.class.getSimpleName();
    private String token = "";

    @Override
    public void onTokenRefresh() {

        token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, token);

        new Preferencia(this).setTokenFcm(token);
        if (ConexionBroadcastReceiver.isConnected()) {
            if (Usuario.getUsuario() != null) {
                if (Usuario.getUsuario().isSesion()) {
                    actualizarToken(Usuario.getUsuario().getId_empresa(), token);
                }
            }
        }
    }

    private void actualizarToken(final int id_empresa, final String token) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constantes.URL_ACTUALIZAR_TOKEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (ConexionBroadcastReceiver.isConnected()) {
                                if (!jsonObject.getBoolean("status")) actualizarToken(id_empresa, token);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.d(volleyError.getMessage());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idempresa", String.valueOf(id_empresa));
                params.put("token", token);
                params.put("dispositivo", "android");
                return params;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }
}
