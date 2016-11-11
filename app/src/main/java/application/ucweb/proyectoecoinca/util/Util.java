package application.ucweb.proyectoecoinca.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ucweb02 on 09/11/2016.
 */
public class Util {

    public static String dateYYYY(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(date);
    }
}
