/**
 * @file QuickSort.java
 * @author Jared Bentvelsen
 * @date 11/04/2020
 * @brief Sorts an ArrayList of SubReddit objects in DESCENDING order of number of connections
 */
package com.group1.app.sorting;

import java.util.ArrayList;
import com.group1.app.graphTheory.SubReddit;

public class QuickSort {

    /**
     * @brief Public helper function to call quicksort
     * @param arr An ArrayList of SubReddit objects to be sorted
     */
    public static void quickSort(ArrayList<SubReddit> arr) {
		quickSort(arr,0,arr.size()-1);
    }
    
    /**
     * @brief Private Quicksort function, recursively partitions and sorts the halves of the array.
     * @param arr An ArrayList of SubReddit objects to be sorted
     * @param start Beginning point of section
     * @param end End point of section
     */
	private static void quickSort(ArrayList<SubReddit> arr,int start, int end ) {
		if(start < end) {
			int partition = partition(arr,start,end);
			quickSort(arr,start,partition - 1);
			quickSort(arr,partition + 1,end);
		}
    }
    /**
     * @brief Private partition function that puts all keys on the correct side of the partition.
     * @details Sorts in descending order
     * @param arr An ArrayList of SubReddit objects to be sorted
     * @param start Beginning point of section
     * @param end End point of section
     * @return Partintion index
     */
	private static int partition(ArrayList<SubReddit> arr,int start, int end) {
		SubReddit pivot = arr.get(start);
		int i = start + 1;
		int j = end;
		loop:
		while(j >= i) {
			if(!gTE(arr.get(i),pivot)) {
				while(!gT(arr.get(j),pivot)) {
					j--;
					if(j < i) break loop;
				}
                SubReddit temp = arr.get(i);
                arr.set(i,arr.get(j));
                arr.set(j,temp);
				i++;
				j--;
			}
			else i++;
        }
        
		SubReddit temp = arr.get(j);
		arr.set(j,pivot);
	    arr.set(start,temp);
		return j;
    }

    /**
     * @brief Returns if one SubReddit is greater than OR EQUAL to another based on number of connections
     * @param r1 First SubReddit to be compared
     * @param r2 Second SubReddit to be compared
     * @return true if r1 has more, or equal, connections than r2
     */
    private static boolean gTE(SubReddit r1, SubReddit r2){
        return r1.getNumConnects() >= r2.getNumConnects();
    }

    /**
     * @brief Returns if one SubReddit is GREATER THAN another based on number of connections
     * @param r1 First SubReddit to be compared
     * @param r2 Second SubReddit to be compared
     * @return true if r1 has strictly more, connections than r2
     */
    private static boolean gT(SubReddit r1, SubReddit r2){
        return r1.getNumConnects() > r2.getNumConnects();
    }

}