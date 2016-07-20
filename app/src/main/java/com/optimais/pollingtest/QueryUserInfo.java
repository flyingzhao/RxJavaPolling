package com.optimais.pollingtest;

import android.util.Log;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tzhao on 2016/7/18.
 */
public class QueryUserInfo {
    public interface QueryUserService {
        @GET("EntranceGuardes/app/appOpen_queryCustomer.action")
        Observable<UserJsonResult> upload(@Query("floor") String floor,
                                          @Query("house") String house);
    }

    private String house;
    private String floor;

    public QueryUserInfo(String floor, String house) {
        this.floor = floor;
        this.house = house;
    }

    public Observable<UserJsonResult> startQuery() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.81.155:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        QueryUserService service = retrofit.create(QueryUserService.class);

        return service.upload(floor, house)
                .doOnNext(userJsonResult -> {
                    List<UserJsonResult.user> l = userJsonResult.getList();
                    for (UserJsonResult.user u : l) {
                        Log.d("userId", u.getUserid());
                    }
                })
                .doOnError(throwable -> Log.d("userid", "userid获取失败"));

    }
}
