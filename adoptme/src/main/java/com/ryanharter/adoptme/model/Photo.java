package com.ryanharter.adoptme.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rharter on 2/17/14.
 */
public class Photo {

    @SerializedName("@size")
    public String size;

    @SerializedName("$t")
    public String url;

    @SerializedName("@id")
    public int id;

}
