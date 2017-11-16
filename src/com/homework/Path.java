package com.homework;

public class Path {

    public final Stop stop;
    public final Path next;

    public Path(Stop stop, Path next) {
        this.stop = stop;
        this.next = next;
    }

    @Override
    public String toString(){
        if (next == null){
            return stop.toString();
        }
        return next.toString() + " --- " + stop.toString();
    }
}