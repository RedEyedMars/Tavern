package entities.heroes;

import items.Item;

import java.util.ArrayList;
import java.util.List;


public abstract class Reward {

	private List<Item> items;
	
	public Reward(){
		items = new ArrayList<Item>();
	}
	
	
	public int getMonetaryValue() {
		int score = 0;
		for(Item item:items){
			score+=item.getValue();
		}
		return score;
	}


	public int getMoralityValue() {
		int score = 0;
		for(Item item:items){
			score+=item.getHoly();
		}
		return score;
	}

}
