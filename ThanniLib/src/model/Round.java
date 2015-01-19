package model;

import java.util.ArrayList;

/**
 * Represents one round in a game.
 * @author apatti
 *
 */
public class Round {
	private Bid roundTarget;
	private Card roundTrump;
	private ArrayList<Card> roundUserTeamWinnings;
	private ArrayList<Card> roundBotTeamWinnings;
	private int roundTrumpTeam;
	private ArrayList<Card> roundPlayedCards;
	private boolean isTrumpPlayed;
	private ArrayList<Chal> roundChals;
	private int dealerId;
	private ArrayList<Bid> roundBidding;
	private int bidderIndex;
	
	public Round(int dealerId)
	{
		roundTarget=Bid.PASS;
		roundUserTeamWinnings = new ArrayList<Card>();
		roundBotTeamWinnings = new ArrayList<Card>();
		roundPlayedCards = new ArrayList<Card>(24);
		roundChals = new ArrayList<Chal>(6);
		isTrumpPlayed=false;
		this.dealerId=dealerId;
		bidderIndex = (this.dealerId+1)%4;
		roundBidding = new ArrayList<Bid>(4);
		roundBidding.add(Bid.DIDNTBID);
		roundBidding.add(Bid.DIDNTBID);
		roundBidding.add(Bid.DIDNTBID);
		roundBidding.add(Bid.DIDNTBID);
	}
	
	public void addRoundBidding(int position,Bid bid)
	{
		this.roundBidding.set(position, bid);
	}
	
	public ArrayList<Bid> getRoundBidding()
	{
		return this.roundBidding;
	}
	
	public void setIsTrumpPlayed()
	{
		this.isTrumpPlayed=true;
	}
	public boolean getIsTrumpPlayed()
	{
		return this.isTrumpPlayed;
	}
	
	public void setRoundTarget(Bid target)
	{
		this.roundTarget=target;
	}
	public Bid getRoundTarget()
	{
		return this.roundTarget;
	}
	
	public ArrayList<Card> getRoundPlayedCards()
	{
		return this.roundPlayedCards;
	}
	
	public void addNewChal()
	{
		this.roundChals.add(new Chal(this));
	}
	
	public void addCurrentCard(Card c)
	{
		this.roundChals.get(this.roundChals.size()-1).addCard(c);
		this.roundPlayedCards.add(c);
	}
	
	public int endChal()
	{
		int winner = this.roundChals.get(this.roundChals.size()-1).winner();
		//winner=1 then dealer team win else other team win.
		//this.addRoundWinnings((1-winner)-dealerId%2, this.roundChals.get(this.roundChals.size()-1).getCardSet());
		if(winner==1)
		{
			this.addRoundWinnings(dealerId%2, this.roundChals.get(this.roundChals.size()-1).getCardSet());
		}
		else
			this.addRoundWinnings(1-dealerId%2, this.roundChals.get(this.roundChals.size()-1).getCardSet());
		
		return winner;
			
	}
	
	public ArrayList<Card> getCurrentChalCards()
	{
		return this.roundChals.get(this.getCurrentChalNumber()).getCardSet();
	}
	
	public int getCurrentChalNumber()
	{
		if(this.roundChals.size()==0)
			addNewChal();
		
		return this.roundChals.size()-1;
	}
	
	public void setRoundTrumpCard(Card roundTrump)
	{
		this.roundTrump=roundTrump;
	}
	public Card getRoundTrumpCard()
	{
		if(this.isTrumpPlayed)
			return this.roundTrump;
		else
			return null;
	}
	public void setRoundTrumpTeam(int roundTrumpTeam)
	{
		this.roundTrumpTeam=roundTrumpTeam;
	}
	public int getRoundTrumpTeam()
	{
		return this.roundTrumpTeam;
	}
	
	public void addRoundWinnings(int id,ArrayList<Card> cards)
	{
		if(id%2==0)
			this.roundBotTeamWinnings.addAll(cards);
		else
			this.roundUserTeamWinnings.addAll(cards);
	}
	
	public int getTeamRoundScore(int id)
	{
		int score=0;
		if(id%2==0)
		{
			for(Card c : this.roundBotTeamWinnings)
			{
				score+=c.getValue();
			}
			return score;
		}
		else
		{
			for(Card c : this.roundUserTeamWinnings)
			{
				score+=c.getValue();
			}
			return score;
			
		}
	}
	
	public int endRound()
	{
		int scoreSign=0;
		if(this.roundTrumpTeam%2==0)
		{
			scoreSign=1;
		}
		else
			scoreSign=-1;
		
		if(this.getTeamRoundScore((this.roundTrumpTeam%2))>=this.roundTarget.value)
		{
			if(this.roundTarget.value<200)
				return scoreSign*1;
			else
				return scoreSign*2;
		}
		else
		{
			if(this.roundTarget.value<200)
				return scoreSign*-1;
			else
				return scoreSign*-2;
		}
		
	}
	
}
