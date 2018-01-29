package run;
import controller.Controller;
import model.Model;
import view.MainView;

public class Garage {

    public static void main(String[] args) {

    	MainView theView = new MainView();

    	Model theModel = new Model();

        Controller theController = new Controller(theView,theModel);

        theView.scherm.setVisible(true);

    }
}