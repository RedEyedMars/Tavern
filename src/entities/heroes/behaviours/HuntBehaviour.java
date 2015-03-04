package entities.heroes.behaviours;

import entities.Direction;
import entities.Entity;
import entities.heroes.Hero;
import entities.heroes.histories.FoundTargetEvent;
import entities.stats.Behaviour;
import entities.stats.BehaviourGenerator;
import entities.stats.skills.Skill;
import game.Square;

public class HuntBehaviour extends Behaviour{

	public final static BehaviourGenerator generator = new BehaviourGenerator("Hunt"){
		@Override
		public Behaviour generate(Hero hero) {
			if(hero.getQuest()!=null&&hero.getQuest().getTarget()!=null){
				return new HuntBehaviour(hero.getQuest().getTarget());
			}
			return null;
		}
	};
	
	private Entity target;
	private boolean trackingInitted = false;
	private boolean seen = false;
	private int wander = 0;
	public HuntBehaviour(Entity target){
		this.target = target;
	}
	@Override
	public void act(Entity entity) {
		Hero hero = (Hero)entity;
		if(!seen&&wander==0){//Target not found
			if(hero.hasSkill("Tracking")){
				Skill tracking = hero.getSkill("Tracking");
				if(!trackingInitted){
					trackingInitted = true;
					tracking.init(hero, target, hero.getParty().getArea());
				}
				tracking.perform(hero, target, hero.getParty().getArea());
				if(((Boolean)tracking.get("done"))){
					if(((Boolean)tracking.get("found"))){
						seen = true;
						hero.report(new FoundTargetEvent(hero,target));
						return;
					}
					else {
						wander = 10;
						return;
					}
				}
			}
		}
		else if(wander>0){
			Square cast = hero.getParty().getArea().get(hero.getSquare().getX()+hero.getFacing().getX(), hero.getSquare().getY()+hero.getFacing().getY());
			if(cast==null){
				Direction facing = hero.getFacing();
				while(facing.equals(hero.getFacing())){
					hero.setFacing(Direction.random());	
				}
			}
			else {
				hero.walkForward();
			}
			--wander;
		}
		else if(seen){
			if(target.distanceFrom(hero)<hero.maxRangeAttack()){
				hero.changeBehaviour(new AttackBehaviour(target));
				done = true;
			}
		}
		
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("hunting ");
		builder.append(target.getName());
		return builder.toString();
	}
}
