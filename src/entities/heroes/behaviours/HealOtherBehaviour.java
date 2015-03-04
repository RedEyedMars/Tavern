package entities.heroes.behaviours;

import entities.Direction;
import entities.Entity;
import entities.heroes.Hero;
import entities.heroes.histories.FailedToHealEvent;
import entities.stats.Behaviour;
import entities.heroes.behaviours.AttackBehaviour;
import entities.stats.BehaviourGenerator;
import entities.stats.skills.Skill;

public class HealOtherBehaviour extends Behaviour {

	public final static BehaviourGenerator generator = new BehaviourGenerator("Heal"){
		@Override
		public Behaviour generate(Hero hero) {
			Hero target = null;
			float hpPercent = 1;
			for(Hero member:hero.getParty()){
				if(!member.equals(hero)){
					float hp = member.getAttribute("hp")/member.getAttribute("maxHp");
					if(hp<hpPercent){
						hpPercent = hp;
						target = member;
					}
				}
			}
			if(target!=null){
				return new HealOtherBehaviour(target);
			}
			return null;
		}
	};

	private Hero target;
	private AttackBehaviour subfight;
	public HealOtherBehaviour(Hero target) {
		this.target = target;
	}

	@Override
	public void act(Entity entity) {
		if(subfight!=null&&!subfight.isDone()){
			subfight.act(entity);
		}
		Hero hero = (Hero)entity;
		if(target.isDead()){
			hero.relationshipAppend((Hero)target, new FailedToHealEvent(hero,(Hero)target));
			this.done = true;
		}
		if(target.getAttribute("hp")/target.getAttribute("maxHp")>=0.65){
			this.done = true;
		}
		Skill heal = hero.getSkill("HealOne");
		if(hero.distanceFrom(target)>heal.getRange(hero)){
			if(!hero.moveTowardTarget(target)){
				Direction to = hero.directionOf(target);
				subfight = new AttackBehaviour(hero.getParty().getArea().get(hero.getSquare(),to).getOccupied());
			}
		}
		else {
			heal.perform(hero, target, hero.getParty().getArea());
		}
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("protecting ");
		builder.append(target.getName());
		return builder.toString();
	}
}
