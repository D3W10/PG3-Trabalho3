package com.trab3;

import java.util.Collection;

public class CVertexMetro extends CVertex<String> implements Metro.VertexMetro<String> {
    private final Collection<String> lines;

    private final int weight;

    public CVertexMetro(String id, Collection<String> adjacents, Collection<String> lines, int weight) {
        super(id, adjacents);
        this.lines = lines;
        this.weight = weight;
    }

    @Override
    public Collection<String> getLines() {
        return lines;
    }

    @Override
    public int getWeight(String v) {
        return weight;
    }
}