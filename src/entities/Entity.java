package entities;

import items.Item;

import entities.heroes.Hero;
import entities.flora.Flora;
import entities.monsters.Monster;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import entities.stats.Damagable;
import entities.stats.Itemhaving;

import game.Area;
import game.Square;

public abstract class Entity implements Damagable, Itemhaving {
	protected Square occupy;
	protected Square backup;
	protected String name;
	protected List<Damagable> damageSharers;
	public Entity(String name){
		this.name = name;
		damageSharers = new ArrayList<Damagable>();
	}
	public void occupy(Square square){
		if(occupy!=null)
		{
			occupy.moveOff();
		}
		square.occupy(this);
		occupy = square;
		backup = occupy;
	}
	public Square getSquare() {
		return occupy;
	}
	public boolean move(Direction direction, Area area){
		Square movingTo = area.get(occupy.getX()+direction.getX(),occupy.getY()+direction.getY());
		if(movingTo!=null&&!movingTo.isOccupied())
		{
			occupy(movingTo);
			return true;
		}
		else {
			return false;
		}
	}
	public Direction getDirectionTowards(Square square){
		List<Direction> possible = new ArrayList<Direction>();
		if(square.getX()<getSquare().getX()){
			possible.add(Direction.left);
		}
		else if(square.getX()>getSquare().getX()){
			possible.add(Direction.right);
		}
		if(square.getY()<getSquare().getY()){
			possible.add(Direction.down);
		}
		else if(square.getX()>getSquare().getX()){
			possible.add(Direction.up);
		}
		if(possible.size()==0){
			return Direction.up;
		}
		else {
			return possible.get((int)(possible.size()*Math.random()));
		}
	}
	public boolean moveTowards(Square square, Area area){
		Direction direction = getDirectionTowards(square);
		if(move(direction,area)) return true;
		if(move(direction.getPositivePerpendicular(),area)) return true;
		if(move(direction.getNegativePerpendicular(),area)) return true;
		if(move(direction.getInverse(),area)) return true;
		return false;
	}

	public boolean moveAwayFrom(Square square, Area area) {
		Direction direction = getDirectionTowards(square).getInverse();
		if(move(direction,area)) return true;
		if(move(direction.getPositivePerpendicular(),area)) return true;
		if(move(direction.getNegativePerpendicular(),area)) return true;
		if(move(direction.getInverse(),area)) return true;
		return false;
	}
	public void moveRandomly(Area area) {
		move(Direction.directions[(int)(Direction.directions.length*Math.random())],area);
	}
	
	public abstract void undamage(int damage, Entity source);
	public abstract void giveBack(Item item);
	public abstract void update();
	public abstract Color getColour();
	public String getName() {
		return name;
	}

	public double distanceFrom(Entity entity) {
		return Math.sqrt(Math.pow(entity.getSquare().getX()-getSquare().getX(),2)+Math.pow(entity.getSquare().getY()-getSquare().getY(),2));
	}
	
	public Direction directionOf(Entity entity){
		int x = entity.getSquare().getX()-getSquare().getX();
		int y = entity.getSquare().getY()-getSquare().getY();
		
		double angle = Math.atan2(y,x);
		if(angle>=Math.PI/4&&angle<Math.PI*3/4){
			return Direction.up;
		}
		else if(angle>=Math.PI*3/4&&angle<Math.PI*5/4){
			return Direction.left;
		}
		else if(angle>=Math.PI*5/4&&angle<Math.PI*7/4){
			return Direction.down;
		}
		else return Direction.right;
	}

	public void resetSquare() {
		if(backup!=null){
			occupy(backup);
		}
	}

	public boolean isHero() {
		return this instanceof Hero;
	}

	public boolean isMonster() {
		return this instanceof Monster;
	}

	public boolean isFlora() {
		return this instanceof Flora;
	}

	public void addDamageSharer(Damagable sharer) {
		if(!this.equals(sharer)){
			this.damageSharers.add(sharer);
		}
	}
	
	protected float shareDamage(float damage, Entity source){
		damage/=(damageSharers.size()+1);
		while(!damageSharers.isEmpty()){
			damageSharers.remove(0).damage(damage, source);
		}
		return damage;
	}

	public void useItem(Item item) {
		item.useOn(this);
	}
	
}
