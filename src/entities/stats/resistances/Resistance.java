package entities.stats.resistances;

import java.util.ArrayList;
import java.util.List;

import entities.stats.skills.Skill;

public class Resistance {
	public static final Resistance Nothing = new Resistance(0,1.0);
	private double chance;
	private double dampening;
	private List<String> skills;
	private List<String> types;
	public Resistance(double chance, double dampen, String rules)
	{
		this(chance,dampen);
		for(String rule:rules.split(",")){
			if(rule.startsWith("ALL")){
				types.add(rule.substring(3));
			}
			else {
				skills.add(rule);
			}
		}
	}
	
	public Resistance(double chance, double dampen)
	{
		this.skills = new ArrayList<String>();
		this.types = new ArrayList<String>();
		this.chance = chance;
		this.dampening = dampen;
	}
	
	public double resist(Skill skill){
		if((skills.contains(skill.getName())||types.contains(skill.getType()))&&Math.random()<chance)
			return 0.2*Math.log(Math.random()+0.07)+dampening;
		else return 1.0;
	}
}

