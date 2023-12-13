package com.trab3;

import java.util.*;
import java.util.function.BiFunction;

public class Dijkstra {
    public static void main(String[] args) {
        // Example graph represented as an adjacency list
        Map<String, CVertex> graph = new HashMap<>();
        graph.put("A", new CVertex("A"));
        graph.put("B", new CVertex("B"));
        graph.put("C", new CVertex("C"));
        graph.put("D", new CVertex("D"));
        graph.put("E", new CVertex("E"));
        graph.put("F", new CVertex("F"));

        graph.get("A").addAdjacent("B", 2);
        graph.get("A").addAdjacent("C", 2);
        graph.get("B").addAdjacent("F", 2);
        graph.get("F").addAdjacent("D", 2);
        graph.get("C").addAdjacent("D", 2);
        graph.get("D").addAdjacent("E", 2);


        String startNode = "A";
        String destinationNode = "E";

        List<CVertex> shortestPath = dijkstra(graph, startNode, destinationNode, Dijkstra::getWeightBetweenVertices);

        // Print the shortest path from the start node to the destination node
        System.out.println("Shortest path from node " + startNode + " to node " + destinationNode + ": ");
        for (CVertex vertex : shortestPath) {
            System.out.println("Node: " + vertex.getId() + ", Cost: " + vertex.getCost() + ", Parent: " + vertex.getParent());
        }
    }

    public static <ID, V extends Metro.Vertex<ID>> List<V> dijkstra(Map<ID, V> graph, ID start, ID destination, BiFunction<V, V, Integer> getWeightBetweenVertices) {
        List<V> shortestPath = new ArrayList<>();
        PriorityQueue<V> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Metro.Vertex::getCost));

        for (ID id : graph.keySet()) {
            V vertex = graph.get(id);
            if (id.equals(start)) {
                vertex.set(0, null);
            } else {
                vertex.set(Integer.MAX_VALUE, null);
            }
            priorityQueue.add(vertex);
        }

        while (!priorityQueue.isEmpty()) {
            V current = priorityQueue.poll();

            if (current.getId().equals(destination)) {
                // Destination reached, reconstruct the path
                while (current != null) {
                    shortestPath.add(current);
                    current = graph.get(current.getParent());
                }
                Collections.reverse(shortestPath);
                break;
            }

            for (ID neighborId : current.getAdjacents()) {
                V neighbor = graph.get(neighborId);
                int newDistance = current.getCost() + getWeightBetweenVertices.apply(current, neighbor);

                if (newDistance < neighbor.getCost()) {
                    priorityQueue.remove(neighbor);
                    neighbor.set(newDistance, current.getId());
                    priorityQueue.add(neighbor);
                }
            }
        }

        return shortestPath;
    }

    private static <V extends Metro.Vertex<String>> int getWeightBetweenVertices(V source, V destination) {
        return source.getAdjacents().contains(destination.getId()) ? source.getAdjacents().size() : Integer.MAX_VALUE;
    }
}
