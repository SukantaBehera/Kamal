package com.example.app.foodie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.sukanta.foodie.R;

public class DistributorFragment extends Fragment {
    private String[] statusArray={"Pending","Dispatched","Delivered"};

    public DistributorFragment() {
        // Required empty public constructor
    }


    public static DistributorFragment newInstance(String param1, String param2) {
        DistributorFragment fragment = new DistributorFragment();

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
        View v = inflater.inflate(R.layout.fragment_distributor, container, false);
        final Spinner spinner = (Spinner)v.findViewById(R.id.spinner);
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,statusArray);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        return v;
    }

}
