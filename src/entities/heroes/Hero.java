package entities.heroes;

import items.Item;
import items.ItemBag;

import java.awt.Color;
import java.util.*;

import entities.Direction;
import entities.Entity;
import entities.heroes.histories.*;
import entities.heroes.magicks.Magic;
import entities.heroes.magicks.MagicType;
import entities.heroes.skills.SkilledHero;
import entities.stats.*;
import entities.stats.resistances.Resistance;
import entities.stats.resistances.Resister;
import entities.stats.skills.Skill;
import game.Area;
import game.interfaces.Nameable;

public class Hero extends SkilledHero implements Strengther, Dodger, Resister, Nameable, Damagable{

	private String name;

	private List<Resistance> resistances;
	private List<Item> items;
	private List<Magic> magicks;

	private Direction facing;
	private Status status;

	public Hero(String name){
		super(name);
		resistances = new ArrayList<Resistance>();
		items = new ArrayList<Item>();
		magicks = new ArrayList<Magic>();
		facing = Direction.random();
		status = new Status();
	}

	@Override
	public Item damage(float damage, Entity effector) {
		damage = shareDamage(damage, effector);
		attributeMinus("hp",damage);
		if(isDead())
		{
			if(effector instanceof Hero){
				((Hero)effector).report(new MurderKillEvent((Hero)effector, this));
			}
			report(new PartyMemberKillEvent(effector,this));
			die();
			ItemBag bag = new ItemBag(getName()+"'s stuff");
			for(Item item:items){
				bag.add(item);
			}
			return bag;
		}
		else if(attributeLessThan("hp",getAttribute("maxHp")/4)){
			report(new PartyMemberWoundedEvent(effector,this));
			status.change(Status.Wounded);			
		}
		return null;
	}

	@Override
	//DEPRECATED
	public void undamage(int damage, Entity effector) {
		if(isDead()){
			if(effector instanceof Hero){
				((Hero)effector).unreport(new MurderKillEvent((Hero)effector, this));
			}
			unreport(new PartyMemberKillEvent(effector,this));
			undie();
		}
		attributeAdd("hp",damage);
		if(attributeGreaterThan("hp",getAttribute("maxHp")/4)){
			status.remove(Status.Wounded);
		}

	}

	@Override
	public boolean isDead() {
		return attributeLessThan("hp",1f);
	}

	private void undie() {
		getParty().getArea().addHero(this);
		getParty().undieHero(this);
	}

	private void die() {
		getParty().getArea().removeHero(this);
		getParty().dieHero(this);
	}


	@Override
	public Color getColour() {
		return Color.pink;
	}


	public String getName() {
		return name;
	}

	public void addItem(Item item){
		items.add(item);
	}

	@Override
	public double getDodgePercent() {
		return ((float)getAttribute("dexterity")*getAttribute("speed")*getAttribute("hp"))/(getAttributeSquared("maxHp"));
	}

	public Direction getFacing() {
		return facing;
	}

	@Override
	public double getResistancer(Skill skill) {
		double resist = 1.0;
		for(Resistance resistance:resistances){
			resist*=resistance.resist(skill);
		}
		return resist;
	}

	public void turnRight() {
		facing = facing.getRight();
	}
	public void turnLeft() {
		facing = facing.getLeft();
	}

	public void setFacing(Direction random) {
		facing = random;
	}

	public void walkForward() {
		this.move(facing, getParty().getArea());
	}

	public boolean is(String status) {
		return this.status.toString().contains(status);
	}

	@Override
	public void giveBack(Item item) {
		if(item instanceof ItemBag){
			for(Item i:((ItemBag)item)){
				items.add(i);
			}
		}
		else {
			items.add(item);
		}
	}
	
	@Override
	public boolean move(Direction direction, Area area){
		if(super.move(direction, area)){
			facing = direction;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean moveTowardTarget(Entity target){
		Direction to = directionOf(target);
		if(getParty().getArea().get(getSquare(),to).isOccupied()){
			if(getParty().getArea().get(getSquare(),to).getOccupied().isMonster()){
				return false;
			}
			else if(getParty().getArea().get(getSquare(),to).getOccupied().isHero()){
				((Hero)getParty().getArea().get(getSquare(),to).getOccupied()).notify(new Notification(Notification.pleaseMove));
			}
		}
		else {
			move(to,getParty().getArea());
		}
		return true;
	}

	@Override
	public List<Item> getItems() {
		return items;
	}

	public void heal(float healingValue) {
		attributeAdd("hp",healingValue*getAttribute("maxHp"));
		if(attributeGreaterThan("hp",getAttribute("maxHp"))){
			setAttributeValue("hp",getAttribute("maxHp"));
		}
	}


	public Magic getMagic(MagicType type) {
		for(Magic magic:magicks){
			if(magic.is(type)){
				return magic;
			}
		}
		return null;
	}

	public List<Magic> getMagicks(MagicType... ts) {
		List<Magic> types = new ArrayList<Magic>();
		for(Magic magic:magicks){
			boolean isType = true;
			for(MagicType type:ts){
				if(!magic.is(type)){
					isType = false;
					break;
				}
			}
			if(isType){
				types.add(magic);
			}
		}
		return types;
	}

	@Override
	public int getStrength() {
		return (int) getAttribute("strength");
	}

}
