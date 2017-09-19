package com.harati.hrmsuite.FCM;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        System.out.println("THIS IS TOKEN" + token);

    }
/*
    private void registerToken(final String userCode,final String accessToken,final String firebasToken) {
        apiInterface=  RetrofitClient.getApiService();
        Call<ResponseModel> call = apiInterface.saveFirebaseToken(userCode,accessToken, firebasToken);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

    }*/
}
