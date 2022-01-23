package scoringMethods;

/**
 * Created by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */

import ch.aplu.jcardgame.Hand;
import pasur.Rank;
import pasur.Suit;
import scoring.IGetScore;

public class TwoOfClubs implements IGetScore {

	@Override
	public int getScore(Hand pickedCards, Hand Surs) {
		return (pickedCards.getCard(Suit.CLUBS, Rank.TWO) != null) ? 2 : 0;
	}

}
