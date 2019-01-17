package com.example.app.MyOrders.OrderListById.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.ITEM.MODEL.ItemDetail;
import com.example.app.MyOrders.OrderListById.Model.OrderItemDetail;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    Context context;
    private ArrayList<OrderItemDetail> listItemDetail;
        public MyOrderAdapter(ArrayList<OrderItemDetail> listUsers,Context context) {
        this.listItemDetail = listUsers;
        this.context = context;
    }

    public void updateList(ArrayList<OrderItemDetail> list){
        listItemDetail = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.allorderlist,viewGroup,false);
        return new ViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        viewHolder.orderId.setText(listItemDetail.get(position).getOrder_id());
        viewHolder.totalPrice.setText(listItemDetail.get(position).getTotal_price()+"");
        viewHolder.userType.setText(listItemDetail.get(position).getCust_id());
        viewHolder.orderDate.setText(listItemDetail.get(position).getOrder_date());
        viewHolder.status.setText(listItemDetail.get(position).getStatus());
        viewHolder.name.setText(listItemDetail.get(position).getName());
        /*viewHolder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewDetailsOrders.class);
                *//* intent.putExtra("name",viewHolder.name.getText().toString());
                intent.putExtra("description",viewHolder.desc.getText().toString());
                intent.putExtra("price",viewHolder.price.getText().toString());
                intent.putExtra("item_count",viewHolder.itemcount.getText().toString());*//*
                context.startActivity(intent);
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        Log.v(MyOrderAdapter.class.getSimpleName(),""+listItemDetail.size());
        return listItemDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId;
        TextView totalPrice;
        TextView userType;
        TextView userName;
        TextView orderDate;
        TextView status;
        TextView name;
        TextView desc;
        TextView price;
        TextView itemcount;
        TextView viewDetails;


        public ViewHolder (View view){
            super(view);
            orderId= view.findViewById(R.id.orderId);
            totalPrice = view.findViewById(R.id.tprice);
            userType = view.findViewById(R.id.userrole);
            userName= view.findViewById(R.id.username);
            orderDate = view.findViewById(R.id.odate);
            status = view.findViewById(R.id.statusallorder);
            name = view.findViewById(R.id.username);
           /* desc = view.findViewById(R.id.description);
            price = view.findViewById(R.id.price);
            itemcount = view.findViewById(R.id.itemcount);*/
          /*  viewDetails = view.findViewById(R.id.more);*/

        }
    }
}
