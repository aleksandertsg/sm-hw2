package com.homework.trip.planner.utils;

import com.homework.trip.planner.domain.Stop;
import com.homework.trip.planner.data.DataLoader;
import com.homework.trip.planner.domain.TripPlan;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TripPlanBuilder {

  public TripPlan createTripPlan(float latFrom, float lonFrom, float latTo, float lonTo, String time) {

    DataLoader data = new DataLoader();

    GraphUtils graph = new GraphUtils();

    graph.createGraph(data.getStops(), data.getLegs());

    DijkstraUtils path = new DijkstraUtils();
    LocalTime startingTime = null;

    try {
      startingTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);

    } catch (DateTimeParseException e) {
      return new TripPlan("Wrong time format");
    }

    return createFullPath(path.findOptimalPath(latFrom, lonFrom, latTo, lonTo, startingTime), startingTime);

  }

  private TripPlan createFullPath(Map<Stop, LocalTime> path, LocalTime startingTime) {
    TripPlan tripPlan = new TripPlan();
    StringBuilder completePath = new StringBuilder();
    if (path == null) {
      completePath.append("Sorry, we can't find available plan for you :( ");
    } else {
      List<Stop> keyList = new ArrayList<>(path.keySet());
      for (int i = 0; i < keyList.size(); i++) {
        Stop k = keyList.get(i);
        LocalTime v = path.get(k);
        tripPlan.addStops(k);
        LocalTime finalV = v;
        LocalTime busStartTime = k.getSchedule().getTimes().stream()
          .min((d1, d2) -> DijkstraUtils.compare(d1, d2, finalV)).orElse(v);
        Stop nextStop = null;
        LocalTime arrivingTime = path.get(path.keySet().stream().reduce((first, second) -> second).get());

        if (path.size() > i + 1) {
          nextStop = keyList.get(i + 1);
        }

        if (i == 0) {
          if (nextStop != null && nextStop.getName().equals(k.getName())) {
            k = nextStop;
            v = path.get(nextStop);
          }
          tripPlan.setStartTime(v);
          completePath.append("Greetings from Trip planner!\n----------------\n")
            .append("Estimated arriving time is: ")
            .append(arrivingTime)
            .append("\n----------------\n")
            .append("Walking time from your location to stop is: ")
            .append(Duration.between(startingTime, v).toMinutes())
            .append(" min")
            .append("\n----------------\n");
          continue;
        }

        if (nextStop != null && nextStop.getName().equals(k.getName()) && path.size() == i + 3) {
          completePath
            .append("Arriving time of tram at stop:  ")
            .append(k)
            .append(" is: ")
            .append(v)
            .append("\n----------------\n")
            .append("Walking time from stop to your location is: ")
            .append(Duration.between(v, arrivingTime).toMinutes())
            .append(" min")
            .append("\n----------------\n");
          tripPlan.setEndTime(arrivingTime);
          completePath.append("Thank you for using us!");
          break;

        } else {
          if (!keyList.get(i - 1).getName().equals(k.getName())) {
            completePath
              .append("Arriving time at stop:  ")
              .append(k)
              .append(" is: ")
              .append(v)
              .append("\n----------------\n");
          }
          if (nextStop != null && nextStop.getName().equals(k.getName())) {
            completePath
              .append("Please change to tram with route number: ")
              .append(nextStop.getRouteNr())
              .append("\n----------------\n");
          } else {
            if (i + 1 != path.size()) {
              completePath
                .append("Tram from stop: ")
                .append(k)
                .append(" Will move out at: ")
                .append(busStartTime)
                .append("\n----------------\n");
            } else {
              completePath
                .append("Walking time from stop to your location is: ")
                .append(Duration.between(v, arrivingTime).toMinutes())
                .append(" min")
                .append("\n----------------\n")
                .append("Thank you for using us!");
            }
          }
        }
      }
      tripPlan.setFullPath(completePath.toString());
    }

    return tripPlan;

  }
}