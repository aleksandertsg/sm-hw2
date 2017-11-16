package com.homework;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Dijkstra{

    public Map<Stop, Path> getShortestPathsFromVertex(GraphImpl graph, Stop startingVertex, LocalTime time) {
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


        paths.put(startingVertex, new Path(startingVertex, null));
        distances.put(startingVertex, 0.0);
        times.put(startingVertex,time);
        unprocessedVertices.add(startingVertex);

        while (!unprocessedVertices.isEmpty()) {
            Stop minVertex = unprocessedVertices.remove();
            System.out.println(minVertex);
            System.out.println("MINVERTEX");
            Optional<LocalTime> optTimeStart = minVertex.getSchedule().getTimes().stream().filter(item -> item.isAfter(times.get(minVertex)) || item.equals(times.get(minVertex))).findFirst();
            long tTimeStart = optTimeStart.map(localTime -> Duration.between(times.get(minVertex), localTime).toMinutes()).orElse(Long.MAX_VALUE);
            LocalTime finalTime = times.get(minVertex).plusMinutes(tTimeStart);
            times.put(minVertex,finalTime);
            System.out.println(finalTime+"HERETIME");



            for (Stop neighbor : graph.neighborsOf(minVertex)) {
                System.out.println(finalTime+" TIME");
                System.out.println(neighbor+ "; NEIGH");
                System.out.println(distances.get(neighbor)+";NEIDIST");
                Long a = new Long(graph.getEdge(minVertex, neighbor));
                System.out.println(finalTime.plusMinutes(a)+"TOBEHERE");
                LocalTime timeAtNeigh = finalTime.plusMinutes(a);
                Optional<LocalTime> optTime = neighbor.getSchedule().getTimes().stream().filter(item -> item.isAfter(timeAtNeigh) || item.equals(timeAtNeigh)).findFirst();
                long tTime;


                tTime = optTime.map(localTime -> Duration.between(timeAtNeigh, localTime).toMinutes()).orElse(Long.MAX_VALUE);

                Long total = Duration.between(time,timeAtNeigh.plusMinutes(tTime)).toMinutes();
                System.out.println(tTime+"TOWAIT");
                System.out.println(distances.get(minVertex)+"DURATIONTOHERE");
                System.out.println(total+"TOTAL DURATION WEIGHT");
                Double altPathWeight = distances.get(minVertex) + Double.parseDouble(total.toString());
                if (distances.get(neighbor) > altPathWeight) {
                    distances.put(neighbor, altPathWeight);
                    paths.put(neighbor, new Path(neighbor, paths.get(minVertex)));
                    times.put(neighbor,timeAtNeigh.plusMinutes(tTime));

                    if (!unprocessedVertices.contains(neighbor)) {
                        System.out.println("POLE PROTSESSITUD");
                        System.out.println(neighbor+"NSA");
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
}