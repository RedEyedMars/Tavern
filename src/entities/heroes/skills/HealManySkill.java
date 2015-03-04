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
public class HealManySkill extends  Skill{

	private boolean done = false;
	public HealManySkill(){
		super("HealMany","Mental");
	}

	@Override
	public void init(Entity source, Entity target, Area area){
		this.done = false;
	}

	@Override
	public Item act(Entity source, Entity target, Area area){
		if(source.isHero()&&target.isHero()){
			Hero hero = ((Hero)source);
			List<Magic> magicks = hero.getMagicks(MagicType.healing,MagicType.many);
			Magic best = null;
			float power = 0;
			for(Magic magic:magicks){
				if(hero.getAttribute("mp")>=best.getCost()&&magic.getPower(hero)>power){
					best = magic;
					power = magic.getPower(hero);
				}
			}
			if(best!=null){
				return best.perform(hero,target,area);
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
