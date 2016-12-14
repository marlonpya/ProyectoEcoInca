package application.ucweb.proyectoecoinca.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TabHost;

import application.ucweb.proyectoecoinca.fragment.CompradorListaFragment;
import application.ucweb.proyectoecoinca.fragment.VendedorListaFragment;
import application.ucweb.proyectoecoinca.model.Empresa;

/**
 * Created by ucweb02 on 14/10/2016.
 */
public class SeguirAdapter extends FragmentStatePagerAdapter {

    private int cantidad;
    private int valor;

    public SeguirAdapter(FragmentManager fm, int cantidad) {
        super(fm);
        this.cantidad = cantidad;
    }

    public SeguirAdapter(FragmentManager fm , int cantidad, int valor) {
        super(fm);
        this.cantidad = cantidad;
        this.valor = valor;
    }

    @Override
    public Fragment getItem(int position) {
        if (valor == Empresa.N_COMPRADOR) {
            return new CompradorListaFragment();
        } else if(valor == Empresa.N_VENDEDOR) {
            return new VendedorListaFragment();
        } else {
            switch (position) {
                case 0 : return new CompradorListaFragment();
                case 1 : return new VendedorListaFragment();
                default: return null;
            }
        }
    }

    @Override
    public int getCount() {
        return cantidad;
    }
}
