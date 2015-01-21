package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a thanni game.
 * @author apatti
 *
 */
public class Game {
	//Usually 0 & 2 are one team, 1 & 3 another team
	private ArrayList<TPlayer> players;
	//Positive score reflects User team score, Negative score the bot score.
	private int score;
	private int dealerId;
	private Round currentRound;
	private TDeck deck;
	private DisplayCardListener displayCardListener;
	private RequestUserCardListener requestUserCardListener;
	private DisplayMessageListener displayMessageListener;
	
	public Game(ArrayList<String> playerNames,DisplayCardListener displayCardListener,RequestUserCardListener requestUserCardListener,DisplayMessageListener displayListener)
	{
		this.displayCardListener = displayCardListener;
		this.requestUserCardListener = requestUserCardListener;
		this.displayMessageListener=displayListener;
		players = new ArrayList<TPlayer>(playerNames.size());
		for(int i=0;i<playerNames.size();i++)
		{
			players.add(new TPlayer(this,i,playerNames.get(i)));
		}
		score=0;
		deck = new TDeck("");
		deck.shuffle();
		dealerId = new Random().nextInt(4);
	}
	
	public Round getCurrentRound()
	{
		return this.currentRound;
	}
	
	public int getDealerId()
	{
		return dealerId;
	}
	
	public void setDealerId(int dealerId)
	{
		this.dealerId=dealerId;
	}
	
	public int getScore()
	{
		return this.score;
	}
	public void setScore(int score)
	{
		this.score=score;
	}
	
	public void changeScore(int scoreDiff)
	{
		this.score+=scoreDiff;
	}
	
	public ArrayList<TPlayer> getPlayers()
	{
		return this.players;
	}
	
	
	public Round startNewRound()
	{
		currentRound = new Round(this.dealerId);
		return currentRound;
	}
	
	public void endRound()
	{
		int roundScore = this.currentRound.endRound();
		int currentScoreSign = (int)Math.signum(this.score);
		this.changeScore(roundScore);
		if(currentScoreSign!=(int)Math.signum(this.score))
			this.dealerId=(this.dealerId+1)%4;
		this.displayCardListener.onRoundEnd();
		this.displayMessageListener.onDisplayMessage("End of round");
	}
	
	public void userCardPlayed(Card c)
	{
		this.currentRound.addCurrentCard(c);
		playGame();
	}
	
	public void trumpCardRequested(int playerId)
	{
		System.out.println("Trump requested by :"+playerId+" IsPlayed:"+this.currentRound.getIsTrumpPlayed());
		if(!this.currentRound.getIsTrumpPlayed())
		{
			this.currentRound.setIsTrumpPlayed();
			System.out.println("Trump Team:"+this.currentRound.getRoundTrumpTeam());
			System.out.println("Trump Team Cards:"+this.getPlayers().get(this.currentRound.getRoundTrumpTeam()).getHand().getCards().size());
			this.getPlayers().get(this.currentRound.getRoundTrumpTeam()).getHand().addCard(this.currentRound.getRoundTrumpCard());
			System.out.println("Trump Team Cards:"+this.getPlayers().get(this.currentRound.getRoundTrumpTeam()).getHand().getCards().size());
			this.displayCardListener.onDisplayTrumpCard(playerId);
			this.playGame();
		}
	}
	
	
	
	public void playGame()
	{
		System.out.println("PlayerID:"+this.currentRound.getNextPlayerIndex());
		
		if(this.currentRound.getCurrentChalNumber()<6)
		{
			System.out.println("CHAL:"+this.currentRound.getCurrentChalNumber());
			while(this.currentRound.getCurrentChalCards().size()!=4)
			{
				if(this.currentRound.getNextPlayerIndex()==3)//user
				{
					this.requestUserCardListener.onRequestUserCard();
					return;
				}
				Card c=this.players.get(this.currentRound.getNextPlayerIndex()).playCard();
				if(c!=null)
					System.out.println("Card played by +"+this.currentRound.getNextPlayerIndex()+" is "+c.getFaceValue()+"-"+c.getSuit());
				else
					System.out.println("Card played by +"+this.currentRound.getNextPlayerIndex()+" is NUll and he requested :"+this.players.get(this.currentRound.getNextPlayerIndex()).getTrumpAsked());
				if(c!=null)
				{
					this.players.get(this.currentRound.getNextPlayerIndex()).getHand().removeCard(c);
					this.currentRound.addCurrentCard(c);
					this.displayCardListener.onDisplayUserCard(c);
				}
				else
				{
					if(this.players.get(this.currentRound.getNextPlayerIndex()).getTrumpAsked())
					{
						this.displayMessageListener.onDisplayMessage("Trump requested by "+this.players.get(this.currentRound.getNextPlayerIndex()).getName());
						this.trumpCardRequested(this.currentRound.getNextPlayerIndex());
					}
				}
			}
			this.currentRound.endChal();
			System.out.println("End of current chal");
			this.displayMessageListener.onDisplayMessage("End of current Chal");
			this.displayCardListener.onDisplayUserCard(null);
			return;
		}
	}
	
}
