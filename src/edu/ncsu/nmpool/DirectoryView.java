/**
 * 
 */
package edu.ncsu.nmpool;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
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
		boolean toPrint = false;
		
		String fileList = "";
		String[] tempFileList = new String[allFiles.size()];
		for (int i = 0; i < allFiles.size(); i++) {
			String fileName = allFiles.get(i).toString();
			fileList += fileName + "\n";
			tempFileList[i] = fileName;
			System.out.println("File List: " + fileList);
		}
		JList theFileList = new JList(tempFileList);
		JScrollPane scrollableTextArea = new JScrollPane(theFileList);
		JLabel label = new JLabel("Are these the files you want to print?");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JFrame frame = new JFrame("Double Check");
		panel.add(label);
		panel.add(scrollableTextArea);
		frame.add(panel);
		int optionChoice = JOptionPane.showConfirmDialog(frame, panel, "Double Check!", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
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
		    	ArrayList<File> newAllFiles = getUnchecked(JCheckBoxes, allFiles);
		    	System.out.println("Button Clicked!");
		    	// Initiate phase 2 of the controller with the updated set of files to print
		    	Controller control = new Controller();
		    	control.phase2(newAllFiles);
		    	System.exit(0);
		    }
		});
		panel.add(done);
		frame.add(panel);
	    frame.setSize(600, 400);
	    frame.setVisible(true);
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
		ArrayList<File> toRemove = new ArrayList<File>();
		for (int i = 0; i < checks.length; i++) {
			boolean checked = checks[i].isSelected();
			if (checked) {
				toRemove.add(allFiles.get(i));
			}
		}
		allFiles.removeAll(toRemove);
		return(allFiles);
	}

}
