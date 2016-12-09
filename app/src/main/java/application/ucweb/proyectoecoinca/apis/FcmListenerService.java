package application.ucweb.proyectoecoinca.apis;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import application.ucweb.proyectoecoinca.PrincipalActivity;
import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.util.Preferencia;

/**
 * Created by ucweb02 on 07/12/2016.
 */

public class FcmListenerService extends FirebaseMessagingService {
    public static final String TAG = FcmListenerService.class.getSimpleName();
    private Preferencia preferencia;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        preferencia = new Preferencia(this);
        for (int i = 0; i < remoteMessage.getData().size(); i++) {
            Log.d(TAG, remoteMessage.getData().get(i));
        }
        Log.d(TAG, String.valueOf(remoteMessage.getData()));
        if (preferencia.isNotificacionActivada()) mostrarNotificacion("");
    }

    private void mostrarNotificacion(String contacto) {
        int cant = preferencia.getCantTokenFcm();

        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this);
        notificacion.setAutoCancel(true);
        notificacion.setContentTitle(getString(R.string.app_name));
        notificacion.setContentText(getString(R.string.comprador) + contacto);
        notificacion.setSmallIcon(R.drawable.icono_aplicacion_ecoinca);
        notificacion.setContentIntent(pendingIntent);
        notificacion.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificacion.setVibrate(new long[]{1000, 1000, 1000, 1000});

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(cant, notificacion.build());

        preferencia.setCantTokenFcm(cant + 1);
    }
}
