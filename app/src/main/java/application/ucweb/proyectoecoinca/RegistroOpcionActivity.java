package application.ucweb.proyectoecoinca;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * DESCARTADO POR EL MOMENTO, ELEGIR PERFIL, COMPRADOR O VENDEDOR
 */
public class RegistroOpcionActivity extends BaseActivity {
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.iv_imagen_contorno_comprador_o_vendedor1) ImageView contorno_imagen1;
    @BindView(R.id.iv_imagen_contorno_comprador_o_vendedor2) ImageView contorno_imagen2;
    @BindView(R.id.iv_imagen_vendedor_redondo) ImageView imagen_vendedor;
    @BindView(R.id.iv_imagen_comprador_redondo) ImageView imagen_comprador;
    private int eleccion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_opcion);
        iniciarLayout();
    }

    @OnClick(R.id.btnAceptarRegistro)
    public void aceptarRegistro() {
        if (eleccion == 0) {
            mensaje();
        } else {
            startActivity(new Intent(this, PrincipalActivity.class));
        }
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.registrarse_min));
        usarGlide(this, R.drawable.imagen_vendedor_redondo, imagen_vendedor);
        usarGlide(this, R.drawable.imagen_comprador_redondo, imagen_comprador);
        usarGlide(this, R.drawable.imagen_contorno_comprador_o_vendedor, contorno_imagen1);
        usarGlide(this, R.drawable.imagen_contorno_comprador_o_vendedor, contorno_imagen2);
        contorno_imagen1.setVisibility(View.GONE);
        contorno_imagen2.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_imagen_vendedor_redondo)
    public void clickVendedor() {
        eleccion = 1;
        contorno_imagen1.setVisibility(View.VISIBLE);
        contorno_imagen2.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_imagen_comprador_redondo)
    public void clickComprador() {
        eleccion = 2;
        contorno_imagen2.setVisibility(View.VISIBLE);
        contorno_imagen1.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void mensaje() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(R.string.titulo_dialogo)
                .setMessage(R.string.descripcion_dialogo)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

}
