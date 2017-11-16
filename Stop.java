package com.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Stop {

    private int stopId;
    private String name;
    private double lat;
    private double lon;
    private int routeNr;
    private List<Leg> legs = new ArrayList<>();
    private Schedule schedule;


    public Stop(int stopId, String name, double lat, double lon, int routeNr, Schedule schedule) {
        this.stopId = stopId;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.routeNr = routeNr;
        this.schedule = schedule;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getRouteNr() {
        return routeNr;
    }

    public void setRouteNr(int routeNr) {
        this.routeNr = routeNr;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }
    public void addLeg(Leg leg){
        this.legs.add(leg);
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "stopId=" + stopId +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", routeNr=" + routeNr +
                '}';
    }
}
