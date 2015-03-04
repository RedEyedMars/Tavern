package entities.monsters;

import items.Item;

import java.util.List;

import entities.Entity;
import entities.stats.Behaviour;
import game.Area;
import game.Square;

public class MonsterBehaviours {
	public static final Behaviour mating = new Behaviour(){
		@Override
		public void act(Entity entity) {
			Monster monster = (Monster)entity;
			Area area = monster.getArea();
			Square square = monster.getSquare();
			Entity target = monster.getTarget();

			List<Square> surrounding = area.getSurroundingSquares(square,1);
			if(target==null||!area.hasEntity(target)||area.entityRatio(monster.getType())>1+monster.getLitterSize()/3){
				monster.setBehaviour(null);
			}
			else if(!surrounding.contains(target.getSquare())){
				if(!monster.moveTowards(target.getSquare(),area))
					monster.setBehaviour(null);
			}
			else {
				int sur=2;
				while(surrounding.size()<monster.getLitterSize()){
					surrounding = area.getSurroundingSquares(square,sur);
					++sur;
				}
				for(int i=0;i<monster.getLitterSize();++i){
					Monster baby = monster.getType().create(surrounding,monster.getArea());
					if(baby!=null){
						monster.getArea().addMonster(baby);
					}
					else break;
				}
				monster.setBehaviour(null);

			}
		}
	};
	public static final Behaviour attacking = new Behaviour(){
		@Override
		public void act(Entity entity) {
			Monster monster = (Monster)entity;
			Area area = monster.getArea();

			Square square = monster.getSquare();
			Entity target = monster.getTarget();
			List<Square> surrounding = area.getSurroundingSquares(square,1);
			if(target==null||!area.hasEntity(target)){
				monster.setBehaviour(null);

			}
			else if(!surrounding.contains(target.getSquare())){
				if(!monster.moveTowards(target.getSquare(),area))
					monster.setBehaviour(null);
			}
			else {

				if(100*monster.getHp()/monster.getMaxHp()<10&&Math.random()<0.4)
				{
					monster.setBehaviour(MonsterBehaviours.fleeing);
				}
				else {
					Item dropped = monster.getRandomSkill().perform(monster, target, area);
					if(dropped!=null){
						dropped.giveTo(monster);
						monster.setBehaviour(null);
					}
				}
			}
		}
	};

	public static final Behaviour wandering = new Behaviour(){
		@Override
		public void act(Entity entity) {
			Monster monster = (Monster)entity;
			Area area = monster.getArea();
			monster.moveRandomly(area);
			if(Math.random()<0.05){
				monster.setBehaviour(null);
			}
		}
	};

	public static final Behaviour sleeping = new Behaviour(){
		@Override
		public void act(Entity entity) {
			Monster monster = (Monster)entity;
			monster.setHp(monster.getHp()+(int)Math.random()*2);
			if(Math.random()<0.15){
				monster.setBehaviour(null);
			}
		}
	};
	public static final Behaviour fleeing = new Behaviour(){
		@Override
		public void act(Entity entity) {
			Monster monster = (Monster)entity;
			Area area = monster.getArea();
			Entity target = monster.getTarget();
			if(target.getSquare()==null){
				monster.setBehaviour(null);
				return;
			}
			monster.moveAwayFrom(target.getSquare(),area);
			if(Math.random()<0.05){
				monster.setBehaviour(null);
			}
		}
	};
	public static final Behaviour foraging = new Behaviour(){
		@Override
		public void act(Entity entity) {
			Monster monster = (Monster)entity;
			Area area = monster.getArea();
			Square square = monster.getSquare();
			Entity target = monster.getTarget();
			List<Square> surrounding = area.getSurroundingSquares(square,1);
			if(target==null){
				monster.setBehaviour(null);
			}
			if(!surrounding.contains(target.getSquare())){
				monster.moveTowards(target.getSquare(),area);
			}
			else {

				Item dropped = monster.getSkill("eat").perform(monster, target, area);
				if(dropped!=null){
					dropped.giveTo(monster);
					monster.setBehaviour(null);
					monster.setHp(monster.getMaxHp());

				}
			}
		}
	};
}
