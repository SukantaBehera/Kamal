package com.example.app.REPORTS;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sukanta.foodie.R;


public class ReportsFragment extends Fragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    View rootView = null;
    public ReportsFragment() {
        // Required empty public constructor
    }


    public static ReportsFragment newInstance() {
        ReportsFragment fragment = new ReportsFragment();

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

        rootView = inflater.inflate(R.layout.fragment_reports, container, false);
        toolbar = rootView.findViewById(R.id.toolbar);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);


        return rootView;
    }


}
