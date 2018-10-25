package com.adityalprabhu.workouttracker.model;

/**
 * Created by prabhadi on 05/05/2017.
 */

public class WorkoutDetails {

    private int id;
    private String workout;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkout() {
        return workout;
    }

    public void setWorkout(String workout) {
        this.workout = workout;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPace() {
        return pace;
    }

    public void setPace(String pace) {
        this.pace = pace;
    }

    public String getInclination() {
        return inclination;
    }

    public void setInclination(String inclination) {
        this.inclination = inclination;
    }

    private String time;
    private String calories;
    private String distance;
    private String pace;
    private String inclination;
    private String Date;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}


