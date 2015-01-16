package com.apatti.thanniclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import model.Bid;
import model.Card;
import model.Hand;
import model.Suite;
import model.TDeck;
import model.TPlayer;

public class Game {

	static model.Game game;
	static TDeck deck;
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nameIndex = (new Random()).nextInt(3);
        String topPlayer="Test1";
        String leftPlayer="Test2";
        String rightPlayer="Test3";

        ArrayList<String> playerNames = new ArrayList<String>();
        playerNames.add(leftPlayer);
        playerNames.add(topPlayer);
        playerNames.add(rightPlayer);
        playerNames.add("User");
        game = new model.Game(playerNames);
        
        game.startNewRound();
        deck = new TDeck("");
        
        deck.shuffle();
        //Start of round
        int dealerId=game.getDealerId();
        dealCards(4);
        Collections.sort(game.getPlayers().get(3).getHand().getCards());
        for(TPlayer p :game.getPlayers())
        {
        	System.out.print(p.getId()+'-');
        	for(Card c:p.getHand().getCards())
        		System.out.print(c.getFaceValue()+',');
        	System.out.println();
        }
        bidProcess(false);
	}
	
	private static void bidProcess(boolean userCompleted)
    {
        int startIndex=(game.getDealerId()+1)%4;
        if(userCompleted)
            startIndex=0;
        for(int i=startIndex,j=0;j<4;i=(i+1)%4)
        {
            if(game.getCurrentRound().getRoundBidding().get(i)==Bid.PASS)
            {
                j++;
                continue;
            }
            if(game.getCurrentRound().getRoundTarget()==game.getCurrentRound().getRoundBidding().get(i))
            {
                break;
            }
            else
            {
                if(i==3) //user
                {
                    setUserBid();
                    return;
                }
                int teamId=(i+2)%4;
                Bid b = game.getPlayers().get(i).bid(game.getCurrentRound().getRoundTarget(),game.getCurrentRound().getRoundBidding().get(teamId),0);
                game.getCurrentRound().getRoundBidding().set(i,b);
                System.out.println(i+"-"+b);
                if(b!=Bid.PASS)
                {
                    game.getCurrentRound().setRoundTarget(b);
                }
                else
                	j++;
            }
        }

        return;
    }
	
	private static void setUserBid()
	{
		System.out.println("User-"+Bid.PASS);
		game.getCurrentRound().getRoundBidding().set(3, Bid.PASS);
		bidProcess(true);
	}
	private static void dealCards(int number)
    {
        int dealerId=game.getDealerId();
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<number;j++)
            {
                game.getPlayers().get(dealerId).getHand().addCard(deck.getCard());
            }
            dealerId=(dealerId+1)%4;
        }
    }


}
