package com.example.app.foodie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sukanta.foodie.R;


public class FranchansorFragment extends Fragment {


    public FranchansorFragment() {
        // Required empty public constructor
    }


    public static FranchansorFragment newInstance(String param1, String param2) {
        FranchansorFragment fragment = new FranchansorFragment();

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
        return inflater.inflate(R.layout.fragment_franchansor, container, false);
    }


}