package com.example.androidtest.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "LISTDATA")
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

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("amt.pledged")
    private String amtPledged;

    @SerializedName("end.time")
    private String endTime;

    @SerializedName("s.no")
    private String serialNo;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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

    public static DiffUtil.ItemCallback<Data> DIFF_CALLBACK = new DiffUtil.ItemCallback<Data>() {
        @Override
        public boolean areItemsTheSame(@NonNull Data oldItem, @NonNull Data newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Data oldItem, @NonNull Data newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Data data = (Data) obj;
        return data.id == this.id && data.serialNo == this.serialNo;
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
                ", sNo='" + serialNo + '\'' +
                ", numBackers='" + numBackers + '\'' +
                ", percentageFunded='" + percentageFunded + '\'' +
                '}';
    }
}
