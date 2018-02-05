package view;

import javax.swing.*;

import model.Simulator;
import java.awt.*;

/**
 * Klasse dat alle zakelijke gegevens laat zien
 * 
 * @author MinuteJ 
 * @version 1.0.0
 */ 
public class InfoView extends AbstractView {
	private static final long serialVersionUID = -3260703954764103294L;
	private static final JLabel day = new JLabel();
	private static final JLabel month = new JLabel();
	private static final JLabel dayOfMonth = new JLabel();
	private static final JLabel time = new JLabel();
	private static final JLabel empty = new JLabel();
	private static final JLabel revenue = new JLabel();
	private static final JLabel expectedRevenue = new JLabel();
	private static final JLabel entranceCarQueue = new JLabel();
	private static final JLabel entrancePassResQueue = new JLabel();
	private static final JLabel paymentCarQueue = new JLabel();
	private static final JLabel exitCarQueue = new JLabel();
	private static final JLabel freeSpots = new JLabel();

	/**
	 * Maakt een nieuwe view aan voor belangrijke informatie
	 * 
	 * @param simulator
	 */
	public InfoView(Simulator simulator) {
		simulator.addView(this);
		setLayout(null);
		add(day);
		add(month);
		add(dayOfMonth);
		add(time);
		add(empty);
		add(revenue);
		add(expectedRevenue);
		add(entranceCarQueue);
		add(entrancePassResQueue);
		add(paymentCarQueue);
		add(exitCarQueue);
		add(freeSpots);
		day.setBounds(10, 10, 400, 30);
		dayOfMonth.setBounds(90, 10, 400, 30);
		month.setBounds(120, 10, 400, 30);
		time.setBounds(200, 10, 310, 30);
		empty.setBounds(10, 10, 400, 60);
		revenue.setBounds(10, 10, 400, 90);
		expectedRevenue.setBounds(10, 10, 400, 120);
		entranceCarQueue.setBounds(10, 10, 400, 150);
		entrancePassResQueue.setBounds(10, 10, 400, 180);
		paymentCarQueue.setBounds(10, 10, 400, 210);
		exitCarQueue.setBounds(10, 10, 400, 240);
		freeSpots.setBounds(10, 10, 400, 270);
	}

	/**
	 * Pas de dag aan voor het datum label
	 * 
	 * @param label
	 */
	public static void setDayLabel(String label) {
		day.setText(label);
	}

	/**
	 * Pas de maand aan voor het datum label
	 * @param label
	 */
	public static void setMonthLabel(String label) {
		month.setText(label);
	}

	/**
	 * Pas de dag van de maand aan voor het datum label
	 * @param label
	 */
	public static void setDayOfMonthLabel(String label) {
		dayOfMonth.setText(label);
	}

	/**
	 * Pas de tijd aan voor het tijd label
	 * @param label
	 */
	public static void setTimeLabel(String label) {
		time.setText(label);
	}

	/**
	 * Pas de normale auto rij label aan
	 * @param label
	 */
	public static void setCarQueueLabel(String label) {
		entranceCarQueue.setText(label);
	}

	/**
	 * Pas de omzet label aan
	 * @param label
	 */
	public static void setRevenueLabel(String label) {
		revenue.setText(label);
	}

	/**
	 * Pas de verwachte omzet label aan
	 * @param label
	 */
	public static void setExpectedRevenueLabel(String label) {
		expectedRevenue.setText(label);
	}

	/**
	 * Pas de abonnement en reservatie rij label aan
	 * @param label
	 */
	public static void setPassResQueueLabel(String label) {
		entrancePassResQueue.setText(label);
	}

	/**
	 * Pas de rij voor auto's die aan het betalen zijn aan
	 * @param label
	 */
	public static void setpaymentCarQueueLabel(String label) {
		paymentCarQueue.setText(label);
	}

	/**
	 * Pas de rij voor de auto's die voor de uitgang staan aan
	 * @param label
	 */
	public static void setexitCarQueueLabel(String label) {
		exitCarQueue.setText(label);
	}

	/**
	 * Pas het aantal vrije parkeerplekken aan
	 * @param label
	 */
	public static void setFreeSpots(String label) {
		freeSpots.setText(label);
	}

	/**
	 * Krijg de grootte terug
	 */
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	@Override
	/**
	 * Update de view
	 */
	public void updateView() {
	}
}