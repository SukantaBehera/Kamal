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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MyOrders.AllItem.adapter.CartAdapter;
import com.example.app.MyOrders.AllItem.adapter.CustomAdapter;
import com.example.app.MyOrders.AllItem.adapter.ViewListAdapter;
import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.mvp.ItemsPresenterImpl;
import com.example.app.MyOrders.AllItem.datamodels.OrderDetails;
import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.app.MyOrders.AllItem.datamodels.PaymentDetails;
import com.example.app.MyOrders.AllItem.mvp.ItemsView;
import com.example.app.Response.ViewResultCart;
import com.example.app.Util.Common.BaseActivity;
import com.example.app.Util.RegPrefManager;
import com.example.app.foodie.DrawerActivity;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

public class CartItem extends BaseActivity{

    ArrayList<OrderItem> cartlist = new ArrayList<OrderItem>();
    TextView subTotal;
    PaymentDetails paymentDetails;
    OrderDetails orderDetails;

    private CartAdapter cartlistlistAdapter;
    RecyclerView recycleview;


    Button submit;
    ItemsPresenterImpl itemsPresenter;

    String acess_token;

    private ArrayList<ViewResultCart> filterResultsArray1;

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

       // filterResultsArray1=new ArrayList<>();

       // filterResultsArray1= RegPrefManager.getInstance(this).getCartItems("CARTLIST");
        filterResultsArray1=   (ArrayList<ViewResultCart>) getIntent().getSerializableExtra("mylist");
       Log.d("Tag","Size====>"+filterResultsArray1.size());

        if(filterResultsArray1.size()>0){
            recycleview.setVisibility(View.VISIBLE);
            recycleview.setHasFixedSize(true);
            recycleview.setLayoutManager(new LinearLayoutManager(this));
            //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
            cartlistlistAdapter=new CartAdapter(CartItem.this,filterResultsArray1);
            recycleview.setAdapter(cartlistlistAdapter);
        }






    }




}
