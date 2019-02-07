/**
 * 
 */
package edu.ncsu.nmpool;

import java.io.File;
import java.util.ArrayList;

/**
 * @author nathanpool
 *
 * This class handles the actual printing of all the files provided to it.
 */
public class PrintingJob {
	
	private ArrayList<File> allFiles;
	
	/**
	 * This object is defined by the ArrayList of files that it receives. These indicate all the files the user wants printed.
	 * A PrintJob will be created and executed for each of the files in the given ArrayList.
	 * 
	 * @param allFiles: a File-based ArrayList that holds all files that the user wishes to print
	 */
	public PrintingJob(ArrayList<File> allFiles) {
		this.allFiles = allFiles;
	}

}
