package items;

import entities.Entity;
import entities.heroes.Hero;
import entities.monsters.Monster;

public class HealingItem extends Item {
	private float healingValue;

	public HealingItem(String name, int value, float healingValue, ItemType... types){
		super(name,value,types);
		this.healingValue = healingValue;
	}
	
	@Override
	public HealingItem clone(){
		HealingItem item = (HealingItem) super.clone();
		item.healingValue = healingValue;
		return item;
	}
	
	@Override
	public void useOn(Entity entity){
		if(entity.isHero()){
			((Hero)entity).heal(healingValue);
		}
		else if(entity.isMonster()){
			((Monster)entity).heal(healingValue);
		}
		super.useOn(entity);
	}
}
