package scoring;

/**
 * Created by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */

import ch.aplu.jcardgame.Hand;

public interface IGetScore {
	
	// getScore method to be implemented by all scoringMethods
	public int getScore(Hand pickedCards, Hand Surs);

}
