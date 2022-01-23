package scoringMethods;

/**
 * Created by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */

import ch.aplu.jcardgame.Hand;
import pasur.Rank;
import pasur.Suit;
import scoring.IGetScore;

public class TenOfDiamonds implements IGetScore {

	@Override
	public int getScore(Hand pickedCards, Hand Surs) {
		return (pickedCards.getCard(Suit.DIAMONDS, Rank.TEN) != null) ? 3 : 0;
	}



}
