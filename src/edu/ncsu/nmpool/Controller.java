/**
 * 
 */
package edu.ncsu.nmpool;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
