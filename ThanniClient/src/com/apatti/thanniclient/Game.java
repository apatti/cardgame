package com.apatti.thanniclient;

import java.util.HashMap;

import model.Card;
import model.Hand;
import model.TDeck;

public class Game {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TDeck deck = new TDeck("");
		deck.shuffle();
		
		HashMap<Integer,Hand> players = new HashMap<Integer,Hand>(4);
		for(int i=0;i<4;i++)
		{
			Hand hand = new Hand();
			for(int j=0;j<4;j++)
			{
				hand.addCard(deck.getCard());
			}
			players.put(i+1, hand);
		}
		
		for(int player : players.keySet())
		{
			System.out.println("Player:"+player);
			for(Card c : players.get(player).getCards())
			{
				System.out.println(c.getName());
			}
			System.out.println("HandValue:"+players.get(player).getHandValue());
		}
		
		for(int player:players.keySet())
		{
			Hand hand = players.get(player);
			for(int i=0;i<2;i++)
			{
				hand.addCard(deck.getCard());
			}
			players.put(player, hand);
		}
		
		for(int player : players.keySet())
		{
			System.out.println("Player:"+player);
			for(Card c : players.get(player).getCards())
			{
				System.out.println(c.getName());
			}
			System.out.println("HandValue:"+players.get(player).getHandValue());
		}
	}

}
