package com.homework.trip.planner;

import com.homework.trip.planner.domain.TripPlan;
import com.homework.trip.planner.utils.TripPlanBuilder;

import java.io.Console;

public class App {

  public static void main(String[] args) {

    Console console = System.console();
    if (console == null) {
        System.out.println("No console: non-interactive mode!");
        System.exit(0);
    }

    String fromLatCoord = console.readLine("Insert starting lat coorinate: ");

    String fromLonCoord = console.readLine("Insert starting lon coordinate: ");

    String toLatCoord = console.readLine("Insert destination lat coorinate: ");

    String toLonCoord = console.readLine("Insert destination lon coordinate: ");

    String time = console.readLine("Insert starting time(hh:mm): ");



    TripPlan tripPlan = TripPlanBuilder
      .createTripPlan(fromLatCoord,fromLonCoord,toLatCoord,toLonCoord, time);

    //      .createTripPlan("59.414665", "24.737735","59.450498", "24.710056", time);

    System.out.println(tripPlan.getFullPath());


  }
}














