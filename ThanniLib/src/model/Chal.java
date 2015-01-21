package model;

import java.util.ArrayList;

public class Chal {

	private ArrayList<Card> cardSet;
	private Round rd;
	private int winner=-1;
	
	public int getWinner()
	{
		if(winner==-1)
			winner = this.calcWinner();
		return winner;
	}
	
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
		//System.out.print("Cards:");
		/*for(int i=0;i<this.cardSet.size();i++)
			System.out.print(this.cardSet.get(i).getFaceValue()+" "+this.cardSet.get(i).getSuit()+",");
		System.out.println();*/
		//for(Card ci : this.cardSet)
		//	System.out.print(ci.getFaceValue()+" "+ci.getSuit()+",");
		//System.out.println();
	}
	
	
	//If trump then add 30 extra.
	private int calcWinner()
	{
		System.out.println("Winner:"+this.winner+" Next:"+this.rd.getNextPlayerIndex());
		int winCard = 0;
		Suite chalSuite = null;
		int winIndex=0;
		int index=this.rd.getNextPlayerIndex();
		for(Card c : this.cardSet)
		{
			System.out.println("Index:"+index+"-Card:"+c.getFaceValue()+"-"+c.getSuit()+"-"+c.getValue()+"-WinCard:"+winCard);
			if(chalSuite==null)
			{
				chalSuite=c.getSuit();
				System.out.println("Chal suite:"+chalSuite);
			}
			
			if(c.getSuit()==chalSuite&&c.getValue()>winCard)
			{
				winCard=c.getValue();
				winIndex=index;
			}
			else if((c.getSuit()!=chalSuite)&&(this.rd.getIsTrumpPlayed())&&(c.getSuit()==this.rd.getRoundTrumpCard().getSuit())&&(c.getValue()+30>winCard))
			{
				System.out.println("getIsTrumpPlayed:"+this.rd.getIsTrumpPlayed()+",Suit:"+c.getSuit()+",TSuit:"+this.rd.getRoundTrumpCard().getSuit()+",Value:"+c.getValue());
				winCard = c.getValue()+30;
				winIndex=index;
			}
			index=(index+1)%4;
		}
		return winIndex;
	}
	
}
