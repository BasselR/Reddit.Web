/**
 * @file InputReader.java
 * @author Matthew McCracken
 * @date 10/04/2020
 * @brief a class to define methods for reading lines of data from a CSV file
 */
package com.group1.app.inputOutput;


import java.util.ArrayList;
import java.util.Date;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class InputReader {

	/**
     * @brief Gets a named file as an InputStream
     * @param fileName A String representing the name of the input file
     * @return The named resource as an InputStream
     * @throws IOException
     */
    private InputStream getInputStream(String fileName) throws IOException {
        ClassLoader cL = Thread.currentThread().getContextClassLoader();
        if(cL.getResource(fileName) == null)
            throw new IOException("File Reading Failed!");
    	return cL.getResourceAsStream(fileName);
    }

    /**
     * @brief Reads a file specified by fileName and stores all of the data in a list of OutputObjects
     * @param fileName String representing a path to a file holding data
     * @return an ArrayList of OutputObjects
     * @throws IOException if no file can be found
     */
    
    public ArrayList<OutputObject> readFile(String fileName) throws IOException{

    	BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream(fileName)));
        ArrayList<OutputObject> StoredData = new ArrayList<OutputObject>();


        String s1 = br.readLine(); //Reads off header line
		while((s1 = br.readLine()) != null && s1.length() != 0) {

            String[] s = s1.split(",", 2);// Splits Source name, target and date from the properties held as doubles
            
            String[] d = s[1].split(",");//parses through properties
            String[] Line1 = s[0].split("\t");//Splits strings by tab
            
            
            //Splits up time into year, month, day, hour, minute, second
            String u[] = Line1[3].split(" ");
            String u2[] = u[0].split("-");
            String u3[] = u[1].split(":");

            int year = Integer.parseInt(u2[0]);
            int month = Integer.parseInt(u2[1]);
            int day = Integer.parseInt(u2[2]);
            int hour = Integer.parseInt(u3[0]);
            int min = Integer.parseInt(u3[1]);
            int second = Integer.parseInt(u3[2]);
            ArrayList<Double> Dlist = new ArrayList<Double>();
            
            //makes String array into new Double ArrayList
            for (String i : d){
                double j = Double.parseDouble(i);
                Dlist.add(j);
            }
            
            //Utilizes Date library for storing Date of reddit connections
            Date date = new Date(year - 1900, month - 1, day, hour, min, second);
            
            OutputObject o = new OutputObject(date, Line1[0], Line1[1], Dlist);
            StoredData.add(o);
        }
        return StoredData;
    }
}