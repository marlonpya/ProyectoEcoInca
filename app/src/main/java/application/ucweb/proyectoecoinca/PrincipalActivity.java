package application.ucweb.proyectoecoinca;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.fragment.InicioFragment;
import application.ucweb.proyectoecoinca.fragment.NavegadorFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class PrincipalActivity extends BaseActivity implements NavegadorFragment.FragmentDrawerListener{
    public static final String TAG = PrincipalActivity.class.getSimpleName();
    @BindView(R.id.myDrawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.appbar) Toolbar toolbar;
    @BindView(R.id.appbarLayout) AppBarLayout appBarLayout;
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
        cambiarFragment(0);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        cambiarFragment(position);
    }

    private void cambiarFragment(int posicion) {
        switch (posicion) {
            case 0 : iniciarPrimerFragment(); break;
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

    /*private void cambiarFragment(int position) {
        String[] strings = {"Liaison", getString(R.string.nav_mi_perfil), getString(R.string.nav_buscar), getString(R.string.nav_vamos_al_negocio), getString(R.string.nav_liaison_plus), getString(R.string.nav_configuracion)};
        Fragment fragment = null;
        String titulo_toolbar = strings[position];
        switch (position) {
            case 0 : fragment = new InicioFragment(); posicion_fragment = 0; appBarLayout.setExpanded(true, true); break;
            case 1 : fragment = new MiPerfilFragment(); posicion_fragment = 1; appBarLayout.setExpanded(false);         break;
            case 2 : fragment = new BuscarFragment(); posicion_fragment = 1; appBarLayout.setExpanded(false);   break;
            case 3 : fragment = new VamosAlNegocioFragment(); posicion_fragment = 1; appBarLayout.setExpanded(false);   break;
        }
        if (fragment != null) {
            //toolbar.setTitle(titulo_toolbar);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.contenedor, fragment);
            transaction.commit();
            getSupportActionBar().setTitle(titulo_toolbar);
            Log.d(TAG, "titulo_toolbar_"+titulo_toolbar);
        }
    }*/

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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
