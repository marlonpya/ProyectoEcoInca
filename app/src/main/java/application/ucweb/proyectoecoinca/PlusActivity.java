package application.ucweb.proyectoecoinca;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class PlusActivity extends BaseActivity {
    @BindView(R.id.toolbar_activity_plus) Toolbar toolbar;
    @BindView(R.id.iv_imagen_estrella) ImageView estrella;
    @BindView(R.id.iv_contorno_check1) ImageView contorno1;
    @BindView(R.id.iv_contorno_check2) ImageView contorno2;
    @BindView(R.id.iv_check1) ImageView circulo_check1;
    @BindView(R.id.iv_check2) ImageView circulo_check2;

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
    }

    @OnClick(R.id.iv_contorno_check2)
    public void opcionDerecha() {
        circulo_check2.setVisibility(View.VISIBLE);
        contorno2.setColorFilter(Color.parseColor("#00b2e2"));

        circulo_check1.setVisibility(View.GONE);
        contorno1.setColorFilter(Color.parseColor("#FFAAAAAA"));
        contorno1.setBackgroundColor(Color.WHITE);
    }

    @OnClick(R.id.btnAceptarPlus)
    public void aceptarPlus() { onBackPressed(); }

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
