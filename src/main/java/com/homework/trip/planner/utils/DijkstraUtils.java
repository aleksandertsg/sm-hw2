package com.homework.trip.planner.utils;

import com.homework.trip.planner.data.DataLoader;
import com.homework.trip.planner.domain.Schedule;
import com.homework.trip.planner.domain.Stop;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class DijkstraUtils {

  private static float distFrom(float lat1, float lng1, float lat2, float lng2) {
    double earthRadius = 6371000; //meters
    double dLat = Math.toRadians(lat2 - lat1);
    double dLng = Math.toRadians(lng2 - lng1);
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLng / 2) * Math.sin(dLng / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    float dist = (float) (earthRadius * c);

    return dist;
  }

  private static Stop findClosestStop(List<Stop> stops, float lat1, float lng1) {
    return stops.stream().min(((d1, d2) -> compare(d1, d2, lat1, lng1))).get();

  }

  private static int compare(Stop d1, Stop d2, float lat1, float lng1) {
    Float stop1 = distFrom(d1.getLat(), d1.getLon(), lat1, lng1);
    Float stop2 = distFrom(d2.getLat(), d2.getLon(), lat1, lng1);
    return stop1.compareTo(stop2);
  }

  private static LinkedHashMap<Stop, LocalTime> getPath(GraphUtils graph, Stop startStop, Stop endStop,
                                                        LocalTime time) {

    Map<Stop, Double> distances = new HashMap<>();
    Map<Stop, LocalTime> times = new HashMap<>();
    Map<Stop, LinkedHashMap<Stop, LocalTime>> path = new HashMap<>();

    Queue<Stop> unprocessedStops = new PriorityQueue<>((one, other) -> {
      if (distances.get(one) > distances.get(other)) {
        return 1;
      } else if (distances.get(one) < distances.get(other)) {
        return -1;
      }
      return 0;
    });

    for (Stop vertex : graph.getStops()) {
      distances.put(vertex, Double.MAX_VALUE);
    }

    LinkedHashMap<Stop, LocalTime> leg = new LinkedHashMap<>();
    leg.put(startStop, time);
    path.put(startStop, leg);
    distances.put(startStop, 0.0);
    times.put(startStop, time);
    unprocessedStops.add(startStop);

    while (!unprocessedStops.isEmpty()) {
      Stop minVertex = unprocessedStops.remove();
      LocalTime minBusStartTime;

      if (minVertex != endStop) {
        minBusStartTime = minVertex.getSchedule().getTimes().stream()
          .min((d1, d2) -> compare(d1, d2, times.get(minVertex))).get();
      } else {
        minBusStartTime = times.get(minVertex);
      }

      for (Stop neighbor : graph.neighboursOf(minVertex)) {

        long tripDuration = graph.getLegDistance(minVertex, neighbor);
        LocalTime arrivingTime = minBusStartTime.plusMinutes(tripDuration);
        if (neighbor.getName().equals(minVertex.getName()) ) {
          arrivingTime = times.get(minVertex);
        }
        Double altPathWeight = (double) Duration.between(time, arrivingTime).toMinutes();

        if (distances.get(neighbor) > altPathWeight) {
          distances.put(neighbor, altPathWeight);
          LinkedHashMap<Stop, LocalTime> clonedLeg = new LinkedHashMap<>(path.get(minVertex));
          clonedLeg.put(neighbor, arrivingTime);
          path.put(neighbor, clonedLeg);
          times.put(neighbor, arrivingTime);

          if (!unprocessedStops.contains(neighbor)) {
            unprocessedStops.add(neighbor);
          }
        }
      }

    }
      System.out.println(path.get(endStop));
    return path.get(endStop);
  }

  protected static int compare(LocalTime d1, LocalTime d2, LocalTime time) {

    long diff1 = Duration.between(time, d1).toMinutes();
    long diff2 = Duration.between(time, d2).toMinutes();
    if (diff1 < 0) {
      diff1 = diff1 + 24 * 60;
    }
    if (diff2 < 0) {
      diff2 = diff2 + 24 * 60;
    }
    return Integer.compare(Math.toIntExact(diff1), Math.toIntExact(diff2));

  }

  public static LinkedHashMap<Stop, LocalTime> findOptimalPath(float latFrom, float lonFrom, float latTo, float lonTo,
                                                               LocalTime time) {

    DataLoader data = DataLoader.getInstance();

    GraphUtils graph = GraphUtils.getInstance();
    graph.createGraph(data.getStops(),data.getLegs());

    Stop from = findClosestStop(data.getStops(), latFrom, lonFrom);

    Stop to = findClosestStop(data.getStops(), latTo, lonTo);

    Float fromDistance = distFrom(latFrom, lonFrom, from.getLat(), from.getLon());

    Float toDistance = distFrom(latTo, lonTo, to.getLat(), to.getLon());

    LocalTime startTime = time.plusMinutes(calculateWalkingMinutes(fromDistance));

    LinkedHashMap<Stop, LocalTime> path = getPath(graph, from, to, startTime);

    LocalTime finalTime = path.get(path.keySet().stream().reduce((first, second) -> second).get());

    Stop end = new Stop("End", latFrom, lonFrom, new Schedule(new ArrayList<>()));

    path.put(end, finalTime.plusMinutes(calculateWalkingMinutes(toDistance)));

    return path;
  }

  private static int calculateWalkingMinutes(Float meters) {

    return (int) Math.ceil((meters / 1000) * 10);

  }

}