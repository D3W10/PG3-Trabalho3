package com.trab3;

import java.util.Collection;

public class CVertex implements Vertex<String> {
    private final String id;

    private int cost;

    private String parent;

    private Collection<String> adjacents;

    public CVertex(String id, Collection<String> adjacents) {
        this.id = id;
        this.cost = 0;
        this.parent = null;
        this.adjacents = adjacents;
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
        return adjacents;
    }

    @Override
    public void set(int cost, String parent) {
        this.cost = cost;
        this.parent = parent;
    }
}
