package entities.monsters;

import items.Item;

import java.util.*;


import entities.*;
import entities.heroes.Hero;
import entities.stats.Behaviour;

import game.*;

public class Carnivore extends MonsterType{

	public Carnivore(String name) {
		super(name);
	}


	@Override
	public void update(Monster monster) {
		Area area = monster.getArea();
		Square square = monster.getSquare();
		if(monster.getAge()/maxAge>Math.random()){
			monster.die();
			return;
		}
		if(monster.getBehaviour()==null){
			List<Square> surrounding = area.getSurroundingSquares(square,3);
			boolean determined = false;
			for(Square sq:surrounding) {
				if(sq.getOccupied() instanceof Hero){
					determined = true;
					monster.setBehaviour(MonsterBehaviours.attacking);
					monster.setTarget(sq.getOccupied());
				}
				else if(monster.getRecentlyMated()==0&&sq.getOccupied() instanceof Monster&&((Monster)sq.getOccupied()).getType() == this&&!activeBehaviour.contains(((Monster)sq.getOccupied()).getBehaviour())){
					determined = true;
					monster.setBehaviour(MonsterBehaviours.mating);
					monster.setTarget(sq.getOccupied());
					monster.setRecentlyMated(250);
				}
				else if(sq.getOccupied() instanceof Monster&&this.preys.contains(((Monster)sq.getOccupied()).getType())){
					determined = true;
					monster.setBehaviour(hunting);
					monster.setTarget(sq.getOccupied());
					((Monster)sq.getOccupied()).beingAttackedBy(monster);
				}
				else if(sq.getOccupied() instanceof Monster&&this.preys.contains(((Monster)sq.getOccupied()).getType())){
					determined = true;
					monster.setBehaviour(hunting);
					monster.setTarget(sq.getOccupied());
					((Monster)sq.getOccupied()).beingAttackedBy(monster);
				}
				if(determined){
					monster.getBehaviour().act(monster);
					determined = monster.getBehaviour()!=null;					
				}
				if(determined){
					break;
				}
			}
			if(!determined){
				if(Math.random()<0.3){
					monster.setBehaviour(MonsterBehaviours.sleeping);
				}
				else {
					monster.setBehaviour(MonsterBehaviours.wandering);
				}
			}
		}
		monster.getBehaviour().act(monster);
	}

	@Override
	public void beingAttackedBy(Monster monster, Entity entity) {
		if(entity instanceof Hero){
			if(monster.getHp()*100/monster.getMaxHp()>25){
				monster.setBehaviour(MonsterBehaviours.attacking);
				monster.setTarget(entity);
			}
			else {
				monster.setBehaviour(MonsterBehaviours.fleeing);
				monster.setTarget(entity);
			}
		}
		else if(entity instanceof Monster){
			if(monster.getHp()*100/monster.getMaxHp()>50){
				monster.setBehaviour(MonsterBehaviours.attacking);
				monster.setTarget(entity);
			}
			else {
				monster.setBehaviour(MonsterBehaviours.fleeing);
				monster.setTarget(entity);
			}
		}
	}

	public static final Behaviour hunting = new Behaviour(){
		@Override
		public void act(Entity entity) {
			Monster monster = (Monster)entity;
			Area area = monster.getArea();
			Square square = monster.getSquare();
			Entity target = monster.getTarget();
			List<Square> surrounding = area.getSurroundingSquares(square,1);
			if(target==null||target.getSquare()==null){
				monster.setBehaviour(null);
				monster.setTarget(null);
			}
			else if(!surrounding.contains(target.getSquare())){
				if(!monster.moveTowards(target.getSquare(),area))
					monster.setBehaviour(null);
			}
			else {
				Item dropped = monster.getRandomSkill().perform(monster, target, area);
				if(dropped!=null){
					monster.addItem(dropped);
					monster.setBehaviour(null);
					monster.setHp(monster.getMaxHp());

				}
			}
		}
	};

	private static List<Behaviour> activeBehaviour = new ArrayList<Behaviour>();
	static{
		activeBehaviour.add(MonsterBehaviours.attacking);
		activeBehaviour.add(MonsterBehaviours.fleeing);
		activeBehaviour.add(hunting);
	}

}
