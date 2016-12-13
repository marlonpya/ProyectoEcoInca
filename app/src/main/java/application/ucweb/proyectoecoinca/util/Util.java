package application.ucweb.proyectoecoinca.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.ucweb.proyectoecoinca.model.BuscarDetalle;

/**
 * Created by ucweb02 on 09/11/2016.
 */
public class Util {

    public static String dateYYYY(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(date);
    }

    public static String getRutaPDF(String ruta) {
        StringBuilder total = new StringBuilder();
        for (int i = (ruta.length() - 1); i >= 0; i--) {
            total.append(ruta.charAt(i));
            if (ruta.charAt(i) == '/') break;
        }
        return total.reverse().substring(1, total.length());
    }

    public static String getPath(Uri uri, Context context) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query( uri, proj, null, null, null );
        assert cursor != null;
        if ( cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
            result = cursor.getString( column_index );
        }
        cursor.close();
        return result;
    }

    public static String generarLista(ArrayList<String> lista) {
        String resultado = "";
        if (lista.size() != 0) {
            for (String unidad : lista) {
                resultado += ("-" + unidad + "\n");
            }
        }
        return resultado;
    }
}
