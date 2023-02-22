package com.example.crepe.database;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Ride {
    private String rideId;
    private String creatorUserId;
    private String rideDate;
    private String rideTime;
    private String capacity;
    private String people;
    private String destination;
    private String departure;
    private String description;
    private String collectorStatus;

    // some constants for collector status
    public static final String FULL = "full";
    public static final String AVAIL = "available";


    public Ride(String rideId, String creatorUserID, String rideDate, String rideTime, String capacity, String people, String destination, String departure, String description) {
        this.rideId = rideId;
        this.creatorUserId = creatorUserID;
        this.rideDate = rideDate;
        this.rideTime = rideTime;
        this.capacity = capacity;
        this.people = people;
        this.destination = destination;
        this.departure = departure;
        this.description = description;

        // auto generate the status of the collector based on current time
        this.autoSetCollectorStatus();

    }

//    public Ride(String rideId, String creatorUserID, String rideDate, String rideTime, String capacity, String people, String destination, String departure, String description, String collectorStatus) {
//        this.rideId = rideId;
//        this.creatorUserId = creatorUserID;
//        this.rideDate = rideDate;
//        this.rideTime = rideTime;
//        this.capacity = capacity;
//        this.people = people;
//        this.destination = destination;
//        this.departure = departure;
//        this.description = description;
//        this.collectorStatus = collectorStatus;
//    }

    public Ride(String rideId) {
        this.rideId = rideId;
    }

    @Override
    public String toString() {
        return "Collector{" +
                "rideId='" + rideId + '\'' +
                ", creatorUserId='" + creatorUserId + '\'' +
                ", destination='" + destination + '\'' +
                ", description='" + description + '\'' +
                ", rideDate=" + rideDate +
                ", rideTime=" + rideTime +
                ", departure= " + departure +
                ", collectorStatus='" + collectorStatus + '\'' +
                '}';
    }

    public String idToString() {
        return "Collector with id: " + rideId;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRideDate() {
        return rideDate;
    }

    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }

//    public String getCollectorStartDateString() {
//        Date date = new Date(rideDate);
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        // force the timezone to be utc because of bug in material design. All time operations will be in utc
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        return dateFormat.format(date);
//    }



    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

//    public String getCollectorStartTimeString() {
//        //TODO: date -> time
//        Date date = new Date(rideTime);
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSSZ");
//        // force the timezone to be utc because of bug in material design. All time operations will be in utc
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//        return dateFormat.format(date);
//    }




    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }


    public String getCollectorStatus() {return collectorStatus;}


    // The collectorStatus will be set based on current time and the collector's start and end time
    // The return value indicates if the status is changed: true for changed, false for unchanged
    public Boolean autoSetCollectorStatus() {

//        long currentTime = System.currentTimeMillis();
        // There are 5 statuses for the collector:
        // 1. deleted (will not be displayed on client phones)
        // 2. not deleted (will be displayed on phones) -- not yet started, active, expired based on time, disabled
        //                      the statuses are represented in camel case (e.g. notYetStarted)
        // by default, the collectors won't be deleted or disabled,
        // so we only need to allocate it based on current time
        if(Integer.parseInt(this.people) < Integer.parseInt(this.capacity)){
            this.collectorStatus = AVAIL;
            return true;
        }
        else{
            this.collectorStatus = FULL;
            return false;
        }
    }

    // We also provide a set status function to manually set the status to an arbitrary value
    public void setCollectorStatus(String collectorStatus) {
        if (collectorStatus == AVAIL || collectorStatus == FULL ) {
            this.collectorStatus = collectorStatus;
        } else {
            Log.e("collector", "The input status is not valid (must be deleted, disabled, notYetStarted, active, or expired)");
        }
    }

    public void availRide() {
        this.collectorStatus = AVAIL;
    }

    public void fullRide() {
        this.collectorStatus = FULL;
    }



//    public Boolean isDeleted() {
//        return this.collectorStatus.equals(DELETED);
//    }

}
