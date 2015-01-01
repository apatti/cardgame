package model;

public class Card {
	private int value;
	private String path;
	private String faceValue;
	private Suite suite;
	private int id;
	
	/***
	 * 
	 * @param faceValue
	 * @param suite
	 * @param value
	 * @param path
	 */
	public Card(String faceValue,Suite suite,int value,String path)
	{
		this.value=value;
		this.path=path;
		this.faceValue=faceValue;
		this.suite=suite;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public void setValue(int value)
	{
		this.value=value;
	}
	
	public String getPath()
	{
		return this.path;
	}
	
	public String getFaceValue()
	{
		return this.faceValue;
	}
	
	public Suite getSuit()
	{
		return this.suite;
	}
	public String getName()
	{
		return this.faceValue+" "+this.suite.toString();
	}

}
