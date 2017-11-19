package com.homework.trip.planner.domain;

public class Leg {
    private Stop from;
    private Stop to;
    private int duration;

    public Leg(Stop from, Stop to, int duration) {
        this.from = from;
        this.to = to;
        this.duration = duration;
    }

    public Stop getFrom() {
        return from;
    }

    public void setFrom(Stop from) {
        this.from = from;
    }

    public Stop getTo() {
        return to;
    }

    public void setTo(Stop to) {
        this.to = to;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Leg{" +
                "from=" + from +
                ", to=" + to +
                ", duration=" + duration +
                '}';
    }
}
