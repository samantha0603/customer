package com.example.customer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by udaysaikumar on 11/06/18.
 */

public class ApiService {
    public static final String BASE_URL="https://api.mongolab.com/api/1/databases/kblabs/";
    public static Retrofit retrofit=null;
    public static Retrofit getService(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}

