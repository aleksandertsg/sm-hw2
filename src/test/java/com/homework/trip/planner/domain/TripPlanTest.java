package com.homework.trip.planner.domain;

import com.homework.trip.planner.utils.TripPlanBuilder;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

public class TripPlanTest {

    @Test
    public void tripPlanTest(){
        TripPlanBuilder tripPlanBuilder = new TripPlanBuilder();

        TripPlan tripPlan = tripPlanBuilder
                .createTripPlan("59.414665", "24.737735","59.450498", "24.710056", "14:00");

        String start =  tripPlan.getStops().get(0).getName();
        String end = tripPlan.getStops().get(tripPlan.getStops().size()-1).getName();

        assertEquals(tripPlan.getStartTime(),LocalTime.parse("14:00"));
        assertEquals(tripPlan.getEndTime(),LocalTime.parse("14:27"));
        assertEquals(start, "Tallinn-Väike");
        assertEquals(end,"Angerja");

    }

}