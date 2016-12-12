package application.ucweb.proyectoecoinca.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.linkedin.platform.LISessionManager;

import java.util.ArrayList;
import java.util.List;

import application.ucweb.proyectoecoinca.InicioActivity;
import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.adapter.NavegadorAdapter;
import application.ucweb.proyectoecoinca.apis.LinkedinA;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.ItemNavegador;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.util.Preferencia;
import application.ucweb.proyectoecoinca.util.RealmHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavegadorFragment extends Fragment {
    public static final String TAG = NavegadorFragment.class.getSimpleName();
    @BindView(R.id.btnSalir) Button btnSalir;
    @BindView(R.id.iv_navegador_icono_empresa) ImageView icono_empresa;
    @BindView(R.id.tv_navegador_nombre_empresa) TextView nombre_empresa;
    @BindView(R.id.tv_tipo_empresa) TextView tipo_empresa;
    @BindView(R.id.rv_lista_navegador) RecyclerView recyclerView;
    @BindView(R.id.switchNotificacion) Switch switchNotificacion;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private FragmentDrawerListener drawerListener;
    private NavegadorAdapter adapter;
    private View containerView;
    private Preferencia preferencia;

    public NavegadorFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navegador, container, false);
        ButterKnife.bind(this, view);

        preferencia = new Preferencia(getActivity());

        adapter = new NavegadorAdapter(getListaNavegador(), getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) { }
        }));
        sesion();
        return view;
    }

    @OnCheckedChanged(R.id.switchNotificacion)
    public void onNotifactionActivadas(boolean zwitch) {
        if (zwitch) preferencia.setNotificacionActivada(true);
        else preferencia.setNotificacionActivada(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.app_name)
                        .setMessage(R.string.m_cerrar_sesion)
                        .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (AccessToken.getCurrentAccessToken() != null) {
                                    LoginManager.getInstance().logOut();
                                } else if(LinkedinA.iniciado(getContext())){
                                    LISessionManager.getInstance(getContext()).clearSession();
                                }
                                RealmHelper.limpiarSesion();
                                Intent intent = new Intent(getActivity(), InicioActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.cancelar, null)
                        .show();
            }
        });
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) { return true; }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child,recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child,rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) { }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar){
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public List<ItemNavegador> getListaNavegador() {
        List<ItemNavegador> lista = new ArrayList<>();
        lista.add(new ItemNavegador(getString(R.string.nav_inicio), R.drawable.icono_inicio));
        lista.add(new ItemNavegador(getString(R.string.nav_mi_perfil), R.drawable.icono_mi_perfil));
        lista.add(new ItemNavegador(getString(R.string.nav_buscar), R.drawable.icono_buscar));
        lista.add(new ItemNavegador(getString(R.string.nav_vamos_al_negocio), R.drawable.icono_vamos_al_negocio));
        lista.add(new ItemNavegador(getString(R.string.nav_liaison_plus), R.drawable.icono_liaison_plus));
        lista.add(new ItemNavegador(getString(R.string.nav_contactos), R.drawable.nav_contactos));
        return lista;
    }

    public static interface ClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    public void setDrawerListener(FragmentDrawerListener listener){
        this.drawerListener = listener;
    }

    private void sesion() {
        BaseActivity.usarGlideCircular(getContext(), Usuario.getUsuario().getImagen_empresa(), icono_empresa);
        nombre_empresa.setText(Usuario.getUsuario().getNombre_empresa());
        switch (Usuario.getUsuario().getTipo_empresa()) {
            case Empresa.N_VENDEDOR : tipo_empresa.setText(R.string.vendedor); break;
            case Empresa.N_COMPRADOR: tipo_empresa.setText(R.string.comprador); break;
            case Empresa.N_AMBOS    : tipo_empresa.setText(R.string.ambos); break;
        }
    }

}
