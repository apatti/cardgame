package model;

/**
 * 
 * @author apatti
 * Represents the player of the game.
 */
public class Player {
	private String name;
	private int id;
	private Hand hand;
	
	public Player(int id,String name)
	{
		this.id=id;
		this.name=name;
		this.hand = new Hand();
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Hand getHand()
	{
		return hand;
	}
}
