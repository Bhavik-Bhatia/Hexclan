package com.example.sih_app.Student.Models;

public class RequestLeaveStudentModel {
    String fromDate, toDate, reason, status;

    public RequestLeaveStudentModel() {
    }

    public RequestLeaveStudentModel(String fromDate, String toDate, String reason, String status) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
