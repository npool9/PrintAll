package edu.ncsu.nmpool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;

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
	
	/**
	 * Find local printers
	 */
	public DocPrintJob createPrintJob() {
		DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(MediaSizeName.ISO_A4);
		aset.add(new Copies(1));
		PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, aset);
		DocPrintJob pj = services[0].createPrintJob();
		
		return(pj);
	}
	
	/**
	 * Load document from every file provided in the array list
	 * 
	 * @param allPrintFiles: an ArrayList<File> of all files to be printed
	 * @throws IOException
	 */
	public FileInputStream[] loadDocuments(ArrayList<File> allPrintFiles) throws IOException {
		FileInputStream[] fileStream = new FileInputStream[allPrintFiles.size()];
		for (int i = 0; i < allPrintFiles.size(); i++) {
			FileInputStream fis = new FileInputStream(allPrintFiles.get(i).toString());
			fileStream[i] = fis;
			fis.close();
		}
		return(fileStream);
	}
	
	/**
	 * Take all file input streams and make them documents to be printed
	 * @param files: an array of FileInputStreams to be turned into printable Docs
	 * @return an array of printable Docs
	 */
	public Doc[] getDocuments(FileInputStream[] files) {
		Doc[] docs = new Doc[files.length];
		for (int i = 0; i < files.length; i++) {
			docs[i] = new SimpleDoc(files[i], DocFlavor.INPUT_STREAM.PDF, null);
		}
		return(docs);
	}
	
	/**
	 * Print all documents provided to the print job provided
	 * 
	 * @param printableDocuments: all the documents we will be printing (selected by the user)
	 * @param job: the print job to send documents to
	 */
	public void submitPrintJobs(Doc[] printableDocuments, DocPrintJob job) {
		// Define the attributes with which to print all of the documents
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(MediaSizeName.ISO_A4);
		aset.add(new Copies(1));
		aset.add(Sides.ONE_SIDED);
		
		for (int i = 0; i < printableDocuments.length; i++) {
			try {
				job.print(printableDocuments[i], aset);
			} catch (PrintException e) {
				System.err.println("A print error occurred.");
				System.err.println(e);
			}
		}
	}

	
}
