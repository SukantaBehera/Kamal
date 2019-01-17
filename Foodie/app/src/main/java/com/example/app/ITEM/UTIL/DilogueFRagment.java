package com.example.app.ITEM.UTIL;

import android.app.Dialog;
import android.support.v4.app.Fragment;

import com.example.sukanta.foodie.R;

public  class DilogueFRagment extends Fragment {

    private Dialog progressDialog;

    public void showProgressBar() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog = DialogUtils.getDialogUtilsInstance().progressDialog(getActivity(), getString(R.string.loading_wait_msg));
        } else {
            closeProgressbar();
            progressDialog = null;
            progressDialog = DialogUtils.getDialogUtilsInstance().progressDialog(getActivity(), getString(R.string.loading_wait_msg));
        }
    }

    public void closeProgressbar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
