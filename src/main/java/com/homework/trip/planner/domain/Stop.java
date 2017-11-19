package com.homework.trip.planner.domain;

import java.util.ArrayList;
import java.util.List;

public class Stop {

    private int stopId;
    private String name;
    private float lat;
    private float lon;
    private int routeNr;
    private List<Leg> legs = new ArrayList<>();
    private Schedule schedule;
    private Route route;


    public Stop(int stopId, String name, float lat, float lon, int routeNr, Schedule schedule, Route route) {
        this.stopId = stopId;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.routeNr = routeNr;
        this.schedule = schedule;
        this.route = route;
    }

    public Stop(String name, float lat, float lon, Schedule schedule) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.schedule = schedule;
    }

    public Stop() {

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

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
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

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }


    @Override
    public String toString() {
        return name+"("+routeNr+")";
    }


}
