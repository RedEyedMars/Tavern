package entities.heroes.classes;

import entities.Entity;

public abstract class ClassedHero extends Entity{
	private HeroClass myClass;
	public ClassedHero(String name){
		super(name);
	}
	public HeroClass getHeroClass(){
		return myClass;
	}

	public void setClass(HeroClass clazz) {
		myClass = clazz;
	}
}
