package com.example.app.foodie;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sukanta.foodie.R;

public class Reports extends Fragment {
    TextView dailyreport, franchisoreport, feedbackreport;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_reports, container, false);
        dailyreport = rootView.findViewById(R.id.dailyreports);
        franchisoreport = rootView.findViewById(R.id.franchisorreports);
        feedbackreport = rootView.findViewById(R.id.feedbackreports);

      /*  dailyreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent ;
                intent = new Intent(getActivity(),DailyReportActivity.class);
                startActivity(intent);
            }
        });

        franchisoreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent ;
                intent = new Intent(getActivity(),FranchisorReportActivity.class);
                startActivity(intent);
            }
        });

        feedbackreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent ;
                intent = new Intent(getActivity(),Feedback.class);
                startActivity(intent);
            }
        });*/
        return rootView;
    }
}
