/**
 * 
 */
package edu.ncsu.nmpool;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.awt.*;
import java.util.*;

/**
 * @author nathanpool
 * 
 * A class that is responsible for providing a directory chooser.
 * Given a choice of directory, the absolute path of that directory is returned.
 * This absolute path is used for printing every file within it including the files within
 * its subdirectories.
 * 
 */
public class DirectoryView extends JPanel {
	
	private JFileChooser chooser;
	private String chooserTitle;
	
	/**
	 * Create the directory chooser view.
	 * 
	 * @return String: the absolute path to the directory from which we want to print all files
	 */
	public String createDirView() {	  
	    chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(chooserTitle);
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
	    // disable the "All files" option.
	    chooser.setAcceptAllFileFilterUsed(false);
	    String selectedDirectory = "No Selection";
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	      System.out.println("Current Directory: " 
	         +  chooser.getCurrentDirectory());
	      selectedDirectory = chooser.getSelectedFile() + "";
	      System.out.println("Selected Directory: " 
	         +  selectedDirectory);
	      } else {
	    	  System.out.println(selectedDirectory);
	    }
	    return(selectedDirectory);
	}
	
	/**
	 * Display all of the files found by our search. Check to make sure these are actually files that the user wants to print.
	 * 
	 * @param allFiles: an ArrayList of files with each File representing the absolute path of a file to be printed
	 * @return boolean: true or false based on confirmation that the user does or does not want to print all of the listed files
	 */
	public boolean displayAllFiles(ArrayList<File> allFiles) {
		JFrame frame = new JFrame("Double Check");

		JPanel container = new JPanel();
		JScrollPane scrollPane = new JScrollPane(container);
		frame.add(scrollPane);
		JLabel label = new JLabel("<html>Are these the files you want to print?<br/><br/></html>");
		container.add(label);
		label = new JLabel("\n");
		container.add(label);
		
		for (int i = 0; i < allFiles.size(); i++) {
			JLabel fileName = new JLabel(allFiles.get(i).toString());
			container.add(fileName);
		}

		frame.setSize(new Dimension(700, 400));
		frame.add(container);
		frame.setVisible(true);
		
		return false;
	}

}
