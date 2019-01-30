package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

public class DashboardResult {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("is_active")
    private String is_active;
    @SerializedName("franch_view_flag")
    private String franch_view_flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getFranch_view_flag() {
        return franch_view_flag;
    }

    public void setFranch_view_flag(String franch_view_flag) {
        this.franch_view_flag = franch_view_flag;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @SerializedName("unit_id")
    private int unit_id;
    @SerializedName("totalCount")
    private int totalCount;
}
