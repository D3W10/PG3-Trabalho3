package com.trab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Metro {
    /**
     * Produz um {@link Map} em que a chave é a identificação da linha e os dados associados é a sequência das estações que compõem a linha.
     *
     * @param br stream de leitura de onde devem ser lidas as linhas e respetivas estações
     * @param supL construtor de inicialização do objeto tipo {@link L}
     * @throws IOException se o ficheiro não existir, for uma pasta ou qualquer outra razão que impossibilita a abertura do ficheiro para leitura
     *
     * @return um {@link Map} com todas as linhas e suas estações
     */
    public static <L extends List<String>> Map<String, L> lines(BufferedReader br, Supplier<L> supL) throws IOException {
        String line;
        L stations = supL.get();
        Map<String, L> map = new HashMap<>();

        while ((line = br.readLine()) != null) {
            if (!Character.isDigit(line.charAt(0))) {
                stations = supL.get();
                map.put(line, stations);
            }
            else {
                stations.add(line.split(" - ")[1]);
            }
        }

        return map;
    }

    /**
     * Produza um {@link Map} em que a chave é o nome da estação e os valores associados são o conjunto de estações que lhes são adjacentes.
     *
     * @param lines um {@link Map} em que a chave é a identificação da linha e os dados são as estações que a compõem
     * @param supC construtor de inicialização do objeto tipo {@link C}
     *
     * @return um {@link Map} com as estações e respetivas estações adjacentes
     */
    public static <C extends Collection<String>> Map<String, C> adjacents(Map<String,? extends List<String>> lines, Supplier<C> supC) {
        Map<String, C> map = new HashMap<>();

        lines.forEach((BiConsumer<String, List<String>>) (name, stations) ->
                stations.forEach(station -> {
                    C adjacents = supC.get();
                    int index = stations.indexOf(station);

                    if (index > 0)
                        adjacents.add(stations.get(index - 1));
                    if (index < stations.size() - 1)
                        adjacents.add(stations.get(index + 1));

                    map.computeIfAbsent(station, k -> supC.get()).addAll(adjacents);
                }));

        return map;
    }

    /**
     * Produza um {@link Map} em que a chave é o nome da estação e os valores são o conjunto de linhas que passam nessa mesma estação.
     *
     * @param lines um {@link Map} em que a chave é a identificação da linha e os dados são as estações que a compõem
     * @param supM construtor de inicialização de objeto derivado de {@link Set}
     * @param supS construtor de inicialização do objeto tipo {@link S}
     *
     * @return um {@link Map} com as estações como chaves e linhas a quais pertencem como valores
     */
    public static <S extends Set<String>> Map<String, S> stations(Map<String, ? extends List<String>> lines, Supplier<? extends Map<String, S>> supM, Supplier<S> supS) {
        Map<String, S> map = supM.get();

        lines.forEach((BiConsumer<String, List<String>>) (name, stations) -> stations.forEach(station -> {
            if (map.containsKey(station))
                map.get(station).add(name);
            else {
                S newStat = supS.get();
                newStat.add(name);

                map.put(station, newStat);
            }
        }));

        return map;
    }

    public interface VertexMetro<ID> extends Vertex<ID> {
        Collection<String> getLines();

        int getWeight(ID v);
    }
}