/**
 * 
 */
package edu.ncsu.nmpool;

import java.io.File;
import java.util.ArrayList;

/**
 * @author nathanpool
 *
 * This class is devoted to handling all things related to identifying files and creating File objects that we
 * wish to print
 */
public class FileHandler {
	
	ArrayList<File> files;
	ArrayList<File> subdirectories;
	
	/**
	 * The FileHandler is defined by a root directory. From this information, we can identify the files that 
	 *  the user wants to print.
	 *  
	 * @param directory: the root directory that is chosen by the user. The user wants to print all files that
	 * 	are present in this directory and its subdirectories.
	 */
	public FileHandler(String directory) {
		File rootDirectory = new File(directory);
		files = new ArrayList<File>();
		subdirectories = new ArrayList<File>();
	}
	
	/**
	 * Given some root directory, identify the files ONLY and save them to a temporary ArrayList
	 * @param directory: a root directory
	 */
	public void getFiles(File directory) {
		File[] fileArray = directory.listFiles();
		for (int i = 0; i < fileArray.length; i++) {
			if (fileArray[i].isFile()) {
				files.add(fileArray[i]);
			}
		}
	}
	
	/**
	 * Given a root directory, identify the (sub)directories ONLY and save them to a temporary ArrayList
	 * @param directory
	 */
	public void getDirectories(File directory) {
		File[] fileArray = directory.listFiles();
		for (int i = 0; i < fileArray.length; i++) {
			if (fileArray[i].isDirectory()) {
				subdirectories.add(fileArray[i]);
			}
		}
	}

}
