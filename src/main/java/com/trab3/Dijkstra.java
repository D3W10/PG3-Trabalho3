package com.trab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Dijkstra {
    public static void main(String[] args) {
        Map<String, CVertexMetro> graph = new HashMap<>();
        Map<String, List<String>> lines;

        try (BufferedReader br = new BufferedReader(new FileReader(PathNormalize.parse("metro/metro.txt")))) {
            lines = Metro.lines(br, (Supplier<List<String>>) ArrayList::new);
        }
        catch (IOException e) {
            System.out.println("ERRO");
            return;
        }

        Map<String, List<String>> adjacents = Metro.adjacents(lines, ArrayList::new);
        Map<String, Set<String>> stations = Metro.stations(lines, HashMap::new, HashSet::new);

        stations.forEach((s, strings) -> {
            graph.put(s, new CVertexMetro(s, adjacents.get(s), stations.get(s), 2));
        });

        String startNode = "A";
        String destinationNode = "E";

        //List<CVertex> shortestPath = dijkstra(graph, startNode, destinationNode, Dijkstra::getWeightBetweenVertices);

        /*System.out.println("Shortest path from node " + startNode + " to node " + destinationNode + ": ");
        for (CVertex vertex : shortestPath) {
            System.out.println("Node: " + vertex.getId() + ", Cost: " + vertex.getCost() + ", Parent: " + vertex.getParent());
        }*/
    }

    public static <ID, V extends Vertex<ID>> List<V> dijkstra(Map<ID, V> graph, ID start, ID destination, BiFunction<V, V, Integer> getWeightBetweenVertices) {
        /*List<V> shortestPath = new ArrayList<>();
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

        return shortestPath;*/
        return null;
    }

    /*private static <V extends Metro.Vertex<String>> int getWeightBetweenVertices(V source, V destination) {
        return 2;
    }*/
}
