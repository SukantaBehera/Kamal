package com.example.sukanta.foodie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.app.Request.CashfreeMerchantRequest;
import com.example.app.Response.CashfreePaymantResponse;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.ApiClientCashFree;
import com.example.app.Util.Common.Token;
import com.example.app.Util.Common.WebApi;
import com.gocashfree.cashfreesdk.CFClientInterface;
import com.gocashfree.cashfreesdk.CFPaymentService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;

public class PaymentActivity extends AppCompatActivity implements CFClientInterface {
    private WebApi webApi;
    Retrofit retrofit;
    String tokengenerate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        retrofit = ApiClientCashFree.getRetrofit();

        webApi = retrofit.create(WebApi.class);
    }


   /* private void tokenGenerate(){
        final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.progressBarDil);
        simpleProgressBar .setVisibility(View.VISIBLE);
        CashfreeMerchantRequest cashfreeMerchantRequest=new CashfreeMerchantRequest();
        cashfreeMerchantRequest.setOrderId("0001");
        cashfreeMerchantRequest.setOrderAmount("1");
        cashfreeMerchantRequest.setOrderCurrency("INR");

        Call<CashfreePaymantResponse> call=webApi.getCashFreeToken(cashfreeMerchantRequest);
        call.enqueue(new Callback<CashfreePaymantResponse>() {
            @Override
            public void onResponse(Call<CashfreePaymantResponse> call, Response<CashfreePaymantResponse> response) {
                String status=response.body().getStatus();
                String message=response.body().getMessage();
                tokengenerate=response.body().getCftoken();
            }

            @Override
            public void onFailure(Call<CashfreePaymantResponse> call, Throwable t) {
                Toast.makeText(PaymentActivity.this,"Token generate Failed,Try again",Toast.LENGTH_LONG).show();
            }
        });

    }*/
    private void triggerPayment(boolean isUpiIntent) {
        /*
         * token can be generated from your backend by calling cashfree servers. Please
         * check the documentation for details on generating the token.
         * READ THIS TO GENERATE TOKEN: https://bit.ly/2RGV3Pp*/

       // String token ="7z9JCN4MzUIJiOicGbhJCLiQ1VKJiOiAXe0Jye.9c9JiYyUWZiVWMmRmN0MWNiojI0xWYz9lIsATMxITNxgDN1EjOiAHelJCLiIlTJJiOik3YuVmcyV3QyVGZy9mIsEjOiQnb19WbBJXZkJ3biwiIxADMwIXZkJ3TiojIklkclRmcvJye.mj3JTg4XxMlPkWjrReLnOyM7wB7JpU7oIQDgsqzQ_5TJojVeppFpRAbq0n7Q7Cl71b";
        String token =tokengenerate;


        /*   * stage allows you to switch between sandboxed and production servers
         * for CashFree Payment Gateway. The possible values are
         *
         * 1. TEST: Use the Test server. You can use this service while integrating
         *      and testing the CashFree PG. No real money will be deducted from the
         *      cards and bank accounts you use this stage. This mode is thus ideal
         *      for use during the development. You can use the cards provided here
         *      while in this stage: https://docs.cashfree.com/docs/resources/#test-data
         *
         * 2. PROD: Once you have completed the testing and integration and successfully
         *      integrated the CashFree PG, use this value for stage variable. This will
         *      enable live transactions*/

        String stage = "TEST";

        /*
         * appId will be available to you at CashFree Dashboard. This is a unique
         * identifier for your app. Please replace this appId with your appId.
         * Also, as explained below you will need to change your appId to prod
         * credentials before publishing your app.*/

        String appId = "3232d5b14911b2d4d0ffeeb22323";
        String orderId = "Order0001";
        String orderAmount = "1";
        String orderNote = "Test Order";
        String customerName = "John Doe";
        String customerPhone = "9040019803";
        String customerEmail = "sbeheraeie@gmail.com";

        Map<String, String> params = new HashMap<>();

        params.put(PARAM_APP_ID, appId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL,customerEmail);


        for(Map.Entry entry : params.entrySet()) {
            Log.d("CFSKDSample", entry.getKey() + " " + entry.getValue());
        }

        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);

        if (isUpiIntent) {
            // Use the following method for initiating UPI Intent Payments
            cfPaymentService.upiPayment(this, params, token, this, stage);
        }
        else {
            // Use the following method for initiating regular Payments
            cfPaymentService.doPayment(this, params, token, this, stage);
        }

    }

    public void doPayment(View view) {
        this.triggerPayment(false);
    }

    public void upiPayment(View view) {
        this.triggerPayment(true);
    }


    @Override
    public void onSuccess(Map<String, String> map) {
        Log.d("CFSDKSample", "Payment Success");
    }

    @Override
    public void onFailure(Map<String, String> map) {
        Log.d("CFSDKSample", "Payment Failure");
    }

    @Override
    public void onNavigateBack() {
        Log.d("CFSDKSample", "Back Pressed");
    }
}
