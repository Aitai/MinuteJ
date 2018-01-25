package controller;

import java.awt.event.ActionListener;

import model.*;

/**
 * In this application one controller is modeled. The controller knows the model.
 * Currently the controller doesn't change any part of the view. Therefore
 * the controller is not aware of the view.
 *  
 * @author ronaldvandijk
 *
 */
public abstract class AbstractController implements ActionListener{
	protected Model model;
	
	/**
	 * Register the model to the controller.
	 * 
	 * @param model the model
	 */
	AbstractController(Model model) {
		this.model=model;
	}
}
