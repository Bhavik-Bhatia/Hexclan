package com.example.sih_app.Student.Models;

public class UpcomingEventsModel {
    private String date, eventTitle, eventDesc;

    public UpcomingEventsModel() {
    }

    public UpcomingEventsModel(String date, String eventTitle, String eventDesc) {
        this.date = date;
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
