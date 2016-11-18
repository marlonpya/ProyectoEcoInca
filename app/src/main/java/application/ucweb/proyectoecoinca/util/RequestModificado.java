package application.ucweb.proyectoecoinca.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
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

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;

/**
 * Created by ucweb02 on 17/11/2016.
 */
public class RequestModificado {
    private Context context;
    private View layout;
    private ProgressDialog progressDialog;

    public RequestModificado(Context context, View layout, String mensaje_progress){
        this.context = context;
        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setMessage(mensaje_progress);
        this.layout = layout;
    }

    public void registrarEmpresa(final String imagen,
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
                                 final String linkedin) {
        if (ConexionBroadcastReceiver.isConnected()) {
            BaseActivity.showDialog(progressDialog);
            StringRequest request = new StringRequest(
                    Constantes.URL_REGISTRAR_USUARIO,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                mostrarDialogo(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("imagen", imagen);
                    parametros.put("pais", pais);
                    parametros.put("ciudad", ciudad);
                    parametros.put("email", email);
                    parametros.put("tipo_usuario", tipo_usuario);
                    parametros.put("anio_fundacion", anio_fundacion);
                    parametros.put("descripcion", descripcion);
                    parametros.put("sector_empresarial", sector_empresarial.toString());
                    return parametros;
                }
            };
        } else {
            ConexionBroadcastReceiver.showSnack(layout);
        }
    }

    private void mostrarDialogo(JSONObject object) {
        try {
            new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.app_name))
                    .setMessage(String.valueOf(object.get("mensaje")))
                    .show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
