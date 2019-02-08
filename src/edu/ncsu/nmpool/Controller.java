/**
 * 
 */
package edu.ncsu.nmpool;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.Doc;
import javax.print.DocPrintJob;
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
	public void phase1() {
		ArrayList<File> allPrintFiles;
		String directory = initView();
		File rootDirectory = new File(directory);
		FileHandler fileHandler = new FileHandler();
		allPrintFiles = fileHandler.subdirectoryLoop(rootDirectory);
		
		DirectoryView view = new DirectoryView();
		boolean toPrint = view.displayAllFiles(allPrintFiles);
		// user wants to update list of files to print
		if (!toPrint) {
			view.removeChoices(allPrintFiles);
		// user doesn't need to update list of files. go with what is already received.
		} else {
			phase2(allPrintFiles);
		}
	}
	
	/**
	 * Now that, we have a final list of files that we want to print (their absolute paths), let's initalize the print job.
	 * 
	 * @param allPrintFiles ArrayList<File>: the final list of files we want to print (mapped by absolute path)
	 */
	public void phase2(ArrayList<File> allPrintFiles) {
		System.out.println("Files to print: ");
		for (int i = 0; i < allPrintFiles.size(); i++) {
			System.out.println(allPrintFiles.get(i).toString());
		}
		
		PrintingJob printJob = new PrintingJob(allPrintFiles);
		DocPrintJob pj = printJob.createPrintJob();
		FileInputStream[] files = null;
		try {
			files = printJob.loadDocuments(allPrintFiles);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Doc[] printableDocs = printJob.getDocuments(files);
		System.out.println("Printing your documents...");
		printJob.submitPrintJobs(printableDocs, pj);
		System.out.println("Printing Complete.");
		System.exit(0);
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
