package entities.monsters;

import entities.*;
import entities.flora.FloraType;
import entities.heroes.classes.NameGenerator;
import entities.stats.resistances.Resistance;
import entities.stats.skills.Skill;
import game.Area;
import game.Square;

import items.Item;

import java.awt.Color;
import java.util.*;

import system.SystemAction;


public abstract class MonsterType {
	protected List<Skill> skills;
	protected List<Resistance> resistances;
	protected List<Item> drops;
	protected List<MonsterType> rivals;
	protected List<MonsterType> preys;
	protected List<FloraType> edibles;
	protected int[] hpRange;
	protected int[] mpRange;
	protected double[] dodgeRange;
	protected double[] dropPercentRange;
	protected int[] strengthRange;
	protected int[] intellectRange;
	protected int[] morality;
	protected String name;
	protected double maxAge;
	private SystemAction onConstruct;
	private int[] litterSizeRange;
	private Color colour;
	protected int[] speedRange;
	public MonsterType(String name)
	{
		this.name = name;
		skills = new ArrayList<Skill>();
		resistances = new ArrayList<Resistance>();
		drops = new ArrayList<Item>();
		rivals = new ArrayList<MonsterType>();
		preys = new ArrayList<MonsterType>();
		edibles = new ArrayList<FloraType>();
	}
	public void addSkill(Skill skill)
	{
		skills.add(skill);
	}
	public void addResistance(Resistance resistance)
	{
		resistances.add(resistance);
	}
	public void addDrop(Item drop)
	{
		drops.add(drop);
	}
	public void addRival(MonsterType rival)
	{
		rivals.add(rival);
	}
	public void addPrey(MonsterType prey)
	{
		preys.add(prey);
	}
	public void setSpeedRange(int... range)
	{
		speedRange = range;
	}
	public void setMoralityRange(int... range){
		morality = range;
	}
	public void setHpRange(int... range)
	{
		hpRange = range;
	}
	public void setMaxAge(double age){
		maxAge = age;
	}
	public void setMpRange(int... range)
	{
		mpRange = range;
	}
	public void setDodgeRange(double... range)
	{
		dodgeRange = range;
	}
	public void setDropPercentRange(double...range)
	{
		dropPercentRange = range;
	}
	public void setStrengthRange(int... range)
	{
		strengthRange = range;
	}	
	public void setIntellectRange(int... range)
	{
		intellectRange = range;
	}	

	public void setLitterSizeRange(int... range) {
		litterSizeRange = range;
	}

	public Monster create(List<Square> spawn,Area area) {
		Monster monster = new Monster(name);
		Square square = null;
		boolean hasSpace = true;
		do {
			square = spawn.get((int) (spawn.size()*Math.random()));
			hasSpace = false;
			for(Square sq:spawn){
				if(!sq.isOccupied()){
					hasSpace = true;
					break;
				}
			}
		} while(square.isOccupied()&&hasSpace);
		if(square.isOccupied())return null;
		monster.occupy(square);
		monster.setHp(hpRange[(int)(hpRange.length*Math.random())]);
		monster.setMaxHp(monster.getHp());
		int mp = mpRange[(int)(mpRange.length*Math.random())];
		monster.setMp(mp);
		monster.setDodge(dodgeRange[(int)(dodgeRange.length*Math.random())]);
		monster.setSpeed(speedRange[(int)(speedRange.length*Math.random())]);
		monster.setDropPercent(dropPercentRange[(int)(dropPercentRange.length*Math.random())]);
		int intellect = intellectRange[(int)(intellectRange.length*Math.random())];
		int strength = strengthRange[(int)(strengthRange.length*Math.random())];
		while(intellect>0&&strength>0)
		{
			Skill skill = skills.get((int)(skills.size()*Math.random()));
			monster.setIntellect(monster.getIntellect()-skill.getRequiredAttribute("Intellect"));
			if(monster.getIntellect()>=0){
				monster.addSkill(skill);
			}
		}
		monster.setIntellect(intellect);
		monster.setStrength(strength);
		monster.addResistance(resistances.get((int)(resistances.size()*Math.random())));
		monster.setDrop(drops.get((int)(drops.size()*Math.random())).clone());
		monster.setType(this);
		monster.setArea(area);
		monster.setLitterSize(litterSizeRange[(int)(litterSizeRange.length*Math.random())]);
		monster.setMorality(morality[(int)(morality.length*Math.random())]);
		monster.setColour(colour);
		if(onConstruct!=null){
			onConstruct.invoke();
		}
		return monster;
	}

	public Monster createAlpha(List<Square> spawn, Area area) {
		Monster monster = create(spawn, area);
		int attributeToDouble = (int)( Math.random()*10);
		switch(attributeToDouble){
		case 0:monster.setStrength(monster.getStrength()*2);
		}
		monster.setName(NameGenerator.getName()+" the "+name);
		return null;
	}

	public Monster createRival(List<Square> spawn, Area area) {
		if(rivals.size()>0){
			return rivals.get((int)(rivals.size()*Math.random())).create(spawn, area);
		}
		return null;
	}

	public Entity createPrey(List<Square> spawn, Area area) {
		if(preys.size()>0||edibles.size()>0)
		{
			int index = (int)((preys.size()+edibles.size())*Math.random());
			if(index>=preys.size()){
				return edibles.get(index-preys.size()).create(area);
			}
			else return preys.get(index).create(spawn, area);
		}
		else return create(spawn, area);
	}
	
    public void addEdible(FloraType type) {
		edibles.add(type);
	}

	public void setOnConstruct(SystemAction systemAction) {
		onConstruct = systemAction;
	}

	public void setColour(Color c) {
		colour = c;
	}
	public String getName() {
		return name;
	}
	public abstract void update(Monster monster);
	public abstract void beingAttackedBy(Monster monster, Entity entity);
}
