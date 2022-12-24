package com.crowdos.portals.jsonFiles;

import com.google.gson.annotations.SerializedName;

public class myEventList {//8
    @SerializedName("uid")
    public long uid;
    @SerializedName("eventname")
    public String eventname;
    @SerializedName("eventid")
    public long enventid;
    @SerializedName("content")
    public String content;
    @SerializedName("longitude")
    public long longitude;
    @SerializedName("latitude")
    public long latitude;
    @SerializedName("starttime")
    public long starttime;
    @SerializedName("endtime")
    public long endtime;
    @SerializedName("emergency")
    public boolean emergency;
}
