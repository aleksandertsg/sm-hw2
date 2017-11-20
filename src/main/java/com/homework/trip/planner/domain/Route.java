package com.homework.trip.planner.domain;

import java.util.ArrayList;
import java.util.List;

public class Route {

  private int routeNr;
  private String routeName;
  private List<Location> locations = new ArrayList<>();

  public Route(int routeNr, String routeName) {
    this.routeNr = routeNr;
    this.routeName = routeName;
  }

  public String getRouteName() {
    return routeName;
  }

  public void setRouteName(String routeName) {
    this.routeName = routeName;
  }

  public int getRouteNr() {
    return routeNr;
  }

  public void setRouteNr(int routeNr) {
    this.routeNr = routeNr;
  }

  public List<Location> getStops() {
    return locations;
  }

  public void setStops(List<Location> locations) {
    this.locations = locations;
  }

  public Route addStop(Location location) {
    this.locations.add(location);
    return this;
  }

  @Override
  public String toString() {
    return "Route{" +
      "routeNr=" + routeNr +
      ", routeName='" + routeName + '\'' +
      ", stops=" + locations +
      '}';
  }
}
