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
import java.util.concurrent.TimeUnit;

import javax.print.Doc;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.JFrame;

/**
 * @author nathanpool
 *
 */
public class Controller extends PrintJobAdapter {
	
	protected ArrayList<File> allPrintFiles;
	private boolean isPrinting = false;
		
	/**
	 * Initialize the controller. Perform all steps for printing all documents in a chosen
	 * directory.
	 */
	public void phase1() {
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
		printJob.createPrintJob();
	}
	
	/**
	 * The third and final phase now that the user has chosen the printer they'd like to use.
	 * 
	 * @param printer: the printer chosen by the user
	 * @param allPrintFiles: the final list of files we want to print (mapped by absolute path)
	 * @throws InterruptedException 
	 */
	public void phase3(PrintService printer) throws InterruptedException {
		PrintingJob printJob = new PrintingJob(allPrintFiles);
		DocPrintJob[] pjs = new DocPrintJob[allPrintFiles.size()];
		for (int i = 0; i < allPrintFiles.size(); i++) {
			pjs[i] = printJob.finishEstablishingPrintJob(printer);
		}
		FileInputStream[] files = null;
		try {
			System.out.println("All Files: " + allPrintFiles);
			files = printJob.loadDocuments(allPrintFiles);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		Doc[] printableDocs = printJob.getDocuments(files);
		System.out.println("Printing your documents...");
		for (int i = 0; i < printableDocs.length; i++) {
			JobCompleteMonitor monitor = new JobCompleteMonitor();
			pjs[i].addPrintJobListener(monitor);
			printJob.submitPrintJob(printableDocs[i], pjs[i]);
			monitor.waitForJobCompletion();
			System.out.println("Job " + i + " Printing.");
		}
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
