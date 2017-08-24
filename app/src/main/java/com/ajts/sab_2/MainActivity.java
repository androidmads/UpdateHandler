package com.ajts.sab_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import androidmads.updatehandler.app.UpdateHandler;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.uhlPrompt);

        new UpdateHandler.Builder(this)
                .setTitle("Update Found")
                .setContent("New Version Found")
                .setUpdateText("Yes")
                .setCancelText("No")
                .showDefaultAlert(true)
                .showWhatsNew(false)
                .setCheckerCount(2)
                .setOnUpdateFoundLister(new UpdateHandler.Builder.UpdateListener() {
                    @Override
                    public void onUpdateFound(boolean newVersion, String whatsNew) {
                        tv.setText(tv.getText() + "\n\nUpdate Found : " + newVersion + "\n\nWhat's New\n" + whatsNew);
                    }
                })
                .setOnUpdateClickLister(new UpdateHandler.Builder.UpdateClickListener() {
                    @Override
                    public void onUpdateClick(boolean newVersion, String whatsNew) {
                        Log.v("onUpdateClick", String.valueOf(newVersion));
                        Log.v("onUpdateClick", whatsNew);
                    }
                })
                .setOnCancelClickLister(new UpdateHandler.Builder.UpdateCancelListener() {
                    @Override
                    public void onCancelClick() {
                        Log.v("onCancelClick", "Cancelled");
                    }
                })
                .build();

    }
}
