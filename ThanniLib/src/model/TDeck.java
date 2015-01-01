package model;

import java.util.ArrayList;
import java.util.Collections;

public class TDeck extends Deck {
	private ArrayList<Card> cards;
	public TDeck()
	{
		super();
		cards= new ArrayList<Card>(24);
		for(Card card : super.getCards())
		{
			String faceValue = card.getFaceValue();
			if(card.getValue()>8)
			{
				switch(faceValue)
				{
				case "J":
					card.setValue(30);
					break;
				case "9":
					card.setValue(20);
					break;
				case "A":
					card.setValue(11);
					break;
				case "10":
					card.setValue(10);
					break;
				case "K":
					card.setValue(6);
					break;
				case "Q":
					card.setValue(5);
					break;
				}
				cards.add(card);
			}
		}
	}
	
	@Override
	public ArrayList<Card> getCards()
	{
		return cards;
	}
	
	@Override
	public void shuffle()
	{
		Collections.shuffle(cards);
	}

	public Card getCard()
	{
		if(cards.size()==0)
			return null;
		return cards.remove(0);
		
	}
}
