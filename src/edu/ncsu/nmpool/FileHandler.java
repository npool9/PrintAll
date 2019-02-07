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
	
	private ArrayList<File> files;
	private ArrayList<File> subdirectories;
	
	/**
	 * The FileHandler collects all files and subdirectories that stem from the user-chosen root directory.
	 *  
	 */
	public FileHandler() {
		files = new ArrayList<File>();
		subdirectories = new ArrayList<File>();
	}
	
	/**
	 * Loop through the root directory and all its subdirectories to append all files that exist in the rooted system to the files 
	 * ArrayList. As we find more subdirectories, continue to append them to the subdirectories ArrayList. Loop until no more 
	 * subdirectories are found and we reach the end of the subdirectories ArrayList.
	 * 
	 * @param root: the directory corresponding to the one chosen by the user
	 */
	public ArrayList<File> subdirectoryLoop(File root) {
		// start at the root-most directory (chosen by the user)
		subdirectories.add(root);
		int dirIndex = 0;
		while(true) {
			// check to see if there are any subdirectories left in the ArrayList. If there aren't, we're done gathering all files to print.
			File theDir;
			try {
				theDir = subdirectories.get(dirIndex);
			} catch(IndexOutOfBoundsException error) {
				break;
			}
			getFiles(theDir);
			getDirectories(theDir);
			dirIndex++;
		}
		return(files);
	}
	
	/**
	 * Given some root directory, identify the files ONLY and save them to a temporary ArrayList
	 * 
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
	 * 
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
