package androidmads.updatehandler.app.helper;

import android.app.Activity;
import android.util.Log;

import androidmads.updatehandler.app.UpdateHandler;

/**
 * Created by Mushtaq
 * Created on 28-05-2016.
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
        return !versionInstalled.equals(versionDownloadable) && ((versionCompare(versionDownloadable, versionInstalled))>0);
    }

    private static int versionCompare(String versionNew, String versionOld) {
        String[] vals1 = versionNew.split("\\.");
        String[] vals2 = versionOld.split("\\.");
        int i = 0;
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
            i++;
        }
        // compare first non-equal ordinal number
        if (i < vals1.length && i < vals2.length) {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff);
        }
        // the strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        return Integer.signum(vals1.length - vals2.length);
    }

}
