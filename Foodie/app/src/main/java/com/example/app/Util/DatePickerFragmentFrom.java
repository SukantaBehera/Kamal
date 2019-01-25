package com.example.app.Util;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.example.sukanta.foodie.R;

/**
 * Created by Ruchi on 23-03-2016.
 */
public class DatePickerFragmentFrom extends DialogFragment {

    OnDateSetListener ondateSet;
//Context context;
    public DatePickerFragmentFrom() {
        //this.context=context;
    }

    public void setCallBack(OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    private int year, month, day;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, ondateSet, year, month, day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
       /* Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,day);
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);*/
        return dialog;
    }
}
