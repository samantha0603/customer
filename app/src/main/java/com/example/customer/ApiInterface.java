package com.example.customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by udaysaikumar on 11/06/18.
 */

public interface ApiInterface {

    //login
    @GET("collections/customer_login/?apiKey=h3IQbkK_J7am5m45-Wd-FryEVIqrvOJI")
    Call<List<LoginDataBringer>> Login(@Query("q") String s);

    @GET("collections/AssignedTo/?apiKey=h3IQbkK_J7am5m45-Wd-FryEVIqrvOJI")
    Call<List<MongoData>> getData(@Query("q") String key);

    //otp

    @GET("sms.php")
    Call<String> getOTP_Reg(@Query("username") String un,@Query("password") String pass,@Query("from") String from,@Query("to")String to,@Query("msg") Integer msg,@Query("type") String type);
    @GET("sms.php")
    Call<String> getOTP(@Query("username") String un,@Query("password") String pass,@Query("from") String from,@Query("to")String to,@Query("msg") Integer msg,@Query("type") String type);
}
