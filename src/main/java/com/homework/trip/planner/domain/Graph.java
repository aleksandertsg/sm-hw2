package com.homework.trip.planner.domain;

import java.util.*;

public class Graph {
  private Map<Stop, Map<Stop, Integer>> graph = new HashMap<>();



    private static final Graph instance = new Graph();

    private Graph() {
    }



  private boolean addStop(Stop vertex) {
    if (graph.containsKey(vertex)) {
      return false;
    } else {
      Map<Stop, Integer> map = new HashMap<>();
      graph.put(vertex, map);
      return true;
    }
  }

  public Set<Stop> getVertices() {
    return graph.keySet();
  }

  private boolean addLeg(Stop one, Stop other, Integer edge) {
    if (graph.containsKey(one)) {
      Map<Stop, Integer> map = new HashMap<>();
      map.put(other, edge);
      if (graph.get(one).containsKey(other)) {
        return false;
      } else {
        graph.get(one).put(other, edge);
        return true;
      }
    } else {
      return false;
    }
  }

  public Set<Stop> neighboursOf(Stop vertex) {

    if (graph.containsKey(vertex)) {
      return graph.get(vertex).keySet();
    } else {
      return new HashSet<>();
    }
  }

  public Integer getEdge(Stop one, Stop other) {
    if (graph.containsKey(one)) {
      if (graph.get(one).containsKey(other)) {
        return graph.get(one).get(other);
      }
    }
    if (graph.containsKey(other)) {
      if (graph.get(other).containsKey(one)) {
        return graph.get(other).get(one);
      }
    }
    return null;
  }

  public void createGraph(List<Stop> stops, List<Leg> legs) {
    stops.forEach(this::addStop);
    legs.forEach(item -> addLeg(item.getFrom(), item.getTo(), item.getDuration()));

    for (Stop stop1 : stops) {

      for (Stop stop2 : stops) {
        if (Objects.equals(stop1.getName(), stop2.getName())) {
          addLeg(stop1, stop2, 0);
        }
      }
    }
  }


    public static Graph getInstance(){
        return instance;
    }

}

