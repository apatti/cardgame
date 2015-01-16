package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the cards dealt to a player
 * @author apatti
 *
 */
public class Hand{
	private ArrayList<Card> cards;
	
	public Hand()
	{
		cards = new ArrayList<Card>(6);
	}
	
	public void addCard(Card card)
	{
		this.cards.add(card);
	}
	
	public ArrayList<Card> getCards()
	{
		return cards;
	}
	
	public Card playCard(int id)
	{
		for(int i=0;i<cards.size();i++)
		{
			if(cards.get(i).getId()==id)
			{
				return cards.remove(i);
			}
		}
		return null;
	}
	
	public int getHandValue()
	{
		int value=0;
		for(Card c : cards)
		{
			value+=c.getValue();
		}
		return value;
	}
	
	public void moveCard(Card moveCard,Card moveToCard)
	{
		int cardLocation=0;
		for(int i=0;i<cards.size();i++)
		{
			if(cards.get(i).getId()==moveCard.getId())
			{
				cards.remove(i);
				cardLocation=i;
				break;
			}
		}
		for(int i=0;i<cards.size();i++)
		{
			if(cards.get(i).getId()==moveToCard.getId())
			{
				cards.add(i, moveCard);
				return;
			}
		}
		cards.add(cardLocation, moveCard);
	}
	
}
