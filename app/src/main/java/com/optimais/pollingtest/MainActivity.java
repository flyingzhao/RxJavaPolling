package com.optimais.pollingtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new QueryUserInfo("22", "502").startQuery()
                .subscribeOn(Schedulers.io())
                .flatMap(userJsonResult -> new UpLoadImage("1218", "/storage/sdcard/Desert.jpg").upload())
                .filter(uploadJsonResult1 -> uploadJsonResult1.getFlag().equals("1"))
                .flatMap(uploadJsonResult2 -> new Polling("1218").startPolling())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pollingJsonResult ->  Log.d("polling result", pollingJsonResult.getMessage()));

    }
}
