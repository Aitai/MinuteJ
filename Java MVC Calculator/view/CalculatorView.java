package view;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CalculatorView{
	public JFrame scherm = new JFrame("Een rekenmachine");
	
	private JTextField firstNumber  = new JTextField(10);
	private JLabel additionLabel = new JLabel("+");
	private JTextField secondNumber = new JTextField(10);
	private JButton calculateButton = new JButton("=");
	private JTextField calcSolution = new JTextField(10);
	private JLabel status = new JLabel("Klaar", JLabel.CENTER);

	private JButton sluitknop = new JButton("Sluiten");

	public CalculatorView(){

		JPanel calcPanel = new JPanel();

		scherm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scherm.setSize(600, 200);

		calcPanel.add(firstNumber);
		calcPanel.add(additionLabel);
		calcPanel.add(secondNumber);
		calcPanel.add(calculateButton);
		calcPanel.add(calcSolution);
		calcPanel.add(status);

		calcPanel.add(sluitknop);

		scherm.add(calcPanel);
	}
	public void setStatus(String string) {
        status.setText(string);
    }

	public int getFirstNumber(){

		return Integer.parseInt(firstNumber.getText());
	}

	public int getSecondNumber(){

		return Integer.parseInt(secondNumber.getText());
	}

	public int getCalcSolution(){

		return Integer.parseInt(calcSolution.getText());
	}

	public void setCalcSolution(int solution){

		calcSolution.setText(Integer.toString(solution));
	}

	// Voert een actie uit in de controller genaamd actionPerformed

	public void addCalculateListener(ActionListener knop){

		calculateButton.addActionListener(knop);
		sluitknop.addActionListener(knop);
	}
	
	// Opent een popup die de error weergeeft
	public void displayErrorMessage(String errorMessage){

		JOptionPane.showMessageDialog(scherm, errorMessage);
	}

}