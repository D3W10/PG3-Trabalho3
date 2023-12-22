package com.trab3;

import java.util.*;
import java.util.function.BiFunction;

public class Dijkstra {
    /**
     * Aplica o algoritmo de Dijkstra para os valores passados.
     *
     * @param graph o mapa de grafos
     * @param start o id do ponto inicial
     * @param destination o id do ponto final
     * @param getWeightBetweenVertices um método que devolva o peso da aresta entre 2 grafos
     *
     * @return uma {@link List} dos grafos que compõem o melhor caminho
     */
    public static <ID, V extends Vertex<ID>> List<V> dijkstra(Map<ID, V> graph, ID start, ID destination, BiFunction<V, V, Integer> getWeightBetweenVertices) {
        List<V> s = new ArrayList<>();
        PriorityQueue<V> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getCost));

        for (ID id : graph.keySet()) {
            V vertex = graph.get(id);

            if (id.equals(start))
                vertex.set(0, null);
            else
                vertex.set(Integer.MAX_VALUE, null);

            priorityQueue.add(vertex);
        }

        while (!priorityQueue.isEmpty()) {
            V v = priorityQueue.poll();
            s.add(v);

            if (v.getId().equals(destination))
                return getSmallerPath(s, graph);

            for (ID adjacent : v.getAdjacents()) {
                V u = graph.get(adjacent);

                int newCost = v.getCost() + getWeightBetweenVertices.apply(v, u);
                if (newCost < u.getCost()) {
                    u.set(newCost, v.getId());
                    priorityQueue.remove(u);
                    priorityQueue.add(u);
                }
            }
        }

        return null;
    }

    /**
     * Obtém o melhor caminho dentro de uma lista com possíveis caminhos até ao destino.
     *
     * @param list a lista dos possíveis caminhos
     * @param graph o mapa de grafos
     *
     * @return uma {@link List} com o melhor caminho entre os grafos
     */
    private static <ID, V extends Vertex<ID>> List<V> getSmallerPath(List<V> list, Map<ID, V> graph) {
        List<V> sPath = new ArrayList<>();
        list = list.reversed();
        V parent = list.getFirst();
        sPath.add(parent);

        while ((parent = graph.get(parent.getParent())) != null)
            sPath.add(parent);

        return sPath.reversed();
    }
}