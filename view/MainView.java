package view;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.*;

public class MainView{
	public JFrame scherm = new JFrame("Een garage");

	private JTextField firstNumber  = new JTextField(10);
	private JLabel additionLabel = new JLabel("+");
	private JTextField secondNumber = new JTextField(10);
	private JButton calculateButton = new JButton("=");
	private JTextField calcSolution = new JTextField(10);
	private JLabel status = new JLabel("Klaar", JLabel.CENTER);
	private JButton stop = new JButton("Stop");

	private JButton sluitknop = new JButton("Sluiten");

	public MainView(){
		JPanel vierkantPanel = new VierkantView();


		JPanel calcPanel = new JPanel();
		calcPanel.add(firstNumber);
		calcPanel.add(additionLabel);
		calcPanel.add(secondNumber);
		calcPanel.add(calculateButton);
		calcPanel.add(calcSolution);
		calcPanel.add(status);
		calcPanel.add(stop);

		JPanel sluitPanel = new JPanel();
		sluitPanel.add(sluitknop);

		JPanel groot = new JPanel(new BorderLayout());
		groot.add(calcPanel, BorderLayout.NORTH);
		groot.add(sluitPanel, BorderLayout.SOUTH);
		groot.add(vierkantPanel,BorderLayout.CENTER);

		scherm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scherm.setSize(600, 300);
		scherm.add(groot);
		
	}
	public void setStatus(String string) {
        status.setText(string);
    }

	public void enableCalcButton(Boolean b) {
		calculateButton.setEnabled(b);

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
		stop.addActionListener(knop);
	}

	// Opent een popup die de error weergeeft
	public void displayErrorMessage(String errorMessage){

		JOptionPane.showMessageDialog(scherm, errorMessage);
	}

}