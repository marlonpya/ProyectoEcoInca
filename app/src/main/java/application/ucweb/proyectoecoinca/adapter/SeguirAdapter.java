package application.ucweb.proyectoecoinca.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import application.ucweb.proyectoecoinca.fragment.CompradorListaFragment;
import application.ucweb.proyectoecoinca.fragment.VendedorListaFragment;

/**
 * Created by ucweb02 on 14/10/2016.
 */
public class SeguirAdapter extends FragmentStatePagerAdapter {
    private int cantidad;
    public SeguirAdapter(FragmentManager fm, int cantidad) {
        super(fm);
        this.cantidad = cantidad;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 : return new CompradorListaFragment();
            case 1 : return new VendedorListaFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return cantidad;
    }
}
