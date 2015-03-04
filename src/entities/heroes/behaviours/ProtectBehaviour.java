package entities.heroes.behaviours;

import entities.Direction;
import entities.Entity;
import entities.heroes.Hero;
import entities.heroes.histories.FailedToProtectEvent;
import entities.stats.Behaviour;
import entities.heroes.behaviours.AttackBehaviour;
import entities.stats.BehaviourGenerator;
import game.Square;

public class ProtectBehaviour extends Behaviour {

	public final static BehaviourGenerator generator = new BehaviourGenerator("Protect"){
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
				return new ProtectBehaviour(target);
			}
			return null;
		}
	};
	
	private Entity target;
	private AttackBehaviour subfight;
	public ProtectBehaviour(Entity target) {
		this.target = target;
	}

	@Override
	public void act(Entity entity) {
		if(subfight!=null&&!subfight.isDone()){
			subfight.act(entity);
		}
		Hero hero = (Hero)entity;
		if(target.isDead()){
			if(target.isHero()){
				hero.relationshipAppend((Hero)target, new FailedToProtectEvent(hero,(Hero)target));
			}
			this.done = true;
		}
		if(hero.getAttribute("hp")/hero.getAttribute("maxHp")<0.25){
			this.done = true;
		}
		boolean nextToTarget = false;
		for(Square square:hero.getParty().getArea().getSurroundingSquares(hero.getSquare(), 1)){
			if(target.getSquare().equals(square)){
				nextToTarget = true;
				break;
			}
		}
		if(!nextToTarget){
			if(!hero.moveTowardTarget(target)){
				Direction to = hero.directionOf(target);
				subfight = new AttackBehaviour(hero.getParty().getArea().get(hero.getSquare(),to).getOccupied());
			}
		}
		else {
			hero.getSkill("Protect").perform(hero, target, hero.getParty().getArea());
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
