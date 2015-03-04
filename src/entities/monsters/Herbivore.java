package entities.monsters;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.heroes.Hero;
import entities.stats.Behaviour;
import game.Area;
import game.Square;

public class Herbivore extends MonsterType{

	public Herbivore(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	private static List<Behaviour> activeBehaviour = new ArrayList<Behaviour>();
	
	static{
		activeBehaviour.add(MonsterBehaviours.attacking);
		activeBehaviour.add(MonsterBehaviours.fleeing);
		activeBehaviour.add(MonsterBehaviours.foraging);
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
			List<Square> surrounding = area.getSurroundingSquares(square,4);
			boolean determined = false;
			for(Square sq:surrounding) {
				if(sq.getOccupied() instanceof Hero){
					determined = true;
					if(Math.random()<0.2){
						monster.setBehaviour(MonsterBehaviours.attacking);
					}
					else {
						monster.setBehaviour(MonsterBehaviours.fleeing);
					}
					monster.setTarget(sq.getOccupied());
				}
				else if(sq.getOccupied() instanceof Monster&&((Monster)sq.getOccupied()).getType() != this){
					
					if(Math.random()<0.1){
						determined = true;
						monster.setBehaviour(MonsterBehaviours.fleeing);
						monster.setTarget(sq.getOccupied());
					}
				}
				else if(monster.getRecentlyMated()==0&&sq.getOccupied() instanceof Monster&&((Monster)sq.getOccupied()).getType() == this&&!activeBehaviour.contains(((Monster)sq.getOccupied()).getBehaviour())){
					determined = true;
					monster.setBehaviour(MonsterBehaviours.mating);
					monster.setTarget(sq.getOccupied());
					monster.setRecentlyMated(300);
				}
				else if(sq.getOccupied() instanceof Monster&&this.preys.contains(((Monster)sq.getOccupied()).getType())){
					determined = true;
					monster.setBehaviour(MonsterBehaviours.foraging);
					monster.setTarget(sq.getOccupied());
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
				if(Math.random()<0.2){
					monster.setBehaviour(MonsterBehaviours.sleeping);
				}
				else {
					monster.setBehaviour(MonsterBehaviours.wandering);
				}
			}

		}
		else 
			monster.getBehaviour().act(monster);
	}

	@Override
	public void beingAttackedBy(Monster monster, Entity entity) {
		if(entity instanceof Hero){
			monster.setBehaviour(MonsterBehaviours.fleeing);
			monster.setTarget(entity);
		}
		else if(entity instanceof Monster){
			monster.setBehaviour(MonsterBehaviours.fleeing);
			monster.setTarget(entity);
		}
	}

}
