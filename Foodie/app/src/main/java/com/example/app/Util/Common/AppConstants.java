package com.example.app.Util.Common;

public class AppConstants {
    public static  final String BASEURL = "http://209.97.136.18:8080/rest-svc/";

    public static  final String takeOrder = "api/orderitem/execute-update-purchase";

    public static  final String LOGIN = "userauth-svc/userlogin";
    public static  final String TOKEN="oauth/token";
    public static  final String QOMLIST="api/itemsvc/getall_item_and_qom";
    public static  final String UPDATEQOM="api/itemsvc/update_item_and_qom";
    public static  final String CHANGEPASSWORD="api/changepassword";

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