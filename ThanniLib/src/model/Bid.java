package model;

public enum Bid {
	DIDNTBID(-1,""),
	PASS(0,"PASS!!"),
	BEAT(150,"BEAT!!"),
	SIXTY(160,"SIXTY!!"),
	SEVENTY(170,"SEVENTY!!"),
	EIGHTY(180,"EIGHTY!!"),
	NINETY(190,"NINETY!!"),
	JOHN(200,"JAAN!!"),
	THANNI(300,"THANNI!!"),
	AATHBAND(320,"AATH BAND!!");
	
	int value;
	String valueString;
	Bid(int value,String valueString){
		this.value=value;
		this.valueString = valueString;
	}
	
	public int getValue()
	{
		return value;
	}
}
