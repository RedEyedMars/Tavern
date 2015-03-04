package entities.flora;

import items.Item;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.Entity;
import game.Area;

public class Flora extends Entity {
	private int hp;
	private int maxHp;
	private Item item;
	private Area area;
	public Flora(String name, int hp, Item item, Area area){
		super(name);
		this.hp = hp;
		this.maxHp = hp;
		this.item = item;
		this.area = area;
	}
	@Override
	public Item damage(float damage, Entity effector) {
		damage = shareDamage(damage, effector);
		hp-=damage;
		if(hp<=0)
		{

			this.occupy.moveOff();
			this.occupy = null;
			this.area.removeFlora(this);
			return item.clone();
		}
		else {
			return null;
		}
	}

	@Override
	public void undamage(int damage, Entity source) {
		if(hp<=0)
		{
			resetSquare();
			this.area.addFlora(this);
		}
		hp+=damage;
	}
	
	@Override
	public void update() {
		if(hp<maxHp&&Math.random()<0.1)hp++;
	}

	@Override
	public Color getColour() {
		return Color.green;
	}

	@Override
	public void giveBack(Item item) {
		//plants give clones
	}
	
	@Override
	public boolean isDead() {
		return hp<=0;
	}
	
	public List<Item> getItems(){
		return new ArrayList<Item>(Arrays.asList(item));
	}
}
