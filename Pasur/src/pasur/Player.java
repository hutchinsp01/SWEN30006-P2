package pasur;

/**
 * @author Alireza Ostovar
 * 29/09/2021
 */

/**
 * Modified by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */

import ch.aplu.jcardgame.*;
import gameLog.IObserver;
import gameLog.GameLog;
import gameLog.IObservable;
import scoring.ScoreFacade;

import java.util.*;

public abstract class Player implements IObservable
{
    private static final int TARGET_VALUE = 11;

    protected int id;
    protected Hand hand;
    protected Hand pickedCards;
    protected Hand surs;
    
    protected int scoreBeforeRound;
    protected ScoreFacade scoreFacade;
    private ArrayList<IObserver> observers = new ArrayList<IObserver>();

    protected Player(int id)
    {
        this.id = id;
        this.scoreBeforeRound = 0;
        this.scoreFacade = new ScoreFacade();
        registerObserver(GameLog.getInstance());
    }

    /**
     *
     * @param pool current pool
     * @return the played card and the set of cards this player wants to pick up from the pool.
     */
    public final Map.Entry<Card, Set<Card>> playCard(Hand pool)
    {
        Card playedCard = selectToPlay();
        Set<Card> cardsToPick = null;
        if(playedCard != null)
        {
        	notifyObserver(toString() + " plays " + Pasur.toString(playedCard));
        	//System.out.println(toString() + " plays " + Pasur.toString(playedCard));

            cardsToPick = pickCards(pool, playedCard);
        }

        return new AbstractMap.SimpleEntry<>(playedCard, cardsToPick);
    }

    protected Set<Card> pickCards(Hand pool, Card playedCard)
    {
        List<Card> poolCards = pool.getCardList();

        Set<Card> cardsToPick = new HashSet<>();
        if(playedCard.getRank() == Rank.JACK)
        {
            for(int i = 0, len = poolCards.size(); i < len; i++)
            {
                Card card = poolCards.get(i);
                if(card.getRank() != Rank.KING && card.getRank() != Rank.QUEEN)
                {
                    // a jack picks any card except kings and queens
                    cardsToPick.add(card);
                }
            }
        }else if(playedCard.getRank() == Rank.KING || playedCard.getRank() == Rank.QUEEN)
        {
            Card candidateCardToPick = null;
            for(int i = 0, len = poolCards.size(); i < len; i++)
            {
                Card card = poolCards.get(i);
                if(card.getRank() == playedCard.getRank())
                {
                    // a king/queen can only pick another king/queen
                    candidateCardToPick = card;
                    if(candidateCardToPick.getSuit() == Suit.CLUBS)
                    {
                        // a club is preferred as it can help us to get to 7 clubs which will give us 7 points at the end
                        break;
                    }
                }
            }
            if(candidateCardToPick != null)
            {
                cardsToPick.add(candidateCardToPick);
            }
        }else
        {
            // the played card is a numeric card, so we need to see what are the potential sets of cards we can pick with it
            List<Set<Card>> candidateSetsOfCardsToPick = new ArrayList<>();
            int targetValue = TARGET_VALUE - playedCard.getValue();
            if(!poolCards.isEmpty())
            {
                findSetsOfCardsSummingToTarget(poolCards, targetValue, candidateSetsOfCardsToPick);
                if(!candidateSetsOfCardsToPick.isEmpty())
                {
                    Set<Card> bestSet = chooseBestCandidateSetToPick(candidateSetsOfCardsToPick);
                    for(Card card : bestSet)
                    {
                        cardsToPick.add(card);
                    }
                }
            }
        }

        return cardsToPick;
    }

    protected Set<Card> chooseBestCandidateSetToPick(List<Set<Card>> candidateSetsOfCardsToPick)
    {
        double valueGivenTo10ofDiamond = 3;
        double valueGivenTo2ofClubs = 2;
        double valueGivenToAce = 1;
        double valueGivenToJack = 1;
        double valueGivenToAcardOfClubs = 0.1; // helps to go towards collecting 7 cards of clubs
        Set<Card> setWithMaxValue = null;
        double maxValue = -1;
        for(Set<Card> set : candidateSetsOfCardsToPick)
        {
            double setValue = 0;
            for(Card card : set)
            {
                if(card.getRank() == Rank.TEN && card.getSuit() == Suit.DIAMONDS)
                {
                    setValue += valueGivenTo10ofDiamond;
                }else if(card.getRank() == Rank.TWO && card.getSuit() == Suit.CLUBS)
                {
                    setValue += valueGivenTo2ofClubs;
                }else if(card.getRank() == Rank.ACE)
                {
                    setValue += valueGivenToAce;
                }else if(card.getRank() == Rank.JACK)
                {
                    setValue += valueGivenToJack;
                }

                if(card.getSuit() == Suit.CLUBS)
                {
                    setValue += valueGivenToAcardOfClubs;
                }
            }

            if(setValue > maxValue)
            {
                maxValue = setValue;
                setWithMaxValue = set;
            }
        }

        return setWithMaxValue;
    }

    private void findSetsOfCardsSummingToTarget(List<Card> cards, int targetValue, List<Set<Card>> setsOfCards)
    {
        _findSetsOfCardsSummingToTarget(cards, setsOfCards, targetValue, new ArrayList<>());
    }

    private void _findSetsOfCardsSummingToTarget(List<Card> cards, List<Set<Card>> setsOfCards, int targetValue,
                                                 List<Card> partial)
    {
        int sum = 0;
        for(int i = 0, len = partial.size(); i < len; i++)
        {
            sum += partial.get(i).getValue();
        }

        if (sum == targetValue)
        {
            Set<Card> set = new HashSet<>(partial.size());
            set.addAll(partial);
            setsOfCards.add(set);
        }

        if (sum >= targetValue)
        {
            return;
        }

        for(int i = 0; i < cards.size(); i++)
        {
            ArrayList<Card> remainingCards = new ArrayList<>();
            Card card = cards.get(i);
            for (int j = i + 1; j < cards.size(); j++)
            {
                remainingCards.add(cards.get(j));
            }

            List<Card> partialCards = new ArrayList<>(partial);
            partialCards.add(card);

            _findSetsOfCardsSummingToTarget(remainingCards, setsOfCards, targetValue, partialCards);
        }
    }

    public Hand getHand()
    {
        return hand;
    }

    public void setHand(Hand hand)
    {
        this.hand = hand;
    }

    public Hand getPickedCards()
    {
        return pickedCards;
    }

    public void setPickedCards(Hand pickedCards)
    {
        this.pickedCards = pickedCards;
    }

    public Hand getSurs()
    {
        return surs;
    }

    public void setSurs(Hand surs)
    {
        this.surs = surs;
    }

    public void reset()
    {
    	
    	// update prev round score to be cur score
    	scoreBeforeRound = getScore();
    	
        hand.removeAll(false);
        pickedCards.removeAll(false);
        surs.removeAll(false);
    }

    public String toString()
    {
        return "Player" + id;
    }

    public int getScore()
    {
    	// score = players score before this round + their hands current score
        return scoreBeforeRound + scoreFacade.getCurrentScore(pickedCards, surs);
    }
    
    @Override
	public void registerObserver(IObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserver(String data) {
		observers.forEach(o -> o.update(data));	
	}

    abstract Card selectToPlay();
    
}
