package model;

import java.util.Collection;
import java.util.HashMap;

/**
 * Represents the cards dealt to a player
 * @author apatti
 *
 */
public class Hand {
	private HashMap<String,Card> cards;
	
	public Hand()
	{
		cards = new HashMap<String,Card>(6);
	}
	
	public void addCard(Card card)
	{
		this.cards.put(card.getName(), card);
	}
	
	public Collection<Card> getCards()
	{
		return (Collection<Card>) cards.values();
	}
	
	public Card playCard(String name)
	{
		if(cards.containsKey(name))
		{
			Card card = cards.get(name);
			cards.remove(name);
			return card;
		}
		else
			return null;
	}
	
	public int getHandValue()
	{
		int value=0;
		for(Card c : cards.values())
		{
			value+=c.getValue();
		}
		return value;
	}
	
}
