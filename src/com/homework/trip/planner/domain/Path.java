package com.homework.trip.planner.domain;

import java.time.LocalTime;

public class Path {

    private Stop stop;
    private Path path;
    private LocalTime time;


    public Path(Stop stop, Path path, LocalTime time) {
        this.stop = stop;
        this.path = path;
        this.time = time;
    }


    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }





    @Override
    public String toString(){
        if (path == null){
            return "Start: "+time+" from: "+stop.toString()+"\n";
        }
        return path.toString() + " Arriving at:  " + time +   " at stop " +stop.toString() + "\n";
    }


}
