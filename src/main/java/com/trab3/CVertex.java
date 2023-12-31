package com.trab3;

import java.util.Collection;

public class CVertex<T> implements Vertex<T> {
    private final T id;

    private int cost;

    private T parent;

    private final Collection<T> adjacents;

    public CVertex(T id, Collection<T> adjacents) {
        this.id = id;
        this.cost = 0;
        this.parent = null;
        this.adjacents = adjacents;
    }

    @Override
    public T getId() {
        return id;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public T getParent() {
        return parent;
    }

    @Override
    public Collection<T> getAdjacents() {
        return adjacents;
    }

    @Override
    public void set(int cost, T parent) {
        this.cost = cost;
        this.parent = parent;
    }
}