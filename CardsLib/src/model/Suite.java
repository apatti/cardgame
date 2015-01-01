package model;

public enum Suite {
	SPADE(0),
	HEART(1),
	DIAMOND(2),
	CLUB(3);
	
	int value;
	Suite(int value){
		this.value=value;
	}
	
}
