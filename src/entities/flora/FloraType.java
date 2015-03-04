package entities.flora;

import game.Area;
import items.Item;

import java.util.*;

public class FloraType {
	private int[] hpRange;
	private List<Item> drops;
	private String name;
	public FloraType(String name){
		super();
		this.name = name;
		this.drops = new ArrayList<Item>();
	}
	public void setHpRange(int... hp){
		hpRange = hp;
	}
	public void addDrop(Item drop){
		drops.add(drop);
	}
	public Flora create(Area area) {
		Flora flora = new Flora(
				name,
				hpRange[(int)(hpRange.length*Math.random())],
				drops.get((int)(drops.size()*Math.random())),
				area
				);
		return flora;
	}

	public Flora createRival(Area area) {
		// TODO Auto-generated method stub
		return null;
	}

	public Flora createPrey(Area area) {
		// TODO Auto-generated method stub
		return null;
	}

}
