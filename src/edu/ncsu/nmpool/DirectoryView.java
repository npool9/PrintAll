/**
 * 
 */
package edu.ncsu.nmpool;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.awt.*;
import java.util.*;
import java.util.List;

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
	protected ArrayList<File> newAllFiles;
	protected List synchedList = Collections.synchronizedList(new LinkedList());;
	
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
		boolean toPrint = false;
		
		JFrame frame = new JFrame("Double Check");
		String fileList = "";
		for (int i = 0; i < allFiles.size(); i++) {
			String fileName = allFiles.get(i).toString();
			fileList += fileName + "\n";
		}
		int optionChoice = JOptionPane.showConfirmDialog(frame, "<html>Are these the files you want to print?</br></br>"
				+ "</html>" + fileList, "Double Check", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (optionChoice == 0) {
			toPrint = true;
		} else {
			toPrint = false;
		}
		return(toPrint);
	}
	
	/**
	 * 
	 * Give the user an option to remove some files
	 * 
	 * @param allFiles: an ArrayList of files with each File representing the absolute path of a file to be printed
	 * @return ArrayList<File>: the updated (subsetted) ArrayList<File>
	 */
	public void removeChoices(ArrayList<File> allFiles) {
		JFrame frame = new JFrame("File Deletion");
		JLabel label = new JLabel("Choose the files you'd like to remove");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(label);
		JCheckBox JCheckBox;
		JCheckBox[] JCheckBoxes = new JCheckBox[allFiles.size()];
		DefaultListModel<JCheckBox> model = new DefaultListModel<JCheckBox>();
		JCheckBoxList JCheckBoxList = new JCheckBoxList(model);
		for (int i = 0; i < allFiles.size(); i++) {
			JCheckBox = new JCheckBox("<html>" + allFiles.get(i).toString() + "</br></html>");
			model.addElement(JCheckBox);
			JCheckBoxes[i] = JCheckBox;
		}
		JScrollPane scrollableTextArea = new JScrollPane(JCheckBoxList);
		panel.add(scrollableTextArea);
		
		JButton done = new JButton("Done");
		done.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Get the JCheckBoxes that were checked. Remove those from the list of files to print.
		    	newAllFiles = getUnchecked(JCheckBoxes, allFiles);
		    	System.out.println("We got 'em, dog.");
		    	System.out.println(newAllFiles);
		    	System.exit(0);
		    }
		});
		
		panel.add(done);
		frame.add(panel);
	    frame.setSize(600, 400);
	    frame.setVisible(true);
	    
		synchedList.add("Got the info");
	}
	
	/**
	 * If a checkbox was checked (corresponding to a file to be printed), that means the user wants it removed. Remove
	 * that file from the allFiles print ArrayList.
	 * 
	 * @param checks: all checkboxes provided as options for the user
	 * @param allFiles: the acxtual files corresponding to each checkbox
	 * @return ArrayList<File>: the list of files after removing unwanted ones by the user
	 */
	public ArrayList<File> getUnchecked(JCheckBox[] checks, ArrayList<File> allFiles) {
		for (int i = 0; i < checks.length; i++) {
			boolean checked = checks[i].isSelected();
			if (checked) {
				System.out.println("Removed: " + allFiles.get(i).toString());
				allFiles.remove(i);
			}
		}
		return(allFiles);
	}

}
