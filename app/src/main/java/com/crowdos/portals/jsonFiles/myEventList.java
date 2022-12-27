package com.crowdos.portals.jsonFiles;

import com.google.gson.annotations.SerializedName;

public class myEventList {//8
    @SerializedName("uid")
    public long uid;
    @SerializedName("eventname")
    public String eventname;
    @SerializedName("eventid")
    public long eventid;
    @SerializedName("content")
    public String content;
    @SerializedName("longitude")
    public double longitude;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("starttime")
    public long starttime;
    @SerializedName("endtime")
    public long endtime;
    @SerializedName("emergency")
    public boolean emergency;
}
