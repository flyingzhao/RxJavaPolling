package com.optimais.pollingtest;


import android.util.Log;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by tzhao on 2016/7/18.
 */
public class UpLoadImage {
    public interface RetrofitImageUploadService {
        @Multipart
        @POST("EntranceGuardes/app/appOpen_pushdDataToApp.action")
        Observable<UploadJsonResult> upload(@Part("userId") RequestBody description,
                                            @Part MultipartBody.Part file);
    }

    private String imagePath;
    private String userId;

    public UpLoadImage(String userId, String imagePath) {
        this.imagePath = imagePath;
        this.userId = userId;
    }

    public Observable<UploadJsonResult> upload() {
        File file = new File(imagePath);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.81.155:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RetrofitImageUploadService service = retrofit.create(RetrofitImageUploadService.class);


        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), userId);

        return service.upload(description, body)
                .doOnNext(uploadJsonResult -> Log.d("upload image", uploadJsonResult.getMessage()))
                .doOnError(throwable -> Log.d("upload image", "upload failed"));

    }
}
