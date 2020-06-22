/**
 * @file JSONWriter.java
 * @author Bassel Rezkalla
 * @date 08/04/2020
 * @brief Writes generated data to a JSON file
 */
package com.group1.app.inputOutput;

import com.group1.app.graphTheory.Graph;
import com.group1.app.graphTheory.SubReddit;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap; 
import java.util.Map; 
 
import org.json.simple.JSONArray;


/*
masterObj will contain 1 key value pair, value is list of subreddit objects
Our JSON object will be: an object (root) -- list of subreddits.
subreddit objects will contain adjacency list (list of subreddit objects)
*/

public class JSONWriter {

    /**
     * @brief Writes a JSON array of random subreddits and their edges to an output file 
     * @param numParents Number of parent subreddits
     * @param maxEdges Max number of edges per subreddit
     */
    public static void writeJSON(int numParents, int maxEdges){

        JSONArray masterList = new JSONArray();

        //Counter variable to count successful subreddit additions to the JSON array
        int s = 0;
        
        // While there are still subreddits to add:
        while(s < numParents){
            // Grabs a random subreddit from the set of all subreddits
            SubReddit subreddit = Graph.getValueSet().get((int)(Math.random() * Graph.getValueSet().size()));

            // Builds the skeleton object for the subreddit JSON object and adds the name entry
            Map sr = new LinkedHashMap(2);
            sr.put("name", subreddit.toString());

            // Only use subreddits that have a positive number of edges
            if(subreddit.getEdgeList().size() == 0) continue;
            else{

                // Create a JSON array to hold this subreddit's adjacent subreddits
                JSONArray srEdges = new JSONArray();

                // Unique edges
                HashSet<String> edges = new HashSet<String>();

                // Create an object for each adjacent subreddit, add its name and add the subreddit to the edge list
                for(int i = 0; i < maxEdges && i < subreddit.getEdgeList().size(); i++){
                    if(!edges.contains(subreddit.getEdgeList().get(i).getTarget().toString())){
                        edges.add(subreddit.getEdgeList().get(i).getTarget().toString());
                        Map<String, String> srAdj = new LinkedHashMap<String, String>(1);
                        srAdj.put("name", subreddit.getEdgeList().get(i).getTarget().toString());
                        srEdges.add(srAdj);
                    }
                }
                // Add the adjacency list to the subreddit's JSON object
                sr.put("adj", srEdges);
            }
            // Add the completed subreddit object to the final (outermost) JSON Array and incrememnt counter
            masterList.add(sr);
            s++;
        }

        // Writes the final JSON Array to the output file, catching IOException
        try (FileWriter file = new FileWriter("nodeData.json")) {
            file.write(masterList.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}