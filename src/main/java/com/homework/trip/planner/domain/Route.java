package com.homework.trip.planner.domain;

import java.util.ArrayList;
import java.util.List;

public class Route {

  private int routeNr;
  private String routeName;
  private List<Stop> stops = new ArrayList<>();

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

  public List<Stop> getStops() {
    return stops;
  }

  public void setStops(List<Stop> stops) {
    this.stops = stops;
  }

  public Route addStop(Stop stop) {
    this.stops.add(stop);
    return this;
  }

  @Override
  public String toString() {
    return "Route{" +
      "routeNr=" + routeNr +
      ", routeName='" + routeName + '\'' +
      ", stops=" + stops +
      '}';
  }
}
