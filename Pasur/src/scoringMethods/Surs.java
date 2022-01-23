package scoringMethods;

/**
 * Created by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */

import ch.aplu.jcardgame.Hand;
import scoring.IGetScore;

public class Surs implements IGetScore {
	
	private static int SURSCORE = 5;

	@Override
	public int getScore(Hand pickedCards, Hand Surs) {
		return SURSCORE * Surs.getNumberOfCards();
	}

}
