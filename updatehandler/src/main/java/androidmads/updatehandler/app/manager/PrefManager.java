package androidmads.updatehandler.app.manager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidmads.updatehandler.app.app.Constants;

public class PrefManager {

    String TAG = PrefManager.class.getSimpleName();
    SharedPreferences pref;
    Editor editor;
    Activity _activity;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "ANDROID_MAD";

    public PrefManager(Activity activity) {
        this._activity = activity;
        pref = activity.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void setCount() {
        editor = pref.edit();
        editor.putInt(Constants.KEY_COUNT, getCount() + 1);
        editor.apply();
    }

    public int getCount() {
        if (pref.getInt(Constants.KEY_COUNT, 0) >= getPref()) {
            editor = pref.edit();
            editor.putInt(Constants.KEY_COUNT, 0);
            editor.apply();
            return 0;
        } else {
            return pref.getInt(Constants.KEY_COUNT, 0);
        }
    }

    public void setPref(int count) {
        editor = pref.edit();
        editor.putInt(Constants.KEY_PREF, count);
        editor.apply();
    }

    public int getPref() {
        return pref.getInt(Constants.KEY_PREF, 5);
    }

}
