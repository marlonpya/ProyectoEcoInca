package application.ucweb.proyectoecoinca.apis;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.VamosAlNegocioActivity;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.util.Preferencia;

/**
 * Created by ucweb02 on 07/12/2016.
 */

public class FcmListenerService extends FirebaseMessagingService {
    private static final String TAG = FcmListenerService.class.getSimpleName();
    private Preferencia preferencia;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        preferencia = new Preferencia(this);
        Log.d(TAG, String.valueOf(remoteMessage.getData()));

        String data = remoteMessage.getData().get("nombre") != null ? remoteMessage.getData().get("nombre") : "";
        int id      = remoteMessage.getData().get("idempresa") != null ? Integer.parseInt(remoteMessage.getData().get("idempresa")) : 0;

        Log.d(TAG, data);
        Log.d(TAG, String.valueOf(id));

        if (Usuario.getUsuario() != null) {
            if (id == Usuario.getUsuario().getId_empresa()) {
                if (preferencia.isNotificacionActivada()) mostrarNotificacion(data);
            }
        }
    }

    private void mostrarNotificacion(String empresa) {
        int cant = preferencia.getCantTokenFcm();

        Intent intent = new Intent(this, VamosAlNegocioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this);
        notificacion.setAutoCancel(true);
        notificacion.setContentTitle(getString(R.string.app_name));
        notificacion.setContentText(empresa + " " + getString(R.string.n_empresa_notificacion));
        notificacion.setSmallIcon(R.drawable.icono_aplicacion_ecoinca);
        notificacion.setContentIntent(pendingIntent);
        notificacion.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificacion.setVibrate(new long[]{1000, 1000, 1000, 1000});

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(cant, notificacion.build());

        preferencia.setCantTokenFcm(cant + 1);
    }
}
