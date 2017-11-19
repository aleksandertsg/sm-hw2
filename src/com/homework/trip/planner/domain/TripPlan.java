package com.homework.trip.planner.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TripPlan {

    private LocalTime startTime;

    private LocalTime endTime;

    private List<Stop> stops;

    private String fullPath;

    public TripPlan() {
        this.stops = new ArrayList<>();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void addStops(Stop stop) {
        this.stops.add(stop);
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    @Override
    public String toString() {
        return "TripPlan: " +fullPath + '\'';
    }
}
