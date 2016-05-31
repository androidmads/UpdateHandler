package com.androidmads.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import androidmads.updatehandler.app.UpdateHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UpdateHandler updateHandler = new UpdateHandler(MainActivity.this);
        updateHandler.start();
        updateHandler.setCount(2);

    }
}
