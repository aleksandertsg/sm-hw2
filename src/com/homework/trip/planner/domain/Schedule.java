package com.homework.trip.planner.domain;

import java.time.LocalTime;
import java.util.List;

public class Schedule {


    private List<LocalTime> times;


    public Schedule(List<LocalTime> times) {
        this.times = times;
    }

    public List<LocalTime> getTimes() {
        return times;
    }

    public void setTimes(List<LocalTime> times) {
        this.times = times;
    }


    @Override
    public String toString() {
        return "Schedule{" +
                "times=" + times +
                '}';
    }
}
