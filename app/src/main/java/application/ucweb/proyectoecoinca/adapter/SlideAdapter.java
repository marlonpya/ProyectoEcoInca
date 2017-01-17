package application.ucweb.proyectoecoinca.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ucweb02 on 03/10/2016.
 */
public class SlideAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private BaseActivity activity;
    private int[] layouts = new int[]{
            R.layout.vista1,
            R.layout.vista2,
            R.layout.vista3};

    public SlideAdapter(LayoutInflater inflater, BaseActivity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(layouts[position], container, false);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
