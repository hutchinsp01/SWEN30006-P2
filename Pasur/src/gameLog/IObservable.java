package gameLog;

/**
 * Created by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */

public interface IObservable {

	public void registerObserver(IObserver observer);

	public void removeObserver(IObserver observer);

	public void notifyObserver(String data);

}
	