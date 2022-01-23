package scoring;

/**
 * Created by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */

import scoringMethods.*;
import java.util.ArrayList;

import ch.aplu.jcardgame.Hand;

public class ScoreFacade {
	
	private ArrayList<IGetScore> allScoringMethods; 

	// Creates a new empty arrayList and instantiated all the methods
	public ScoreFacade() {
		allScoringMethods = new ArrayList<IGetScore>();
		instantiateScoringMethods();
	}
	
	// Instantiated all of the required scoring methods
	public void instantiateScoringMethods() {
		allScoringMethods.add(new MostClubs());
		allScoringMethods.add(new TenOfDiamonds());
		allScoringMethods.add(new TwoOfClubs());
		allScoringMethods.add(new Aces());
		allScoringMethods.add(new Jacks());
		allScoringMethods.add(new Surs());
	}
	
	// Iterates through all score methods and sums all the returned scores
	public int getCurrentScore(Hand pickedCards, Hand Surs) {
		int currentScore = 0;
		
		// newPickedCards = PickedCards + Surs, as we score with the entire hand
		// Surs still exists so we can count surs in the Surs method
		Hand newPickedCards = pickedCards;
		newPickedCards.insert(Surs, true);
		
		for (IGetScore method : allScoringMethods) {
			currentScore += method.getScore(newPickedCards, Surs);
		}
		return currentScore;
	}
}
