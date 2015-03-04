package entities.heroes.classes;

import java.util.ArrayList;
import java.util.List;

import entities.heroes.Hero;
import entities.heroes.Party;

public class HeroClass {
	
	private String name;
	
	private List<Attribute> attributes;
	
	public HeroClass(String name){
		this.name = name;
		this.attributes = new ArrayList<Attribute>();
		init();
	}
	
	public Hero createHero(){
		Hero hero = new Hero(NameGenerator.getName());
		hero.setClass(this);
		Party party = new Party();
		party.setName(hero.getName()+"'s party");
		party.addHero(hero);
		for(Attribute attribute:attributes){
			attribute.mold(hero);
		}
		return hero;
	}
	
	public void addAttribute(Attribute attribute){
		attributes.add(attribute);
	}
	
	public Attribute getAttribute(String name){
		for(Attribute attribute:attributes){
			if(attribute.getName().equals(name)){
				return attribute;
			}
		}
		return null;
	}
	
	public String getName(){
		return name;
	}
	
	public void init(){}
	
	public HeroClass baseClass = new HeroClass("Base"){
		public void init(){
			attributes.add(new Attribute("hp",10));
			attributes.add(new Attribute("maxHp",10));
			attributes.add(new Attribute("mp",1));
			attributes.add(new Attribute("maxMp",1));
			attributes.add(new Attribute("level", 1));

			attributes.add(new Attribute("strength",3));
			attributes.add(new Attribute("speed", 3));
			attributes.add(new Attribute("intellect", 3));
			attributes.add(new Attribute("dexterity",3));
			attributes.add(new Attribute("fortitude",3));

			attributes.add(new Attribute("cowardess",0.8f));
			attributes.add(new Attribute("patience",0.2f));
			attributes.add(new Attribute("morality",0.5f));

			attributes.add(new Attribute("mood", 1));
			attributes.add(new Attribute("stubborness",1f));
			attributes.add(new Attribute("cautiousness",0f));
			attributes.add(new Attribute("extrovert",0.025f));
		}
	};
}
