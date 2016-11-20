package androidmads.updatehandler.app.helper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import androidmads.updatehandler.app.app.Config;

import static androidmads.updatehandler.app.UpdateHandler.TAG;
import static androidmads.updatehandler.app.app.Config.PLAY_STORE_HTML_TAGS_TO_DIV_WHATS_NEW_END;
import static androidmads.updatehandler.app.app.Config.PLAY_STORE_HTML_TAGS_TO_DIV_WHATS_NEW_START;
import static androidmads.updatehandler.app.app.Config.PLAY_STORE_WHATS_NEW;

public class Alert {

    private AppCompatActivity activity;

    public Alert(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void showDialog(String response, boolean info, String version) {
        String whatsNew = whatNew(response);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Update Found");
        try {
            String appName = activity.getPackageManager().getApplicationLabel(
                    activity.getPackageManager().getApplicationInfo(
                            activity.getPackageName(), 0)).toString();
            String message = "New Version (" + version + ") Available for " + appName;
            if (info) {
                message = message + "\n" + whatsNew;
            }
            alertDialogBuilder.setMessage(message);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToMarket();
            }
        });
        alertDialogBuilder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        try {
            alertDialog.show();
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }

    }

    public String whatNew(String response) {
        if (!response.toUpperCase().contains(PLAY_STORE_WHATS_NEW)) {
            return "";
        }
        Document doc = Jsoup.parse(response);
        String whatsNew = doc.select("div[class=recent-change]").toString() + "\n";
        whatsNew = whatsNew.replace(PLAY_STORE_HTML_TAGS_TO_DIV_WHATS_NEW_START, "");
        whatsNew = whatsNew.replace(PLAY_STORE_HTML_TAGS_TO_DIV_WHATS_NEW_END, "");
        whatsNew = whatsNew.replaceAll("[\r\n]+", "\n-");
        whatsNew = whatsNew.substring(0, whatsNew.length() - 2);
        return whatsNew;
    }

    private void goToMarket() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.ROOT_PLAY_STORE_DEVICE + activity.getPackageName())));
    }

}
