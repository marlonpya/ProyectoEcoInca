package application.ucweb.proyectoecoinca.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by ucweb02 on 03/10/2016.
 */
public class Preferencia {
    public static final String TAG = Preferencia.class.getSimpleName();

    public static final String REGISTRO = "REGISTRO";

    public static void setEmpresarial(String industria, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).edit();
        editor.putString(Constantes.S_SEC_INDUSTRIAL, industria);
        editor.commit();
    }

    public static String getEmpresarial(Context context) {
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

    public static void setCertificado(String certificado, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).edit();
        editor.putString(Constantes.S_CERTIFICADO_REGISTRO, certificado);
        editor.commit();
    }

    public static String getCertificado(Context context) {
        return context.getSharedPreferences(REGISTRO, Context.MODE_PRIVATE).getString(Constantes.S_CERTIFICADO_REGISTRO, "");
    }

}
