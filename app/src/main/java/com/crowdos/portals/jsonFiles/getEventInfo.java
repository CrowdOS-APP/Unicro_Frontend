package com.crowdos.portals.jsonFiles;

import com.google.gson.annotations.SerializedName;

public class getEventInfo {
    @SerializedName("eventname")
    public String eventname;
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
    public boolean isFollow;

//4
}
