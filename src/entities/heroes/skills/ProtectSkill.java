package entities.heroes.skills;

import items.Item;
import items.ItemType;
import entities.Entity;
import entities.heroes.Hero;
import entities.stats.skills.Skill;
import game.Area;
public class ProtectSkill extends  Skill{

	private boolean done = false;
	public ProtectSkill(){
		super("Protect","Physical");
	}

	@Override
	public void init(Entity source, Entity target, Area area){
		this.done = false;
	}

	@Override
	public Item act(Entity source, Entity target, Area area){
		for(Item item:source.getItems()){
			if(item.isEquipped()&&item.is(ItemType.shield)){
				target.addDamageSharer(source);
				return null;
			}
		}
		for(Item item:source.getItems()){
			if(item.isEquipped()&&item.is(ItemType.armour)){
				target.addDamageSharer(source);
				return null;
			}
		}
		target.addDamageSharer(source);
		return null;
	}

	@Override
	public Object get(String name){
		if("done".equals(name)){
			return done;
		}
		else return null;
	}
	
	@Override
	public boolean isUsable(Hero hero){
		if(hero.is("Blinded")||hero.getAttribute("eyeSight")<2){
			return false;
		}
		else return super.isUsable(hero);
	}
	
	@Override
	public float getRange(Hero hero){
		return hero.getAttribute("eyeSight");
	}

}
