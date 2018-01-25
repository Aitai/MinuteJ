package model;
/**
 * The model is not much more than an incrementing amount. There is a class
 * variable that holds the current (integer) amount, a setter and a getter. The
 * main responsibility of this class is to keep track of the amount and
 * increment it's value. The class implements the interface Runnable.
 * This means the code can run 'independent' of the mainthread and as
 * a result the GUI of the application remains responsive during execution.
 *
 * @author ronaldvandijk
 * @version 01-02-2017
 */
public class Model extends AbstractModel implements Runnable {

	private int amount;
	private boolean run;

	/**
	 * Retrieves the current amount in this model
	 *
	 * @return amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the current amount in the model. The amount must be an
	 * integer number between 0 and 360. Upon change the observers
	 * are notified.
	 *
	 * @param amount the amount to set. A value between 0 and 360 (including)
	 */
	public void setAmount(int amount) {
		if (amount>=0 && amount <=360) {
			this.amount=amount;
			notifyObservers();
		}
	}

	/**
	 * Starts the thread.
	 */
	public void start() {
		new Thread(this).start();
	}

	/**
	 * Stops the iteration in the run method. Note that
	 * this method doesn't stop the thread.
	 */
	public void stop() {
		run=false;
	}

	/**
	 * Sets the amount to zero
	 */
	public void reset(){
		setAmount(0);
	}

	/**
	 * The run method increases the amount to 360 in an iterative
	 * and incremental way. It stops when the amount reaches a value
	 * of 360.
	 */
	@Override
	public void run() {
		run=true;
		while(run) {
			setAmount(getAmount()+1);
			if (getAmount()==360) stop();
			try {
				Thread.sleep(10);
			} catch (Exception e) {}
		}
	}
}