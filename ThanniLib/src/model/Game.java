package model;

import java.util.ArrayList;

/**
 * Represents a thanni game.
 * @author apatti
 *
 */
public class Game {
	private ArrayList<Player> players;
	private int score;
	private int dealerId;
	private Round currentRound;
	
	public Game(int playerCount)
	{
		players = new ArrayList<Player>(playerCount);
		score=0;
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
	
	public ArrayList<Player> getPlayers()
	{
		return this.players;
	}
}
