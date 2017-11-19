package com.homework.trip.planner.data;

import com.homework.trip.planner.domain.Leg;
import com.homework.trip.planner.domain.Route;
import com.homework.trip.planner.domain.Schedule;
import com.homework.trip.planner.domain.Stop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataLoader {


    private static String LEG_FILE = "data/edges.csv";
    private static String TRAM_FILE = "data/tram_stops.csv";

    private List<Stop> stops;
    private List<Leg> legs;
    private List<Route> routes = new ArrayList<>();

    private static final int STOP_ID = 1;
    private static final int STOP_R_NR = 2;
    private static final int STOP_NAME = 3;
    private static final int STOP_LAT = 4;
    private static final int STOP_LON = 5;
    private static final int STOP_DATE = 6;
    private static final int LEG_FROM = 1;
    private static final int LEG_TO = 2;
    private static final int LEG_DURATION = 3;
    private static final int LEG_R_NR = 4;


    public DataLoader() {
        loadData();
    }

    private  Stop findStopWithIdAndNr(String id, String routeNr) {
        return stops.stream().filter(item -> item.getStopId() == Integer.parseInt(id) &&
                item.getRouteNr() == Integer.parseInt(routeNr.replace("\"", ""))).
                findFirst().orElse(new Stop());
    }


    public void loadData(){



        Route route1 = new Route(1,"Kopli-Kadriorg");
        Route route2 = new Route(2,"Kopli-Suur-Paala");
        Route route3 = new Route(3,"Tondi-Kadriorg");
        Route route4 = new Route(4,"Tondi-Lennujaam");
        routes.addAll(Arrays.asList(route1,route2,route3,route4));





        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(TRAM_FILE))) {

            stops = stream.skip(1)
                    .map(line -> line.split(","))
                    .map(data ->
                            new Stop(
                                    Integer.parseInt(data[STOP_ID]),
                                    data[STOP_NAME],
                                    Float.parseFloat(data[STOP_LAT]),
                                    Float.parseFloat(data[STOP_LON]),
                                    Integer.parseInt(data[STOP_R_NR].replace("\"", "")),
                                    new Schedule(new ArrayList<>
                                            (Arrays.asList(data[STOP_DATE].replace("\"","").split(" "))).
                                            stream().map(item -> {
                                        String[] array = item.split(":");
                                        return LocalTime.parse(array[0].replace("24","00")+":"+array[1]+":"+array[2]);
                                    })
                                            .collect(Collectors.toList())
                                    ),
                                    routes.stream().
                                            filter(item-> item.getRouteNr()==Integer.parseInt(data[STOP_R_NR].
                                                    replace("\"", ""))).
                                            findFirst().get()
                            )

                    )
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }


        try (Stream<String> stream = Files.lines(Paths.get(LEG_FILE))) {
            legs = stream.skip(1)
                    .map(line -> line.split(","))
                    .map(data -> new Leg(findStopWithIdAndNr(data[LEG_FROM], data[LEG_R_NR]),
                            findStopWithIdAndNr(data[LEG_TO], data[LEG_R_NR]),
                            Integer.parseInt(data[LEG_DURATION])))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }


        stops.forEach(stop -> stop.getRoute().addStop(stop));



        for (Leg leg : legs) {
            Stop from = leg.getFrom();
            Stop to = leg.getTo();
            for (Stop stop : stops) {
                if (from.getStopId() == stop.getStopId()) {
                    stop.addLeg(leg);
                }
                if (to.getStopId() == stop.getStopId()) {
                    stop.addLeg(leg);
                }

            }
        }



    }


    public List<Stop> getStops() {
        return stops;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
