package application.ucweb.proyectoecoinca.apis;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
    private static final int SOLO_MATCH = 0;
    private static final int ACEPTADO_MACTH = 1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        preferencia = new Preferencia(this);
        Log.d(TAG, String.valueOf(remoteMessage.getData()));

        String data = remoteMessage.getData().get("nombre") != null ? remoteMessage.getData().get("nombre") : "";
        int id      = remoteMessage.getData().get("idempresa") != null ? Integer.parseInt(remoteMessage.getData().get("idempresa")) : -1;
        int tipo    = remoteMessage.getData().get("tipo") != null ? Integer.parseInt(remoteMessage.getData().get("tipo")) : -1 ;

        Log.d(TAG, data);
        Log.d(TAG, String.valueOf(id));
        Log.d(TAG, String.valueOf(tipo));

        if (Usuario.getUsuario() != null) {
            if (id == Usuario.getUsuario().getId_empresa()) {
                switch (tipo) {
                    case 0 : if (preferencia.isNotificacionActivada()) {
                                    mostrarNotificacion(data, tipo);
                                }
                        break;
                    case 1 : mostrarNotificacion(data, tipo);
                        break;
                }

            }
        }
    }

    private void mostrarNotificacion(String empresa, int tipo) {
        int cant = preferencia.getCantTokenFcm();
        int cantActualEspera = preferencia.getCantEspera() + 1;
        preferencia.setCantEspera(cantActualEspera);
        String texto = tipo == SOLO_MATCH ?  empresa + " " + getString(R.string.n_empresa_notificacion) : empresa;

        Intent intent = new Intent(this, VamosAlNegocioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this);
        notificacion.setAutoCancel(true);
        notificacion.setContentTitle(getString(R.string.app_name));
        notificacion.setContentText(texto);
        notificacion.setSmallIcon(R.drawable.icono_aplicacion_ecoinca);
        notificacion.setContentIntent(pendingIntent);
        notificacion.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificacion.setVibrate(new long[]{1000, 1000, 1000, 1000});

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(cant, notificacion.build());

        preferencia.setCantTokenFcm(cant + 1);
    }
}
