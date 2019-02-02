package com.example.app.foodie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.app.MyOrders.OrderListById.Adapter.CustomAdapter;
import com.example.app.MyOrders.OrderListById.UI.ViewOrderItems;
import com.example.app.Response.EmployeeIDResultResponse;
import com.example.app.Response.ViewOrderResult;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Retrofit;


/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class FransAdapterNew1 extends RecyclerView.Adapter<FransAdapterNew1.ViewHolder> {
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

    public FransAdapterNew1(Context context, ArrayList<ViewOrderResult> viewlist) {
        this.viewlist = viewlist;
        this.context=context;
        viewlistcart=new ArrayList<>();

        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);

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

      //  String status=viewResult.getOrder_deliv_status();
       // if(status.equals("PENDING")) {

            holder.relative.setVisibility(View.GONE);
            holder.dispatchDateLin.setVisibility(View.GONE);
            holder.deliveryDateLin.setVisibility(View.GONE);
            holder.dispatchDateByLin.setVisibility(View.GONE);
            holder.deliveryByLin.setVisibility(View.GONE);

            holder.orderId.setText(viewResult.getOrder_id() + "");
            holder.tprice.setText(viewResult.getTotal_price() + "");
            //    holder.userType.setText(viewResult.getOrderby_custId());
            holder.odate.setText(viewResult.getOrderDate());
            holder.statusallorder.setText(viewResult.getOrder_deliv_status());
            holder.username.setText(viewResult.getUserName());
            holder.dispatchDateTv.setText(viewResult.getDispatch_date());
            holder.deliveredDateTv.setText(viewResult.getDelivery_date());
            holder.dispatchedByTv.setText(viewResult.getDispatched_by_empName());
            holder.deliveredByTv.setText(viewResult.getDelivered_by_empName());

        //}


    }




    @Override
    public int getItemCount() {
        return viewlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderId,tprice,username,odate,statusallorder,dispatchDateTv,
                deliveredDateTv,dispatchedByTv,deliveredByTv,changeStatusTv,viewmoreTv;
        LinearLayout deliveryByLin,dispatchDateByLin,deliveryDateLin,dispatchDateLin;
        RelativeLayout relative;
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
            relative=itemView.findViewById(R.id.relative);
            dispatchDateLin=itemView.findViewById(R.id.dispatchDateLin);
            deliveryDateLin=itemView.findViewById(R.id.deliveryDateLin);
            dispatchDateByLin=itemView.findViewById(R.id.dispatchDateByLin);
            deliveryByLin=itemView.findViewById(R.id.deliveryByLin);
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
