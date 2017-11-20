package com.homework.trip.planner.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TripPlan {

  private LocalTime startTime;

  private LocalTime endTime;

  private List<Location> locations;

  private String fullPath;

  public TripPlan() {
    this.locations = new ArrayList<>();
  }

  public TripPlan(String fullPath) {
    this.fullPath = fullPath;
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

  public List<Location> getStops() {
    return locations;
  }

  public void addStops(Location location) {
    this.locations.add(location);
  }

  public String getFullPath() {
    return fullPath;
  }

  public void setFullPath(String fullPath) {
    this.fullPath = fullPath;
  }

  @Override
  public String toString() {
    return "TripPlan: " + fullPath + '\'';
  }
}
