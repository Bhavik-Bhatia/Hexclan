package com.example.sih_app.Student.Models;

public class DeptActivityModel {
    String time,status;

    public DeptActivityModel(String time, String status) {
        this.time = time;
        this.status = status;
    }

    public DeptActivityModel() {
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
