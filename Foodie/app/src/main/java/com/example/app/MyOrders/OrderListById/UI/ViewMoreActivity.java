package com.example.app.MyOrders.OrderListById.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.sukanta.foodie.R;

import org.w3c.dom.Text;

public class ViewMoreActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView itemListRecyclerview;
    TextView noOrderTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);
        toolbar=findViewById(R.id.toolbar);
        TextView text=toolbar.findViewById(R.id.text);
        text.setText("View More");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(View.this,RemiterDetailsActivity.class));
                finish();
            }
        });

        intialize();

    }
    private void intialize(){
        itemListRecyclerview=findViewById(R.id.itemListRecyclerview);
        noOrderTv=findViewById(R.id.noOrderTv);

    }
}
