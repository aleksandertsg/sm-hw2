package com.homework.trip.planner.domain;

public class Location {
  protected String name;
  protected float lat;
  protected float lon;

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
}
