package com.example.sih_app.Parent.Models;

public class LeaveRequestParentModel {
    private String status,fromDate,toDate,message,leaveRequestID ;

    public String getLeaveRequestID() {
        return leaveRequestID;
    }

    public void setLeaveRequestID(String leaveRequestID) {
        this.leaveRequestID = leaveRequestID;
    }

    public LeaveRequestParentModel() {
    }

    public LeaveRequestParentModel(String status, String fromDate, String toDate, String message, String leaveRequestID) {
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.message = message;
        this.leaveRequestID=leaveRequestID;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
