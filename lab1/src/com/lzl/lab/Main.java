/**
 * @Author: LZL
 * @Date: 2024/5/14
 * @编译命令：javac -encoding UTF-8 -d out src/com/lzl/lab/*.java
 * @运行命令：java -cp out com.lzl.lab.Main
 */

package com.lzl.lab;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

//      TODO: 从命令行接受参数，找不到生成的.dot文件;还不能写中文，什么鬼
//            已解决：-encoding UTF-8;dot文件是因为后边写了个删除临时文件的谜之操作

        String filePath = "./src/test.txt";
        // Create WordGraph instance
        WordGraph wordGraph = new WordGraph();


        try {
            String processedContent = readFileContent(filePath);
            wordGraph.buildGraph(processedContent);
            Graphviz.showDirectedGraph(wordGraph);

            boolean exit = false;
            Scanner scanner = new Scanner(System.in);
            while (!exit) {
                System.out.println("Choose an option:");
                System.out.println("1. Query Bridge Words");
                System.out.println("2. Generate New Text");
                System.out.println("3. Calculate Shortest Path");
                System.out.println("4. Calculate Shortest Path from one word");
                System.out.println("5. Random Walk");
                System.out.println("6. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        queryBridgeWords(wordGraph, scanner);
                        break;
                    case 2:
                        generateNewText(wordGraph, scanner);
                        break;
                    case 3:
                        calculateShortestPath(wordGraph, scanner);
                        break;
                    case 4:
                        printShortestDistancesFromWord(wordGraph, scanner);
                        break;
                    case 5:
                        randomWalk(wordGraph, scanner);
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

    }

    private static String readFileContent(String filePath) throws IOException {
        // Read the file content
            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

//         Process the content: replace newline and punctuation with space, ignore non-alphabetic characters
            String processedContent = content.toString().replaceAll("[^a-zA-Z\\s]", " ").replaceAll("\\s+", " ").toLowerCase();

        // Process the content: replace newline and punctuation with space, ignore non-alphabetic characters
        return processedContent;
    }

    private static void queryBridgeWords(WordGraph wordGraph, Scanner scanner) {
        System.out.println("Enter two words to find bridge words:");
        String word1 = scanner.nextLine();
        String word2 = scanner.nextLine();
        String bridgeWords = wordGraph.queryBridgeWords(word1, word2);
        System.out.println(bridgeWords);
//        System.out.println("Bridge words between " + word1 + " and " + word2 + ": " + bridgeWords);
    }

    private static void generateNewText(WordGraph wordGraph, Scanner scanner) {
        System.out.println("Enter the source text:");
        String sourceText = scanner.nextLine();
//        String sourceText = "explore new worlds to seek new life new lzl";
        String newText = wordGraph.generateNewText(sourceText);
        System.out.println(newText);

//        System.out.println(wordGraph.generateNewText("explore new worlds to seek new life new lzl"));
    }

    private static void calculateShortestPath(WordGraph wordGraph, Scanner scanner) {
        System.out.println("Enter two words to calculate shortest path:");
        String word1 = scanner.nextLine();
        String word2 = scanner.nextLine();
        String shortestPath = wordGraph.calcShortestPath(word1, word2);
        System.out.println(shortestPath);
        String outputFileName = word1 + "_to_" + word2 + ".png";
        Graphviz.generateGraphWithShortestPath(wordGraph, shortestPath, outputFileName, "png");
//        System.out.println("Shortest path between " + word1 + " and " + word2 + ": " + shortestPath);
    }

    private static void printShortestDistancesFromWord(WordGraph wordGraph, Scanner scanner){
        System.out.println("Enter one word to calculate shortest path:");
        String word = scanner.nextLine();
        wordGraph.printShortestDistancesFromWord(word);
    }

    private static void randomWalk(WordGraph wordGraph, Scanner scanner) {
        System.out.println("Starting random walk...");
        String randomWalkResult = wordGraph.randomWalk();
        System.out.println("Random walk result: " + randomWalkResult);
        wordGraph.writeToFile("./src/random_walk_result.txt", randomWalkResult);
    }

}
