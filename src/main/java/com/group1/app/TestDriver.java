package com.group1.app;

import java.io.IOException;
import java.util.ArrayList;

import com.group1.app.graphTheory.Dijkstra;
import com.group1.app.graphTheory.Graph;
import com.group1.app.graphTheory.SubReddit;
import com.group1.app.inputOutput.JSONWriter;
import com.group1.app.sorting.QuickSort;

public class TestDriver {
    public static void main(String[] args) throws IOException{
    	System.out.println("Sample run:");
    	System.out.println("Initializing graph from data...");
        Graph.initialize("DataBody.csv");
        System.out.println("Graph initialized!\n");
        System.out.println("Generating JSON file...");
        JSONWriter.writeJSON(10, 4);
        System.out.println("JSON file generated!\n");
        System.out.println("Finding paths between askcomputerscience and relationship_advice...");
        System.out.print("BFS Path: ");
        System.out.println(Graph.getPathBFS("askcomputerscience", "relationship_advice") + "\n");
        System.out.print("DFS Path: ");
        System.out.println(Graph.getPathDFS("askcomputerscience", "relationship_advice") + "\n");
        System.out.println("Generating a shortest path tree rooted at soccer...");
        Dijkstra.generateSPT("soccer");
        System.out.println("Finding shorest path (minimal negative sentiment rating from VADER) between soccer and kanye...\n");
        System.out.println("Path found to be: " + Dijkstra.findPath("soccer", "kanye") + " with a cumulative negative sentiment rating of " + Dijkstra.getCost("kanye") + "\n");
        System.out.println("Quicksorting SubReddits in descending order based on number of outgoing edges...");
        ArrayList<SubReddit> subReddits = Graph.getValueSet();
        QuickSort.quickSort(subReddits);
        System.out.println("The top ten SubReddits (of " + subReddits.size() + " unique SubReddits) with the most outgoing edges are...\n");
        for(int i = 0; i < 10; i++)
        	System.out.println(subReddits.get(i).toString() + " with " + subReddits.get(i).getNumConnects() + " outgoing edges");
        System.out.println("\n-- Make sure to use the generated JSON file and check out the front end at https://basselr.github.io/Reddit.Web/ --");
        	

        
        
        
    }
}