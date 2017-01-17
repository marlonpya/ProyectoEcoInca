package application.ucweb.proyectoecoinca;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.messaging.FirebaseMessaging;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.fragment.InicioFragment;
import application.ucweb.proyectoecoinca.fragment.NavegadorFragment;
import application.ucweb.proyectoecoinca.util.Preferencia;
import butterknife.BindView;
import butterknife.OnClick;

public class PrincipalActivity extends BaseActivity implements NavegadorFragment.FragmentDrawerListener{
    public static final String TAG = PrincipalActivity.class.getSimpleName();
    @BindView(R.id.myDrawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.appbar) Toolbar toolbar;
    @BindView(R.id.iv_contorno_mundo_principal) ImageView contorno_mundo_principal;
    @BindView(R.id.iv_mundo_principal) ImageView imagen_mundo;

    private NavegadorFragment navegadorFragment;
    private ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_activity_principal);
        iniciarLayout();
        configuracionNavegador();

        FirebaseMessaging.getInstance().subscribeToTopic("liaison");

        Log.d(TAG, new Preferencia(this).getTokenFcm());
        Log.d(TAG, String.valueOf(new Preferencia(this).getTokenFcm().length()));
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        cambiarFragment(position);
    }

    private void cambiarFragment(int posicion) {
        switch (posicion) {
            //case 0 : iniciarPrimerFragment(); break;
            case 1 : startActivity(new Intent(this, MiPerfilActivity.class));       break;
            case 2 : startActivity(new Intent(this, BuscarActivity.class));         break;
            case 3 : startActivity(new Intent(this, VamosAlNegocioActivity.class)); break;
            case 4 : startActivity(new Intent(this, PlusActivity.class));           break;
            case 5 : startActivity(new Intent(this, MisContactosActivity.class));   break;
        }
    }

    private void iniciarPrimerFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedor, new InicioFragment());
        transaction.commit();
    }

    private void configuracionNavegador() {
        navegadorFragment = (NavegadorFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        navegadorFragment.setUp(R.id.fragment_navigation_drawer, drawerLayout, toolbar);
        navegadorFragment.setDrawerListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void iniciarLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
    }

    private void girarContorno() {
        animator = ObjectAnimator.ofFloat(contorno_mundo_principal, "rotation", 360);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(10000);
        //animator.setStartDelay(1000);
        animator.setInterpolator(new FastOutLinearInInterpolator());
        animator.start();
    }

    @OnClick(R.id.btnIrABuscar)
    public void irABuscar() { startActivity(new Intent(getApplicationContext(), BuscarActivity.class)); }

    @OnClick(R.id.btnIrANegocio)
    public void irANegocio() { startActivity(new Intent(getApplicationContext(), VamosAlNegocioActivity.class)); }

    @OnClick(R.id. btnIrAMiPerfil)
    public void irAMiPerfil() {startActivity(new Intent(getApplicationContext(), MisContactosActivity.class)); }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.terminos_condiciones){
            View view = LayoutInflater.from(this).inflate(R.layout.terminos_y_condiciones_uso, null);
            new AlertDialog.Builder(this)
                    .setTitle("LIAISON TERMS OF USE AGREEMENT")
                    .setView(view)
                    .setPositiveButton("aceptar",null)
                    .show();
        }
        return false;
    }

    private void detenerContorno() {
        animator.end();
        animator.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        girarContorno();
    }

    @Override
    protected void onResume() {
        super.onResume();
        girarContorno();
    }

    @Override
    protected void onStop() {
        super.onStop();
        detenerContorno();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detenerContorno();
    }

    @OnClick(R.id.btnSalir)
    public void salir() {
        super.onBackPressed();
    }
}
