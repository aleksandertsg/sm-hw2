package com.homework.trip.planner.utils;

import com.homework.trip.planner.domain.Stop;
import com.homework.trip.planner.data.DataLoader;
import com.homework.trip.planner.domain.TripPlan;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TripPlanBuilder {




    public String createTripPlan(String from, String to, String date){

        DataLoader data = new DataLoader();

        GraphUtils graph = new GraphUtils();

        graph.createGraph(data.getStops(),data.getLegs());

        DijkstraUtils path = new DijkstraUtils();



        Stop from1 = data.getStops().get(0);
        Stop to1 = data.getStops().get(25);
        List<Stop> from2 = data.getStops().stream().filter(item -> item.getStopId()==1086).collect(Collectors.toList());
        Stop from3 = from2.get(0);
        Stop from4 = from2.get(1);


        createFullPath(path.getPath(graph, from4,to1, LocalTime.parse("14:00:00")));


        return "TEST";

    }


    private String createFullPath(Map<Stop,LocalTime> path){
        TripPlan tripPlan = new TripPlan();
        AtomicInteger index = new AtomicInteger();
        StringBuilder completePath = new StringBuilder();

        path.forEach((k,v)->{
            tripPlan.addStops(k);
            LocalTime busStartTime = k.getSchedule().getTimes().stream().min((d1, d2) -> DijkstraUtils.compare(d1, d2, v)).get();
            Stop nextStop = null;
            if(index.get()==0){
                tripPlan.setStartTime(v);
                completePath.append("Greetings from Trip planner!\n").append("----------------\n");
                completePath.append("Your starting point is: ").append(k).append("\n").append("----------------\n");
                completePath.append("Estimated arriving time is: ");
                completePath.append(path.get(path.keySet().stream().reduce((first, second) -> second).get())).append("\n").append("----------------\n");
                completePath.append("Final destination is: ");
                completePath.append(path.keySet().stream().reduce((first, second) -> second).get()).append("\n").append("----------------\n");
            }



            if(path.size() > index.get()+1){
                nextStop = new ArrayList<>(path.keySet()).get(index.get()+1);
                //prevStop = new ArrayList<>(path.keySet()).get(index.get()-1);
            }


            if(nextStop != null && nextStop.getName().equals(k.getName())){
                completePath.append("Please change to tram with route number: ").append(nextStop.getRouteNr()).append("\n").append("----------------\n");
            }
            else {
                completePath.append("Arriving time of tram at stop:  ").append(k).append(" is: ").append(v).append("\n").append("----------------\n");
                if(index.get()+1!=path.size()) {
                    completePath.append("Tram from stop: ").append(k.getName()).append(" ").append("Will move out at: ").append(busStartTime).append("\n");
                    completePath.append("----------------\n");
                }
            }
            if(index.get()+1==path.size()){
                tripPlan.setEndTime(v);
                completePath.append("Thank you for using us!");
            }

            index.incrementAndGet();


        });
        tripPlan.setFullPath(completePath.toString());
        System.out.println(completePath.toString());

        return "a";

    }




}
