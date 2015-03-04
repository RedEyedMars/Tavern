package items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.Entity;
import entities.heroes.Hero;
import entities.heroes.histories.CreateItemEvent;
import entities.heroes.histories.DropItemEvent;
import entities.heroes.histories.GiveItemToEvent;
import entities.heroes.histories.History;
import entities.heroes.histories.UseItemEvent;
import entities.monsters.Monster;
import game.Area;
import game.Square;

public class Item implements Cloneable{
	protected String name;
	protected int value;
	protected int holy;
	protected History history;
	protected List<ItemType> types = new ArrayList<ItemType>();
	protected boolean equipped = false;
	
	public Item(){
		super();
	}
	
	public Item(String string, Integer value, ItemType... types) {
		super();
		this.name = string;
		this.value = value;
		history = new History();
		history.append(new CreateItemEvent(this));
		this.types.addAll(Arrays.asList(types));
	}
	public void giveTo(Hero hero){
		history.append(new GiveItemToEvent(this,hero));
		holy+=hero.getAttribute("morality");
		hero.addItem(this);
	}
	public void giveTo(Monster monster){
		history.append(new GiveItemToEvent(this,monster));
		holy+=monster.getMorality();
		monster.addItem(this);
	}
	public void drop( Area area, Square square){
		history.append(new DropItemEvent(this,square,area));
		square.addItem(this);
	}
	public Item clone(){
		try {
			Item clone = this.getClass().newInstance();
			clone.name = this.name;
			clone.value = value;
			clone.history = new History();
			clone.history.append(new CreateItemEvent(clone));
			clone.types = types;
			return clone;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getName() {
		return name;
	}
	public int getValue(){
		return value;
	}
	public int getHoly() {
		return holy;
	}
	
	public void equip(){
		equipped = true;
	}
	
	public void unequip(){
		equipped = false;
	}

	public boolean isEquipped() {
		return equipped;
	}

	public boolean is(ItemType type) {
		return types.contains(type);
	}

	public void useOn(Entity entity) {
		history.append(new UseItemEvent(this,entity));
	}

}
