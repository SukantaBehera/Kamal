package com.example.app.Util.Common;

public class AppConstants {
    public static  final String BASEURL = "http://209.97.136.18:8080/rest-svc/";

   // public static  final String BASEURL = "http://192.168.3.75:8085/rest-svc/";

    public static  final String BASEURL_CASHFREE = "https://test.cashfree.com/api/v2/";

    public static  final String takeOrder = "api/orderitem/execute-update-purchase";

    public static  final String LOGIN = "userauth-svc/userlogin";
    public static  final String TOKEN="oauth/token";
    public static  final String QOMLIST="api/itemsvc/getall_item_and_qom";
    public static  final String UPDATEQOM="api/itemsvc/update_item_and_qom";
    public static  final String CHANGEPASSWORD="api/changepassword";
    public static  final String UPDATEFRANCHISOR="api/edit-franchisor-by-id";
    public static  final String UPDATEDISTRIBUTOR="api/edit-distriButor-by-id";
    public static  final String UPDATEEMPLOYEE="api/edit-employee-by-id";

    public static  final String ALLITEMS="api/itemsvc/getallitem";

    //order reports
    public static  final String ORDERALLITEMS="api/orderitem/get_cons_order_details";
    public static  final String ORDERALLITEMS_BYID="api/orderitem/get-order-details-by-custid";
    public static  final String STATUS_UPDATE="api/orderitem/update-order-status";
    public static  final String ORDER_REPORT_PENDING="api/orderitem/get-order-details-by-status/1";
    public static  final String ORDER_REPORT_DISPATCHED="api/orderitem/get-order-details-by-status/2";
    public static  final String ORDER_REPORT_DELIVERED="api/orderitem/get-order-details-by-status/3";
    public static  final String ORDER_REPORT_DATEWISE="api/orderitem//get-datewise-report";

    //delete users
    public static final String DELETEDISTRIBUTOR_BYID = "api/delete-distributor-by-id";
    public static final String DELETEFRANCHISOR_BYID = "api/delete-franchisor-by-id";
    public static final String DELETEEMPLOYEE_BYID = "api/delete-employee-by-id";
    public static final String EMPID = "api/get-alluser-by-type/1";
    public static final String ORDERITEMS_VIEWS = "api/orderitem/get-itemdetails-by-order/";

    public static  final String CASHFREE="cftoken/order";

    // public static  final String takeOrder = "api/orderitem/executeorder";
    public static final String SUCCESS = "success" ;


    public static String toCamelCase(final String init) {
        if (init==null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length()==init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

}
