package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.CalculatorModel;
import view.CalculatorView;

public class CalculatorController {

	private CalculatorView theView;
	private CalculatorModel theModel;

	public CalculatorController(CalculatorView theView, CalculatorModel theModel) {
		this.theView = theView;
		this.theModel = theModel;

		this.theView.addCalculateListener(new CalculateListener());
	}

	class CalculateListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();
			if (command.equals("=")) {
				Thread worker = new Thread() {
					public void run() {
						int firstNumber, secondNumber = 0;
						try {
							firstNumber = theView.getFirstNumber();
							secondNumber = theView.getSecondNumber();

							theModel.addTwoNumbers(firstNumber, secondNumber);
							theView.enableCalcButton(false);
							theView.setStatus("Bezig...");
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
			} else if (command.equals("Sluiten")) {
				System.out.println("Scherm gesloten");
				theView.scherm.dispose();
			}
		}

	}

}