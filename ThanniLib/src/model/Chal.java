package model;

import java.util.ArrayList;

public class Chal {

	private ArrayList<Card> cardSet;
	private Round rd;
	
	public Chal(Round rd) {
		// TODO Auto-generated constructor stub
		cardSet = new ArrayList<Card>(4);
		this.rd=rd;
	}
	
	public ArrayList<Card> getCardSet()
	{
		return this.cardSet;
	}
	
	public void addCard(Card c)
	{
		this.cardSet.add(c);
	}
	
	
	//If trump then add 30 extra.
	public int winner()
	{
		int winCard = 0;
		Suite chalSuite = null;
		int winIndex=0;
		int index=0;
		for(Card c : this.cardSet)
		{
			if(chalSuite==null)
				chalSuite=c.getSuit();
			
			if(c.getSuit()==chalSuite&&c.getValue()>winCard)
			{
				winCard=c.getValue();
				winIndex=index;
			}
			else if(this.rd.getIsTrumpPlayed()&&c.getSuit()==this.rd.getRoundTrumpCard().getSuit()&&c.getValue()+30>winCard)
			{
				winCard = c.getValue()+30;
				winIndex=index;
			}
			index++;
		}
		return winIndex%2;
	}
	
}
