package com.example.app.MyOrders.OrderListById.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.app.MyOrders.OrderListById.UI.ViewOrderItems;
import com.example.app.Response.EmployeeIDResultResponse;
import com.example.app.Response.ResultOrderView;
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

public class ViewAdapterNew extends RecyclerView.Adapter<ViewAdapterNew.ViewHolder> {
    private Context context;
    private ArrayList<ResultOrderView> viewlist;
    private ArrayList<ResultOrderView> viewlistcart;
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

    public ViewAdapterNew(Context context, ArrayList<ResultOrderView> viewlist) {
        this.viewlist = viewlist;
        this.context=context;
        viewlistcart=new ArrayList<>();

        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
        this.emplist=emplist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewmoreadapterlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final ResultOrderView viewResult=viewlist.get(position);
        holder.nameTv.setText("Name: "+viewResult.getName());
        holder.descriptionTv.setText("Description: "+viewResult.getDescription());
        holder.priceTv.setText("Price: "+viewResult.getPrice()+"");
        holder.item_countTv.setText("Count: "+viewResult.getItem_count()+"");
        holder.custidTv.setText("Cust_id :"+viewResult.getOrder_by_cust_id());
        holder.purchaseidTv.setText("purchase_status :"+viewResult.getPurchase_status());



    }




    @Override
    public int getItemCount() {
        return viewlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv,descriptionTv,priceTv,item_countTv,custidTv,purchaseidTv;

        LinearLayout orderlayout;
        ViewHolder(View itemView) {
            super(itemView);

            nameTv = (TextView) itemView.findViewById(R.id.nameTv);
            descriptionTv=(TextView)itemView.findViewById(R.id.descriptionTv);
            priceTv=itemView.findViewById(R.id.priceTv);
            item_countTv=itemView.findViewById(R.id.item_countTv);
            custidTv=itemView.findViewById(R.id.custidTv);
            purchaseidTv=itemView.findViewById(R.id.purchaseidTv);

        }
    }
    public void updateList(ArrayList<ResultOrderView> list){
        viewlist = list;
        notifyDataSetChanged();
    }
    // method to access in activity after updating selection
   /* public ArrayList<ViewOrderResult> getSelectedItems() {
        return viewlistcart;
    }*/



}
