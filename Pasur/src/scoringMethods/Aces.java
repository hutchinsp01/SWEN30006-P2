package scoringMethods;

import ch.aplu.jcardgame.Hand;
import pasur.Rank;
import scoring.IGetScore;

public class Aces implements IGetScore {

	@Override
	public int getScore(Hand pickedCards, Hand Surs) {
		
		return pickedCards.getNumberOfCardsWithRank(Rank.ACE);
		
	}

}
