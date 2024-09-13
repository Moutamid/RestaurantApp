package com.moutamid.easyroomapp.helper;

// BookingModel.java

public class BookingModel {
    private String userName;
    private String userPhone;
    private String easyroomName;
    private String easyroomAddress;
    private String easyroomImageUrl;
    private String easyroomKey;
    private String tableNumber;

    public BookingModel(String userName, String userPhone, String easyroomName, String easyroomAddress,
                        String easyroomImageUrl, String easyroomKey, String tableNumber) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.easyroomName = easyroomName;
        this.easyroomAddress = easyroomAddress;
        this.easyroomImageUrl = easyroomImageUrl;
        this.easyroomKey = easyroomKey;
        this.tableNumber = tableNumber;
    }

    // Getters and Setters
}
