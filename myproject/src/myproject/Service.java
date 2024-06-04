package myproject;

import java.sql.Date;

public class Service {
    private int id;
    private int customerId;
    private Date date;
    private String serviceName;

    // Constructor
    public Service(int id, int customerId, Date date, String serviceName) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.serviceName = serviceName;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
