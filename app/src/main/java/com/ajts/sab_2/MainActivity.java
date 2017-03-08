package com.ajts.sab_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import androidmads.updatehandler.app.UpdateHandler;
import androidmads.updatehandler.app.UpdateListener;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.uhlPrompt);

        // this library works in release mode only with the same JKS key used for your Previous Version
        UpdateHandler updateHandler = new UpdateHandler(MainActivity.this);
        // to start version checker
        updateHandler.start();
        // prompting intervals
        updateHandler.setCount(2);
        // to print new features added automatically
        updateHandler.setWhatsNew(true);
        // to enable or show default dialog prompt for version update
        updateHandler.showDefaultAlert(true);
        // listener for custom update prompt
        updateHandler.setOnUpdateListener(new UpdateListener() {
            @Override
            public void onUpdateFound(boolean newVersion, String whatsNew) {
                Log.v("Update", String.valueOf(newVersion));
                Log.v("Update", whatsNew);
                tv.setText(tv.getText() + "\n\nUpdate Found : " + newVersion + "\n\nWhat's New\n" + whatsNew);
            }
        });

    }
}
