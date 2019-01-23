package com.example.app.Util.Common;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.app.Response.TokenResponse;
import com.example.app.USERLIST.UI.ViewFranchisorDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class Token extends Activity {
    private WebApi webApi;
    Retrofit retrofit;
String acess_token = null;

    public Token() {

    }

    public Token(WebApi webApi, Retrofit retrofit) {
        this.webApi = webApi;
        this.retrofit = retrofit;

        retrofit = ApiClient.getRetrofit();

        webApi = retrofit.create(WebApi.class);
    }

    public  String accessToken(){

        Call<TokenResponse> call=webApi.accessToken("password","fbApp","fbApp","admin","123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                 acess_token=response.body().getValue();
                Log.d("Tag","token===>"+acess_token);

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });
        return  acess_token;
    }




}
