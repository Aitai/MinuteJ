package controller;

import model.*;

import java.awt.event.*;

/**
 * @author Chris Roscher
 * @version 01-02-2017
 */
public class Controller extends AbstractController {

	/**
	 * Constructor for the controller.
	 * @param model the model
	 */
	public Controller(Model model) {
		super(model);
	}
	
	
	/**
	 * @param e the actionEvent that is given to the controller.
	 */
	public void actionPerformed(ActionEvent e) {
		
		
		if (e.getActionCommand().equals("test")) {
//			System.out.println("test");
			model.run();
		}
		
	}
}
