package com.crowdos.portals.jsonFiles;

import com.google.gson.annotations.SerializedName;

public class eventList {
    @SerializedName("eventname")
    public String eventname;
    @SerializedName("eventid")
    public long eventid;
    @SerializedName("longitude")
    public double longitude;
    @SerializedName("latitude")
    public double latitude;
    public boolean emergency;
}//1
