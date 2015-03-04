package entities.monsters;

import entities.*;
import entities.heroes.Hero;
import entities.heroes.histories.MonsterKillEvent;
import entities.stats.Behaviour;
import entities.stats.Dodger;
import entities.stats.Strengther;
import entities.stats.resistances.Resistance;
import entities.stats.resistances.Resister;
import entities.stats.skills.Skill;
import game.*;
import game.interfaces.Nameable;

import items.Item;
import items.ItemBag;

import java.awt.Color;
import java.util.*;

public class Monster extends Entity implements Dodger, Resister, Strengther, Nameable{
	private static int ids = 0;
	private int id=ids++;
	private int morality;
	private int age=0;
	private int hp;
	private int maxHp;
	private int mp;
	private int maxMp;
	private int intellect;
	private int strength;
	private double dodge;
	private List<Skill> skills;
	private Map<String, Skill> skillMap;
	private List<Resistance> resistances;
	private List<Item> items;
	private double dropPercent;
	private MonsterType type;
	private Area area;
	private Entity target;
	private Behaviour behaviour;
	private int litterSize;
	private Color colour;
	private int speed;
	private int recentlyMated = 250;
	public Monster(String typeName)
	{
		super(typeName + "#" + ids);
		skills = new ArrayList<Skill>();
		resistances = new ArrayList<Resistance>();
		items = new ArrayList<Item>();
		skillMap = new HashMap<String,Skill>();
		colour = Color.black;
	}
	public void setArea(Area area){
		this.area = area;
	}
	public void setType(MonsterType type){
		this.type = type;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public void setHp(int hp)
	{
		this.hp = hp;
	}
	public void setMaxHp(int hp)
	{
		this.maxHp = hp;
	}
	public void setMp(int mp)
	{
		this.mp = mp;
	}
	public void setMaxMp(int mp)
	{
		this.maxMp = mp;
	}
	public void setIntellect(int in)
	{
		this.intellect = in;
	}
	public void setDodge(double dodge)
	{
		this.dodge = dodge;
	}
	public void setMorality(int moral){
		this.morality = moral;
	}
	public void addSkill(Skill skill)
	{
		skills.add(skill);
		skillMap.put(skill.getName(), skill);
		
	}
	public void addResistance(Resistance resistance)
	{
		resistances.add(resistance);
	}
	public void setDrop(Item item)
	{
		items.add(item);
	}
	public void addItem(Item item)
	{
		items.add(item);
	}
	public void setTarget(Entity target){
		this.target = target;
	}
	public Entity getTarget(){
		return target;
	}

	public void setRecentlyMated(int i) {
		recentlyMated = i;
	}
	public void setDropPercent(double drop) {
		this.dropPercent = drop;
	}
	public void setStrength(int i) {
		strength = i;
	}

	public int getIntellect() {
		return intellect;
	}
	public int getStrength() {
		return strength ;
	}
	public Area getArea() {
		return area;
	}
	public MonsterType getType() {
		return type;
	}
	public Skill getRandomSkill() {
		return skills.get((int)(skills.size()*Math.random()));
	}
	public int getRecentlyMated(){
		return recentlyMated;
	}
	public int getHp() {
		return hp;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public void setBehaviour(Behaviour behaviour) {
		this.behaviour = behaviour;
	}
	public Behaviour getBehaviour(){
		return behaviour;
	}
	public double getDodgePercent(){
		return dodge;
	}

	@Override
	public double getResistancer(Skill skill) {
		double resist = 1.0;
		for(Resistance resistance:resistances){
			resist*=resistance.resist(skill);
		}
		return resist;
	}

	public Skill getSkill(String string) {
		return skillMap.get(string);
	}
	public int getAge() {
		return age;
	}
	public void setLitterSize(int i) {
		litterSize = i;
	}
	public int getLitterSize() {
		return litterSize;
	}
	public void setColour(Color c){
		colour = c;
	}
	@Override
	public Color getColour() {
		return colour;
	}

	public int getMorality() {
		return morality;
	}

	public void setName(String string) {
		this.name = string;
	}
	@Override
	public Item damage(float damage, Entity effector) {
		damage = shareDamage(damage, effector);
		hp-=damage;
		if(hp<=0)
		{
			if(effector.isHero()){
				((Hero)effector).report(new MonsterKillEvent((Hero)effector, this));
			}
			die();
			if(Math.random()<=dropPercent)
			{
				Item drop = items.get((int)(items.size()*Math.random()));
				for(Item item:items){
					if(item==drop)continue;
					item.drop(getArea(),getSquare());
				}
				return drop;
			}
		}
		return null;
	}

	@Override
	public void undamage(int damage, Entity effector) {
		if(hp<=0)
		{
			if(effector instanceof Hero){
				((Hero)effector).unreport(new MonsterKillEvent((Hero)effector, this));
			}
			undie();
		}
		hp+=damage;
		
	}
	@Override
	public void update(){
		age+=1;
		if(recentlyMated>0){
			--recentlyMated;
		}
		if(age%speed==0){
			type.update(this);
		}
	}
	
	

	@Override
	public boolean isDead() {
		return hp<=0;
	}
	
	public void die() {

		this.occupy.moveOff();
		this.occupy = null;
		this.area.removeMonster(this);
	}
	
	public void undie() {
		this.resetSquare();
		this.area.addMonster(this);
	}
	
	public void beingAttackedBy(Entity entity){
		this.getType().beingAttackedBy(this, entity);
	}
	public boolean isLegendary() {
		return type!=null&&!getName().equals(type.getName());
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
	public List<Item> getItems(){
		return items;
	}

	public void heal(float healingValue) {
		hp+=healingValue*maxHp;
		if(hp>maxHp){
			hp = maxHp;
		}
	}

}
