package com.example.app.foodie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sukanta.foodie.R;

public class OrderPlaceToMEFragment extends Fragment {


    public OrderPlaceToMEFragment() {
        // Required empty public constructor
    }


    public static OrderPlaceToMEFragment newInstance(String param1, String param2) {
        OrderPlaceToMEFragment fragment = new OrderPlaceToMEFragment();

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
        return inflater.inflate(R.layout.fragment_order_place_to_me, container, false);
    }

}
