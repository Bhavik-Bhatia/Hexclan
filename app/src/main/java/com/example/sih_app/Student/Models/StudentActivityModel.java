package com.example.sih_app.Student.Models;

public class StudentActivityModel {
    String time,status;

    public StudentActivityModel(String time, String status) {
        this.time = time;
        this.status = status;
    }

    public StudentActivityModel() {
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
