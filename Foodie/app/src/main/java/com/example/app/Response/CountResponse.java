package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

public class CountResponse {
    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CountResult getResult() {
        return result;
    }

    public void setResult(CountResult result) {
        this.result = result;
    }

    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private CountResult result;

    public class CountResult{
        @SerializedName("Franchisor Count")
        private int Franchisor ;
        @SerializedName("Distributor Count")
        private int Distributor;

        public int getFranchisor() {
            return Franchisor;
        }

        public void setFranchisor(int franchisor) {
            Franchisor = franchisor;
        }

        public int getDistributor() {
            return Distributor;
        }

        public void setDistributor(int distributor) {
            Distributor = distributor;
        }

        public int getEmployee() {
            return Employee;
        }

        public void setEmployee(int employee) {
            Employee = employee;
        }

        @SerializedName("Employee Count")
        private int Employee;

    }
}
