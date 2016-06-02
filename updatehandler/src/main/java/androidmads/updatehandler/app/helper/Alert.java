package androidmads.updatehandler.app.helper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import androidmads.updatehandler.app.app.Config;

public class Alert {

    AppCompatActivity activity;

    public Alert(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void goToMarket() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.ROOT_PLAY_STORE_DEVICE + activity.getPackageName())));
    }

    public void showDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Update Found");
        try {
            alertDialogBuilder.setMessage("New Version Available for "
                    + activity.getPackageManager().getApplicationLabel(
                            activity.getPackageManager().getApplicationInfo(
                            activity.getPackageName(), 0)));
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
            Log.v("TAG", e.toString());
        }

    }

}
