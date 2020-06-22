/**
 * @file Graph.java
 * @author Jared Bentvelsen
 * @date 10/04/2020
 * @brief A class to read input data and represent the graph.
 */
package com.group1.app.graphTheory;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.group1.app.inputOutput.InputReader;
import com.group1.app.inputOutput.OutputObject;

public class Graph {

	private static HashMap<String,SubReddit> objectMapper = new HashMap<String, SubReddit>();

	/**
	 * Method to read in connections from file
	 * @param fileName The name of the file which holds the connections, ie edges that constitutes the graph
	 * @throws IOException If the specified file cannot be found
	 */
    public static void initialize(String inputFileName){
        ArrayList<OutputObject> output = null;
        InputReader iR = new InputReader();
        try{
            output = iR.readFile(inputFileName);
        }
        catch(IOException e){
        	/* Exception is thrown within InputReader.readFile */
            e.printStackTrace();
		}
		
        for(OutputObject o : output){ // Iterate over the output objects
            String src = o.getSource();
			String dest = o.getTarget();
		
			// Update the obejct Mapper if we encounter SubReddits not seen before
        	if(!objectMapper.containsKey(src))
				objectMapper.put(src, new SubReddit(src));
				
            if(!objectMapper.containsKey(dest))
				objectMapper.put(dest, new SubReddit(dest));
			
			// Create an Edge object
			Edge e = new Edge(objectMapper.get(src), objectMapper.get(dest), o.getDate(),o.getProperties()); //Make a directed edge
			
			// Add the Edge to the source node of the connection
            objectMapper.get(src).addEdge(e);
        }        
    }

	/**
	 * Computes a path between a specified source and specified destination node, using a Breadth First Search
	 * @param source String representing the source node
	 * @param dest String representing the destination node
	 * @return A deque of strings representing the (backwards) path
	 */
    public static Deque<String> getPathBFS(String source, String dest) {
		HashSet<String> visited = new HashSet<String>(); // HashSet to keep track of visited nodes
		HashMap<String,String> prev = new HashMap<String, String>(); // HashMap to track which nodes visit which, to form the path
		visited.add(source); // Visit the source
		/* Standard BFS */ 
		Queue<String> todo = new LinkedList<String>(); // Queue to keep nodes still left to visit
		todo.add(source);
		while(!todo.isEmpty()) {
			SubReddit s = objectMapper.get(todo.poll()); // Parent pulled from top of the queue

			for(Edge e : s.getEdgeList()) { // Iterate over neighbours
				// If not visited, visit, add to queue, and add parent as previous node for path tracking
				if(!visited.contains(e.getTarget().toString())){
					visited.add(e.getTarget().toString());
					todo.add(e.getTarget().toString());
					prev.put(e.getTarget().toString(),s.toString());
				}
			}
		}
		// If the destination node is not visited
		if(!visited.contains(dest)) {
			System.out.println("No path from " + source + " to " + dest + " exists!");
			return null;
		}
		
		// Track back from the destination node to the source node, storing the path on the stack
		// A stack is used since the path can only be retrieved starting from the destination, so it needs to be reversed
		// When the path is unloaded from the stack, it is in the correct order
		Deque<String> path = new ArrayDeque<String>();
		path.addFirst(dest);
		while(!path.peek().equals(source)) {
			path.addFirst(prev.get(path.peek()));
		}
		return path;	
    }
	
	/**
	 * Computes a path between a specified source and specified destination node, using a Depth First Search
	 * @param source String representing source node to start the search
	 * @param visited HashSet to track visited nodes
	 * @param prev HashMap to map the path during the source, tracking parent nodes
	 */
    public static Deque<String> getPathDFS(String source, String dest) {
		HashSet<String> visited = new HashSet<String>(); // HashSet to keep track of visited nodes
		HashMap<String,String> prev = new HashMap<String, String>(); // HashMap to track which nodes visit which, to form the path
		visited.add(source); // Visit the source
		/* Standard BFS */ 
		Stack<String> todo = new Stack<String>(); // Queue to keep nodes still left to visit
		todo.add(source);
		while(!todo.isEmpty()) {
			SubReddit s = objectMapper.get(todo.pop()); // Parent pulled from top of the queue

			for(Edge e : s.getEdgeList()) { // Iterate over neighbours
				// If not visited, visit, add to queue, and add parent as previous node for path tracking
				if(!visited.contains(e.getTarget().toString())){
					visited.add(e.getTarget().toString());
					todo.add(e.getTarget().toString());
					prev.put(e.getTarget().toString(),s.toString());
				}
			}
		}
		// If the destination node is not visited
		if(!visited.contains(dest)) {
			System.out.println("No path from " + source + " to " + dest + " exists!");
			return null;
		}
		
		// Track back from the destination node to the source node, storing the path on the stack
		// A stack is used since the path can only be retrieved starting from the destination, so it needs to be reversed
		// When the path is unloaded from the stack, it is in the correct order
		Deque<String> path = new ArrayDeque<String>();
		path.addFirst(dest);
		while(!path.peek().equals(source)) {
			path.addFirst(prev.get(path.peek()));
		}
		return path;	
	}

	/**
	 * @brief Returns a reference to the SubReddit object with a given name
	 * @param name The name of the SubReddit of which a reference is returned
	 * @return A reference to the specified SubReddit
	 * @throws IllegalArgumentException If there is no SubReddit object with the given name
	 */
    public static SubReddit getSubRedditPointer(String name){
		if(objectMapper.get(name) == null)
			throw new IllegalArgumentException("SubReddit does not exist!");
        return objectMapper.get(name);
    }

	/**
	 * @brief Returns a reference to a list of all individual SubReddit objects read from the input file
	 * @return An ArrayList of SubReddit objects, each representing a unique subreddit found in the input file
	 */
	public static ArrayList<SubReddit> getValueSet(){
		return new ArrayList<SubReddit>(objectMapper.values());
	}
}