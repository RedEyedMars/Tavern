package entities.stats.skills;

import game.Area;
import items.Item;
import entities.Entity;
import entities.stats.Dodger;
import entities.stats.Strengther;
import entities.stats.resistances.Resister;

public abstract class AttackSkill extends ModifyingSkill {

	public boolean hit = false;
	public Item item;
	private int baseDamage;
	private double optimalDistance;
	
	public AttackSkill(String name, int base) {
		super(name);
		baseDamage = base;
		this.optimalDistance = 1;
	}
	public AttackSkill(String name, String type, int base) {
		super(name, type);
		baseDamage = base;
		this.optimalDistance = 1;
	}

	public AttackSkill(String name, int base, double optimalDistance) {
		super(name);
		baseDamage = base;
		this.optimalDistance = optimalDistance;
	}
	public AttackSkill(String name, String type, int base, double optimalDistance) {
		super(name, type);
		baseDamage = base;
		this.optimalDistance = optimalDistance;
	}

	public int getDamage(Entity source, Entity target) {
		double resist = 1.0;
		double damage = baseDamage;
		if(source instanceof Strengther){
			damage+=((Strengther)source).getStrength();
		}
		if((target instanceof Resister)){
			resist *= ((Resister)target).getResistancer(this);						
		}
		if((target instanceof Dodger)){
			resist *= 1-((Dodger)target).getDodgePercent();
		}
		if((source instanceof Dodger)){
			resist /= 1-((Dodger)source).getDodgePercent();
		}
		return (int) (damage*resist*Math.exp(-(Math.pow(source.distanceFrom(target)-optimalDistance,2)/Math.PI)));
	}

	protected void undamage(Entity source, Entity target) {
		
		target.undamage(getDamage(source,target),source);
		if(item!=null){
			target.giveBack(item);
		}
	}

	@Override
	protected Item act(Entity source, Entity target, Area area) {
		return source.damage(getDamage(source,target),source);
	}
	
	public double getOptimalDistance(){
		return optimalDistance;
	}

}
