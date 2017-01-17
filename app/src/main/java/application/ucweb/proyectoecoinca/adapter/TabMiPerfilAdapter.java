package application.ucweb.proyectoecoinca.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import application.ucweb.proyectoecoinca.fragment.InformacionPerfilEditarFragment;
import application.ucweb.proyectoecoinca.fragment.InformacionPerfilFragment;

/**
 * Created by ucweb02 on 05/10/2016.
 */
public class TabMiPerfilAdapter extends FragmentStatePagerAdapter {
    private int cantidad;

    public TabMiPerfilAdapter(FragmentManager fm, int cantidad) {
        super(fm);
        this.cantidad = cantidad;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 : return new InformacionPerfilFragment();
            case 1 : return new InformacionPerfilEditarFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return cantidad;
    }
}
