/**
 * @file SubReddit.java
 * @author Jared Bentvelsen
 * @date 11/04/2020
 * @brief An ADT representing a Subreddit 
 */
package com.group1.app.graphTheory;

import java.util.ArrayList;
import java.util.HashMap;


public class SubReddit
{
    private String name; // The name of the SubReddit
    private ArrayList<Edge> adj; // An ArrayList (from Java.util) of Edge objects representing outgoing edges

    // A HashMap (from Java.util) to hold the connection strength from this SubReddit to another
    // Connection strength is defined as the number of parallel edges between this SubReddit and another
    private HashMap<String,Integer> connectionStrength; 

    /**
     * @brief Constructs a SubReddit object
     * @param name A String representing the name of the SubReddit
     */
    public SubReddit(String name){
        adj = new ArrayList<Edge>();
        connectionStrength = new HashMap<String, Integer>();
        this.name = name;
    }

    /**
     * @brief Adds an outgoing Edge from this SubReddit
     * @details The connectionStrength map is also updated
     * @param e An Edge object to be added to this SubReddit
     */
    public void addEdge(Edge e){
        String targetName = e.getTarget().toString();
        if(!connectionStrength.containsKey(targetName))
            connectionStrength.put(targetName,1);
        else connectionStrength.put(targetName, connectionStrength.get(targetName) + 1);
        adj.add(e);
    }

    /**
     * @brief Returns a reference to the ArrayList of outgoing edges
     * @details A deep copy is not made here due to the amount of new object creation and copying involved.
     *          This would not be feasible with large data sets.
     * @return
     */
    public ArrayList<Edge> getEdgeList(){
        return this.adj;
    }

    /**
     * @return A String representing the SubReddit name
     */
    public String toString(){
        return this.name;
    }

    /**
     * @brief gets the number of outgoing Edges from this SubReddit (including parallel edges)
     * @return An integer representing then number of outgoing Edges from this SubReddit
     */
    public int getNumConnects(){
        return this.adj.size();
    }

    /**
     * @brief Returns the connection strength (number of parallel Egdes from this SubReddit to another)
     * @param tar A String representing the name of the target SubReddit, to get the connection strength
     * @return An integer representing the connection strength.
     */
    public int getConnectionStrength(String tar){
        if(!connectionStrength.containsKey(tar))
            return 0;
        else return connectionStrength.get(tar);
    }
}