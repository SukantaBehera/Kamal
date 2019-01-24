package com.example.app.MyOrders.AllItem.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.MyOrders.AllItem.adapter.OrderItemListAdapter;
import com.example.app.MyOrders.AllItem.adapter.ViewListAdapter;
import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.mvp.ItemsPresenterImpl;
import com.example.app.MyOrders.AllItem.mvp.ItemsView;
import com.example.app.ITEM.MODEL.ItemDetail;
import com.example.app.ITEM.UTIL.DilogueFRagment;
import com.example.app.MyOrders.AllItem.datamodels.OrderDetails;
import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.app.MyOrders.AllItem.datamodels.PaymentDetails;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.ViewAllItemResponse;
import com.example.app.Response.ViewResult;
import com.example.app.Response.ViewResultCart;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.RecyclerItemClickListener;
import com.example.app.Util.Common.WebApi;
import com.example.app.Util.RegPrefManager;
import com.example.app.Util.SessionManager;
import com.example.app.foodie.LoginActivity;
import com.example.sukanta.foodie.R;
import com.example.app.foodie.ServerLinks;
import com.example.app.foodie.SharedPreferenceClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@SuppressWarnings("All")
public class ViewItems extends DilogueFRagment  {
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token = "";

    ArrayList<ItemDetail> itemList = new ArrayList<ItemDetail>();
    ArrayList<ItemDetail> itemListcart = new ArrayList<ItemDetail>();
    ArrayList<OrderItem> cartlist = new ArrayList<OrderItem>();
    private OrderItemListAdapter itemlistAdapter;
    RecyclerView recycleview;
    private ViewListAdapter viewListAdapter;

    FloatingActionButton fab;
    TextView cartCount;
    String userId;
    Double totalAmount = 0.0;

    SessionManager session;
    ItemsPresenterImpl itemsPresenter;
    TextView textCartItemCount;
    int mCartItemCount = 10;
    private WebApi webApi;
    private RelativeLayout myorderLayout;
    Retrofit retrofit;
    private ArrayList<ViewResult> viewResultsArray;
    private ArrayList<ViewResult> filterResultsArray;
    private ArrayList<ViewResultCart> filterResultsArray1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Confirm this fragment has menu items.
        setHasOptionsMenu(true);
        session = new SessionManager(getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_item_list, container, false);


        progressDialog = new ProgressDialog(getContext());
        recycleview = (RecyclerView) rootView.findViewById(R.id.item_list);

        retrofit = ApiClient.getRetrofit();

        webApi = retrofit.create(WebApi.class);
        viewResultsArray=new ArrayList<>();
        filterResultsArray=new ArrayList<>();
        filterResultsArray1=new ArrayList<>();
         myorderLayout=rootView.findViewById(R.id.myorderLayout);
        if (isNetworkAvailable()) {
            fetchAcessToken();
        } else {
            Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
        }
        /*String flag=RegPrefManager.getInstance(getContext()).getFlagCart();
        if(flag==null) {
            Log.d("Tag","Tag");
        }else {
            getSelected();
        }*/

        return rootView;
    }

    private void fetchAcessToken() {
        //getting the progressbar


        Call<TokenResponse> call=webApi.accessToken("password","fbApp","fbApp","admin","123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token=response.body().getValue();
                Log.d("Tag","token===>"+acess_token);
                networkAllItems();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void networkAllItems(){
        Call<ViewAllItemResponse> call=webApi.getAllItem(acess_token);
        call.enqueue(new Callback<ViewAllItemResponse>() {
            @Override
            public void onResponse(Call<ViewAllItemResponse> call, Response<ViewAllItemResponse> response) {
            String status=response.body().getStatus();
            if(status.equals("SUCCESS")){
                viewResultsArray=response.body().getResult();
                String role= RegPrefManager.getInstance(getContext()).getLoginRoleId();
                if(role.equals("ROLE_FRANCH")){
                //    filterResultsArray.clear();
                    for(int i=0;i<viewResultsArray.size();i++){
                        String flag=viewResultsArray.get(i).getFranch_view_flag();
                        if(!flag.equals("N")){
                            ViewResult v=new ViewResult();
                            v.setDescription(viewResultsArray.get(i).getDescription());
                            v.setEntered_by(viewResultsArray.get(i).getEntered_by());
                            v.setId(viewResultsArray.get(i).getId());
                            v.setIs_active(viewResultsArray.get(i).getIs_active());
                            v.setItem_id(viewResultsArray.get(i).getItem_id());
                            v.setName(viewResultsArray.get(i).getName());
                            v.setPrice(viewResultsArray.get(i).getPrice());

                            filterResultsArray.add(v);

                        }

                   }
                    viewResultsArray.clear();
                    if(filterResultsArray.size()>0){
                        viewResultsArray=filterResultsArray;
                        myorderLayout.setVisibility(View.GONE);
                        recycleview.setVisibility(View.VISIBLE);
                        recycleview.setHasFixedSize(true);
                        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
                        //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
                        viewListAdapter=new ViewListAdapter(getContext(),viewResultsArray,ViewItems.this);
                        recycleview.setAdapter(viewListAdapter);
                        //filterResultsArray.clear();
                    }else {
                        myorderLayout.setVisibility(View.VISIBLE);
                        recycleview.setVisibility(View.GONE);
                    }

                }
                else { // distributor
                    if(viewResultsArray.size()>0){
                        myorderLayout.setVisibility(View.GONE);
                        recycleview.setVisibility(View.VISIBLE);
                        recycleview.setHasFixedSize(true);
                        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
                        //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
                        viewListAdapter=new ViewListAdapter(getContext(),viewResultsArray,ViewItems.this);
                        recycleview.setAdapter(viewListAdapter);
                    }else {
                        myorderLayout.setVisibility(View.VISIBLE);
                        recycleview.setVisibility(View.GONE);
                    }
                }

            }
            }

            @Override
            public void onFailure(Call<ViewAllItemResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getSelected(){
        ArrayList<ViewResultCart> arrayList = ((ViewListAdapter) viewListAdapter)
                .getSelectedItems();
        Log.d("Tag","size===>"+arrayList.size());

        filterResultsArray1=arrayList;
        Log.d("Tag","size===>"+filterResultsArray1.size());
        if(filterResultsArray1.size()>0) {
            setupBadge();
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the fragment menu items.
        inflater.inflate(R.menu.cartmenu, menu);

        final MenuItem menuItem = menu.findItem(R.id.cartmenuId);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();


    if(itemId == R.id.cartmenuId)
        {
            if(filterResultsArray1.size() > 0){
                RegPrefManager.getInstance(getContext()).ClearCartFlag();
             //   startActivity(new Intent(getContext(),CartItem.class));
                Intent i = new Intent(getActivity(),CartItem.class);
                session.ClearArraylist();
                session.SetCart_ProductList(filterResultsArray1);
                startActivity(i);

            }else {
                Toast.makeText(getActivity(), "Please add atleast one item to cart...", Toast.LENGTH_SHORT).show();

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (filterResultsArray1.size() == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(filterResultsArray1.size(), 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}