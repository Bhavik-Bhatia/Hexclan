package com.example.sih_app.Student.Models;

public class NotificationsModel {
    private String status;
    private String fromDate, toDate, message;

    public NotificationsModel() {
    }

    public NotificationsModel(String status, String fromDate, String toDate, String message) {
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
