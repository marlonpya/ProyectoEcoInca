package application.ucweb.proyectoecoinca.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ucweb02 on 03/10/2016.
 */
public class Preferencia {
    public static final String TAG = Preferencia.class.getSimpleName();
    public static final String NOMBRE = "Preferencia";

    private SharedPreferences preferencia;
    private static final String NOTIFICACION_ACTIVADA       = "notificacion_activada";
    private static final String TOKEN_FCM                   = "token_fcm";
    private static final String CANT_TOKEN_FCM              = "cant_token_fcm";
    private static final String ACTUALIZAR_SECTOR_EMPRESARIAL = "actualizar_sector_empresarial";
    private static final String ACTUALIZAR_CERTIFICACION    = "actualizar_certificacion";
    private static final String BUSQUEDA_EMPRESARIAL        = "busqueda_empresarial";
    private static final String BUSQUEDA_PAIS               = "busqueda_pais";
    private static final String CANT_ESPERA                 = "cant_espera";

    public Preferencia(Context activity) {
        preferencia = activity.getSharedPreferences(NOMBRE, Context.MODE_PRIVATE);
    }

    public boolean isNotificacionActivada() {
        Log.d(TAG, String.valueOf(preferencia.getBoolean(NOTIFICACION_ACTIVADA, false)));
        return preferencia.getBoolean(NOTIFICACION_ACTIVADA, false);
    }

    public void setNotificacionActivada(boolean notificacionActivada) {
        Log.d(TAG, String.valueOf(notificacionActivada));
        preferencia.edit().putBoolean(this.NOTIFICACION_ACTIVADA, notificacionActivada).commit();
    }

    public String getTokenFcm() {
        return preferencia.getString(TOKEN_FCM, "");
    }

    public void setTokenFcm(String tokenFcm) {
        preferencia.edit().putString(this.TOKEN_FCM, tokenFcm).commit();
    }

    public int getCantTokenFcm() {
        return preferencia.getInt(CANT_TOKEN_FCM, 0);
    }

    public void setCantTokenFcm(int cantidad) {
        preferencia.edit().putInt(this.CANT_TOKEN_FCM, cantidad).commit();
    }

    public boolean isActualizar_sector_empresarial() {
        return preferencia.getBoolean(ACTUALIZAR_SECTOR_EMPRESARIAL, false);
    }

    public void setActualizar_sector_empresarial(boolean actualizar_sector_empresarial) {
        preferencia.edit().putBoolean(this.ACTUALIZAR_SECTOR_EMPRESARIAL, actualizar_sector_empresarial).commit();
    }

    public boolean isActualizar_certificacion() {
        return preferencia.getBoolean(ACTUALIZAR_CERTIFICACION, false);
    }

    public void setActualizar_certificacion(boolean actualizar_certificacion) {
        preferencia.edit().putBoolean(this.ACTUALIZAR_CERTIFICACION, actualizar_certificacion).commit();
    }

    public boolean isBusqueda_empresarial() {
        return preferencia.getBoolean(BUSQUEDA_EMPRESARIAL, false);
    }

    public void setBusqueda_empresarial(String busqueda_empresarial) {
        preferencia.edit().putString(this.BUSQUEDA_EMPRESARIAL, busqueda_empresarial).commit();
    }

    public boolean isBusqueda_pais() {
        return preferencia.getBoolean(BUSQUEDA_PAIS, false);
    }

    public void setBusqueda_pais(String busqueda_pais) {
        preferencia.edit().putString(this.BUSQUEDA_PAIS, busqueda_pais).commit();
    }

    public int getCantEspera() {
        return preferencia.getInt(CANT_ESPERA, 0);
    }
    public void setCantEspera(int cantEspera) {
        preferencia.edit().putInt(this.CANT_ESPERA, cantEspera).commit();
    }
}
