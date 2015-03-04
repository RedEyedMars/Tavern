package entities.heroes.behaviours;

import java.util.List;

import entities.Direction;
import entities.Entity;
import entities.heroes.Hero;
import entities.stats.Behaviour;
import entities.stats.BehaviourGenerator;
import entities.stats.skills.AttackSkill;

public class AttackBehaviour extends Behaviour {

	public final static BehaviourGenerator generator = new BehaviourGenerator("Attack"){
		@Override
		public Behaviour generate(Hero hero) {
			if(hero.getQuest()!=null&&hero.getQuest().getTarget()!=null&&hero.getQuest().isMallicious()){
				return new AttackBehaviour(hero.getQuest().getTarget());
			}
			return null;
		}
	};


	private Entity target;
	private AttackSkill maxEfficiencySkill;
	private AttackBehaviour subfight;
	public AttackBehaviour(Entity target) {
		this.target = target;
	}

	@Override
	public void act(Entity entity) {

		if(subfight!=null&&!subfight.done){
			subfight.act(entity);
		}		
		else {
			Hero hero = (Hero)entity;
			if(maxEfficiencySkill == null||!maxEfficiencySkill.isUsable(hero)){
				List<AttackSkill> skills = hero.getAttackSkills(target.distanceFrom(entity));
				int maxDamage = 0;
				for(AttackSkill skill:skills){
					int damage = skill.getDamage(hero, target);
					if(damage>maxDamage){
						maxDamage = damage;
						maxEfficiencySkill = skill;
					}
				}

			}
			if(maxEfficiencySkill!=null){
				if(Math.abs(1-(maxEfficiencySkill.getOptimalDistance()/hero.distanceFrom(target)))>0.25+hero.getAttribute("cautiousness")){
					if(!hero.moveTowardTarget(target)){
						Direction to = hero.directionOf(target);
						subfight = new AttackBehaviour(hero.getParty().getArea().get(hero.getSquare(),to).getOccupied());
					}
				}
				else {
					maxEfficiencySkill.perform(hero, target, hero.getParty().getArea());
					if(target.isDead()){
						this.done = true;
					}
				}
			}
		} 
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("attacking ");
		builder.append(target.getName());
		return builder.toString();
	}
}
