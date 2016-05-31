package androidmads.updatehandler.app.helper;

import android.app.Activity;
import android.util.Log;

import androidmads.updatehandler.app.UpdateHandler;

/**
 * Created by Mushtaq on 28-05-2016.
 */
public class Comparator {

    public static boolean isVersionNewer(Activity activity, String versionDownloadable) {
        String versionInstalled = null;
        try {
            versionInstalled = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
        } catch (Exception ignored) {
            Log.v(UpdateHandler.TAG, ignored.toString());
        }
        assert versionInstalled != null;
        return !versionInstalled.equals(versionDownloadable) && versionCompare(versionDownloadable, versionInstalled);
    }

    public static boolean versionCompare(String NewVersion, String OldVersion) {
        return Double.valueOf(NewVersion) > Double.valueOf(OldVersion);
    }

}
