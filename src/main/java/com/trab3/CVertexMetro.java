package com.trab3;

import java.util.Collection;

public class CVertexMetro extends CVertex implements Metro.VertexMetro<String> {
    private Collection<String> lines;

    private int weight;

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
