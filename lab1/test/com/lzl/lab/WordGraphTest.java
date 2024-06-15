/**
 * @Date: 2024-06-13
 * @Info: 1. 白盒测试
 *        2. 已知wordgraph类中的方法的具体实现，测试能否得到预期输出
 *        3. calcShortestPath方法
 *        4. queryBridgeWords方法
 *        5. generateNewText方法
 *        6. randomWalk方法
 */
package com.lzl.lab;

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
    }

    @Test
    void queryBridgeWords() {
        assertEquals("The bridge words from explore to new are: strange", wordGraph.queryBridgeWords("explore", "new"));
        assertEquals("No bridge words from to to new!", wordGraph.queryBridgeWords("to", "new"));
        assertEquals("Neither enter nor tje is in the graph!", wordGraph.queryBridgeWords("enter", "tje"));
        assertEquals("No enter in the graph!", wordGraph.queryBridgeWords("enter", "new"));
        assertEquals("No tje in the graph!", wordGraph.queryBridgeWords("lzl", "tje"));
    }

    @Test
    void generateNewText() {
        assertEquals("explore strange new worlds to seek out new life and new lzl",
                wordGraph.generateNewText("explore new worlds to seek new life new lzl"));
        assertEquals("explore strange new worlds to seek out new life and new lzl",
                wordGraph.generateNewText("explore strange new worlds to seek out new life and new lzl"));
    }

    @Test
    void calcShortestPath() {
        assertEquals("The shortest path(s) from explore to lzl with length 3 are:\n" +
                "explore -> strange -> new -> lzl\n", wordGraph.calcShortestPath("explore", "lzl"));
        assertEquals("The shortest path(s) from explore to new with length 2 are:\n" +
                "explore -> strange -> new\n", wordGraph.calcShortestPath("explore", "new"));
        assertEquals("The shortest path(s) from explore to worlds with length 3 are:\n" +
                "explore -> strange -> new -> worlds\n", wordGraph.calcShortestPath("explore", "worlds"));
    }


    @Test
    void randomWalk() {
        String walk1 = wordGraph.randomWalk();
        String walk2 = wordGraph.randomWalk();
        String walk3 = wordGraph.randomWalk();

        // Assuming randomWalk returns a non-empty string
        assertNotNull(walk1);
        assertFalse(walk1.isEmpty());

        assertNotNull(walk2);
        assertFalse(walk2.isEmpty());

        assertNotNull(walk3);
        assertFalse(walk3.isEmpty());
    }
}
