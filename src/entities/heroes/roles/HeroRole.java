package entities.heroes.roles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.heroes.Hero;
import entities.heroes.histories.HistoryEvent;
import entities.heroes.roles.requirements.HeroRoleRequirement;
import entities.stats.BehaviourGenerator;

public abstract class HeroRole {

	private List<HeroRoleRequirement> requirements;
	private List<BehaviourGenerator> behaviours;
	private String name;
	public HeroRole(String name, List<HeroRoleRequirement> requirements, BehaviourGenerator... behaviours) {
		this.name = name;
		this.behaviours = new ArrayList<BehaviourGenerator>();
		this.behaviours.addAll(Arrays.asList(behaviours));
		this.requirements = requirements;

	}

	public float compitency(Hero hero){
		float comp = 0f;
		for(HeroRoleRequirement req:requirements){
			if(req.satisfy(hero)){
				comp+=1.0;
			}
		}
		return comp/requirements.size();
	}

	public String getName() {
		return name;
	}

	public abstract float factor(HistoryEvent event);

	public abstract float evaluate(Hero hero, BehaviourGenerator gen);

	public Object[] getMostImportantBehaviour(Hero hero) {
		BehaviourGenerator bestGenerator = null;
		Float best=0f;
		for(BehaviourGenerator gen:behaviours){
			if(gen.generate(hero)!=null){
				float evaluation = this.evaluate(hero, gen);
				if(evaluation>best){
					best = evaluation;
					bestGenerator = gen;
				}
			}
		}
		if(bestGenerator==null)return null;
		return new Object[]{best,bestGenerator.generate(hero)};
	}


}
