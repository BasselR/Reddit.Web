/**
 * @file OutputObject.java
 * @author Matthew McCracken
 * @date 10/04/2020
 * @brief a class to define an object used for storing lines of data
 */
package com.group1.app.inputOutput;
import java.util.Date;
import java.util.ArrayList;


public class OutputObject {
    private Date d;
    private String source;
    private String target;
    private ArrayList<Double> properties;
    /**
     * @brief Constructs a OutputObject object
     * @param date a Date type object representing the date that a SubReddit object was added to the dataset.
     * @param S a String representing the source of a connection being read in
     * @param T a String representing the target of a connection being read in
     * @param props an ArrayList holding the float value properties of a connection
     */
    public OutputObject(Date date, String S, String T, ArrayList<Double> props){
        d = date;
        source = S;
        target = T;
        properties = props;

    }
    /**
     * @return the source of an OutputObject as a String
     */
    public String getSource(){
        return this.source;
    }
    /**
     * @return the target of an OutputObject as a String
     */
    public String getTarget(){
        return this.target;
    }
    /**
     * @return the date of an OutputObject as a Date
     */
    public Date getDate(){
        return this.d;
    }
    /**
     * @return the property values of an Output object as a list of doubles
     */
    public ArrayList<Double> getProperties(){
        return this.properties;
    }

}