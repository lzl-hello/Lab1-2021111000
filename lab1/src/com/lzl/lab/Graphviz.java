package com.lzl.lab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graphviz {

    private static final String TEMP_DIR = "C:\\Users\\lzl\\IdeaProjects\\lab1\\temp";
    private static final String DOT_EXECUTABLE_PATH = "C:\\Program Files\\Graphviz\\bin\\dot.exe";

    public static void generateGraph(String dotSource, String outputFilePath, String imageFormat) throws IOException {
        File dotFile = writeDotSourceToFile(dotSource);
        if (dotFile != null) {
//            System.out.println("DOT file generated successfully: " + dotFile.getAbsolutePath());
            convertDotToImage(dotFile, outputFilePath, imageFormat);
//            dotFile.delete(); // Delete the temporary DOT file
        } else {
            System.err.println("Error: Failed to generate DOT file.");
        }
    }

    private static File writeDotSourceToFile(String dotSource) throws IOException {
//        File tempDotFile = Files.createTempFile(Paths.get(TEMP_DIR), "graph_", ".dot").toFile();
        File tempDotFile = Files.createTempFile(Paths.get(TEMP_DIR), "graph", ".dot").toFile();

        try (FileWriter writer = new FileWriter(tempDotFile)) {
            writer.write(dotSource);
        }
//        System.out.println("DOT file path: " + tempDotFile.getAbsolutePath());
        return tempDotFile;
    }


    private static void convertDotToImage(File dotFile, String outputFilePath, String imageFormat) throws IOException {
        String[] command = {DOT_EXECUTABLE_PATH, "-T" + imageFormat, dotFile.getAbsolutePath(), "-o", outputFilePath};
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error: Failed to convert DOT to image. Exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error: Interrupted while converting DOT to image.");
        }
    }

    public static void showDirectedGraph(WordGraph wordGraph) {
        Map<String, Integer> edgeWeights = wordGraph.getEdgeWeights();
        String dotSource = generateDotSource(wordGraph.getAdjacencyList(), edgeWeights);

//        System.out.println(dotSource);

        String outputFilePath = "./picture/graph.png";
        String imageFormat = "png";
        try {
//            System.out.println("Generating graph...");

            Graphviz.generateGraph(dotSource, outputFilePath, imageFormat);
            System.out.println("Graph generated successfully: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error generating graph: " + e.getMessage());
        }
    }


    private static String generateDotSource(Map<String, List<String>> adjacencyList, Map<String, Integer> edgeWeights) {
        StringBuilder dotSource = new StringBuilder();
        dotSource.append("digraph G {\n");

        // Add nodes with corresponding labels
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            String node = entry.getKey();
            dotSource.append("\t\"" + node + "\" [label=\"" + node + "\"];\n");
        }

        // Add edges with corresponding weights
        Set<String> processedEdges = new HashSet<>(); // Keep track of processed edges to avoid duplicates
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            String node = entry.getKey();
            List<String> adjacentNodes = entry.getValue();
            for (String adjNode : adjacentNodes) {
                String edge = node + " -> " + adjNode;
                int weight = edgeWeights.getOrDefault(edge, 1); // Get weight from edgeWeights map
                if (!processedEdges.contains(edge)) {
                    dotSource.append("\t\"" + node + "\" -> \"" + adjNode + "\" [label=\"" + weight + "\"];\n");
                    processedEdges.add(edge);
                }
            }
        }

        dotSource.append("}");
        return dotSource.toString();
    }

    public static void generateGraphWithShortestPath(WordGraph wordGraph, String shortestPath, String outputFileName, String imageFormat) {
        Map<String, Integer> edgeWeights = wordGraph.getEdgeWeights();
        String dotSource = generateDotSourceWithShortestPath(wordGraph.getAdjacencyList(), edgeWeights, shortestPath);
        String outputFilePath = "./picture/"+ outputFileName;
        try {
            generateGraph(dotSource, outputFilePath, imageFormat);
            System.out.println("Graph with shortest path generated successfully: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error generating graph with shortest path: " + e.getMessage());
        }
    }

    private static String generateDotSourceWithShortestPath(Map<String, List<String>> adjacencyList, Map<String, Integer> edgeWeights, String shortestPath) {
        StringBuilder dotSource = new StringBuilder();
        dotSource.append("digraph G {\n");

        // Add nodes with corresponding labels
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            String node = entry.getKey();
            dotSource.append("\t\"" + node + "\" [label=\"" + node + "\"];\n");
        }

        // Add edges with corresponding weights, highlighting the edges in the shortest path
        Set<String> processedEdges = new HashSet<>(); // Keep track of processed edges to avoid duplicates
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            String node = entry.getKey();
            List<String> adjacentNodes = entry.getValue();
            for (String adjNode : adjacentNodes) {
                String edge = node + " -> " + adjNode;
                int weight = edgeWeights.getOrDefault(edge, 1); // Get weight from edgeWeights map
                if (!processedEdges.contains(edge)) {
                    if (shortestPath.contains(edge)) {
                        dotSource.append("\t\"" + node + "\" -> \"" + adjNode + "\" [label=\"" + weight + "\", color=\"red\"];\n");
                    } else {
                        dotSource.append("\t\"" + node + "\" -> \"" + adjNode + "\" [label=\"" + weight + "\"];\n");
                    }
                    processedEdges.add(edge);
                }
            }
        }

        dotSource.append("}");
        return dotSource.toString();
    }

}

