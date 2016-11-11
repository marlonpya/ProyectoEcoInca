package application.ucweb.proyectoecoinca;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import application.ucweb.proyectoecoinca.adapter.TabMiPerfilAdapter;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import butterknife.BindView;

public class MiPerfilActivity extends BaseActivity {
    public static final String TAG = MiPerfilActivity.class.getSimpleName();
    @BindView(R.id.tab_layout) TabLayout tab_layout;
    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.toolbar_principal) Toolbar toolbar;
    @BindView(R.id.iv_fondo_mi_perfil) ImageView fondo;
    private TabMiPerfilAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        iniciarLayout();

        tab_layout.addTab(tab_layout.newTab());
        tab_layout.addTab(tab_layout.newTab());
        setupTabLayout();
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new TabMiPerfilAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void setupTabLayout() {
        TextView customTab1 = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.textview_tab_fragment_perfil, null);
        TextView customTab2 = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.textview_tab_fragment_perfil, null);
        customTab1.setText(R.string.informacion);
        tab_layout.getTabAt(0).setCustomView(customTab1);
        customTab2.setText(R.string.editar);
        tab_layout.getTabAt(1).setCustomView(customTab2);
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.nav_mi_perfil));
        usarGlide(this, R.drawable.fondo_iniciar_sesion, fondo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
