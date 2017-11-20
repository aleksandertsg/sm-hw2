package com.homework.trip.planner.domain;

public class Location {
  private String name;
  private float lat;
  private float lon;


  public Location(String name, float lat, float lon) {
    this.name = name;
    this.lat = lat;
    this.lon = lon;
  }

  public Location() {
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
}
