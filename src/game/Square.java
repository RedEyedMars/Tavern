package game;

import items.Item;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import entities.*;

public class Square {
	private int x;
	private int y;
	private Entity occupied;
	private List<Item> items;
	public Square(int m, int n) {
		x=m;
		y=n;
		items = new ArrayList<Item>();
	}

	public void occupy(Entity entity)
	{
		occupied = entity;
	}

	public boolean isOccupied(){
		return occupied!=null;
	}

	public void moveOff()
	{
		occupied = null;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public Entity getOccupied() {
		return occupied;
	}
	
	@Override
	public String toString(){
		return "("+x+","+y+")";
	}

	public Color getColour() {
		if(occupied==null){
			return Color.white;
		}
		else {
			return occupied.getColour();
		}
	}

	public void addItem(Item item) {
		items.add(item);
	}
}
