package com.homework;

import java.util.*;

public class GraphImpl{
    private Map<Stop, Map<Stop, Integer>> graph = new HashMap<>();

    public boolean addVertex(Stop vertex) {
        if (graph.containsKey(vertex)){
            return false;
        }
        else{
            Map<Stop, Integer> map = new HashMap<>();
            graph.put(vertex, map);
            return true;
        }


    }

    public boolean removeVertex(Stop vertex) {
        if (graph.containsKey(vertex)){
            Set<Stop> keySet = graph.get(vertex).keySet();
            for (Stop key : keySet){
                graph.get(key).remove(vertex);
            }
            graph.remove(vertex);
            return true;
        }
        else{
            return false;
        }
    }

    public Set<Stop> getVertices() {
        return graph.keySet();
    }

    public boolean addEdge(Stop one, Stop other, Integer edge) {
        if (graph.containsKey(one)){
            Map<Stop,Integer> map = new HashMap<>();
            map.put(other, edge);
            if (graph.get(one).containsKey(other)){
                return false;
            }
            else{
                graph.get(one).put(other, edge);
/*                graph.get(other).put(one, edge);*/
                return true;
            }
        }
        else{
            return false;
        }
    }

    public boolean removeEdge(Stop one, Stop other) {
        if (graph.containsKey(one) && graph.containsKey(other)){
            if (graph.get(one).containsKey(other)){
                graph.get(one).remove(other);
                graph.get(other).remove(one);
                return true;
            }
            else{
                return false;
            }
        }

        else{
            return false;
        }
    }

    public Set<Stop> neighborsOf(Stop vertex) {

        if (graph.containsKey(vertex)){
            return graph.get(vertex).keySet();
        }

        else{
            return new HashSet<>();
        }

    }

    public Integer getEdge(Stop one, Stop other) {
        if (graph.containsKey(one)){
            if (graph.get(one).containsKey(other)){
                return graph.get(one).get(other);
            }
        }
        if(graph.containsKey(other)){
            if (graph.get(other).containsKey(one)){
                return graph.get(other).get(one);
            }
        }
        // This edge doesn't exist
        return null;

    }

}
