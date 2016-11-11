package application.ucweb.proyectoecoinca.aplicacion;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import application.ucweb.proyectoecoinca.util.CirculoView;
import butterknife.ButterKnife;

/**
 * Created by ucweb02 on 03/10/2016.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    public static void setToolbarSon(Toolbar toolbar, AppCompatActivity activity, String titulo) {
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        activity.getSupportActionBar().setTitle(titulo);
    }

    public static void usarGlide(Context context, int rutaIcono, ImageView imageView) {
        Glide.with(context)
                .load(rutaIcono)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }

    public static void usarGlide(Context context, String rutaIcono, ImageView imageView) {
        Glide.with(context)
                .load(rutaIcono)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imageView);
    }

    public static void usarGlideCircular(Context context, int ruta_imagen,ImageView imageView) {
        Glide.with(context.getApplicationContext()).load(ruta_imagen)
                .transform(new CirculoView(context.getApplicationContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(imageView);
    }

    public static void usarGlideCircular(Context context, String ruta_imagen,ImageView imageView) {
        Glide.with(context.getApplicationContext()).load(ruta_imagen)
                .transform(new CirculoView(context.getApplicationContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(imageView);
    }

}
