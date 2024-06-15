/**
 * @Date: 2024-06-13
 * @Info: 1. 黑盒测试
 *        2. 不知到wordgraph类中的方法的具体实现，只知道一个规格文档，输入输出的关系
 *        3. calcShortestPath方法
 *        4. queryBridgeWords方法
 *        5. generateNewText方法
 *        6. randomWalk方法
 */
package com.lzl.lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordGraphBlackBoxTest {

    private WordGraph wordGraph;

    @BeforeEach
    void setup() {
        wordGraph = new WordGraph();
        wordGraph.buildGraph("explore strange new worlds to seek out new life and new lzl");
    }

    @Test
    void testQueryBridgeWords() {
        // 正常情况
        assertEquals("The bridge words from explore to new are: strange", wordGraph.queryBridgeWords("explore", "new"));
        assertEquals("No bridge words from to to new!", wordGraph.queryBridgeWords("to", "new"));

        // 异常情况：测试单词不在图中
        assertEquals("Neither enter nor tje is in the graph!", wordGraph.queryBridgeWords("enter", "tje"));
        assertEquals("No enter in the graph!", wordGraph.queryBridgeWords("enter", "new"));
        assertEquals("No tje in the graph!", wordGraph.queryBridgeWords("lzl", "tje"));

        // 边界情况：测试单词相邻但没有桥接词
        assertEquals("No bridge words from life to lzl!", wordGraph.queryBridgeWords("life", "lzl"));
        assertEquals("No bridge words from out to new!", wordGraph.queryBridgeWords("out", "new"));
    }

    @Test
    void testGenerateNewText() {

        // 正常情况：使用桥接词生成新文本
        assertEquals("explore strange new worlds to seek out new life and new lzl",
                wordGraph.generateNewText("explore new worlds to seek new life new lzl"));
        assertEquals("explore strange new worlds to seek out new life and new lzl",
                wordGraph.generateNewText("explore strange new worlds to seek out new life and new lzl"));
        // 异常情况：没有桥接词
        assertEquals("seek out new life",wordGraph.generateNewText("seek out new life"));
    }

    @Test
    void testCalcShortestPath() {

        // 正常情况：测试已知单词之间的最短路径
        assertEquals("The shortest path(s) from explore to lzl with length 3 are:\n" +
                "explore -> strange -> new -> lzl\n", wordGraph.calcShortestPath("explore", "lzl"));

        assertEquals("The shortest path(s) from explore to new with length 2 are:\n" +
                "explore -> strange -> new\n", wordGraph.calcShortestPath("explore", "new"));

        //无最短路径
        // 异常情况：测试单词不在图中
        assertEquals("No word2 in the graph!", wordGraph.calcShortestPath("explore", "unknown"));
        assertEquals("No word1 in the graph!", wordGraph.calcShortestPath("unknown", "explore"));
    }
    @Test
    void testRandomWalk() {

        // 多次运行随机游走，确保结果非空
        for (int i = 0; i < 10; i++) { // Run multiple times to ensure randomness
            String walk = wordGraph.randomWalk();
            assertNotNull(walk);
            assertFalse(walk.isEmpty());
        }
    }

}
