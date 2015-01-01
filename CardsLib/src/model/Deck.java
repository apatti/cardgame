package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Deck {
	private ArrayList<Card> cards;
	
	public Deck(String imageResourcePath)
	{
		if(imageResourcePath.isEmpty())
			imageResourcePath = "/images/";
		this.cards = new ArrayList<Card>(56);
		for(Suite suite: Suite.values())
		{
			for(int i=0;i<13;i++)
			{
				String faceValue="";
				int value=0;
				URL imagePath = getClass().getResource(imageResourcePath+String.valueOf((suite.value*13)+i+1)+".png");
				if(i>0&&i<10)
				{
					value=i+1;
					faceValue = String.valueOf(value);
				}
				else
				{
					value=10;
					switch(i)
					{
					case 10:
						faceValue="J";
						break;
					case 11:
						faceValue="Q";
						break;
					case 12:
						faceValue="K";
						break;
					case 0:
						faceValue="A";
						break;
					}
				}
				this.cards.add(new Card(faceValue, suite, value, imagePath.getPath()));
			}
		}
	}
	
	public void shuffle()
	{
		Collections.shuffle(cards);
	}

	public ArrayList<Card> getCards()
	{
		return this.cards;
	}

	
}
