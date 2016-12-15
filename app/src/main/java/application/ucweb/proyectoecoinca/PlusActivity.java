package application.ucweb.proyectoecoinca;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.culqi.Card;
import com.culqi.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Usuario;
import butterknife.BindView;
import butterknife.OnClick;

public class PlusActivity extends BaseActivity {
    public static final String TAG = PlusActivity.class.getSimpleName();
    @BindView(R.id.toolbar_activity_plus) Toolbar toolbar;
    @BindView(R.id.iv_imagen_estrella) ImageView estrella;
    @BindView(R.id.iv_cuadro_plus1) ImageView cuadro_plus1;
    @BindView(R.id.iv_cuadro_plus2) ImageView cuadro_plus2;
    @BindView(R.id.iv_cuadro_plus3) ImageView cuadro_plus3;
    @BindView(R.id.iv_check_plus1) ImageView circulo_plus1;
    @BindView(R.id.iv_check_plus2) ImageView circulo_plus2;
    @BindView(R.id.iv_check_plus3) ImageView circulo_plus3;
    private int tipo_compra = 0;
    private int cvv = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);
        iniciarLayout();

    }

    @OnClick(R.id.btnAceptarPlus)
    public void aceptarPlus() {
        pruebaCulqi();
    }

    private void pruebaCulqi() {
        final Card card = new Card();
        card.setApellido("prueba");
        card.setNombre("prueba");
        card.setCorreo_electronico("jonqqq@culqi.com");
        card.setCvv(123);
        card.setNumero(Long.parseLong("4111111111111111"));
        card.setA_exp(9);
        card.setM_exp(2020);

        String token = new Token().getToken(card, "test_GaLSmy33nYBB");
        if (token.equals("error")) {
            Log.d(TAG, "Ocurrió un error al crear el token");
        } else {
            Log.d(TAG, "Token: " + token);
        }
    }

    private void pruebaCulqiHere() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                "https://integ-pago.culqi.com/api/v1/tokens",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            Log.d(TAG, jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.e(volleyError.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", "application/json");
                header.put("Content-type", "application/json");
                header.put("Authorization", "Bearer test_GaLSmy33nYBB");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("correo_electronico", "wmuro@me.com");
                data.put("nombre", "William");
                data.put("apellido", "Muro");
                data.put("numero", String.valueOf("4444333322221111"));
                data.put("cvv", "123");
                data.put("m_exp", "9");
                data.put("a_exp", "2019");
                data.put("guardar", "true");
                return data;
            }
        };
        Configuracion.getInstance().addToRequestQueue(request, TAG);
    }

    private void mensajeCompra() {
        EditText editText = new EditText(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_compra_plus, null);
        final EditText etCVV = (EditText) view.findViewById(R.id.et_cvv_plus);
        dialog.setView(view);
        dialog.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cvv = Integer.parseInt(etCVV.getText().toString().trim());
            }
        });
        dialog.setNegativeButton(R.string.cancelar, null);


        Card card = new Card();
        card.setNombre(Usuario.getUsuario().getCargo_contacto());
        card.setApellido(Usuario.getUsuario().getApellido_contacto());
        card.setCorreo_electronico(Usuario.getUsuario().getEmail_contacto());
        card.setCvv(cvv);
        long tipo = tipo_compra == 1 ? (long) 1.99 : (long) 5.99;
        card.setNumero(tipo);
        card.setM_exp(4);
        card.setA_exp(2000);

        /*String token = Token.getToken(card, Constantes.CULQUI_KEY);
        if (token.equals("error")) {
            Log.d(TAG, "errorrrr");
        } else {
            Log.d(TAG, token);
        }*/
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_liaison_plus));
        usarGlide(this, R.drawable.imagen_estrella_plus, estrella);
        circulo_plus1.setVisibility(View.GONE);
        circulo_plus2.setVisibility(View.GONE);
        circulo_plus3.setVisibility(View.GONE);
        cuadro_plus1.setBackgroundColor(Color.WHITE);
        cuadro_plus2.setBackgroundColor(Color.WHITE);
        cuadro_plus3.setBackgroundColor(Color.WHITE);
        cuadro_plus1.setColorFilter(Color.parseColor("#FFAAAAAA"));
        cuadro_plus2.setColorFilter(Color.parseColor("#FFAAAAAA"));
        cuadro_plus3.setColorFilter(Color.parseColor("#FFAAAAAA"));
    }

    @OnClick(R.id.iv_cuadro_plus1)
    public void seleccionarPlan1() {
        limpiarMarcados(0);
    }

    @OnClick(R.id.iv_cuadro_plus2)
    public void seleccionarPlan2() {
        limpiarMarcados(1);
    }

    @OnClick(R.id.iv_cuadro_plus3)
    public void seleccionarPlan3() {
        limpiarMarcados(2);
    }

    private void limpiarMarcados(int tipo) {
        circulo_plus1.setVisibility(View.GONE);
        circulo_plus2.setVisibility(View.GONE);
        circulo_plus3.setVisibility(View.GONE);
        cuadro_plus1.setBackgroundColor(Color.WHITE);
        cuadro_plus2.setBackgroundColor(Color.WHITE);
        cuadro_plus3.setBackgroundColor(Color.WHITE);
        cuadro_plus1.setColorFilter(Color.parseColor("#FFAAAAAA"));
        cuadro_plus2.setColorFilter(Color.parseColor("#FFAAAAAA"));
        cuadro_plus3.setColorFilter(Color.parseColor("#FFAAAAAA"));
        if (tipo == 0) {
            circulo_plus1.setVisibility(View.VISIBLE);
            cuadro_plus1.setColorFilter(Color.parseColor("#00b2e2"));
            tipo_compra = 0;
        } else if (tipo == 1) {
            circulo_plus2.setVisibility(View.VISIBLE);
            cuadro_plus2.setColorFilter(Color.parseColor("#00b2e2"));
            tipo_compra = 1;
        } else if (tipo == 2) {
            circulo_plus3.setVisibility(View.VISIBLE);
            cuadro_plus3.setColorFilter(Color.parseColor("#00b2e2"));
            tipo_compra = 2;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
