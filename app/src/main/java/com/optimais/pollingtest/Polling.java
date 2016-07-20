package com.optimais.pollingtest;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by tzhao on 2016/7/18.
 */
public class Polling {

    public interface PollingService {
        @GET("EntranceGuardes/app/appOpen_checkResult.action")
        Observable<PollingJsonResult> polling(@Query("userId") String id);
    }


    private String userId;

    public Polling(String userId) {
        this.userId = userId;
    }

    private PollingService service;

    public Observable<PollingJsonResult> startPolling() {

        Log.d("Polling", "start polling");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.81.155:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(PollingService.class);

        return Observable.interval(10, TimeUnit.SECONDS)
                .take(5)
                .observeOn(Schedulers.io())
                .flatMap(aLong -> service.polling(userId))
                .takeUntil(pollingJsonResult -> pollingJsonResult.getResult().equals("1") || pollingJsonResult.getResult().equals("-1"));

    }

}

