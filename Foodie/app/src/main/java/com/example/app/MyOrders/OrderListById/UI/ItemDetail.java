package com.example.app.MyOrders.OrderListById.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.Util.Common.BaseActivity;
import com.example.app.MyOrders.OrderListById.Adapter.ItemDetailAdapter;
import com.example.app.MyOrders.AllItem.datamodels.OrderDetails;
import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.app.MyOrders.AllItem.datamodels.PaymentDetails;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

public class ItemDetail extends BaseActivity {

    ArrayList<OrderItem> cartlist = new ArrayList<OrderItem>();
    TextView subTotal;
    TextView totalQunatity;
    EditText search;
    PaymentDetails paymentDetails;
    OrderDetails orderDetails;

    private ItemDetailAdapter cartlistlistAdapter;
    RecyclerView recycleview;
    String acess_token;
    EditText searchList;
    Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);
        recycleview = findViewById(R.id.recycler_view);
        subTotal = findViewById(R.id.totalPrice);
        totalQunatity = findViewById(R.id.totalQunatity);
        searchList = findViewById(R.id.searchlist);
        submit = findViewById(R.id.next);
        submit.setVisibility(View.GONE);
        searchList.setVisibility(View.GONE);
        totalQunatity.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Item Detail");
        setSupportActionBar(toolbar);
        enableActionBar(true);

        cartlist = this.getIntent().getExtras().getParcelableArrayList("itemArray");
        String totalPrice = this.getIntent().getExtras().getString("totalPrice");
        subTotal.setText(totalPrice);
        subTotal.setVisibility(View.GONE);

        cartlistlistAdapter = new ItemDetailAdapter(cartlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleview.setLayoutManager(mLayoutManager);
        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setAdapter(cartlistlistAdapter);
        cartlistlistAdapter.notifyDataSetChanged();



    }


}
