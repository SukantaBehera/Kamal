package ratna.genie1.user.genie.helper;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


/**
 * Created by Raju Satheesh on 10/18/2016.
 */
public class RegPrefManager {

    private SharedPreferences mSharedPreferences;
    private static RegPrefManager mPrefManager;



    public Context mContext;


    private RegPrefManager(Context context) {
        this.mContext=context;
        mSharedPreferences = context.
                getSharedPreferences("ratna", Context.MODE_PRIVATE);
    }

    public static RegPrefManager getInstance(Context context) {
        if (mPrefManager == null) {
            mPrefManager = new RegPrefManager(context);
        }
        return mPrefManager;
    }



   public void setCity(String name){
       mSharedPreferences.edit().putString("cityname",name).apply();
   }
   public String getCity(){
       return mSharedPreferences.getString("cityname",null);
   }


    public void setFromPlace(String place){
        mSharedPreferences.edit().putString("FlightFromPlace",place).apply();
    }
    public String getFromPlace(){
        return mSharedPreferences.getString("FlightFromPlace",null);
    }

    public void setToPlace(String place){
        mSharedPreferences.edit().putString("FlightToPlace",place).apply();
    }
    public String getToPlace(){
        return mSharedPreferences.getString("FlightToPlace",null);
    }

    public void setPlace(String place){
        mSharedPreferences.edit().putString("FlightPlace",place).apply();
    }
    public String getPlace(){
        return mSharedPreferences.getString("FlightPlace",null);
    }

    public void setAdultNo(String place){
        mSharedPreferences.edit().putString("AdultNo",place).apply();
    }
    public String getAdultNo(){
        return mSharedPreferences.getString("AdultNo",null);
    }

    public void setChildNo(String place){
        mSharedPreferences.edit().putString("ChildNo",place).apply();
    }
    public String getChildNo(){
        return mSharedPreferences.getString("ChildNo",null);
    }

    public void setInfantNo(String place){
        mSharedPreferences.edit().putString("InfantNo",place).apply();
    }
    public String getInfantNo(){
        return mSharedPreferences.getString("InfantNo",null);
    }
    public void setClassName(String place){
        mSharedPreferences.edit().putString("ClassName",place).apply();
    }
    public String getClassName(){
        return mSharedPreferences.getString("ClassName",null);
    }

    public void setDestinationName(String place){
        mSharedPreferences.edit().putString("Destinname",place).apply();
    }
    public String getDestinationName(){
        return mSharedPreferences.getString("Destinname",null);
    }
    public void setTrainFromPlace(String place){
        mSharedPreferences.edit().putString("FlightTrainFromPlace",place).apply();
    }
    public String getTainFromPlace(){
        return mSharedPreferences.getString("FlightTrainFromPlace",null);
    }

    public void setTainToPlace(String place){
        mSharedPreferences.edit().putString("FlightTrainToPlace",place).apply();
    }
    public String getTainToPlace(){
        return mSharedPreferences.getString("FlightTrainToPlace",null);
    }
    public void setCabToPlace(String place){
        mSharedPreferences.edit().putString("CabToPlace",place).apply();
    }
    public String getCabToPlace(){
        return mSharedPreferences.getString("CabToPlace",null);
    }
    public void setCabFromPlace(String place){
        mSharedPreferences.edit().putString("CabFromPlace",place).apply();
    }
    public String getCabFromPlace(){
        return mSharedPreferences.getString("CabFromPlace",null);
    }
    public void setTripRadio(String trip){
        mSharedPreferences.edit().putString("trip",trip).apply();
    }
    public String getTripRadio(){
        return mSharedPreferences.getString("trip",null);
    }


    public void setLoggedInPhoneNo(String phone){
        mSharedPreferences.edit().putString("Logphoneno",phone).apply();
    }
    public String getLoggedInPhoneNo(){
        return mSharedPreferences.getString("Logphoneno",null);
    }

    public void setPhoneNo(String phone){
        mSharedPreferences.edit().putString("phoneno",phone).apply();
    }
    public String getPhoneNo(){
        return mSharedPreferences.getString("phoneno",null);
    }

   /* public void setPhoneNo(String phone){
        mSharedPreferences.edit().putString("phoneno",phone).apply();
    }
    public String getPhoneNo(){
        return mSharedPreferences.getString("phoneno",null);
    }*/


    public void setServiceId(String serviceId){
        mSharedPreferences.edit().putString("serviceId",serviceId).apply();
    }
    public String getServiceId(){
        return mSharedPreferences.getString("serviceId",null);
    }


    public void setCustomerId(String customerId){
        mSharedPreferences.edit().putString("customerId",customerId).apply();
    }
    public String getCustomerId(){
        return mSharedPreferences.getString("customerId",null);
    }

    public void setFseUserId(String fseUserId){
        mSharedPreferences.edit().putString("fseUserId",fseUserId).apply();
    }
    public String getFseUserId(){
        return mSharedPreferences.getString("fseUserId",null);
    }


    public void setRetailerUserId(String retailerUserId){
        mSharedPreferences.edit().putString("retailerUserId",retailerUserId).apply();
    }
    public String getRetailerUserId(){ return mSharedPreferences.getString("retailerUserId",null);
    }


    public void setDataCardNo(String phone){
        mSharedPreferences.edit().putString("datacardno",phone).apply();
    }
    public String getDataCardNo(){
        return mSharedPreferences.getString("datacardno",null);
    }
    public void setBack(String back){
        mSharedPreferences.edit().putString("back",back).apply();
    }
    public String getBack(){
        return mSharedPreferences.getString("back",null);
    }

    public void setDataCardOperator(String operator,String code){
        mSharedPreferences.edit().putString("operator",operator).apply();
        mSharedPreferences.edit().putString("opearator_code",code).apply();
    }
    public String getDataCardOperator(){
        return mSharedPreferences.getString("operator",null);
    }
    public String getDataCardOperatorCode(){
        return mSharedPreferences.getString("opearator_code",null);
    }
    public void setDataCardCircle(String name,String code){
        mSharedPreferences.edit().putString("circlename",name).apply();
        mSharedPreferences.edit().putString("circlecode",code).apply();
    }
    public String getDDataCardCirclename(){
        return mSharedPreferences.getString("circlename",null);
    }
    public String gettDDataCardCircleCode(){
        return mSharedPreferences.getString("circlecode",null);
    }

    public void setMobileOperator(String name,String code){
        mSharedPreferences.edit().putString("mobileoperatorname",name).apply();
        mSharedPreferences.edit().putString("mobileoperatorcode",code).apply();
    }
    public String getMobileOperatorName(){
        return mSharedPreferences.getString("mobileoperatorname",null);
    }
    public String getMobileOperatorCode(){
        return mSharedPreferences.getString("mobileoperatorcode",null);
    }




    public void setMobileRechargeAmount (String amount) {
        mSharedPreferences.edit().putString("rechargeAmount", amount).apply();
    }
     public String getMobileRechargeAmount(){
        return mSharedPreferences.getString("rechargeAmount",null);
    }

    public void setWaterBoard(String name,String code){
        mSharedPreferences.edit().putString("waterBoardname",name).apply();
        mSharedPreferences.edit().putString("waterBoardcode",code).apply();
    }
    public String getWaterBoardName(){
        return mSharedPreferences.getString("waterBoardname",null);
    }
    public String getWaterBoardCode(){
        return mSharedPreferences.getString("waterBoardcode",null);
    }


    public void setGasBoard(String name,String code){
        mSharedPreferences.edit().putString("gasBoardname",name).apply();
        mSharedPreferences.edit().putString("gasBoardcode",code).apply();
    }
    public String getGasBoardName(){
        return mSharedPreferences.getString("gasBoardname",null);
    }
    public String getGasBoardCode(){
        return mSharedPreferences.getString("gasBoardcode",null);
    }

    public void setMobileCircle(String name,String code){
        mSharedPreferences.edit().putString("circlename",name).apply();
        mSharedPreferences.edit().putString("circlecode",code).apply();
    }
    public String getMobileCircleName(){
        return mSharedPreferences.getString("circlename",null);
    }
    public String getMobileCircleCode(){
        return mSharedPreferences.getString("circlecode",null);
    }



    public void setDTHOperator(String name,String code){
        mSharedPreferences.edit().putString("dthoperatorname",name).apply();
        mSharedPreferences.edit().putString("dthoperatorcode",code).apply();
    }
    public String getDTHOperatorName(){
        return mSharedPreferences.getString("dthoperatorname",null);
    }
    public String getDTHOperatorCode(){
        return mSharedPreferences.getString("dthoperatorcode",null);
    }


    public void setLandlineOperator(String name,String code){
        mSharedPreferences.edit().putString("landlineoperatorname",name).apply();
        mSharedPreferences.edit().putString("landlineoperatorcode",code).apply();
    }
    public String getLandlineOperatorname(){
        return mSharedPreferences.getString("landlineoperatorname",null);
    }
    public String getLandlineOperatorcode(){
        return mSharedPreferences.getString("landlineoperatorcode",null);
    }

    public void setLandlineCircle(String name,String code){
        mSharedPreferences.edit().putString("landlinecirclename",name).apply();
        mSharedPreferences.edit().putString("landlinecirclecode",code).apply();
    }
    public String getLandlinecirclename(){
        return mSharedPreferences.getString("landlinecirclename",null);
    }
    public String getLandlinecirclecode(){
        return mSharedPreferences.getString("landlinecirclecode",null);
    }

    public void setInsuranceId(String id){
        mSharedPreferences.edit().putString("InsuranceID",id).apply();

    }
    public String getInsuranceId(){
        return mSharedPreferences.getString("InsuranceID",null);
    }
    public void setReqId(String id){
        mSharedPreferences.edit().putString("ReqID",id).apply();

    }
    public String getReqId(){
        return mSharedPreferences.getString("ReqID",null);
    }
    public void setRemitterId(String id){
        mSharedPreferences.edit().putString("RemitterId",id).apply();

    }
    public String getRemitterId(){
        return mSharedPreferences.getString("RemitterId",null);
    }

    public void setBeneficaryId(String id){
        mSharedPreferences.edit().putString("BeneficaryId",id).apply();

    }
    public String getBeneficaryId(){
        return mSharedPreferences.getString("BeneficaryId",null);
    }
    public void setRemiterName(String name){
        mSharedPreferences.edit().putString("RemiterName",name).apply();

    }
    public String getRemiterPhone(){
        return mSharedPreferences.getString("RemiterPhone",null);
    }
    public void setRemiterPhone(String phone){
        mSharedPreferences.edit().putString("RemiterPhone",phone).apply();

    }
    public String getRemiterName(){
        return mSharedPreferences.getString("RemiterName",null);
    }
    public void setBusFromID(String id){
        mSharedPreferences.edit().putString("BusFromID",id).apply();

    }
    public String getBusFromID(){
        return mSharedPreferences.getString("BusFromID",null);
    }
    public void setBusFromName(String name){
        mSharedPreferences.edit().putString("BusFromName",name).apply();

    }
    public String getBusFromName(){
        return mSharedPreferences.getString("BusFromName",null);
    }


    public void setBusToID(String id){
        mSharedPreferences.edit().putString("BusToID",id).apply();

    }
    public String getBusToID(){
        return mSharedPreferences.getString("BusToID",null);
    }
    public void setBusToName(String name){
        mSharedPreferences.edit().putString("BusToName",name).apply();
    }
    public String getBusToName(){
        return mSharedPreferences.getString("BusToName",null);
    }

    public void setRemiterDetails(String beneficiaryId,String account,String bankname,
                                  String ifsc,String name,String mobile,String lastAccessDate){
        mSharedPreferences.edit().putString("BeneID",beneficiaryId).apply();
        mSharedPreferences.edit().putString("Account",account).apply();
        mSharedPreferences.edit().putString("BankName",bankname).apply();
        mSharedPreferences.edit().putString("IFSC",ifsc).apply();
        mSharedPreferences.edit().putString("Name",name).apply();
        mSharedPreferences.edit().putString("Mobile",mobile).apply();
        mSharedPreferences.edit().putString("LastAccessDate",lastAccessDate).apply();

    }
    public HashMap<String, String>  getRemiterDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put("BeneID",mSharedPreferences.getString("BeneID",null));
        user.put("Account", mSharedPreferences.getString("Account",null));
        user.put("BankName",mSharedPreferences.getString("BankName",null));
        user.put("IFSC",mSharedPreferences.getString("IFSC",null));
        user.put("Name",mSharedPreferences.getString("Name",null));
        user.put("Mobile", mSharedPreferences.getString("Mobile",null));
        user.put("LastAccessDate",mSharedPreferences.getString("LastAccessDate",null));

        return user;
    }

    public void setAgentId(String id){
        mSharedPreferences.edit().putString("AgentId",id).apply();

    }
    public String getAgentId(){
        return mSharedPreferences.getString("AgentId",null);
    }

    public void setInsuranceMessage(String msg){
        mSharedPreferences.edit().putString("InsuranceMessage",msg).apply();

    }
    public String getInsuranceMessage(){
        return mSharedPreferences.getString("InsuranceMessage",null);
    }
    public void setMovieCityId(String msg){
        mSharedPreferences.edit().putString("MovieCityId",msg).apply();

    }
    public String getMovieCityId(){
        return mSharedPreferences.getString("MovieCityId",null);
    }
    public void setPolicyId(String id){
        mSharedPreferences.edit().putString("InsurancePolicyID",id).apply();

    }
    public String getPolicyId(){
        return mSharedPreferences.getString("InsurancePolicyID",null);
    }

    public void setServiceName(String name){
        mSharedPreferences.edit().putString("ServiceName",name).apply();

    }
    public String getServiceName(){
        return mSharedPreferences.getString("ServiceName",null);
    }

    public void setServiceAmount(String amount){
        mSharedPreferences.edit().putString("ServiceAmount",amount).apply();

    }
    public String getServiceAmount(){
        return mSharedPreferences.getString("ServiceAmount",null);
    }

    public void setLandlineBroadband(String customerid,String operartorid,String accountno,String amount){
        mSharedPreferences.edit().putString("Landline_customerid",customerid).apply();
        mSharedPreferences.edit().putString("Landline_operatorid",operartorid).apply();
        mSharedPreferences.edit().putString("Landline_accountno",accountno).apply();
        mSharedPreferences.edit().putString("Landline_amount",amount).apply();

    }

    public String  getLandlineBroadbandCustid(){

        return mSharedPreferences.getString("Landline_customerid",null);
    }
    public String  getLandlineBroadbandoperatorid(){

        return mSharedPreferences.getString("Landline_operatorid",null);
    }
    public String  getLandlineBroadbandaccountno(){

        return mSharedPreferences.getString("Landline_accountno",null);
    }
    public String  getLandlineBroadbandamount(){

        return mSharedPreferences.getString("Landline_amount",null);
    }

    public void setBackService(String name){
        mSharedPreferences.edit().putString("BackServiceName",name).apply();

    }
    public String getBackService(){
        return mSharedPreferences.getString("BackServiceName",null);
    }

    public void setTourTravels(String tripvalue,String form,String to,String traveldate,String returndate,String time){
        mSharedPreferences.edit().putString("CAB_TRIPVALUE",tripvalue).apply();
        mSharedPreferences.edit().putString("CAB_FORM",form).apply();
        mSharedPreferences.edit().putString("CAB_TO",to).apply();
        mSharedPreferences.edit().putString("CAB_TRAVELDATE",traveldate).apply();
        mSharedPreferences.edit().putString("CAB_RETURNDATE",returndate).apply();
        mSharedPreferences.edit().putString("CAB_TIME",time).apply();

    }
    public String getCABTRIPVALUE(){
        return mSharedPreferences.getString("CAB_TRIPVALUE",null);
    }

    public String getCABFORM(){
        return mSharedPreferences.getString("CAB_FORM",null);
    }

    public String getCABTO(){
        return mSharedPreferences.getString("CAB_TO",null);
    }

    public String getCABTRAVELDATE(){
        return mSharedPreferences.getString("CAB_TRAVELDATE",null);
    }

    public String getCABRETURNDATE(){
        return mSharedPreferences.getString("CAB_RETURNDATE",null);
    }
    public String getCABTIME(){
        return mSharedPreferences.getString("CAB_TIME",null);
    }
    public void setDatacardService(String cus,String amount){
        mSharedPreferences.edit().putString("DatacardCus",cus).apply();
        mSharedPreferences.edit().putString("DatacardAmount",amount).apply();

    }
    public String getDatacardCus(){
        return mSharedPreferences.getString("DatacardCus",null);
    }
    public String getDatacardAmount(){
        return mSharedPreferences.getString("DatacardAmount",null);
    }

    public void setUserName(String name){
        mSharedPreferences.edit().putString("Username",name).apply();

    }
    public String getUserName(){
        return mSharedPreferences.getString("Username",null);
    }

    public void setUserGroup(String userGroup){
        mSharedPreferences.edit().putString("UserGroup",userGroup).apply();

    }
    public String getUserGroup(){
        return mSharedPreferences.getString("UserGroup",null);
    }

    public void setLoggedinUserId(String loggedinUserId){
        mSharedPreferences.edit().putString("UserId",loggedinUserId).apply();

    }
    public String getLoggedinUserId(){
        return mSharedPreferences.getString("UserId",null);
    }

    public void setUserEmail(String name){
        mSharedPreferences.edit().putString("Useremail",name).apply();

    }
    public String getUserEmail(){
        return mSharedPreferences.getString("Useremail",null);
    }
    public void setUpdateProfileImage(String uri){
        mSharedPreferences.edit().putString("UpdateProfileImage",uri).apply();

    }
    public String getUpdateProfileImage(){
        return mSharedPreferences.getString("UpdateProfileImage",null);
    }

    public void setElectricityOperator(String operator,String code){
        mSharedPreferences.edit().putString("OperatorElectricity",operator).apply();
        mSharedPreferences.edit().putString("OperatorcodeElectricity",code).apply();

    }
    public String getElectricityOperator(){

        return mSharedPreferences.getString("OperatorElectricity",null);
    }
    public String getElectricityOperatorCode(){

        return mSharedPreferences.getString("OperatorcodeElectricity",null);
    }


    public void SetElectricityBoard(String operator,String code){
        mSharedPreferences.edit().putString("electricityBoardName",operator).apply();
        mSharedPreferences.edit().putString("electricityBoardCode",code).apply();

    }
    public String getElectricityBoardName(){

        return mSharedPreferences.getString("electricityBoardName",null);
    }
    public String getElectricityBoardCode(){

        return mSharedPreferences.getString("electricityBoardCode",null);
    }


    public void setSuccessID(String id){
        mSharedPreferences.edit().putString("SuccessID",id).apply();


    }
    public String getSuccessID(){

        return mSharedPreferences.getString("SuccessID",null);
    }

    public void setDateAndTime(String dt){
        mSharedPreferences.edit().putString("DateAndTime",dt).apply();


    }
    public String getDateAndTime(){

        return mSharedPreferences.getString("DateAndTime",null);
    }


    public void setOperatorId(String Opid){
        mSharedPreferences.edit().putString("OperatorID",Opid).apply();


    }
    public String getOperatorId(){

        return mSharedPreferences.getString("OperatorID",null);
    }

    public void setMobReCircleId(String Ciid){
        mSharedPreferences.edit().putString("CircleId",Ciid).apply();


    }
    public String getMobReCircleId(){

        return mSharedPreferences.getString("CircleId",null);
    }
    public void setRadioClick(String click){
        mSharedPreferences.edit().putString("RadioClick",click).apply();


    }
    public String getRadioClick(){

        return mSharedPreferences.getString("RadioClick",null);
    }

}

