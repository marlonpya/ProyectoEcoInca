package application.ucweb.proyectoecoinca.util;

import android.app.NotificationManager;
import android.content.Context;

import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.model.UsuarioCertificacion;
import application.ucweb.proyectoecoinca.model.UsuarioProducto;
import application.ucweb.proyectoecoinca.model.UsuarioSectorEmpresarial;

/**
 * Created by marlonpya on 9/12/16.
 */

public class RealmHelper {

    public static void limpiarSesion(Context context) {
        Usuario.cerrarSesion();
        UsuarioCertificacion.limpiarCertificacion();
        UsuarioProducto.limpiarProductos();
        UsuarioSectorEmpresarial.limpiarSectoresEmpresariales();
        Empresa.limpiarEmpresa();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }
}
