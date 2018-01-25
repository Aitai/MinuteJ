package controller;

import model.*;

import java.awt.event.*;

/**
 * The controller reacts on input received from the view. 
 * Here implementation is provided for the actionPerformed method
 * that is implemented in the AbstractController.
 * @author ronaldvandijk
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
	 * Implementation for the actionPerformed method that
	 * this class has to provide. Currently the method figures
	 * out which actionevent was triggered and initiates the 
	 * steps to be taken.
	 * 
	 * @param e the actionEvent that is given to the controller.
	 */
	public void actionPerformed(ActionEvent e) {
		
		
		if (e.getActionCommand().equals("-1")) {
			model.setAmount(model.getAmount()-1);
		}
		
		if (e.getActionCommand().equals("+1")) {
			model.setAmount(model.getAmount()+1);
		}
		
		if (e.getActionCommand().equals("Start")) {
			model.start();
		}
		
		if (e.getActionCommand().equals("Stop")) {
			model.stop();
		}
		
		if (e.getActionCommand().equals("Reset")) {
			model.reset();
			model.stop();
		}
		if (e.getActionCommand().equals("Test")) {
//			model.stop();
//			System.exit(0);
//			new 
//			new Simulator().run();
			model.startGarage();
		}
		
	}
}
