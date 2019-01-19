package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TokenResponse {
    @SerializedName("value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    public ArrayList<Scope> getScope() {
        return scope;
    }

    public void setScope(ArrayList<Scope> scope) {
        this.scope = scope;
    }

    public AdditionalInformation getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(AdditionalInformation additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean getExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @SerializedName("expiration")
    private String expiration;
    @SerializedName("tokenType")
    private String tokenType;
    @SerializedName("refreshToken")
    private RefreshToken refreshToken;
    @SerializedName("scope")
    private ArrayList<Scope> scope;
    @SerializedName("additionalInformation")
    private AdditionalInformation additionalInformation;
    @SerializedName("expiresIn")
    private int expiresIn;
    @SerializedName("expired")
    private boolean expired;


    public class RefreshToken{
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public long getExpiration() {
            return expiration;
        }

        public void setExpiration(long expiration) {
            this.expiration = expiration;
        }

        @SerializedName("value")
        private String value;

        @SerializedName("expiration")
        private long expiration;

    }

    public class Scope{

    }

    public class AdditionalInformation{

    }

}
