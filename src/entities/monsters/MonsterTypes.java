package entities.monsters;

import items.Items;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import entities.flora.Floras;
import entities.stats.resistances.Resistance;

public class MonsterTypes {
	public static Carnivore wolf = new Carnivore("wolf");
	public static Carnivore bear = new Carnivore("bear");
	public static Herbivore rabbit = new Herbivore("rabbit");
	public static List<MonsterType> types;
	static{
		types = new ArrayList<MonsterType>();
		types.add(wolf);
		types.add(bear);
		
		wolf.addSkill(MonsterSkills.bite);
		wolf.addResistance(Resistance.Nothing);
		wolf.addResistance(new Resistance(0.1,0.95,"ALLIce"));
		wolf.addDrop(Items.wolfPelt);
		wolf.addDrop(Items.wolfTooth);
		wolf.addDrop(Items.toughMeat);
		wolf.addRival(bear);
		wolf.addPrey(rabbit);
		wolf.setHpRange(8,9,10,11,12,14,16);
		wolf.setMpRange(0,1);
		wolf.setMaxAge(2500000);
		wolf.setDodgeRange(0.4,0.3,0.2,0.1,0.5,0.025,0.0);
		wolf.setDropPercentRange(0.7,0.75,0.8);
		wolf.setStrengthRange(1,2);
		wolf.setIntellectRange(1,2,3);
		wolf.setLitterSizeRange(1,2,0,0,1);
		wolf.setMoralityRange(-2,-3,-1,-3,-2);
		wolf.setColour(Color.gray);
		//wolf.setOnConstruct(new SystemAction(){ public void invoke(){System.out.println("made wolf");}});
		wolf.setSpeedRange(4);
		
		bear.addSkill(MonsterSkills.claw);
		bear.addSkill(MonsterSkills.chomp);
		bear.addResistance(Resistance.Nothing);
		bear.addResistance(new Resistance(0.1,0.95,"ALLEarth"));
		bear.addDrop(Items.bearPelt);
		bear.addDrop(Items.bearClaw);
		bear.addDrop(Items.fish);
		bear.addRival(wolf);
		bear.addPrey(rabbit);
		bear.addEdible(Floras.forestBerries);
		bear.setHpRange(12,13,14,15,16,18,20,25,30);
		bear.setMpRange(0,1);
		bear.setDodgeRange(0.2,0.18,0.16,0.15,0.14);
		bear.setDropPercentRange(0.7,0.75,0.8);
		bear.setStrengthRange(3,4);
		bear.setIntellectRange(2,3,4);
		bear.setMaxAge(1000000);
		bear.setLitterSizeRange(0,1);
		bear.setMoralityRange(-2,-2,-1,-1);
		bear.setColour(Color.orange);
		bear.setSpeedRange(6);
		
		rabbit.addSkill(MonsterSkills.nibble);
		rabbit.addResistance(Resistance.Nothing);
		rabbit.addDrop(Items.rabbitPelt);
		rabbit.addDrop(Items.rabbitFoot);
		rabbit.addEdible(Floras.forestBerries);
		rabbit.setHpRange(1,2,3,4,5);
		rabbit.setMpRange(0);
		rabbit.setDodgeRange(0.6,0.57,0.54,0.5);
		rabbit.setDropPercentRange(0.7,0.75,0.8);
		rabbit.setStrengthRange(0,1);
		rabbit.setIntellectRange(0,1,2);
		rabbit.setMaxAge(250000);
		rabbit.setLitterSizeRange(5);
		rabbit.setMoralityRange(-1,0,1,1);
		rabbit.setColour(Color.cyan);
		rabbit.setSpeedRange(2);
	}
}
