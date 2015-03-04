package entities.heroes.skills;

import items.Item;
import entities.Direction;
import entities.Entity;
import entities.heroes.Hero;
import entities.stats.skills.Skill;
import game.Area;
import game.Square;

public class TrackingSkill extends  Skill{
	private int distance = 1;
	private boolean found = false;
	private boolean done = false;
	public TrackingSkill(){
		super("Tracking","Mental");
	}

	@Override
	public void init(Entity source, Entity target, Area area){
		this.distance = 1;
		this.found = false;
		this.done = false;
	}

	@Override
	public Item act(Entity source, Entity target, Area area){
		Hero hero = (Hero)source;
		Direction facing = hero.getFacing();
		if(facing!=null&&facing.equals(hero.getFacing())){
			++distance;			
		}
		if(distance>hero.getAttribute("eyeSight")){
			this.found = false;
			this.done = true;
			return null;
		}
		Square cast = area.get(hero.getSquare().getX()+distance*facing.getX(), hero.getSquare().getY()+distance*facing.getY());
		if(target.getSquare().equals(cast)){
			this.found = true;
			this.done = true;
			return null;
		}
		Direction positive = facing.getRight();
		Direction negative = facing.getLeft();

		Square up = null;
		if(cast!=null){
			up  = area.get(cast,positive);
		}
		Square down = null;
		if(cast!=null){
			down = area.get(cast,negative);
		}
		for(int iteration = 0;iteration<distance;++iteration){
			if(target.getSquare().equals(up)||target.getSquare().equals(down)){
				this.found = true;
				this.done = true;
				return null;
			}
			else {
				if(up!=null){
					up = area.get(up,positive);
				}
				if(down!=null){
					down = area.get(down,negative);
				}
			}
		}
		hero.turnRight();
		return null;
	}

	@Override
	public Object get(String name){
		if("found".equals(name)){
			return found;
		}
		else if("done".equals(name)){
			return done;
		}
		else return null;
	}
	
	@Override
	public boolean isUsable(Hero hero){
		if(hero.is("Blinded")||hero.getAttribute("eyeSight")<2){
			return false;
		}
		else return super.isUsable(hero);
	}
	
	@Override
	public float getRange(Hero hero){
		return hero.getAttribute("eyeSight");
	}

}
