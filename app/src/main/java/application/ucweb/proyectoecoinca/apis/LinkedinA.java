package application.ucweb.proyectoecoinca.apis;

import android.content.Context;

import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;

/**
 * Created by ucweb02 on 10/11/2016.
 */
public class LinkedinA {

    public static boolean iniciado(Context context) {
        LISessionManager manager = LISessionManager.getInstance(context.getApplicationContext());
        LISession session = manager.getSession();
        return session.isValid();
    }

}
