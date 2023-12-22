package com.trab3;

import java.util.Collection;

public interface Vertex<ID> {
    ID getId();

    int getCost();

    ID getParent();

    Collection<ID> getAdjacents();

    void set(int cost, ID parent);

    int hashCode();

    boolean equals(Object o);
}