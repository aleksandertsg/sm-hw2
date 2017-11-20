package java;

import com.homework.trip.planner.domain.TripPlan;
import com.homework.trip.planner.utils.TripPlanBuilder;
import org.junit.Test;

class TripPlanBuilderTest {
  @Test
  void createTripPlan() {
    TripPlanBuilder tripPlanBuilder = new TripPlanBuilder();

    TripPlan tripPlan = tripPlanBuilder
      .createTripPlan("59.414665", "24.737735","59.450498", "24.710056", "14:03");

    assert tripPlan.getStartTime().equals("14:03");
  }

}