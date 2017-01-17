package application.ucweb.proyectoecoinca;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.culqi.Card;
import com.culqi.*;

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
import butterknife.OnClick;

public class PlusActivity extends BaseActivity {
    public static final String TAG = PlusActivity.class.getSimpleName();
    @BindView(R.id.constraintLayout4) ConstraintLayout layout;
    @BindView(R.id.toolbar_activity_plus) Toolbar toolbar;
    @BindView(R.id.iv_imagen_estrella) ImageView estrella;
    @BindView(R.id.iv_cuadro_plus1) ImageView cuadro_plus1;
    @BindView(R.id.iv_cuadro_plus2) ImageView cuadro_plus2;
    @BindView(R.id.iv_cuadro_plus3) ImageView cuadro_plus3;
    @BindView(R.id.iv_check_plus1) ImageView circulo_plus1;
    @BindView(R.id.iv_check_plus2) ImageView circulo_plus2;
    @BindView(R.id.iv_check_plus3) ImageView circulo_plus3;
    private static int tipo_compra = 0;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);
        iniciarLayout();

    }

    @OnClick(R.id.btnAceptarPlus)
    public void aceptarPlus() {
        if (ConexionBroadcastReceiver.isConnected()) {
            if (tipo_compra == 0) {
                 mensajeElegirTipoPlan();
            } else {
                startActivity(new Intent(this, ComprarPlusActivity.class).putExtra(Constantes.I_TIPO_PLUS, tipo_compra));
            }
        } else {
            ConexionBroadcastReceiver.showSnack(layout, this);
        }
    }

    private void pruebaCulqi() {
        final Card card = new Card();
        card.setApellido("marlon");
        card.setNombre("marlon");
        card.setCorreo_electronico("marlon@culqi.com");
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

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_liaison_plus));
        usarGlide(this, R.drawable.imagen_estrella_plus, estrella);

        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.app_name);
        pDialog.setMessage(getString(R.string.m_generando_token));
        pDialog.setCancelable(false);

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
            tipo_compra = 1;
        } else if (tipo == 1) {
            circulo_plus2.setVisibility(View.VISIBLE);
            cuadro_plus2.setColorFilter(Color.parseColor("#00b2e2"));
            tipo_compra = 2;
        } else if (tipo == 2) {
            circulo_plus3.setVisibility(View.VISIBLE);
            cuadro_plus3.setColorFilter(Color.parseColor("#00b2e2"));
            tipo_compra = 3;
        }
    }

    private void mensajeElegirTipoPlan() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(getString(R.string.escoge_tipo_plus))
                .setPositiveButton(R.string.aceptar, null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
