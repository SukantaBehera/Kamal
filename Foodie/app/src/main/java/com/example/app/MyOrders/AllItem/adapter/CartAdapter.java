package com.example.app.MyOrders.AllItem.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.example.app.MyOrders.AllItem.ui.CartItem;
import com.example.app.Response.ViewResultCart;
import com.example.app.Util.RegPrefManager;
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

    double price_more,totalprice_more;

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



                    if(spinnerpos!=0){
                        double price=orderItem.getPrice();
                        double totalprice=Integer.parseInt(qty)*price;
                        viewHolder.qtyTv.setText("Quantity: "+qty);
                        viewHolder.price.setText(""+totalprice);
                        listItemDetail.get(position1).setTotalPrice(totalprice);
                        listItemDetail.get(position1).setTotalQuantity(Integer.parseInt(qty));

                        ((CartItem)context1).getSelected(); //return array
                    }
                    spinnerpos=0;

                   // viewHolder.itemPrice.setText("Quantity: " + name);
                }
                if(qty.equals("more")){
                    Log.d("Tag","Name====>"+qty);
                    spinnerpos=2;
                    showDialog(context1,position1,viewHolder);



                }
                if(!qty.equals("more") && !qty.equals("1")){
                    Log.d("Tag",qty);
                   spinnerpos=1;
                    double price=orderItem.getPrice();
                    double totalprice=Integer.parseInt(qty)*price;
                    listItemDetail.get(position1).setTotalPrice(totalprice);
                    listItemDetail.get(position1).setTotalQuantity(Integer.parseInt(qty));
                    viewHolder.qtyTv.setText("Quantity: "+qty);
                    viewHolder.price.setText(""+totalprice);
                    ((CartItem)context1).getSelected(); //return array

                //    viewHolder.itemPrice.setText("Quantity: " + name);
                  /*  double price=orderItem.getTotal_price();
                    double totalprice=Integer.parseInt(name)*price;*/




                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                qty=itemlist[0];
            }
        });

        viewHolder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemDetail.remove(position1);

                Log.d("dharitri","CART_POSITION------>");
                notifyDataSetChanged();
                ((CartItem)context1).getSelected();

            }
        });


    }

    private void showDialog(final Context context, final int position, final ViewHolder viewHolder){
        final ViewResultCart orderItem=listItemDetail.get(position);
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //  LayoutInflater inflater =. getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText quantityEdt = (EditText) dialogView.findViewById(R.id.quantity);


        dialogBuilder.setTitle("Enter Quantity");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(quantityEdt.getText().toString().isEmpty()){
                    quantityEdt.setError("Please Enter quantity");
                }else {
                    qty=quantityEdt.getText().toString();
                    price_more=orderItem.getPrice();
                    totalprice_more=Integer.parseInt(quantityEdt.getText().toString())*price_more;

                }
                listItemDetail.get(position).setTotalQuantity(Integer.parseInt(qty));
                listItemDetail.get(position).setTotalPrice(totalprice_more);
                viewHolder.qtyTv.setText("Quantity: "+qty);
                viewHolder.price.setText(""+totalprice_more);
                dialog.dismiss();
                ((CartItem)context1).getSelected();
                //cartList.remove(selectedObject);
            }

        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();


    }



    @Override
    public int getItemCount() {
        Log.v(CartAdapter.class.getSimpleName(),""+listItemDetail.size());
        return listItemDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ItemName,qtyTv,price;
        ImageView deleteImg;
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
            deleteImg=view.findViewById(R.id.deleteImg);





        }
    }

    // method to access in activity after updating selection
    public ArrayList<ViewResultCart> getSelectedItems() {
        return listItemDetail;
    }
}
