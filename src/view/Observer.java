package view;
/**
 * An observer should contain an update method. This method is called upon by the observed object
 * when there is a change in state. The observer than initiates logic to retrieve updated information
 * from the observable (model).
 * A pull mechanism is implemented.
 * @author ronaldvandijk
 *
 */
public interface Observer {

	/**
	 * The method that is called by the observed object. Note that this requires the observer to
	 * register the observable.
	 */
	public void update();
}
