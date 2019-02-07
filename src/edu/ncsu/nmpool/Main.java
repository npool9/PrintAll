package edu.ncsu.nmpool;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * @author Nathan Pool
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String s[]) {
		Controller controller = new Controller();
		controller.go();
		System.exit(0);
	  }

}
