package model;

public class Card implements Comparable<Card> {
	private int value;
	private String path;
	private String faceValue;
	private Suite suite;
	private int id;
	private int resource_id;
	
	/***
	 * 
	 * @param faceValue
	 * @param suite
	 * @param value
	 * @param path
	 */
	public Card(int id,String faceValue,Suite suite,int value,String path)
	{
		this.id=id;
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
	public void setPath(String path)
	{
		this.path=path;
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
	
	public int getId()
	{
		return this.id;
	}
	
	public int getResourceId()
	{
		return this.resource_id;
	}
	
	public void setResourceId(int resource_id)
	{
		this.resource_id=resource_id;
	}

	@Override
	public int compareTo(Card compareCard) {
		// TODO Auto-generated method stub
		int compareSuite=compareCard.getSuit().value;
		if(this.suite.value==compareSuite)
		{
			return compareCard.value-this.value;
		}
		else
			return compareSuite-this.suite.value;
	}
	
	@Override
	public boolean equals(Object compareCard){
		int compareValue = ((Card)compareCard).getValue();
		return compareValue==this.value;
	}


}
