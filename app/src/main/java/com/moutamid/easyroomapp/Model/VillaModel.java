package com.moutamid.easyroomapp.Model;

import java.util.List;
import java.util.Map;

public class VillaModel {

    private String name;
    private String description;
    private int roomType;
    private int bill;
    public int no_of_persons;
    boolean bills_included;
    String image, userImage, userName;
    String key;
    private double lat;
    private double lng;
    private String title;

    public String available;
    public String token;
    public String city_name;
    public String town_name;
    public String available_dates;
    public List<String> selectedImageUris;

    public double distance;
    public boolean verified;
    public String ownerID;
    public VillaModel() {
    }

    public VillaModel(double lat, double lng, String title) {
        this.lat = lat;
        this.lng = lng;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public int getRoomType() {
        return roomType;
    }

    public String getImage() {
        return image;
    }

    public int getBill() {
        return bill;
    }

    public boolean isBills_included() {
        return bills_included;
    }

    public String getUserImage() {

        return userImage;
    }

    public String getUserName() {
        return userName;
    }

    public String getKey() {
        return key;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getTitle() {
        return title;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setName(String name) {
        this.name = name;
    }
    private Map<String, String> images;

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }


}
