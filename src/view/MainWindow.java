package view;

//import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import controller.Controller;
/**
 * @author Chris Roscher
 *
 */
public class MainWindow {
	private JFrame mainwindow;
	private JPanel buttonpane;

	public MainWindow(GaragePane garage, Controller controller){

		mainwindow=new JFrame("De garagesimulator");
		mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainwindow.setSize(1000, 700);
		mainwindow.setResizable(false);
		mainwindow.setLayout(null);
		mainwindow.getContentPane().add(garage);
		
		garage.setBounds(10, 10, 800, 500);

		buttonpane = new JPanel();
		buttonpane.setBounds(10, 520, 990, 65);
//		buttonpane.setSize(450, 65);
		mainwindow.getContentPane().add(buttonpane);

		JButton test;

		test=new JButton("test");
		test.addActionListener(controller);
		buttonpane.setLayout(new SpringLayout());
		buttonpane.add(test);




		mainwindow.setVisible(true);

	}

}
