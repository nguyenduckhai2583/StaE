package com.example.stapemotion.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("img")
    private int img;

    public Location() {
    }

    public Location(String name, String address, int img) {
        this.name = name;
        this.address = address;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
