package com.homework.trip.planner;

import com.homework.trip.planner.domain.TripPlan;
import com.homework.trip.planner.utils.TripPlanBuilder;

import java.io.Console;

public class App {

  public static void main(String[] args) {
    System.out.println("HELLO WORLD");

    /*
    Console console = System.console();
        if (console == null) {
            System.out.println("No console: non-interactive mode!");
            System.exit(0);
        }
    */

    String time = "14:03";

    TripPlanBuilder tripPlanBuilder = new TripPlanBuilder();

    TripPlan tripPlan = tripPlanBuilder
      .createTripPlan(59.417229f, 24.797322f, 59.421008f, 24.739687f, time);

    System.out.println(tripPlan.getFullPath());

  }
}














