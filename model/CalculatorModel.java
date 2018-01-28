package model;

public class CalculatorModel implements Runnable {

	private int calculationValue;

	public void addTwoNumbers(int firstNumber, int secondNumber){

		calculationValue = firstNumber + secondNumber;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getCalculationValue(){

		return calculationValue;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}