package myproject;

import java.sql.Date;

public class Customer {
    private int id;
    private String name;
    private String phoneNumber;
    private String aadhaarCard;
    private Date checkinDate;
    private Date checkoutDate;
    private String[] roomPreferences;
    private String[][] services;

    // Constructor
    public Customer(int id, String name, String phoneNumber, String aadhaarCard, Date checkinDate, Date checkoutDate, String[] roomPreferences, String[][] services) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.aadhaarCard = aadhaarCard;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.roomPreferences = roomPreferences;
        this.services = services;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAadhaarCard() {
        return aadhaarCard;
    }

    public void setAadhaarCard(String aadhaarCard) {
        this.aadhaarCard = aadhaarCard;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String[] getRoomPreferences() {
        return roomPreferences;
    }

    public void setRoomPreferences(String[] roomPreferences) {
        this.roomPreferences = roomPreferences;
    }

    public String[][] getServices() {
        return services;
    }

    public void setServices(String[][] services) {
        this.services = services;
    }
}
