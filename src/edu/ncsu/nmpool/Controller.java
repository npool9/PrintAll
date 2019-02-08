/**
 * 
 */
package edu.ncsu.nmpool;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * @author nathanpool
 *
 */
public class Controller {
	
	/**
	 * Initialize the controller. Perform all steps for printing all documents in a chosen
	 * directory.
	 */
	public void go() {
		String directory = initView();
		File rootDirectory = new File(directory);
		FileHandler fileHandler = new FileHandler();
		ArrayList<File> allPrintFiles = fileHandler.subdirectoryLoop(rootDirectory);
		// print out all files that will be printed just to check
		System.out.println("All Found Files:");
		for (int i = 0; i < allPrintFiles.size(); i++) {
			System.out.println(allPrintFiles.get(i));
		}
		DirectoryView view = new DirectoryView();
		boolean toPrint = view.displayAllFiles(allPrintFiles);
		if (!toPrint) {
			view.removeChoices(allPrintFiles);
		}
		System.out.println("Updated Print List:");
		for (int i = 0; i < allPrintFiles.size(); i++) {
			System.out.println(allPrintFiles.get(i).toString());
		}
	}
	
	/**
	 * Create the directory view while getting the chosen directory
	 * @return String directory: the absolute path of the chosen directory from which to be
	 * 	printed
	 */
	public String initView() {
		JFrame frame = new JFrame("");
	    DirectoryView panel = new DirectoryView();
	    String directory = panel.createDirView();
	    frame.addWindowListener(
	      new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	          System.exit(0);
	        }
	    });
	    frame.getContentPane().add(panel,"Center");
	    frame.setSize(panel.getPreferredSize());
	    frame.setVisible(true);
	    return(directory);
	}
		
}
