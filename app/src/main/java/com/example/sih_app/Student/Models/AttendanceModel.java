package com.example.sih_app.Student.Models;

public class AttendanceModel {
    String subject,status;

    public AttendanceModel(String subject, String status) {
        this.subject = subject;
        this.status = status;
    }

    public AttendanceModel() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
