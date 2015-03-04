package entities.heroes.skills;

import java.util.List;

import items.Item;
import items.ItemType;
import entities.Entity;
import entities.heroes.Hero;
import entities.heroes.magicks.Magic;
import entities.heroes.magicks.MagicType;
import entities.stats.skills.Skill;
import game.Area;
public class HealOneSkill extends  Skill{

	private boolean done = false;
	public HealOneSkill(){
		super("HealOne","Mental");
	}

	@Override
	public void init(Entity source, Entity target, Area area){
		this.done = false;
	}

	@Override
	public Item act(Entity source, Entity target, Area area){
		if(source.isHero()&&target.isHero()){
			Hero hero = ((Hero)source);
			Hero sick = ((Hero)source);
			List<Magic> magicks = hero.getMagicks(MagicType.healing);
			Magic best = null;
			float difference = 100000;
			for(Magic magic:magicks){
				float diff = sick.getAttribute("maxHp")-(magic.getPower(hero)+sick.getAttribute("hp"));
				if(hero.getAttribute("mp")>=best.getCost()&&diff>=0&&diff<difference){
					best = magic;
					difference = diff;
				}
			}
			if(best!=null){
				return best.perform(hero,target,area);
			}
		}

		for(Item item:source.getItems()){
			if(item.is(ItemType.heal)){
				target.useItem(item);
				return null;
			}
		}
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
