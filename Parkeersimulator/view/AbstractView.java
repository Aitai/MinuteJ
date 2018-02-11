package view;

import javax.swing.*;

/**
 * Abstracte klasse voor het aanmaken van nieuwe views
 *
 * @author MinuteJ
 * @version 1.0.0
 */
public abstract class AbstractView extends JPanel {
    private static final long serialVersionUID = -7699321466531192553L;

    abstract public void updateView();

}
