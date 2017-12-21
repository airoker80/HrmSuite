package com.harati.hrmsuite.Retrofit.RetrofiltClient;


import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 8/9/2017.
 */

public class RetrofitClient {
    //private static final String ROOT_URL = "http://192.168.1.163:8080/pms/api/v1/";
    // private static final String BASE_URL = "http://202.51.74.134:8080/pms/";
//    private static final String BASE_URL = "http://128.199.160.33/";
    private static final String BASE_URL = "http://128.199.160.33/";
//     private static final String BASE_URL = "http://192.168.1.138:8080/pms/";
//     private static final String BASE_URL = "http://192.168.1.41:8080/pms/";
//     private static final String BASE_URL = "http://192.168.1.138:8080/pms/";
    private static final String APP_URL = "api/v1/";
    private static final String ROOT_URL = BASE_URL + APP_URL;

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiInterface getApiService() {
        return getRetrofitInstance().create(ApiInterface.class);
    }
}
