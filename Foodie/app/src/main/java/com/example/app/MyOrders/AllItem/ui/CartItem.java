package com.example.app.MyOrders.AllItem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MyOrders.AllItem.adapter.CartAdapter;
import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.mvp.ItemsPresenterImpl;
import com.example.app.MyOrders.AllItem.datamodels.OrderDetails;
import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.app.MyOrders.AllItem.datamodels.PaymentDetails;
import com.example.app.MyOrders.AllItem.mvp.ItemsView;
import com.example.app.Util.Common.BaseActivity;
import com.example.app.foodie.DrawerActivity;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

public class CartItem extends BaseActivity implements ItemsView{

    ArrayList<OrderItem> cartlist = new ArrayList<OrderItem>();
    TextView subTotal;
    PaymentDetails paymentDetails;
    OrderDetails orderDetails;

    private CartAdapter cartlistlistAdapter;
    RecyclerView recycleview;


    Button submit;
    ItemsPresenterImpl itemsPresenter;

    String acess_token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        subTotal = findViewById(R.id.totalPrice);
        submit = findViewById(R.id.next);
        recycleview = findViewById(R.id.recycler_view);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);
        enableActionBar(true);

        itemsPresenter = new ItemsPresenterImpl(this);
        cartlist = this.getIntent().getExtras().getParcelableArrayList("itemArray");
        orderDetails = this.getIntent().getExtras().getParcelable("orderDetail");
        paymentDetails = this.getIntent().getExtras().getParcelable("paymentdetail");
        String totalPrice = this.getIntent().getExtras().getString("totalPrice");
        acess_token = this.getIntent().getExtras().getString("acesstoken");
        Log.e("acessToken",acess_token);

        subTotal.setText(totalPrice+"");

        cartlistlistAdapter = new CartAdapter(cartlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleview.setLayoutManager(mLayoutManager);
        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setAdapter(cartlistlistAdapter);
        cartlistlistAdapter.notifyDataSetChanged();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "PLACING ORDER...", Toast.LENGTH_SHORT).show();
                itemsPresenter.createOrder(orderDetails,acess_token,paymentDetails,cartlist);


            }
        });

    }

    @Override
    public void onValidationFail(int type) {

    }

    @Override
    public void onLoad(OrderPlacedResponse response) {
        if(response.getStatus().equals("SUCCESS")){
            submit.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Order Placed Sucessfully...", Toast.LENGTH_SHORT).show();

            Intent in1 = new Intent(CartItem.this, DrawerActivity.class);
            startActivity(in1);
            finish();

        }else{
            Toast.makeText(getApplicationContext(), "Something went wrong! try later...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showDialog() {
        showProgressBar();
    }

    @Override
    public void hideDialog() {
        closeProgressbar();
    }

    @Override
    public void showError(String message) {

    }
}
