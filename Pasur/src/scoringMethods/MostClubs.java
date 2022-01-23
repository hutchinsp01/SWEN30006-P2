package scoringMethods;

/**
 * Created by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */

import ch.aplu.jcardgame.*;
import pasur.Suit;
import scoring.IGetScore;

public class MostClubs implements IGetScore {
	
	private static int CLUBSNEEDED = 7;

	@Override
	public int getScore(Hand pickedCards, Hand Surs) {
		
		return (pickedCards.getNumberOfCardsWithSuit(Suit.CLUBS) >= CLUBSNEEDED) ? 7 : 0;

	}



}
