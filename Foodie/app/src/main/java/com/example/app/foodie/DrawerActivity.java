package com.example.app.foodie;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.FEEDBACK.UI.Feedback;
import com.example.app.FEEDBACK.UI.ViewAllFeedBack;
import com.example.app.MyOrders.AllItem.ui.ViewItems;
import com.example.app.MyOrders.OrderListById.UI.ViewOrderItems;
import com.example.app.Password.ChangePasswordFragment;
import com.example.app.QOM.UI.QomList;
import com.example.app.USERLIST.UI.ViewDistributor;
import com.example.app.USERLIST.UI.ViewEmployee;
import com.example.app.USERLIST.UI.ViewFranchisor;
import com.example.app.Util.RegPrefManager;
import com.example.sukanta.foodie.R;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Toolbar toolbar;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static boolean viewIsAtHome;
    NavigationView navigationView = null;
    boolean doubleBackToExitPressedOnce = false;
    SessionManager sessionManager = null;
    SharedPreferenceClass sharedPreferenceClass;
    String userName = null;
    String rolename = null;
    String name = null;
    String email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sessionManager = new SessionManager(DrawerActivity.this);


        rolename=  SharedPreferenceClass.readString(getApplicationContext(), "ROLEID","");
        name=  SharedPreferenceClass.readString(getApplicationContext(), "NAME","");
        email=  SharedPreferenceClass.readString(getApplicationContext(), "EMAILID","");
        Log.i("Role", rolename);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.logintextColor));

        }
        toolbar = (Toolbar) findViewById(R.id.toolbar1);

       setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(rolename.equals("ROLE_DIST")){
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_distributor_drawermenu);

        }else if(rolename.equals("ROLE_FRANCH")){
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_fransise_drawer);

        }else if(rolename.equals("ROLE_KML_EMP")) {
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_employee_drawermenu);
        }
       /* else{
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer);

        }*/



        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        TextView nameTxt = headerView.findViewById(R.id.user_name);
        TextView emailTxt = headerView.findViewById(R.id.email);
        nameTxt.setText(name);
        emailTxt.setText(email);


        toolbar.setTitle(getResources().getString(R.string.app_display_name));
        toolbar.setSubtitle(getResources().getString(R.string.dashboard));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.nv);

        if(rolename.equals("ROLE_DIST")){
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new DistributorDashboardFragment()).commit();
        }else if(rolename.equals("ROLE_FRANCH")){
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new FransDashboardFragment()).commit();
        }else if(rolename.equals("ROLE_KML_EMP")){
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new DashboardActivity()).commit();
        }else {
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new DashboardActivity()).commit();
        }
        viewIsAtHome = true;




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        resetAllMenuItemsTextColor(navigationView);
        //resetAllMenuItems(navigationView);
        setTextColorForMenuItem(item, R.color.logintextColor);

// admin
        if(rolename.equals("ROLE_ADMIN")){
            if (id == R.id.dashboard) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new DashboardActivity()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.dashboard));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
            else if (id == R.id.employeelist) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ViewEmployee()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.employeelist));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
            else if (id == R.id.distbutorlist) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ViewDistributor()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.distributorlist));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }

            else if (id == R.id.franchisorlist) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ViewFranchisor()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.promoterlist));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }


           /* else if (id == R.id.additems) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new AddItems()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.add_new_item));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }*/
                else if (id == R.id.viewitems) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new com.example.app.ITEM.UI.ViewItems()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle("Item List");
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }

            else if (id == R.id.quantitymanagement) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                //mFragmentTransaction.replace(R.id.containerView, new QuantityManagement()).commit();
                mFragmentTransaction.replace(R.id.containerView, new QomList()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.quantity_management));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }

            else if (id == R.id.reports) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new Reports()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.reports));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }


            else if (id == R.id.feedback) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ViewAllFeedBack()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.feedbackstr));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }

            else if (id == R.id.orderstatus) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new OrderStatus()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.order_status));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }

           else if (id == R.id.allorders) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ViewOrderItems()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.allorders));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
            else if(id == R.id.changepassword){
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ChangePasswordFragment()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle("Password");
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }



            else if (id == R.id.logout) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setCancelable(false);
                dialog.setMessage("Do you want to logout?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       // sharedPreferenceClass.setValue_string("ROLEID", "");

                        Intent intent1 = new Intent(DrawerActivity.this, LoginActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent1);
                        finish();

                        dialog.dismiss();
                    }
                })
                        .setNegativeButton("No ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();



            }
        }
        else {  // display to distributor and fran and emp
            if (id == R.id.neworder) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ViewItems()).commit(); // display to distributor and fran
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle("Item List");
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
            if (id == R.id.myorder) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ViewOrderItems()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle("My Orders");
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
           if (id == R.id.distbutorlist) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ViewDistributor()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.distributorlist));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
            if (id == R.id.franchisorlist) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ViewFranchisor()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.promoterlist));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
            if (id == R.id.viewitems) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new com.example.app.ITEM.UI.ViewItems()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle("Item List");
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
            if (id == R.id.quantitymanagement) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                //mFragmentTransaction.replace(R.id.containerView, new QuantityManagement()).commit();
                mFragmentTransaction.replace(R.id.containerView, new QomList()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.quantity_management));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
            if (id == R.id.reports) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new Reports()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.reports));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }


            if (id == R.id.orderstatus) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new OrderStatus()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.order_status));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
            if (id == R.id.allorders) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new ViewOrderItems()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.allorders));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }
            if (id == R.id.dashboard) {
                if(rolename.equals("ROLE_FRANCH")){
                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, new FransDashboardFragment()).commit();
                    setTextColorForMenuItem(item, R.color.logintextColor);
                    toolbar.setTitle(getResources().getString(R.string.app_display_name));
                    toolbar.setSubtitle(getResources().getString(R.string.dashboard));
                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                    viewIsAtHome = true;
                }else if(rolename.equals("ROLE_DIST")) {
                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, new DistributorDashboardFragment()).commit();
                    setTextColorForMenuItem(item, R.color.logintextColor);
                    toolbar.setTitle(getResources().getString(R.string.app_display_name));
                    toolbar.setSubtitle(getResources().getString(R.string.dashboard));
                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                    viewIsAtHome = true;
                }
                else {
                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, new DashboardActivity()).commit();
                    setTextColorForMenuItem(item, R.color.logintextColor);
                    toolbar.setTitle(getResources().getString(R.string.app_display_name));
                    toolbar.setSubtitle(getResources().getString(R.string.dashboard));
                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                    viewIsAtHome = true;
                }

            }

            else if (id == R.id.feedback) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new Feedback()).commit();
                setTextColorForMenuItem(item, R.color.logintextColor);
                toolbar.setTitle(getResources().getString(R.string.app_display_name));
                toolbar.setSubtitle(getResources().getString(R.string.feedbackstr));
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
                viewIsAtHome = true;
            }


            else if (id == R.id.logout) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DrawerActivity.this);

                // set title
                alertDialogBuilder.setTitle("Logout!!");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                RegPrefManager.getInstance(DrawerActivity.this).Clear();
                                sessionManager.logoutUser();
                                SharedPreferenceClass.clearData(DrawerActivity.this);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


/*
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setCancelable(false);
                dialog.setMessage("Do you want to logout?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sharedPreferenceClass.setValue_string("ROLEID", "");

                        Intent intent1 = new Intent(DrawerActivity.this, LoginActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent1);
                        finish();

                        dialog.dismiss();
                    }
                })
                        .setNegativeButton("No ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();*/



            }
       }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void resetAllMenuItems(NavigationView navigationView) {
        for (int i = 0; i < navigationView.getMenu().size() - 1; i++)
            navigationView.getMenu().getItem(i).setVisible(false);
        //setTextColorForMenuItem(navigationView.getMenu().getItem(i), R.color.titleBackColor);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void resetAllMenuItemsTextColor(NavigationView navigationView) {
        for (int i = 0; i < navigationView.getMenu().size(); i++)
            setTextColorForMenuItem(navigationView.getMenu().getItem(i), R.color.logintextColor);
    }

    private void setTextColorForMenuItem(MenuItem menuItem, @ColorRes int color) {
        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, color)), 0, spanString.length(), 0);
        menuItem.setTitle(spanString);
    }

   /* private void updateMenuTitles(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.user_name);
        menuItem.setTitle(name);
    }*/

    private void showNoteDialog(final boolean shouldUpdate, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.note_dialog, null);
    }
}

