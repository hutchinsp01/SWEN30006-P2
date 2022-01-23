package scoringMethods;

/**
 * Created by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */

import ch.aplu.jcardgame.*;
import pasur.Rank;
import scoring.IGetScore;

public class Jacks implements IGetScore {

	@Override
	public int getScore(Hand pickedCards, Hand Surs) {
		return pickedCards.getNumberOfCardsWithRank(Rank.JACK);
	
	}



}
