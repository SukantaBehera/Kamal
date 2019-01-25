package com.example.app.MyOrders.OrderListById.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.app.MyOrders.AllItem.ui.ViewItems;
import com.example.app.Response.ViewOrderResult;
import com.example.app.Response.ViewResult;
import com.example.app.Response.ViewResultCart;
import com.example.app.Util.RegPrefManager;
import com.example.sukanta.foodie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class MyOrderAdapterNew extends RecyclerView.Adapter<MyOrderAdapterNew.ViewHolder> {
    private Context context;
    private ArrayList<ViewOrderResult> viewlist;
    private ArrayList<ViewOrderResult> viewlistcart;
    ViewItems fragments;
    final Calendar myCalendar = Calendar.getInstance();
    private String[] statusArray={"Pending","Dispatched","Delivered"};
    private int mYear, mMonth, mDay, mHour, mMinute;
    public MyOrderAdapterNew(Context context, ArrayList<ViewOrderResult> viewlist) {
        this.viewlist = viewlist;
        this.context=context;
        viewlistcart=new ArrayList<>();
        this.fragments=fragments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allorderlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final ViewOrderResult viewResult=viewlist.get(position);
        holder.orderId.setText(viewResult.getOrder_id()+"");
        holder.tprice.setText(viewResult.getTotal_price()+"");
    //    holder.userType.setText(viewResult.getOrderby_custId());
        holder.odate.setText(viewResult.getOrderDate());
       // holder.statusallorder.setText(viewResult.getOrder_deliv_status());
        holder.username.setText(viewResult.getUserName());
        holder.dispatchDateTv.setText(viewResult.getDispatch_date());
        holder.deliveredDateTv.setText(viewResult.getDelivery_date());
        holder.dispatchedByTv.setText(viewResult.getDispatched_by_empName());
        holder.deliveredByTv.setText(viewResult.getDelivered_by_empName());

        holder.changeStatusTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ViewOrderResult viewResult=viewlist.get(position);
                //  Toast.makeText(context, viewResult1.getId()+"----Name"+viewResult1.getName(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                //  LayoutInflater inflater =. getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.statusdialog, null);

                dialogBuilder.setView(dialogView);

                final Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner);
                final  EditText dispatchdateTv=(EditText)dialogView.findViewById(R.id.dispatchdateTv);
                 final EditText dispatchEd=(EditText)dialogView.findViewById(R.id.dispatchEd);
               final EditText deliveryDateEd=(EditText)dialogView.findViewById(R.id.deliveryDateEd);
               final EditText deliveryByEd=(EditText)dialogView.findViewById(R.id.deliveryByEd);
                //Creating the ArrayAdapter instance having the country list
                ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,statusArray);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spinner.setAdapter(aa);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String select=statusArray[position];
                        RegPrefManager.getInstance(context).setUpdateStatus(select);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        RegPrefManager.getInstance(context).setUpdateStatus("Pending");
                    }
                });
                dispatchdateTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        dispatchdateTv.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

                deliveryDateEd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        deliveryDateEd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

               // dialogBuilder.setTitle("Enter Quantity");
                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    RegPrefManager.getInstance(context).setDeliveryDate(deliveryDateEd.getText().toString());
                    RegPrefManager.getInstance(context).setDispatchDate(dispatchdateTv.getText().toString());
                    RegPrefManager.getInstance(context).setDeliverBy(deliveryByEd.getText().toString());
                    RegPrefManager.getInstance(context).setDispatchBy(dispatchEd.getText().toString());
                    dialog.dismiss();
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

        TextView orderId,tprice,username,odate,statusallorder,dispatchDateTv,
                deliveredDateTv,dispatchedByTv,deliveredByTv,changeStatusTv,viewmoreTv;
        LinearLayout orderlayout;
        ViewHolder(View itemView) {
            super(itemView);

           // statusallorder = (TextView) itemView.findViewById(R.id.statusallorder);
            odate=(TextView)itemView.findViewById(R.id.odate);
            username=itemView.findViewById(R.id.username);
            tprice=itemView.findViewById(R.id.tprice);
            orderId=itemView.findViewById(R.id.orderId);
            dispatchDateTv=itemView.findViewById(R.id.dispatchDateTv);
            deliveredDateTv=itemView.findViewById(R.id.deliveredDateTv);
            dispatchedByTv=itemView.findViewById(R.id.dispatchedByTv);
            deliveredByTv=itemView.findViewById(R.id.deliveredByTv);
            viewmoreTv=itemView.findViewById(R.id.viewmoreTv);
            changeStatusTv=itemView.findViewById(R.id.changeStatusTv);

        }
    }
    public void updateList(ArrayList<ViewOrderResult> list){
        viewlist = list;
        notifyDataSetChanged();
    }
    // method to access in activity after updating selection
   /* public ArrayList<ViewOrderResult> getSelectedItems() {
        return viewlistcart;
    }*/


}
