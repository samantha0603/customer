package com.example.customer;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by udaysaikumar on 11/06/18.
 */

public interface ApiInterfacePut {
    @POST("collections/manager/?apiKey=h3IQbkK_J7am5m45-Wd-FryEVIqrvOJI")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> savePost(@Body String body);

    //register
    @POST("collections/customer_login/?apiKey=h3IQbkK_J7am5m45-Wd-FryEVIqrvOJI")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> saveDetails(@Body String body);
    @PUT("collections/Login/?apiKey=h3IQbkK_J7am5m45-Wd-FryEVIqrvOJI")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> SaveUpdate(@Query("q") String str ,@Body String body);



    @POST("collections/Login/?apiKey=h3IQbkK_J7am5m45-Wd-FryEVIqrvOJI")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> Registration(@Body String str);
}
