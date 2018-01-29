package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;
import view.MainView;

public class Controller {

	private MainView theView;
	private Model theModel;

	public Controller(MainView theView, Model theModel) {
		this.theView = theView;
		this.theModel = theModel;

		this.theView.addCalculateListener(new CalculateListener());
	}
	private void startWorker() {
		Thread worker = new Thread() {
			public void run() {
				int firstNumber, secondNumber = 0;
				try {
					firstNumber = theView.getFirstNumber();
					secondNumber = theView.getSecondNumber();

					theView.enableCalcButton(false);
					theView.setStatus("Bezig...");
					theModel.addTwoNumbers(firstNumber, secondNumber);
					theView.setCalcSolution(theModel.getCalculationValue());
					theView.enableCalcButton(true);
					theView.setStatus("Klaar");

				} catch (NumberFormatException ex) {
					System.out.println(ex);
					theView.displayErrorMessage("Voer twee integers in.");
				}
			}
		};
		worker.start();
	}
	private void stopWorker() {
		Thread.currentThread().interrupt();
	}

	class CalculateListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();
			
			if (command.equals("=")) {
				startWorker();
			} else if (command.equals("Sluiten")) {
				System.out.println("Scherm gesloten");
				theView.scherm.dispose();
			} else if (command.equals("Stop")) {
				stopWorker();
			}
		}

	}

}