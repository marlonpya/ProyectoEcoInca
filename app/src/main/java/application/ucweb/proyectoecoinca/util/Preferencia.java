package application.ucweb.proyectoecoinca.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ucweb02 on 03/10/2016.
 */
public class Preferencia {
    public static final String TAG = Preferencia.class.getSimpleName();
    public static final String NOMBRE = "Preferencia";

    private SharedPreferences preferencia;
    private static final String actualizar_sector_empresarial = "actualizar_sector_empresarial";
    private static final String actualizar_certificacion = "actualizar_certificacion";
    private static final String busqueda_empresarial = "busqueda_empresarial";
    private static final String busqueda_pais = "busqueda_pais";


    public Preferencia(Activity activity) {
        preferencia = activity.getSharedPreferences(NOMBRE, Context.MODE_PRIVATE);
    }

    public boolean isActualizar_sector_empresarial() {
        return preferencia.getBoolean(actualizar_sector_empresarial, false);
    }

    public void setActualizar_sector_empresarial(boolean actualizar_sector_empresarial) {
        preferencia.edit().putBoolean(this.actualizar_sector_empresarial, actualizar_sector_empresarial).commit();
    }

    public boolean isActualizar_certificacion() {
        return preferencia.getBoolean(actualizar_certificacion, false);
    }

    public void setActualizar_certificacion(boolean actualizar_certificacion) {
        preferencia.edit().putBoolean(this.actualizar_certificacion, actualizar_certificacion).commit();
    }

    public boolean isBusqueda_empresarial() {
        return preferencia.getBoolean(busqueda_empresarial, false);
    }

    public void setBusqueda_empresarial(String busqueda_empresarial) {
        preferencia.edit().putString(this.busqueda_empresarial, busqueda_empresarial).commit();
    }

    public boolean isBusqueda_pais() {
        return preferencia.getBoolean(busqueda_pais, false);
    }

    public void setBusqueda_pais(String busqueda_pais) {
        preferencia.edit().putString(this.busqueda_pais, busqueda_pais).commit();
    }
}
