package com.homework;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Dijkstra{

    public Map<Stop, Path> getShortestPathsFromVertex(GraphImpl graph, Stop startingVertex, Stop endingPoint, LocalTime time) {
        Map<Stop, Path> paths = new HashMap<>();
        Map<Stop, Double> distances = new HashMap<>();
        Map<Stop, LocalTime> times = new HashMap<>();

        Queue<Stop> unprocessedVertices = new PriorityQueue<>((one, other) -> {
            if (distances.get(one) > distances.get(other)) {
                return 1;
            } else if (distances.get(one) < distances.get(other)) {
                return -1;
            }
            return 0;
        });

        // initialize
        for (Stop vertex : graph.getVertices()) {
            distances.put(vertex, Double.MAX_VALUE);
        }


        paths.put(startingVertex, new Path(startingVertex, null,time));
        distances.put(startingVertex, 0.0);
        times.put(startingVertex,time);
        unprocessedVertices.add(startingVertex);

        while (!unprocessedVertices.isEmpty()) {
            Stop minVertex = unprocessedVertices.remove();
            System.out.println(minVertex);
            System.out.println("MINVERTEX");
            if(minVertex!=endingPoint) {
                LocalTime minBusStartTime = minVertex.getSchedule().getTimes().stream().min((d1, d2) -> compare(d1, d2, times.get(minVertex))).get();
                System.out.println(minBusStartTime+"STARTTIMEBUSTIME");
                times.put(minVertex,minBusStartTime);
            }
            else{
                System.out.println("Arrived at:"+times.get(minVertex));
            }





            for (Stop neighbor : graph.neighborsOf(minVertex)) {
                System.out.println(neighbor+"NEIGHBOUR");
                long tripDuration = graph.getEdge(minVertex,neighbor);
                LocalTime arrivingTime = times.get(minVertex).plusMinutes(tripDuration);
                double totalTripTime = Duration.between(time,arrivingTime).toMinutes();
                System.out.println(tripDuration+"tripDuration");
                System.out.println(arrivingTime+"arrivingTIME");
                System.out.println(totalTripTime+"totalTripTime");




                Double altPathWeight = distances.get(minVertex) + totalTripTime;
                if (distances.get(neighbor) > altPathWeight) {
                    distances.put(neighbor, altPathWeight);
                    System.out.println("PANEN KOKKU");
                    System.out.println(neighbor);
                    paths.put(neighbor, new Path(neighbor, paths.get(minVertex),arrivingTime));
                    times.put(neighbor,arrivingTime);

                    if (!unprocessedVertices.contains(neighbor)) {
                        unprocessedVertices.add(neighbor);
                    }
                }
            }

        }

        return paths;
    }


    public Stop getShortest(GraphImpl graph, Stop from, Stop to){
        return null;
    }


    private int compare(LocalTime d1, LocalTime d2,LocalTime time){

        long diff1 = Duration.between(time,d1).toMinutes();
        long diff2 = Duration.between(time,d2).toMinutes();
        if(diff1<0){
            diff1 = diff1 + 24 * 60;
        }
        if(diff2<0){
            diff2 = diff2 + 24 * 60;
        }

        return Integer.compare(Math.toIntExact(diff1), Math.toIntExact(diff2));

    }
}