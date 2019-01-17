package com.example.app.FEEDBACK.MODEL;

public class FeedBackDetail {



    String itemName;
    String feedBack;
    Integer id;
    Integer feedBackBy;


    public FeedBackDetail(String itemName, String feedBack, Integer id, Integer feedBackBy) {
        this.itemName = itemName;
        this.feedBack = feedBack;
        this.id = id;
        this.feedBackBy = feedBackBy;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFeedBackBy() {
        return feedBackBy;
    }

    public void setFeedBackBy(Integer feedBackBy) {
        this.feedBackBy = feedBackBy;
    }
}
