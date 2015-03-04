package entities.heroes.behaviours;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.heroes.Hero;
import entities.heroes.roles.HeroRole;
import entities.stats.Behaviour;
import entities.stats.BehaviourGenerator;

public class DecideNextBehaviourBehaviour extends Behaviour {

	public final static BehaviourGenerator generator = new BehaviourGenerator("DecideNextBehaviour"){
		@Override
		public Behaviour generate(Hero hero) {
			return new DecideNextBehaviourBehaviour();
		}
	};
	
	@Override
	public void act(Entity entity) {
		List<Float> importances = new ArrayList<Float>();
		List<Behaviour> behaviours = new ArrayList<Behaviour>();
		for(HeroRole role:((Hero)entity).getRoles()){
			Object[] importanceAndBehaviour = role.getMostImportantBehaviour((Hero)entity);
			if(importanceAndBehaviour!=null){
				importances.add((Float) importanceAndBehaviour[0]);
				behaviours.add((Behaviour) importanceAndBehaviour[1]);
			}
		}
		
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("deciding what to do");
		return builder.toString();
	}
	
}
