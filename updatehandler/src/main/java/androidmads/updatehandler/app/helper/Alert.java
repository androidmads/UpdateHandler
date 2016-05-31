package androidmads.updatehandler.app.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import androidmads.updatehandler.R;
import androidmads.updatehandler.app.app.Config;

/**
 * Created by Mushtaq on 29-05-2016.
 */
public class Alert {

    Activity activity;

    public Alert(Activity activity) {
        this.activity = activity;
    }

    public void goToMarket() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.ROOT_PLAY_STORE_DEVICE + activity.getPackageName())));
    }

    public void showDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(activity.getString(R.string.update_info));
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
