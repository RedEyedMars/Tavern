package entities.heroes.behaviours;

import entities.Entity;
import entities.heroes.Hero;
import entities.stats.Behaviour;

public abstract class BriefBehaviour extends Behaviour {
	
	private Behaviour overhead;
	
	public BriefBehaviour(Behaviour over){
		this.overhead = over;
	}

	@Override
	public void act(Entity entity) {
		Hero hero = (Hero)entity;
		if(!perform(hero)){
			overhead.act(entity);
		}
		else {
			done = true;
			hero.setBehaviour(overhead);
		}
	}
	
	public abstract boolean perform(Hero hero);

}
