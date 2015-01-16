package model;

import java.util.Comparator;

public class CardValueComparator implements Comparator<Card>{

	@Override
	public int compare(Card c1, Card c2) {
		// TODO Auto-generated method stub
		
		return c2.getValue()-c1.getValue();
	}

}
