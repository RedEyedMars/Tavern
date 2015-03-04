package entities.heroes.magicks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.Area;
import items.Item;
import entities.Entity;
import entities.heroes.Hero;
import entities.stats.skills.Skill;

public abstract class Magic extends Skill{

	protected List<MagicType> types;
	protected int cost;
	
	public Magic(String name,int cost, MagicType... types) {
		super(name, "Mental");
		this.types = new ArrayList<MagicType>(Arrays.asList(types));
		this.cost = cost;
	}

	public float getCost() {
		return cost;
	}
	
	@Override
	public Item perform(Entity source, Entity target, Area area){
		Hero hero = (Hero)source;
		hero.useAttribute("mp",cost);
		return perform(source,target,area);
	}

	public boolean is(MagicType type) {
		return types.contains(type);
	}

	public abstract float getPower(Hero hero);

}
