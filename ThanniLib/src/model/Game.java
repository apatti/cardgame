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
	
	public Game(ArrayList<String> playerNames)
	{
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
	}
	
	public void StartGame()
	{
		//Start New Round
		//Deal cards
		//Play Round
		//End Round
		//If Game is still on, repeat
	}
	
}
