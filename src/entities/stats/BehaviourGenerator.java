package entities.stats;

import entities.heroes.Hero;

public abstract class BehaviourGenerator {
	private String name;
	public BehaviourGenerator(String string){
		this.name = string;
	}

	public abstract Behaviour generate(Hero hero);
	
	public String getName(){
		return name;
	}
}
