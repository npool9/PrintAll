/**
 * 
 */
package edu.ncsu.nmpool;

import javax.swing.*;
import java.awt.event.*;
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
	
	/*
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

}
