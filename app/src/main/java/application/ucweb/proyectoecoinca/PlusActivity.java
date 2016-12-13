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
    @BindView(R.id.iv_contorno_check1) ImageView contorno1;
    @BindView(R.id.iv_contorno_check2) ImageView contorno2;
    @BindView(R.id.iv_check1) ImageView circulo_check1;
    @BindView(R.id.iv_check2) ImageView circulo_check2;
    private int tipo_compra = 0;
    private int cvv = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);
        iniciarLayout();

    }

    @OnClick(R.id.iv_contorno_check1)
    public void opcionIzquierda() {
        circulo_check1.setVisibility(View.VISIBLE);
        contorno1.setColorFilter(Color.parseColor("#00b2e2"));

        circulo_check2.setVisibility(View.GONE);
        contorno2.setColorFilter(Color.parseColor("#FFAAAAAA"));
        contorno2.setBackgroundColor(Color.WHITE);
        tipo_compra = 1;
    }

    @OnClick(R.id.iv_contorno_check2)
    public void opcionDerecha() {
        circulo_check2.setVisibility(View.VISIBLE);
        contorno2.setColorFilter(Color.parseColor("#00b2e2"));

        circulo_check1.setVisibility(View.GONE);
        contorno1.setColorFilter(Color.parseColor("#FFAAAAAA"));
        contorno1.setBackgroundColor(Color.WHITE);
        tipo_compra = 2;
    }

    @OnClick(R.id.btnAceptarPlus)
    public void aceptarPlus() {

    }

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
        contorno1.setColorFilter(Color.parseColor("#FFAAAAAA"));
        contorno1.setBackgroundColor(Color.WHITE);
        contorno2.setColorFilter(Color.parseColor("#FFAAAAAA"));
        contorno2.setBackgroundColor(Color.WHITE);
        circulo_check1.setVisibility(View.GONE);
        circulo_check2.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
