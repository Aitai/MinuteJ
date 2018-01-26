package run;

import view.*;
import model.*;
import controller.*;

public class Garage {
	
	public Garage() {
		Model model = new Model();
		Controller controller = new Controller(model);
		GaragePane garage = new GaragePane(model);

	    new MainWindow(garage, controller);

	}
	public static void main(String[] args) {
		new Garage();
	}

}