package entities.stats.skills;

import items.Item;

import java.util.HashMap;
import java.util.Map;

import entities.Entity;
import entities.heroes.Hero;
import entities.heroes.histories.UseSkillEvent;
import game.Area;

public abstract class Skill{
	
	private String name;
	private String type;

	protected int experience = 0;
	protected Map<String, Integer> requiredAttributes = new HashMap<String,Integer>(); 
	
	public Skill(String name) {
		this.name = name;
		this.type = "Physical";
	}
	
	public Skill(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public Item perform(Entity source, Entity target, Area area){
		++experience;
		if(source.isHero()){
			Hero hero = (Hero) source;
			hero.report(new UseSkillEvent(source,this,target));
		}
		return act(source,target,area);
	}
	
	protected abstract Item act(Entity source, Entity target, Area area);
	
	public int getRequiredAttribute(String name){
		return requiredAttributes.containsKey(name)?requiredAttributes.get(name):0;
	}

	public int getExperience(){
		return experience;
	}

	public String getName(){
		return name;
	}

	public String getType() {
		return type;
	}	

	public void init(Entity source, Entity target, Area area){
		
	}

	public Object get(String string) {
		return null;
	}

	public boolean isUsable(Hero hero) {
		return true;
	}
	
	public float getRange(Hero hero){
		return 1;
	}

	public final boolean isAttack() {
		return (this instanceof AttackSkill);
	}
}
