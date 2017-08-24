package androidmads.updatehandler.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.jsoup.Jsoup;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import androidmads.updatehandler.app.helper.Alert;
import androidmads.updatehandler.app.manager.PrefManager;

public class UpdateHandler {

    private AppCompatActivity activity;
    private Alert alert;
    private PrefManager prefManager;
    private Builder builder;

    private UpdateHandler(AppCompatActivity activity, Builder builder) {
        this.activity = activity;
        this.alert = new Alert(activity);
        this.prefManager = new PrefManager(activity);
        this.builder = builder;
        start();
    }

    public String start = "";

    private void start() {
        try {
            if (prefManager.getCount() == 0) {
                if (!isNetworkAvailable()) {
                    if (builder.updateListener != null)
                        builder.updateListener.onUpdateFound(false, "No Internet Connection");
                }
                if (IsNewVersionAvailable(activity)) {
                    start = whatNew(activity.getPackageName());
                    builder.updateListener.onUpdateFound(true, start);
                    if (!builder.showAlert)
                        return;
                    alert.showDialog(builder,
                            start,
                            getMarketVersion(activity));
                } else {
                    builder.updateListener.onUpdateFound(false, "No Update");
                }
            }
            prefManager.setCount();
        } catch (Exception e) {
            builder.updateListener.onUpdateFound(false, e.getMessage());
        }
    }

    // Check Version Availability

    private boolean IsNewVersionAvailable(AppCompatActivity appCompatActivity) {
        try {
            if (isNetworkAvailable()) {
                CheckUrlExists checkUrlExists = new CheckUrlExists(appCompatActivity);
                boolean result = checkUrlExists.execute().get();
                if (result) {
                    String AppVersion = getInstalledAppVersion(appCompatActivity);
                    String MarketVersion = getMarketVersion(appCompatActivity);
                    return versionCompare(MarketVersion, AppVersion);
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    private boolean versionCompare(String NewVersion, String OldVersion) {
        String[] vals1 = NewVersion.split("\\.");
        String[] vals2 = OldVersion.split("\\.");
        int i = 0;
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
            i++;
        }
        // compare first non-equal ordinal number
        if (i < vals1.length && i < vals2.length) {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff) > 0;
        }
        // the strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        return Integer.signum(vals1.length - vals2.length) > 0;
    }

    private String getMarketVersion(AppCompatActivity appCompatActivity) {
        try {
            return new MarketInfo(appCompatActivity.getPackageName(), "softwareVersion").execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String getInstalledAppVersion(AppCompatActivity appCompatActivity) {
        try {
            return new AppInfo(appCompatActivity).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String whatNew(String packageName) {
        String whatsNew = null;
        try {
            whatsNew = new whatsNew(packageName, "div[class=recent-change]").execute().get();
            whatsNew = whatsNew.replaceAll("[\r\n]+", "\n-");
            whatsNew = whatsNew.substring(0, whatsNew.length());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return whatsNew;
    }

    // Check Url Existence
    private class CheckUrlExists extends AsyncTask<Void, Void, Boolean> {

        AppCompatActivity appCompatActivity;
        boolean IsUrlExist = false;

        CheckUrlExists(AppCompatActivity compatActivity) {
            appCompatActivity = compatActivity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL("https://play.google.com/store/apps/details?id=" + appCompatActivity.getPackageName());
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("GET");
                huc.connect();
                IsUrlExist = isNetworkAvailable() && huc.getResponseCode() == 200;
            } catch (Exception e) {
                IsUrlExist = false;
            }
            return IsUrlExist;
        }
    }

    // Check App's Market Version Details
    private class MarketInfo extends AsyncTask<Void, Object, String> {
        String MarketVersion = "";
        String PackageName = "";
        String ItemType = "";

        MarketInfo(String PackageName, String ItemType) {
            this.PackageName = PackageName;
            this.ItemType = ItemType;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return Jsoup.connect("https://play.google.com/store/apps/details?id=" + PackageName)
                        .timeout(60000)
                        .ignoreHttpErrors(true)
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=" + ItemType + "]")
                        .first() // .recent-change
                        .ownText();
            } catch (Exception ignored) {

            }
            return MarketVersion;
        }
    }

    // Check App's Version Details
    private class AppInfo extends AsyncTask<Void, Object, String> {
        String AppVersion = "";
        AppCompatActivity appCompatActivity;

        AppInfo(AppCompatActivity compatActivity) {
            appCompatActivity = compatActivity;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return AppVersion = appCompatActivity.getPackageManager().getPackageInfo(appCompatActivity.getPackageName(), 0).versionName + "";
            } catch (Exception ignored) {

            }
            return AppVersion;
        }

    }

    // Check for What's New
    private class whatsNew extends AsyncTask<Void, Object, String> {
        String PackageName = "";
        String ItemType = "";

        whatsNew(String PackageName, String ItemType) {
            this.PackageName = PackageName;
            this.ItemType = ItemType;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return Jsoup.connect("https://play.google.com/store/apps/details?id=" + PackageName)
                        .timeout(60000)
                        .ignoreHttpErrors(true)
                        .referrer("http://www.google.com")
                        .get()
                        .select(ItemType)
                        .first().ownText();

            } catch (Exception e) {
                Log.v("ItemType", e.getMessage() + " 123");
                e.printStackTrace();
            }
            return "";
        }
    }

    // Check Internet Connection
    @SuppressWarnings("deprecation")
    private boolean isNetworkAvailable() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null
                    && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null
                        && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    public static class Builder {

        AppCompatActivity appCompatActivity;
        public String title = "Update Found";
        public boolean showWhatsNew = false;
        public String update = "Update";
        public String cancel = "Cancel";
        public String updateContent = "New Version ([VERSION]) Available for [APP_NAME]";
        public boolean showAlert = true;
        public boolean cancelable = true;
        public UpdateClickListener updateClickLister = null;
        public UpdateCancelListener updateCancelListener = null;
        public UpdateListener updateListener = null;
        PrefManager prefManager;

        public interface UpdateListener {
            void onUpdateFound(boolean newVersion, String whatsNew);
        }

        public interface UpdateClickListener {
            void onUpdateClick(boolean newVersion, String whatsNew);
        }

        public interface UpdateCancelListener {
            void onCancelClick();
        }

        public Builder(AppCompatActivity appCompatActivity) {
            this.appCompatActivity = appCompatActivity;
            prefManager = new PrefManager(appCompatActivity);
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder showWhatsNew(boolean showWhatsNew) {
            this.showWhatsNew = showWhatsNew;
            return this;
        }

        public Builder showDefaultAlert(boolean showAlert) {
            this.showAlert = showAlert;
            return this;
        }

        public Builder setUpdateText(String update) {
            this.update = update;
            return this;
        }

        public Builder setCancelText(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public Builder setContent(String updateContent) {
            this.updateContent = updateContent;
            return this;
        }

        public Builder setCheckerCount(int count) {
            this.prefManager.setPref(count);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setOnUpdateClickLister(UpdateClickListener updateClickLister) {
            this.updateClickLister = updateClickLister;
            return this;
        }

        public Builder setOnCancelClickLister(UpdateCancelListener updateCancelListener) {
            this.updateCancelListener = updateCancelListener;
            return this;
        }

        public Builder setOnUpdateFoundLister(UpdateListener updateLister) {
            this.updateListener = updateLister;
            return this;
        }

        public UpdateHandler build() {
            return new UpdateHandler(appCompatActivity, this);
        }
    }

}
