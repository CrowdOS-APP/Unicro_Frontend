package com.crowdos.portals.jsonFiles;

import com.google.gson.annotations.SerializedName;

public class getUserInfo {
    @SerializedName("username")
    public String username;
    @SerializedName("signature")
    public String signature;
    @SerializedName("UID")
    public long UID;
}
