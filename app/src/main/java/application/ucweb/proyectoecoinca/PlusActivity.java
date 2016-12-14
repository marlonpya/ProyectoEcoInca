package application.ucweb.proyectoecoinca;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.culqi.Card;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Usuario;
import butterknife.BindView;
import butterknife.OnClick;

public class PlusActivity extends BaseActivity {
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
    public void aceptarPlus() { }

    private void mensajeCompra(int tipo_compra) {
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
