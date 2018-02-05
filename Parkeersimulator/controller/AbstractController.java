package controller;

import model.Simulator;

import javax.swing.*;

public abstract class AbstractController extends JPanel {
    private static final long serialVersionUID = 4113198028695964708L;
    protected Simulator simulator;

    public AbstractController(Simulator simulator) {
        this.simulator = simulator;
    }
}
