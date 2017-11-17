package com.homework;

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

    @Override
    public String toString(){
        if (path == null){
            return "Start: "+time+" from: "+stop.toString();
        }
        return path.toString() + " Arriving at:  " + time +   " at stop " +stop.toString();
    }
}