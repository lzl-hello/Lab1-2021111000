package com.lzl.lab;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WordGraphTest {

    private WordGraph wordGraph;

    @BeforeEach
    void buildGraph() {
        String txt = "explore strange new worlds to seek out new life and new lzl";
        wordGraph = new WordGraph();
        wordGraph.buildGraph(txt);

        // Print the adjacency list
        Map<String, List<String>> adjacencyList = wordGraph.getAdjacencyList();
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            String word = entry.getKey();
            List<String> adjacentWords = entry.getValue();
            for (String adjWord : adjacentWords) {
                System.out.println(word + " -> " + adjWord);
            }
        }
    }

    @Test
    void queryBridgeWords() {
        System.out.println(wordGraph.queryBridgeWords("explore", "new"));
        System.out.println(wordGraph.queryBridgeWords("to", "out"));
        System.out.println(wordGraph.queryBridgeWords("life", "lzl"));
        System.out.println(wordGraph.queryBridgeWords("out", "new"));
        System.out.println(wordGraph.queryBridgeWords("enter", "tje"));
        System.out.println(wordGraph.queryBridgeWords("enter", "new"));
        System.out.println(wordGraph.queryBridgeWords("lzl", "tje"));
    }

    @Test
    void generateNewText() {
        System.out.println(wordGraph.generateNewText("explore new worlds to seek new life new lzl"));
        System.out.println(wordGraph.generateNewText("explore strange new worlds to seek out new life and new lzl"));
    }

    @Test
    void calcShortestPath() {
        System.out.println(wordGraph.calcShortestPath("explore", "lzl"));
        System.out.println(wordGraph.calcShortestPath("explore", "new"));
        System.out.println(wordGraph.calcShortestPath("explore", "worlds"));
        System.out.println(wordGraph.calcShortestPath("explore", "strange"));
    }

    @Test
    void printShortestDistancesFromWord(){
        wordGraph.printShortestDistancesFromWord("worlds");
    }

    @Test
    void randomWalk() {
        System.out.println(wordGraph.randomWalk());
        System.out.println(wordGraph.randomWalk());
        System.out.println(wordGraph.randomWalk());

    }
}