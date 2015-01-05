/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snapme;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.prefs.Preferences;

/**
 *
 * @author Md Imran Hasan Hira
 */
public class W {

    /**
     *
     * @return
     */
    public static String getDateStr(Calendar c) {
        return "" + c.get(Calendar.YEAR) //
                + String.format("%02d", c.get(Calendar.MONTH) + 1) //
                + String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) //
                ;
    }

    public static String getDateStr() {
        return "" + getDateStr(Calendar.getInstance());
    }
    
    public static String getSlashSeparatedDateStr(){
        Calendar c = Calendar.getInstance();
        return "" + c.get(Calendar.YEAR) //
                + String.format("/%02d", c.get(Calendar.MONTH) + 1) //
                + String.format("/%02d", c.get(Calendar.DAY_OF_MONTH)) //
                ;
    }

    public static String getTimeStr(Calendar c) {
        return "" + String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) //
                + String.format("%02d", c.get(Calendar.MINUTE)) //
                + String.format("%02d", c.get(Calendar.SECOND));
    }

    public static String getTimeStr() {
        return getTimeStr(Calendar.getInstance());
    }

    synchronized static void prl(String string) {
        System.out.println(string);
    }

    static String getTimestamp() {
        return "" + new Timestamp(System.currentTimeMillis());
    }

    static String getDateTime() {
        Calendar c = Calendar.getInstance();
        return "" + getDateStr(c) + "_" + getTimeStr(c);
    }

}
