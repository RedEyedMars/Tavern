package entities.monsters;

import entities.Entity;
import entities.heroes.Hero;

public class Omnivore extends MonsterType {

	public Omnivore(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Monster monster) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void beingAttackedBy(Monster monster, Entity entity) {
		if(entity instanceof Hero){
			if(monster.getHp()*100/monster.getMaxHp()>50){
				monster.setBehaviour(MonsterBehaviours.attacking);
				monster.setTarget(entity);
			}
			else {
				monster.setBehaviour(MonsterBehaviours.fleeing);
				monster.setTarget(entity);
			}
		}
		else if(entity instanceof Monster){
			if(monster.getHp()*100/monster.getMaxHp()>70){
				monster.setBehaviour(MonsterBehaviours.attacking);
				monster.setTarget(entity);
			}
			else {
				monster.setBehaviour(MonsterBehaviours.fleeing);
				monster.setTarget(entity);
			}
		}
	}


}
