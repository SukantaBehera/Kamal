package com.example.app.MyOrders.AllItem.mvp;


import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.Common.BaseView;

public interface ItemsView extends BaseView {
    void onValidationFail(int type);

    void onLoad(OrderPlacedResponse response);
}
