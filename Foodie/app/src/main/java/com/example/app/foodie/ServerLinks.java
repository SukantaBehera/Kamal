package com.example.app.foodie;

public class ServerLinks {

    //public static final String BASE_URL = "http://192.168.3.127:8080/SecureRESTWithOAuth/";

   public static final String BASE_URL =  "http://209.97.136.18:8080/SecureRESTWithOAuth/";
    //public static final String access_token = BASE_URL + "oauth/token?grant_type=password&client_id=fbApp&client_secret=fbApp&username=admin&password=123";
    public static final String login = BASE_URL +"api/login/";
    public static final String register = BASE_URL +"api/userregister";
    public static final String ADDITEMS = BASE_URL +"api/saveitem";
    public static final String QOM = BASE_URL +"api/getAllItem";
    //BASE URLS
   public static String BASE_URL_NEW = "http://209.97.136.18:8080/rest-svc/";
   public static String BASE_URL_API = "http://209.97.136.18:8080/rest-svc/api/";

   //ACCESS TOKEN
   public static String getToken = BASE_URL_NEW+"oauth/token?grant_type=password&client_id=fbApp&client_secret=fbApp&username=admin&password=123";
   //REGISTRATION
   public static String ADD_EMPLYOEE = BASE_URL_API+"reg_employee?access_token=";
   public static String ADD_DISTRIBUTOR = BASE_URL_API+"reg_distributor?access_token=";
   public static String ADD_FRANCHISOR = BASE_URL_API+"reg_franchisor?access_token=";

   //GET ALL EMPLOYEE
   public static String GET_ALLEMPLYOEE = BASE_URL_API+"getall_employee?access_token=";

    //GET ALL DISTRIBUTOR
    public static String GET_ALLDISTRIBUTOR = BASE_URL_API+"getall_distributor?access_token=";

    //GET ALL FRANCHISOR
    public static String GET_ALLFRANCHISOR = BASE_URL_API+"getall_franchisor?access_token=";

   //LOGIN
   public static String USER_LOGIN = BASE_URL_NEW+"userauth-svc/userlogin ";
   //GET ALL ITEM
   public static String getAllItems = BASE_URL_API+"itemsvc/getallitem?access_token=";
   //ADD ITEM
   public static String ADDITEMS_NEW = BASE_URL_API+"itemsvc/additem_and_qom?access_token=";

   //PLACEORDER
   public static String PLACE_ORDER = BASE_URL_API+"orderitem/execute-update-purchase?access_token=";
   public static String getqomlist = BASE_URL_API+"itemsvc/getall_item_and_qom?access_token=";
   //ADD ORDERSTATUS
   public static String ADD_ORDER_STATUS = BASE_URL_API+"orderitem/add_order_status?access_token=";

    //Order History/Get Order details - User Specific
    public static String ORDER_HISTORYBYID = BASE_URL_API+"orderitem/executeorder?access_token=";

    //Send Feed Back
    public static String SEND_FEEDBACK = BASE_URL_API+"itemsvc/addfeedback?access_token=";

    //ORDER REPORTS
   public static String DATEWISE_ORDERREPORT = BASE_URL_API+"report/get-order-by-orderdate-or-custid?access_token=";
   public static String PARTYWISE_REPORT = BASE_URL_API+"report/get-order-by-custid/{id}?access_token=";

    //Distributor Feedback Reports
   public static String DISTRIBUTOR_FEEDBACK_REPORT = BASE_URL_API+"itemsvc/getall_feedback_bytype/2?access_token=";
   //Franchisor Feedback Reports
   public static String FRANCHISOR_FEEDBACK_REPORT = BASE_URL_API+"itemsvc/getall_feedback_bytype/3?access_token=";
    //Employee Feedback Reports
   public static String EMPLOYEE_FEEDBACK_REPORT = BASE_URL_API+"itemsvc/getall_feedback_bytype/4?access_token=";
    //Get All Feedbacks
    public static String ALL_FEEDBACK_REPORT = BASE_URL_API+"itemsvc/getall_feedback?access_token=";

    public static String LOGIN_USER = "http://209.97.136.18:8080/rest-svc/userauth-svc/userlogin";
    public static String ordersById = "http://209.97.136.18:8080/rest-svc/api/orderitem/get_order_details";
    public static String allorders = "http://209.97.136.18:8080/rest-svc/api/orderitem/get_cons_order_details?access_token=";


    //GET ALL ITEM
    public static String getAllIFeedBack = BASE_URL_API+"itemsvc/getall_feedback?access_token=";
}