package com.example.androidtest.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class Data {

    private String country;
    private String url;
    private String by;
    private String currency;
    private String location;
    private String state;
    private String blurb;
    private String title;
    private String type;

    @SerializedName("amt.pledged")
    private String amtPledged;

    @SerializedName("end.time")
    private String endTime;

    @SerializedName("s.no")
    private String sNo;

    @SerializedName("num.backers")
    private String numBackers;

    @SerializedName("percentage.funded")
    private String percentageFunded;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmtPledged() {
        return amtPledged;
    }

    public void setAmtPledged(String amtPledged) {
        this.amtPledged = amtPledged;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getNumBackers() {
        return numBackers;
    }

    public void setNumBackers(String numBackers) {
        this.numBackers = numBackers;
    }

    public String getPercentageFunded() {
        return percentageFunded;
    }

    public void setPercentageFunded(String percentageFunded) {
        this.percentageFunded = percentageFunded;
    }

    @NotNull
    @Override
    public String toString() {
        return "Data{" +
                "country='" + country + '\'' +
                ", url='" + url + '\'' +
                ", by='" + by + '\'' +
                ", currency='" + currency + '\'' +
                ", location='" + location + '\'' +
                ", state='" + state + '\'' +
                ", blurb='" + blurb + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", amtPledged='" + amtPledged + '\'' +
                ", endTime='" + endTime + '\'' +
                ", sNo='" + sNo + '\'' +
                ", numBackers='" + numBackers + '\'' +
                ", percentageFunded='" + percentageFunded + '\'' +
                '}';
    }
}
