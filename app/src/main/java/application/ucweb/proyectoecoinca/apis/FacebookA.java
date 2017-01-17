package application.ucweb.proyectoecoinca.apis;

import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

/**
 * Created by ucweb02 on 11/11/2016.
 */
public class FacebookA {

    public static boolean iniciado() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    public static void cerrarSesion() {
        LoginManager.getInstance().logOut();
    }
}
