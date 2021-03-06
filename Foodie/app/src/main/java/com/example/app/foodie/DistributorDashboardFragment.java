package com.example.app.foodie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sukanta.foodie.R;

import java.util.ArrayList;
import java.util.List;


public class DistributorDashboardFragment extends Fragment {


    public DistributorDashboardFragment() {
        // Required empty public constructor
    }


    public static DistributorDashboardFragment newInstance(String param1, String param2) {
        DistributorDashboardFragment fragment = new DistributorDashboardFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_distributor_dashboard, container, false);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) v.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);
        return v;
    }
    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {


        Reports.Adapter adapter = new Reports.Adapter(getChildFragmentManager());
        adapter.addFragment(new MyOrdersFragment(), "My Orders");
        adapter.addFragment(new OrderPlaceToMEFragment(), "OrderPlaced To Me");

        viewPager.setAdapter(adapter);



    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
