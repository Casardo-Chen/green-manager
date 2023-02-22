package com.example.crepe.database;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class User {

    private String name;
    private String userId;
    private String photo;
    private String tel;
    private ArrayList<String> trips;
    private String gender;

    public User(String userId, String name, String photo, String tel, String gender) {
        this.userId = userId;
        this.name = name;
        this.photo = photo;
        this.tel = tel;
        this.gender = gender;
        this.trips = new ArrayList<String>();
    }

    public User(String userId, String name, String photo, String tel, String gender, String trips) {
        this.userId = userId;
        this.name = name;
        this.photo = photo;
        this.tel = tel;
        this.gender = gender;
        this.trips = new ArrayList<String>();
        String[] arrOfTrips = trips.split(",");
        for(String trip: arrOfTrips){
            this.trips.add(trip);
        }
    }

    public User() {
        this.userId = "";
        this.name = "N/A";
        this.photo = "";
        this.tel = "";
        this.gender = "";
        this.trips = new ArrayList<String>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto(){return photo;}

    public void setPhoto(String photo){this.photo = photo;}

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void addTrip(String tripID){
        this.trips.add(tripID);
    }

    public String getTrips(){
        String str = "";
        for(int i = 0; i < this.trips.size(); i++){
            str += this.trips.get(i);
            if(i == this.trips.size()-1){break;}
            str += ",";
        }
        return str;
    }
}
