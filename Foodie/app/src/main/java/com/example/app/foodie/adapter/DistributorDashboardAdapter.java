package com.example.app.foodie.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import com.example.app.MyOrders.OrderListById.Adapter.CustomAdapter;
import com.example.app.MyOrders.OrderListById.UI.ViewMoreActivity;
import com.example.app.MyOrders.OrderListById.UI.ViewOrderItems;
import com.example.app.Response.EmployeeIDResultResponse;
import com.example.app.Response.ViewOrderResult;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.Util.RegPrefManager;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Retrofit;


/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class DistributorDashboardAdapter extends RecyclerView.Adapter<DistributorDashboardAdapter.ViewHolder> {
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

    public DistributorDashboardAdapter(Context context, ArrayList<ViewOrderResult> viewlist) {
        this.viewlist = viewlist;
        this.context=context;
        viewlistcart=new ArrayList<>();

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
        holder.changeStatusTv.setVisibility(View.GONE);
        holder.viewmoreTv.setVisibility(View.GONE);



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
