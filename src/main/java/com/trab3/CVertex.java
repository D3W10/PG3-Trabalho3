package com.trab3;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CVertex implements Metro.Vertex<String> {

    private final String id;
    private int cost;
    private String parent;
    private Map<String, Integer> adjacents;

    public CVertex(String id) {
        this.id = id;
        this.adjacents = new HashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getParent() {
        return parent;
    }

    @Override
    public Collection<String> getAdjacents() {
        return adjacents.keySet();
    }

    @Override
    public void set(int cost, String parent) {
        this.cost = cost;
        this.parent = parent;
    }





    // Carlos




    public void addAdjacent(String neighbor, int weight) {
        adjacents.put(neighbor, weight);
    }
}
