package com.homework.trip.planner.domain;

import com.homework.trip.planner.utils.TripPlanBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

public class TripPlanTest {

    @Test
    public void tryTest(){
        TripPlanBuilder tripPlanBuilder = new TripPlanBuilder();

        TripPlan tripPlan = tripPlanBuilder
                .createTripPlan("59.414665", "24.737735","59.450498", "24.710056", "14:03");

        assertEquals(tripPlan.getStartTime(),LocalTime.parse("14:03"));

    }

}