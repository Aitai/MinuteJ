package model;

import view.Observer;
/**
 * This interface part of the observable design pattern. Here we 
 * require that all observables are able to register an ob-
 * server, and have capability to notify all observers.
 * 
 * In Layman's terms: Each observerable knows who is observing.
 * 
 * @author ronaldvandijk
 * @version 01-02-2017
 */
public interface Observable {
	
	/**
	 * An observable should hold reference to the observer.
	 * @param observer the observer
	 */
	public void registerObserver(Observer observer);
	
	/**
	 * An observable should also notify it's observers when 
	 * this is required.
	 */
	public void notifyObservers();
}
