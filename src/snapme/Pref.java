/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snapme;

import java.util.prefs.Preferences;

/**
 *
 * @author Md Imran Hasan Hira
 */
public class Pref {

    public static final String DEFAULT_INTERVAL = "default_interval";

    static void set(String key, String value) {
        getPref().put(key, value);
    }

    static void setLong(String key, long value) {
        getPref().putLong(key, value);
    }

    static String get(String key, String defaultValue) {
        return getPref().get(key, defaultValue);
    }

    static long getLong(String key, long defaultValue) {
        return getPref().getLong(key, defaultValue);
    }

    private static Preferences getPref() {
        return Preferences.systemRoot().node(Pref.class.getName());
    }
}
