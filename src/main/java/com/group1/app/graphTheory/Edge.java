/**
 * @Edge.java
 * @author Jared Bentvelsen
 * @date 11/04/2020
 * @brief Class to represent a weighted edge between two Subreddits, with a list of properties available
 * @details Properties include information about the connecting post, such as the number words, characters, swear words, as well as positive and negative sentiment.
 */
package com.group1.app.graphTheory;

import java.util.ArrayList;
import java.util.Date;

public class Edge {
    private SubReddit source;
    private SubReddit dest;
    private Date date;
    private ArrayList<Double> properties;

    /**
     * @brief Constructs a new Edge object
     * @param source A reference to the source SubReddit object
     * @param dest A reference to the destination SubReddit object
     * @param date A Date object (from Java.util) representing the timestamp of the connecting post
     * @param properties An ArrayList of Double values representing the properties of the Edge
     */
    public Edge(SubReddit source,SubReddit dest, Date date, ArrayList<Double> properties){
        this.source = source;
        this.dest = dest;
        this.source = source;
        this.date = date;
        this.properties = properties;
    }
    /**
     * @brief prints a string representation of the Edge
     */
    public String toString(){
        return "Link from " + dest.toString() + " to " + source.toString();
    }

    /**
     * @return A reference to the Date object representing the time of the post
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * @return A reference to the destination SubReddit object involved in this Edge
     */
    public SubReddit getTarget(){
        return this.dest;
    }
 
    /**
     * @return A reference to the source SubReddit object involved in this Edge
     */
    public SubReddit getSource(){
        return this.source;
    }

    /**
     * @brief Returns the value of the specified property within this Edge
     * @param p A Prop enum specifying the property to be returned
     * @return A double equal to the value of the specified property in this Edge
     */
    public double getProperty(Prop p){
        return this.properties.get(p.ordinal());
    }
}