package com.example.app.MyOrders.OrderListById.Adapter;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.Toast;

import com.example.app.MyOrders.AllItem.ui.ViewItems;
import com.example.app.MyOrders.OrderListById.UI.ViewMoreActivity;
import com.example.app.MyOrders.OrderListById.UI.ViewOrderItems;
import com.example.app.Response.EmployeeIDResultResponse;
import com.example.app.Response.EmployeeIdResponse;
import com.example.app.Response.MyOrderUpdateResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.ViewOrderResult;
import com.example.app.Response.ViewResult;
import com.example.app.Response.ViewResultCart;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.Util.RegPrefManager;
import com.example.sukanta.foodie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class MyOrderAdapterNew extends RecyclerView.Adapter<MyOrderAdapterNew.ViewHolder> {
    private Context context;
    private ArrayList<ViewOrderResult> viewlist;
    private ArrayList<ViewOrderResult> viewlistcart;
    ViewOrderItems fragments;
    final Calendar myCalendar = Calendar.getInstance();
    private String[] statusArray={"Pending","Dispatched","Delivered"};
    private int mYear, mMonth, mDay, mHour, mMinute,by_format_value;
    private String spinselect,by_format,date_format,date_format_value,orderid;
    private WebApi webApi;
    private String acess_token;
    Retrofit retrofit;
    private Spinner dispatchEd,deliveryByEd;
    private ArrayList<EmployeeIDResultResponse> emplist;
    private CustomAdapter adapter;

    public MyOrderAdapterNew(Context context, ArrayList<ViewOrderResult> viewlist,ViewOrderItems frag,ArrayList<EmployeeIDResultResponse> emplist) {
        this.viewlist = viewlist;
        this.context=context;
        viewlistcart=new ArrayList<>();
        this.fragments=frag;
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
        this.emplist=emplist;
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
        String status=viewResult.getOrder_deliv_status();

        holder.orderId.setText(viewResult.getOrder_id()+"");
        holder.tprice.setText(viewResult.getTotal_price()+"");
    //    holder.userType.setText(viewResult.getOrderby_custId());
        holder.odate.setText(viewResult.getOrderDate());
        holder.statusallorder.setText(viewResult.getOrder_deliv_status());
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
              final   Spinner   dispatchEd=(Spinner)dialogView.findViewById(R.id.dispatchSpinner);
               final EditText deliveryDateEd=(EditText)dialogView.findViewById(R.id.deliveryDateEd);
             final   Spinner deliveryByEd=(Spinner)dialogView.findViewById(R.id.deliveryBySpinner);
                //Creating the ArrayAdapter instance having the country list
                ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,statusArray);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spinner.setAdapter(aa);

                if (emplist.size()>0){
// Create custom adapter object ( see below CustomAdapter.java )
                    adapter = new CustomAdapter(context, R.layout.customspinnerlayout, emplist);

                    // Set adapter to spinner
                    dispatchEd.setAdapter(adapter);
                    deliveryByEd.setAdapter(adapter);
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                         spinselect=statusArray[position];
                        RegPrefManager.getInstance(context).setUpdateStatus(spinselect);
                        if (spinselect.equals("Dispatched")){
                            dispatchdateTv.setVisibility(View.VISIBLE);
                            dispatchEd.setVisibility(View.VISIBLE);
                            deliveryDateEd.setVisibility(View.GONE);
                            deliveryByEd.setVisibility(View.GONE);


                        }else if(spinselect.equals("Delivered")){
                            deliveryDateEd.setVisibility(View.VISIBLE);
                            deliveryByEd.setVisibility(View.VISIBLE);
                            dispatchdateTv.setVisibility(View.GONE);
                            dispatchEd.setVisibility(View.GONE);
                        }
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

                                        dispatchdateTv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

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

                                        deliveryDateEd.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

                dispatchEd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        by_format_value=emplist.get(position).getUserID();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        by_format_value=emplist.get(0).getUserID();
                    }
                });
                deliveryByEd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        by_format_value=emplist.get(position).getUserID();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        by_format_value=emplist.get(0).getUserID();
                    }
                });

               // dialogBuilder.setTitle("Enter Quantity");
                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    if(spinselect.equals("Pending")) {
                        Toast.makeText(context, "Please Select Dispatch or delivery status", Toast.LENGTH_LONG).show();
                    }else   if (spinselect.equals("Dispatched")){


                            date_format_value=dispatchdateTv.getText().toString();
                        orderid=String.valueOf(viewResult.getOrder_id());
                        fragments.updateStatus(spinselect,by_format_value,date_format_value,orderid);
                        }
                        else {


                        date_format_value=deliveryDateEd.getText().toString();
                        orderid=String.valueOf(viewResult.getOrder_id());

                        fragments.updateStatus(spinselect,by_format_value,date_format_value,orderid);
                    }

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

        holder.viewmoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ViewOrderResult viewResult=viewlist.get(position);
            RegPrefManager.getInstance(context).setOrderId(String.valueOf(viewResult.getOrder_id()));
                context.startActivity(new Intent(context, ViewMoreActivity.class));

            }
        });
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

            statusallorder = (TextView) itemView.findViewById(R.id.statusallorder);
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
