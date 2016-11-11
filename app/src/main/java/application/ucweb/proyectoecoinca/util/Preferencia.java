package application.ucweb.proyectoecoinca.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ucweb02 on 03/10/2016.
 */
public class Preferencia {
    public static final String TAG = Preferencia.class.getSimpleName();

    public static final String REGISTRO = "REGISTRO";

    public static void setIndustria(String industria, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).edit();
        editor.putString(Constantes.S_SEC_INDUSTRIAL, industria);
        editor.commit();
    }

    public static String getIndustria(Context context) {
        return context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).getString(Constantes.S_SEC_INDUSTRIAL, "");
    }

    public static void setProducto(String industria, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).edit();
        editor.putString(Constantes.S_PRODUCTO_REGISTRO, industria);
        editor.commit();
    }

    public static String getProducto(Context context) {
        return context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).getString(Constantes.S_PRODUCTO_REGISTRO, "");
    }

    public static void setServicio(String industria, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).edit();
        editor.putString(Constantes.S_SERVICIO_REGISTRO, industria);
        editor.commit();
    }

    public static String getServicio(Context context) {
        return context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).getString(Constantes.S_SERVICIO_REGISTRO, "");
    }

    public static void setCertificado(String certificado, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).edit();
        editor.putString(Constantes.S_CERTIFICADO_REGISTRO, certificado);
        editor.commit();
    }

    public static String getCertificado(Context context) {
        return context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).getString(Constantes.S_CERTIFICADO_REGISTRO, "");
    }

}
