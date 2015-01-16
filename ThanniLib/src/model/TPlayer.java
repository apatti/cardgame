package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TPlayer extends Player {

	private Game game;
	private Bid bid;
	private Card trump;
	private boolean trumpAsked;
	
	public TPlayer(Game game,int id, String name) {
		super(id, name);
		this.game=game;
		this.bid=Bid.DIDNTBID;
		this.trump=null;
		this.trumpAsked=false;
	}
	
	public Card getTrump()
	{
		if(this.trump==null)
			selectTrump();
		return this.trump;
	}
	
	private void selectTrump()
	{
		HashMap<Suite,ArrayList<Card>> handDetails = this.getHandDetails();
		Suite trumpSuite=null;
		int max=0;
		for(Suite s:handDetails.keySet())
		{
			if(handDetails.get(s).size()>max)
			{
				max=handDetails.get(s).size();
				trumpSuite=s;
			}
		}
		this.trump=handDetails.get(trumpSuite).get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Bid bid(Bid currentBid,Bid teamBid,int order)
	{
		if(currentBid==this.bid)
			return this.bid;
		//If we already bid and teamBid is more then pass.
		if(bid!=Bid.DIDNTBID&&teamBid.value>bid.value)
		{
			this.trump=null;
			return Bid.PASS;
		}
		
		//If current bid is 200 or thanni then pass.
		if(currentBid.value>=200)
		{
			this.trump=null;
			return Bid.PASS;
		}
		
		Collections.sort(this.getHand().getCards());
		HashMap<Suite,ArrayList<Card>> handDetails = new HashMap<Suite,ArrayList<Card>>();
		for(Card c :this.getHand().getCards())
		{
			if(handDetails.containsKey(c.getSuit()))
			{
				handDetails.get(c.getSuit()).add(c);
			}
			else
			{
				ArrayList<Card> suitCards = new ArrayList<Card>();
				suitCards.add(c);
				handDetails.put(c.getSuit(),suitCards);
			}
		}
		
		this.bid=Bid.PASS;
		ArrayList<Card> cardMemory = new ArrayList<Card>(4);
		for(Suite s:handDetails.keySet())
		{
			ArrayList<Card> suitCards = handDetails.get(s);
			if(suitCards.get(0).getValue()!=30||suitCards.size()==1)
				continue;
			
			if(suitCards.size()==4)
			{
				this.trump=suitCards.get(3);
				this.bid=Bid.JOHN;
				return Bid.JOHN;
			}
			
			if(suitCards.size()==2)
			{
				if(currentBid.value<Bid.BEAT.value)
				{
					this.bid=Bid.BEAT;
					if(this.trump==null || suitCards.get(1).getValue()>this.trump.getValue())
						this.trump=suitCards.get(1);
				}
			}
			
			if(suitCards.size()==3)
			{
				this.trump = suitCards.get(2);
				if(currentBid.value<Bid.BEAT.value)
				{
					return Bid.BEAT;
				}
				if(currentBid.value<Bid.SIXTY.value)
				{
					return Bid.SIXTY;
				}
				if(currentBid.value<Bid.SEVENTY.value)
				{
					if((suitCards.get(1).getValue()==20)||(suitCards.get(1).getValue()==11&&suitCards.get(2).getValue()==10))
						return Bid.SEVENTY;
					else
					{
						this.trump=null;
						return Bid.PASS;
					}
				}
				if(currentBid.value<Bid.EIGHTY.value)
				{
					if(suitCards.get(1).getValue()==20&&suitCards.get(2).getValue()==11)
						return Bid.SEVENTY;
					else
					{
						this.trump=null;
						return Bid.PASS;
					}
				}
			}
		}
		
		if(this.bid==Bid.PASS)
			this.trump=null;
		return this.bid;
	}

	@SuppressWarnings("unchecked")
	private HashMap<Suite,ArrayList<Card>> getHandDetails()
	{
		Collections.sort(this.getHand().getCards());
		HashMap<Suite,ArrayList<Card>> handDetails = new HashMap<Suite,ArrayList<Card>>();
		for(Card c :this.getHand().getCards())
		{
			if(handDetails.containsKey(c.getSuit()))
			{
				handDetails.get(c.getSuit()).add(c);
			}
			else
			{
				ArrayList<Card> suitCards = new ArrayList<Card>();
				suitCards.add(c);
				handDetails.put(c.getSuit(),suitCards);
			}
		}
		return handDetails;
	}
	
	@SuppressWarnings("unchecked")
	public Card playCard()
	{
		Card trumpCard = this.game.getCurrentRound().getRoundTrumpCard();
		HashMap<Suite,ArrayList<Card>> handDetails = this.getHandDetails();
		Collections.sort(this.getHand().getCards(), new CardValueComparator());
		int handSize = this.getHand().getCards().size();
		if(this.trumpAsked)
		{
			ArrayList<Card> trumpCards = handDetails.get(trumpCard.getSuit());
			if(trumpCards!=null)
			{
				return trumpCards.get(trumpCards.size()-1);
			}
			else
			{
				return this.getHand().getCards().get(handSize-1);
			}
		}
		ArrayList<Card> playedCards = game.getCurrentRound().getRoundPlayedCards();
		ArrayList<Card> rdChals = game.getCurrentRound().getCurrentChalCards();
		Suite leadSuite = null;
		if(rdChals.size()!=0)
			leadSuite=rdChals.get(0).getSuit();
		Card teamCard=null;
		switch(rdChals.size())
		{
		case 0:
			if(this.getHand().getCards().get(0).getValue()==30)
				return this.getHand().getCards().get(0);
			else
			{
				if(trumpCard==null)
					return this.getHand().getCards().get(handSize-1);
				else
				{
					for(Card c: this.getHand().getCards())
					{
						if(c.getSuit()!=trumpCard.getSuit())
							return c;
					}
					return this.getHand().getCards().get(handSize-1);
				}
			}
		case 1:
			if(handDetails.containsKey(leadSuite))
			{
				ArrayList<Card> suiteCards = handDetails.get(leadSuite);
				if(suiteCards.size()==1)
					return suiteCards.get(0);
				return suiteCards.get(suiteCards.size()-1);	
			}
			else
			{
				if(trumpCard==null)
				{
					this.trumpAsked=true;
					return null;
				}
				else
				{
					ArrayList<Card> trumpCards = handDetails.get(trumpCard.getSuit());
					return trumpCards.get(trumpCards.size()-1);
				}
			}
		case 2:
		case 3:
			teamCard = rdChals.get(rdChals.size()-1);
			Collections.sort(rdChals);
			if(handDetails.containsKey(leadSuite))
			{
				ArrayList<Card> suiteCards = handDetails.get(leadSuite);
				if(suiteCards.size()==1||rdChals.get(0)==teamCard)
					return suiteCards.get(0);
				return suiteCards.get(suiteCards.size()-1);	
			}
			else
			{
				if(rdChals.get(0)==teamCard)
				{
					if(trumpCard==null)
						return this.getHand().getCards().get(0);
					for(Card c: this.getHand().getCards())
					{
						if(c.getSuit()!=trumpCard.getSuit())
							return c;
					}
					return this.getHand().getCards().get(handSize-1);
				}
				else
				{
					if(trumpCard==null)
					{
						this.trumpAsked=true;
						return null;
					}
					else
					{
						ArrayList<Card> trumpCards = handDetails.get(trumpCard.getSuit());
						return trumpCards.get(trumpCards.size()-1);
						
					}
				}
			}
			default:
				return null;
		}
	}
}
