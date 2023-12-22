package com.trab3;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.*;
import java.util.function.Supplier;

import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class MetroWindow extends JFrame {
    private static final int HEIGHT = 300;

    private static final int WIDTH = 400;

    private static final JTextArea textArea = new JTextArea();

    private static final ImageIcon metroIcon = new ImageIcon(PathNormalize.parse("src/main/resources/logo.png"));

    MetroWindow() {
        super("Metro");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        Container content = getContentPane();
        JPanel textPanel = new JPanel(new GridLayout(1, 1));
        JScrollPane scrollPane = new JScrollPane(textArea);

        textArea.setEnabled(false);

        JMenuBar menuBar = new JMenuBar();

        JMenu lines = new JMenu("Lines");
        JMenu metroInfo = new JMenu("Metro Info");
        JMenu path = new JMenu("Path");

        JMenuItem noOfStations = getNoOfStations();
        JMenuItem stations = getStations();
        JMenuItem crossStations = getCrossStations();
        JMenuItem redLine = getRedLine();
        JMenuItem yellowLine = getYellowLine();
        JMenuItem greenLine = getGreenLine();
        JMenuItem blueLine = getBlueLine();
        JMenuItem pathfinder = getPathfinder();

        menuBar.add(metroInfo);
        menuBar.add(lines);
        menuBar.add(path);

        metroInfo.add(noOfStations);
        metroInfo.add(stations);
        metroInfo.add(crossStations);

        lines.add(redLine);
        lines.add(yellowLine);
        lines.add(greenLine);
        lines.add(blueLine);

        path.add(pathfinder);

        setJMenuBar(menuBar);

        textArea.setBorder(new TitledBorder("list"));
        textPanel.add(scrollPane);
        content.add(textPanel);

        setVisible(true);
    }

    private static JMenuItem getNoOfStations() {
        JMenuItem noOfStations = new JMenuItem("Number of stations per line");
        noOfStations.addActionListener(e -> {
            switchMode(noOfStations.getText());

            try (BufferedReader br = new BufferedReader(new FileReader(PathNormalize.parse("metro/metro.txt")))) {
                Map<String, List<String>> map = Metro.lines(br, (Supplier<List<String>>) ArrayList::new);
                StringBuilder sb = new StringBuilder();

                map.forEach((elm, list) -> sb.append(elm).append(" - ").append(list.size()).append("\n"));
                textArea.setText(sb.toString());
            }
            catch (Exception ex) {
                showMessageDialog(null, "Não foi possível visualizar as linhas", "Erro", JOptionPane.ERROR_MESSAGE, metroIcon);
            }
        });
        return noOfStations;
    }

    private static JMenuItem getStations() {
        JMenuItem stations = new JMenuItem("Stations");
        stations.addActionListener(e -> {
            switchMode(stations.getText());

            try (BufferedReader br = new BufferedReader(new FileReader(PathNormalize.parse("metro/metro.txt")))) {
                Map<String, List<String>> map = Metro.lines(br, (Supplier<List<String>>) ArrayList::new);
                Map<String, Collection<String>> adjacents = Metro.adjacents(map, (Supplier<Collection<String>>) HashSet::new);
                StringBuilder sb = new StringBuilder();

                List<String> sortedKeys = new ArrayList<>(adjacents.keySet());
                Collections.sort(sortedKeys);

                sortedKeys.forEach(key -> sb.append(key).append(" - ").append(adjacents.get(key).toString()).append("\n"));
                textArea.setText(sb.toString());
            }
            catch (Exception ex) {
                showMessageDialog(null, "Não foi possível ver as estações adjacentes", "Erro", JOptionPane.ERROR_MESSAGE, metroIcon);
            }
        });
        return stations;
    }

    private static JMenuItem getCrossStations() {
        JMenuItem crossStations = new JMenuItem("Crossing Stations");
        crossStations.addActionListener(e -> {
            switchMode(crossStations.getText());

            try (BufferedReader br = new BufferedReader(new FileReader(PathNormalize.parse("metro/metro.txt")))) {
                Map<String, List<String>> map = Metro.lines(br, (Supplier<List<String>>) ArrayList::new);
                Map<String, Set<String>> stationsMap = Metro.stations(map, HashMap::new, HashSet::new);
                StringBuilder sb = new StringBuilder();

                stationsMap.forEach((key, value) -> {
                    if (value.size() > 1)
                        sb.append(key).append(" - ").append(value).append("\n");
                });
                textArea.setText(sb.toString());
            }
            catch (Exception ex) {
                showMessageDialog(null, "Não foi possível ver as estações de correspondência", "Erro", JOptionPane.ERROR_MESSAGE, metroIcon);
            }
        });
        return crossStations;
    }

    private static JMenuItem getRedLine() {
        JMenuItem redLine = new JMenuItem("Vermelha");
        redLine.addActionListener(e -> getStationsFromLine(redLine));
        return redLine;
    }

    private static JMenuItem getYellowLine() {
        JMenuItem yellowLine = new JMenuItem("Amarela");
        yellowLine.addActionListener(e -> getStationsFromLine(yellowLine));
        return yellowLine;
    }

    private static JMenuItem getGreenLine() {
        JMenuItem greenLine = new JMenuItem("Verde");
        greenLine.addActionListener(e -> getStationsFromLine(greenLine));
        return greenLine;
    }

    public static JMenuItem getBlueLine() {
        JMenuItem blueLine = new JMenuItem("Azul");
        blueLine.addActionListener(e -> getStationsFromLine(blueLine));
        return blueLine;
    }

    private static void getStationsFromLine(JMenuItem menuItem) {
        switchMode(menuItem.getText());

        try (BufferedReader br = new BufferedReader(new FileReader(PathNormalize.parse("metro/metro.txt")))) {
            Map<String, List<String>> map = Metro.lines(br, (Supplier<List<String>>) ArrayList::new);
            StringBuilder sb = new StringBuilder();

            map.get(menuItem.getText()).forEach(elm -> sb.append(elm).append("\n"));
            textArea.setText(sb.toString());
        }
        catch (Exception ex) {
            showMessageDialog(null, "Não foi possível visualizar as estações da linha " + menuItem.getText(), "Erro", JOptionPane.ERROR_MESSAGE, metroIcon);
        }
    }

    private static JMenuItem getPathfinder() {
        JMenuItem pathfinder = new JMenuItem("Path Finder");
        pathfinder.addActionListener(e -> {
            try (BufferedReader br = new BufferedReader(new FileReader(PathNormalize.parse("metro/metro.txt")))) {
                Map<String, List<String>> map = Metro.lines(br, (Supplier<List<String>>) ArrayList::new);
                Map<String, Set<String>> stationsMap = Metro.stations(map, HashMap::new, HashSet::new);

                List<String> sortedKeys = new ArrayList<>(stationsMap.keySet());
                Collections.sort(sortedKeys);

                String origin = showInputDialog(null, "Estação de origem", "Origem", JOptionPane.QUESTION_MESSAGE, metroIcon, sortedKeys.toArray(), sortedKeys.toArray()[0]).toString();
                String destination = showInputDialog(null, "Estação de chegada", "Chegada", JOptionPane.QUESTION_MESSAGE, metroIcon, sortedKeys.toArray(), sortedKeys.toArray()[0]).toString();

                Map<String, CVertexMetro> graph = new HashMap<>();
                Map<String, List<String>> adjacentsMap = Metro.adjacents(map, ArrayList::new);

                stationsMap.forEach((s, strings) -> graph.put(s, new CVertexMetro(s, adjacentsMap.get(s), stationsMap.get(s), 2)));

                List<CVertexMetro> shortestPath = Dijkstra.dijkstra(graph, origin, destination, (s, d) -> 2);

                if (origin.equals(destination))
                    showMessageDialog(null, "A estação de origem e de destino são iguais.", "Path Finder", JOptionPane.ERROR_MESSAGE, metroIcon);
                else if (shortestPath == null)
                    throw new NullPointerException("Non-existent path");
                else
                    showMessageDialog(null, "Trajeto " + origin + " ↔ " + destination + "\n\n\t\t\t\t\t" + writeStations(shortestPath).replace("\n", "\n\t\t\t\t\t"), "Path Finder", JOptionPane.INFORMATION_MESSAGE, metroIcon);
            }
            catch (Exception ex) {
                showMessageDialog(null, "Não foi possível visualizar o caminho entre estas estações", "Erro", JOptionPane.ERROR_MESSAGE, metroIcon);
            }
        });
        return pathfinder;
    }

    private static void switchMode(String name) {
        textArea.setBorder(new TitledBorder(name));
        textArea.setText("");
    }

    private static String writeStations(List<CVertexMetro> path) {
        StringBuilder sb = new StringBuilder();

        for (CVertexMetro station : path)
            sb.append("\n").append(station.getId());

        return sb.toString().replaceFirst("\n", "");
    }

    public static void main(String[] args) {
        new MetroWindow();
    }
}