package com.moutamid.restaurantapp.helper;

// BookingModel.java

public class BookingModel {
    private String userName;
    private String userPhone;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantImageUrl;
    private String restaurantKey;
    private String tableNumber;

    public BookingModel(String userName, String userPhone, String restaurantName, String restaurantAddress,
                        String restaurantImageUrl, String restaurantKey, String tableNumber) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantImageUrl = restaurantImageUrl;
        this.restaurantKey = restaurantKey;
        this.tableNumber = tableNumber;
    }

    // Getters and Setters
}
