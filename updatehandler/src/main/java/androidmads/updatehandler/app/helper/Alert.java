package androidmads.updatehandler.app.helper;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import androidmads.updatehandler.app.UpdateHandler;

public class Alert {

    private AppCompatActivity activity;

    public Alert(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void showDialog(final UpdateHandler.Builder builder, final String whatsNew, final String version) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(builder.title);
        try {
            String appName = activity.getPackageManager().getApplicationLabel(
                    activity.getPackageManager().getApplicationInfo(
                            activity.getPackageName(), 0)).toString();
            String message = builder.updateContent.replace("[VERSION]", version).replace("[APP_NAME]", appName);
            if (builder.showAlert) {
                if (builder.showWhatsNew)
                    message = message + "\n" + whatsNew;
            }
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setCancelable(builder.cancelable);
            alertDialogBuilder.setPositiveButton(builder.update, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (builder.updateClickLister != null)
                        builder.updateClickLister.onUpdateClick(true, whatsNew);
                    else
                        goToMarket();
                }
            });
            alertDialogBuilder.setNegativeButton(builder.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (builder.updateCancelListener != null)
                        builder.updateCancelListener.onCancelClick();
                    else
                        dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } catch (Exception ignored) {
        }

    }

    private void goToMarket() {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="
                    + activity.getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="
                    + activity.getPackageName())));
        }
    }

}
