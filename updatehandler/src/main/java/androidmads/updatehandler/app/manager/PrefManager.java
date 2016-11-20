package androidmads.updatehandler.app.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidmads.updatehandler.app.app.Config;

@SuppressLint("CommitPrefEdits")
public class PrefManager {

    private SharedPreferences pref;
    private Editor editor;

    public PrefManager(Activity activity) {
        int PRIVATE_MODE = 0;
        pref = activity.getSharedPreferences(activity.getPackageName(), PRIVATE_MODE);
    }

    public void setCount() {
        editor = pref.edit();
        editor.putInt(Config.KEY_COUNT, getCount() + 1);
        editor.apply();
    }

    public int getCount() {
        if (pref.getInt(Config.KEY_COUNT, 0) >= getPref()) {
            editor = pref.edit();
            editor.putInt(Config.KEY_COUNT, 0);
            editor.apply();
            return 0;
        } else {
            return pref.getInt(Config.KEY_COUNT, 0);
        }
    }

    public void setPref(int count) {
        editor = pref.edit();
        editor.putInt(Config.KEY_PREF, count);
        editor.apply();
    }

    private int getPref() {
        return pref.getInt(Config.KEY_PREF, 5);
    }

}
