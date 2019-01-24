package com.example.app.MyOrders.AllItem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MyOrders.AllItem.ui.ViewItems;
import com.example.app.Response.ViewResult;
import com.example.app.Response.ViewResultCart;
import com.example.app.Util.RegPrefManager;
import com.example.app.foodie.DrawerActivity;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;



/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class ViewListAdapter extends RecyclerView.Adapter<ViewListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ViewResult> viewlist;
    private ArrayList<ViewResultCart> viewlistcart;
    ViewItems fragments;

    public ViewListAdapter(Context context, ArrayList<ViewResult> viewlist, ViewItems fragments) {
        this.viewlist = viewlist;
        this.context=context;
        viewlistcart=new ArrayList<>();
        this.fragments=fragments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderitemlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final ViewResult viewResult=viewlist.get(position);
        holder.itemdescription.setText(viewResult.getDescription());
        holder.itemname.setText(viewResult.getName());
        holder.itemprice.setText(""+viewResult.getPrice());


     //   viewHolder.pPhoto.setText(listItemDetail.get(position).getBuyStatus());

        holder.orderlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final ViewResult viewResult1=viewlist.get(position);
              //  Toast.makeText(context, viewResult1.getId()+"----Name"+viewResult1.getName(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
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
                            String quantity_value = quantityEdt.getText().toString();

                            double total=Integer.parseInt(quantity_value)*viewResult1.getPrice();

                            ViewResultCart viewResult=new ViewResultCart();
                            // viewResult.setName(selectedObject.getName());
                            viewResult.setDescription(viewResult1.getDescription());
                            viewResult.setEntered_by(viewResult1.getEntered_by());
                            viewResult.setId(viewResult1.getId());
                            viewResult.setIs_active(viewResult1.getIs_active());
                            viewResult.setItem_id(viewResult1.getItem_id());
                            viewResult.setName(viewResult1.getName());
                            viewResult.setPrice(viewResult1.getPrice());
                            viewResult.setTotalPrice(total);
                            viewResult.setTotalQuantity(Integer.parseInt(quantity_value));
                            viewlistcart.add(viewResult);


                        }
                        //setupBadge();
                        RegPrefManager.getInstance(context).setFlagCart("flagCart");
                        holder.orderlayout.setBackgroundResource(R.drawable.custom_border_green);
                        fragments.getSelected();
                        dialog.dismiss();
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
        });

    }

    @Override
    public int getItemCount() {
        return viewlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemname,itemprice,itemdescription;
        LinearLayout orderlayout;
        ViewHolder(View itemView) {
            super(itemView);

            itemdescription = (TextView) itemView.findViewById(R.id.itemdescription);
            itemname=(TextView)itemView.findViewById(R.id.itemname);
            itemprice=itemView.findViewById(R.id.itemprice);

            orderlayout=itemView.findViewById(R.id.orderlayout);

        }
    }

    // method to access in activity after updating selection
    public ArrayList<ViewResultCart> getSelectedItems() {
        return viewlistcart;
    }


}
