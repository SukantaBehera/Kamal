package com.example.app.MyOrders.AllItem.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.app.Response.ViewResultCart;
import com.example.app.foodie.LoginActivity;
import com.example.sukanta.foodie.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
   /* public final Activity context;
    public final String[] promotername;
    *//*public final Integer[] imgid;*//*
    public final String[] promoterAddress;
    int count = 0;*/
    Context context1;
    String quanty="null";
    String qty;
    private ArrayList<ViewResultCart> listItemDetail;

    String[] itemlist={"1","2","3","4","5","more"};
    int spinnerpos=0;
    boolean flag;

    public CartAdapter(Context context,ArrayList<ViewResultCart> listUsers) {
        this.listItemDetail = listUsers;
        context1=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cartlist,viewGroup,false);


        return new ViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        ViewResultCart orderItem=listItemDetail.get(position);
       final int position1=position;
        viewHolder.ItemName.setText(orderItem.getName());
       // viewHolder.itemPrice.setText("Quantity: "+listItemDetail.get(position).getItem_count()+"   Total Price"+listItemDetail.get(position).getTotal_price());
        //viewHolder.itemDescription.setText("Status: "+listItemDetail.get(position).getOrder_status());
        viewHolder.qtyTv.setText("Quantity: "+orderItem.getTotalQuantity());
        viewHolder.price.setText("Price:"+orderItem.getTotalPrice());
        flag=false;
        CustomAdapter customAdapter=new CustomAdapter(context1,itemlist);
        viewHolder.spinner.setAdapter(customAdapter);
        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ViewResultCart orderItem=listItemDetail.get(position1);
                 qty=itemlist[position];
                Log.d("Tag",qty);

                if(qty.equals("1")) {

                    /*Log.d("Tag",name);
                     viewHolder.qtyTv.setText("Quantity: "+orderItem.getItemCount());
                     //int qtyv=Integer.valueOf(name);
                  //   double pricev=listItemDetail.get(position1).getTotal_price();
                    // double value=pricev*qtyv;
                     viewHolder.price.setText(""+orderItem.getTotal_price());
                    // double price=listItemDetail.get(position).getTotal_price();*/

                    if(spinnerpos!=0){
                        double price=orderItem.getPrice();
                        double totalprice=Integer.parseInt(qty)*price;
                        viewHolder.qtyTv.setText("Quantity: "+qty);
                        viewHolder.price.setText(""+totalprice);
                    }
                    spinnerpos=0;

                   // viewHolder.itemPrice.setText("Quantity: " + name);
                }else if(qty.equals("more")){
                    Log.d("Tag","Name====>"+qty);
                    spinnerpos=2;

                }else {
                    Log.d("Tag",qty);
                    spinnerpos=1;
                    double price=orderItem.getPrice();
                    double totalprice=Integer.parseInt(qty)*price;
                    viewHolder.qtyTv.setText("Quantity: "+qty);
                    viewHolder.price.setText(""+totalprice);
                //    viewHolder.itemPrice.setText("Quantity: " + name);
                  /*  double price=orderItem.getTotal_price();
                    double totalprice=Integer.parseInt(name)*price;*/




                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    /*private void dialogDisplay(View v) {


        final Dialog dialog = new Dialog(context1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.quantitycart);

        final TextView text = (TextView) dialog.findViewById(R.id.quantity);
        // text.setText(msg);
        TextView cancelTv=dialog.findViewById(R.id.cancelTv);
        TextView okTv=dialog.findViewById(R.id.okTv);


        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // context1. viewHolder.itemPrice.setText("Quantity: "+name+"   Total Price"+listItemDetail.get(position).getTotal_price());
                if(!text.getText().toString().isEmpty()) {
                    String name = text.getText().toString();
                    q
                    Log.d("Tag","name"+name);
                }

            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }*/

    @Override
    public int getItemCount() {
        Log.v(CartAdapter.class.getSimpleName(),""+listItemDetail.size());
        return listItemDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ItemName,qtyTv,price;
        ImageView pPhoto;
      //  TextView itemPrice;
        TextView itemDescription;
        Spinner spinner;

        public ViewHolder (View view){
            super(view);
            ItemName = view.findViewById(R.id.itemname);
            qtyTv = view.findViewById(R.id.qtyTv);
            price=view.findViewById(R.id.price);
            itemDescription = view.findViewById(R.id.itemdescription);
            spinner=view.findViewById(R.id.spinner);




        }
    }
}
