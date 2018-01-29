package run;
import controller.Controller;
import model.Model;
import view.MainView;
import view.VierkantView;

public class Garage {

    public static void main(String[] args) {

    	MainView theView = new MainView();
    	
    	VierkantView vierkantView = new VierkantView();

    	Model theModel = new Model();

        Controller theController = new Controller(theView,vierkantView,theModel);

        theView.scherm.setVisible(true);

    }
}