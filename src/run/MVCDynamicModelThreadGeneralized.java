package run;

import controller.*;
import model.*;
import view.*;
/**
 * The MVCDynamicModelThreadGenaralized application illustrates the 
 * Model View Controller concept via a simple Swing application.
 * Two displays are updated via shared model that implements a Thread.
 * 
 * The MVC uses a pull implementation of the observer pattern. Whenever 
 * changes in the model occur the observers are notified and pull the 
 * relevant information from the model.
 * 
 * In this example the controller is not aware of the view, since no 
 * GUI elements need to be modified based on the state of the model.
 * 
 * @author ronaldvandijk
 * @version 01-02-2017
 */
public class MVCDynamicModelThreadGeneralized {
	
	/**
	 * Constructor to set-up the application
	 */
	public MVCDynamicModelThreadGeneralized() {
		Model model                    = new Model();
		Controller controller          = new Controller(model); //observe that the view(s) are not registered to the controller.
		AbstractDisplayPane countpane  = new CountPane(model); // part of the view
		AbstractDisplayPane piepane    = new PiePane(model); // part of the view
		
	    new MainWindow(countpane, piepane, controller); // part of the view
		
	}
	
	/**
	 * Main method to start the application
	 * @param args no arguments are required.
	 */
	public static void main(String[] args) {
			new MVCDynamicModelThreadGeneralized();
	}
	
}
