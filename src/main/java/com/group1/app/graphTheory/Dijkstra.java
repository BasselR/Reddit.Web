/**
 * @file Dijkstra.java
 * @author Jared Bentvelsen
 * @date 10/04/2020
 * @brief A class to generate the shortest path tree minimizing negative sentiment on connections.
 */
package com.group1.app.graphTheory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Dijkstra{

    
    private static HashMap<String,String> prev; // HashMap from Java.util to store the paths to each node in the SPT
    private static HashMap<String,Double> distance; // HashMap from Java.util to map a SubReddit name to its distance from source

    // A comparator to compare SubReddits based on distance, for use in the Priority Queue of Dijkstra's
    private static Comparator<SubReddit> subRedditComparator = new Comparator<SubReddit>(){
        public int compare(SubReddit r1, SubReddit r2){
            return Double.compare(distance.get(r1.toString()),distance.get(r2.toString()));
        }
    };
    /**
     * @brief Generates the Shortest Path Tree from a given source SubReddit, with negative sentiment as the edge weights.
     * @param source A SubReddit object from which to construct the Shortest Path Tree
     */

    public static void generateSPT(String src){
        prev = new HashMap<String, String>();
        distance = new HashMap<String,Double>();

        for(SubReddit r : Graph.getValueSet()) // Reset the distance values
            distance.put(r.toString(), Double.MAX_VALUE);

        SubReddit source = Graph.getSubRedditPointer(src);
        PriorityQueue<SubReddit> pQ = new PriorityQueue<>(subRedditComparator);
        
        distance.put(source.toString(),0.0); // Set distance to source as 0
        pQ.add(source); // Initialize the priority queue to start with the source node

        while(!pQ.isEmpty()){ // Continue building the SPT until the priority queue is empty
            SubReddit m = pQ.poll(); // Take the top of the priority queue

            for(Edge e : m.getEdgeList()){ // Relax all outgoing edges
                SubReddit s = e.getTarget();
                
                // Edge relax if getting to s through m is shorter than what we already know
                if(distance.get(m.toString()) + e.getProperty(Prop.NEG_SENT) < distance.get(s.toString())){
                    pQ.remove(m); // Remove the node we just finished with
                    prev.put(s.toString(),m.toString());
                    distance.put(s.toString(),distance.get(m.toString()) + e.getProperty(Prop.NEG_SENT));
                    pQ.add(s); // Add to the priority queue if edge was relaxed
                }
            }
        }
    }
    /**
     * @brief Finds a path of minimum negative sentiment from src to dest 
     * @param src A string representing the name of the source SubReddit
     * @param dest A string representing the name of the destination 
     * @return A list of the Subreddits that make up the minimal path
     * @throws IllegalArgumentException if there is no path from src to dest
     */
    public static ArrayList<SubReddit> findPath(String src, String dest){
        generateSPT(src); // Generate the SPT

        if(!prev.containsKey(dest))
            throw new IllegalArgumentException("No path from " + src + " to " + dest + " exists!");
        
        // Unload the path onto an ArrayList
        ArrayList<SubReddit> path = new ArrayList<SubReddit>();
        path.add(Graph.getSubRedditPointer(dest));
        while(!path.get(path.size() - 1).toString().equals(src)){
            path.add(Graph.getSubRedditPointer(prev.get(path.get(path.size() - 1).toString())));
        }

        Collections.reverse(path); // Reverse the path and return it

        return path;	
    }

    /**
     * @brief Gets the cost to visit a particular node in the SPT (if it is in the SPT)
     * @details Cost is total negative sentiment score across the edges
     * @param s A String representing the name of the of the SubReddit
     * @return A double representing the cost to the given SubReddit
     */
    public static double getCost(String s){
        if(distance.get(s) == null || distance.get(s) == Double.MAX_VALUE){
            throw new IllegalArgumentException("The " + s + " SubReddit is not in the SPT!");
        }
        return distance.get(s);
    }


}