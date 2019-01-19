package com.example.app.Password;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.app.Response.ChangePasswordResponse;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.Response.TokenResponse;
import com.example.app.Util.RegPrefManager;
import com.example.app.foodie.DrawerActivity;
import com.example.sukanta.foodie.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ChangePasswordFragment extends Fragment {

    private WebApi webApi;
    Retrofit retrofit;
    String acess_token = "";
    private EditText oldPassTv,newPassTv;
    private Button enterBtn;
    String userId;
    ProgressBar simpleProgressBar;
    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_change_password, container, false);
       //  simpleProgressBar = (ProgressBar)v. findViewById(R.id.progressBarDil);
        newPassTv=v.findViewById(R.id.newPassTv);
        oldPassTv=v.findViewById(R.id.oldPassTv);
        enterBtn=v.findViewById(R.id.enterBtn);
        userId= RegPrefManager.getInstance(getContext()).getLoginUserID();


        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!newPassTv.getText().toString().isEmpty()&&!oldPassTv.getText().toString().isEmpty()){
                    if(isNetworkAvailable()) {
                        fetchAcessToken();
                    }else {
                        Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Please Enter New and Old Password", Toast.LENGTH_LONG).show();
                }

            }
        });

        return v;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void changePassword(){
       // simpleProgressBar .setVisibility(View.VISIBLE);
        Call<ChangePasswordResponse> call=webApi.changePassword(acess_token,userId,oldPassTv.getText().toString().trim(),newPassTv.getText().toString().trim());
        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
           //     simpleProgressBar.setVisibility(View.INVISIBLE);
                int code=response.code();
                switch (code){
                    case 200:
                        String status = response.body().getStatus();
                        if (status.equals("SUCCESS")) {
                            String msg = response.body().getMessage();
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), DrawerActivity.class));
                        }
                        break;
                    case 417:
                        Toast.makeText(getActivity(), "Please Enter Correct old Password", Toast.LENGTH_SHORT).show();
                        break;
                }
                /*if(code==417){
                    Toast.makeText(getActivity(), "Please Enter Correct old Password", Toast.LENGTH_SHORT).show();
                }else {
                    String status = response.body().getStatus();
                    if (status.equals(null)) {
                        Toast.makeText(getActivity(), "Please Enter Correct old Password", Toast.LENGTH_SHORT).show();

                    } else {
                        if (status.equals("SUCCESS")) {
                            String msg = response.body().getMessage();
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), DrawerActivity.class));
                        }
                    }
                }*/
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
            //   simpleProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAcessToken() {
        //getting the progressbar


        Call<TokenResponse> call=webApi.accessToken("password","fbApp","fbApp","admin","123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token=response.body().getValue();
                Log.d("Tag","token===>"+acess_token);
                changePassword();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });



      /*  //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerLinks.getToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        pprogressBar.setVisibility(View.INVISIBLE);
                        Log.e("Response", response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            acess_token = obj.getString("value");
                            if (acess_token != null) {
                                getAllItemList(view);
                            } else {
                                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);*/
    }


}
