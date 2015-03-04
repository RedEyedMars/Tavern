package entities.heroes;

import entities.heroes.discussionpoints.DiscussionPoint;
import entities.heroes.discussionpoints.MergePartiesDiscussionPoint;
import entities.heroes.histories.History;
import entities.heroes.histories.HistoryEvent;
import entities.heroes.misc.Condition;
import entities.heroes.roles.HeroRole;
import entities.heroes.roles.HeroRoles;
import game.Area;
import game.Game;

import java.util.*;

public class Party implements Iterable<Hero>{
	private List<Hero> heroes;

	private String name;

	private Area area;

	private List<DiscussionPoint> discussionPoints;

	private History history;

	public Party(){
		heroes = new ArrayList<Hero>();
		discussionPoints = new ArrayList<DiscussionPoint>();
		history = new History();
	}

	public void addHero(Hero hero){
		heroes.add(hero);
		hero.setParty(this);
		notifyHeroes(heroes,new Notification(Notification.partyChanged, Notification.heroJoined, hero));
	}

	public void removeHero(Hero hero){
		heroes.remove(hero);
		notifyHeroes(heroes,new Notification(Notification.partyChanged, Notification.heroLeft, hero));
	}

	public void setName(String name){
		this.name = name;
	}

	public void setArea(Area area){
		this.area = area;
	}

	public static void notifyHeroes(Collection<Hero> heroes, Notification note){
		for(Hero other:heroes){
			other.notify(note);
		}
	}


	public static void unnotifyHeroes(Collection<Hero> heroes,	Notification note) {
		for(Hero other:heroes){
			other.unnotify(note);
		}
	}

	public String getName() {
		return name;
	}

	public Area getArea() {
		return area!=null?area:new Area(Game.game);
	}

	public void dieHero(Hero hero) {
		removeHero(hero);
		notifyHeroes(hero.knownHeroes(),new Notification(Notification.heroUpdate, Notification.heroDies, hero));
	}	

	public void undieHero(Hero hero) {
		heroes.add(hero);
		unnotifyHeroes(hero.knownHeroes(),new Notification(Notification.heroUpdate, Notification.heroDies, hero));
	}	

	public List<Hero> getHeroes() {
		return heroes;
	}

	public boolean contains(HeroRole role) {
		for(Hero hero:heroes){
			if(hero.hasRole(role)){
				return true;
			}
		}
		return false;
	}

	public float maxCompentencyForRole(HeroRole role) {
		float max = 0f;
		for(Hero hero:heroes){
			float trial = role.compitency(hero);
			if(trial>max){
				max = trial;
			}
		}
		return max;
	}

	@Override
	public Iterator<Hero> iterator() {
		return heroes.iterator();
	}

	public void addDiscussionPoint(DiscussionPoint discussionPoint) {
		discussionPoints.add(discussionPoint);
	}

	public void reportEvent(HistoryEvent event) {
		history.append(event);
	}

	public void unreportEvent(HistoryEvent event) {
		history.append(event);
	}
	public Hero getHeroWithRole(String role) {
		for(Hero hero:heroes){
			if(hero.hasRole(role)){
				return hero;
			}
		}
		return null;
	}

	public Hero getHeroWithRoleElseRandom(String role) {
		for(Hero hero:heroes){
			if(hero.hasRole(role)){
				return hero;
			}
		}
		return heroes.get((int) (Math.random()*heroes.size()));
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void disband() {
		while(!heroes.isEmpty()){
			heroes.get(0).leaveParty();
		}
		for(Hero hero1:this){
			for(Hero hero2:this){
				if(!hero2.equals(hero1)){
					if(hero1.respect(hero2)>2-hero1.getAttribute("extrovert")){
						hero2.getParty().addDiscussionPoint(new MergePartiesDiscussionPoint(hero1.getParty(),hero2.getParty()));
					}
				}
			}
		}
	}

	public List<Hero> with(Party p) {
		List<Hero> list = new ArrayList<Hero>();
		list.addAll(heroes);
		list.addAll(p.heroes);
		return list;
	}

	public float value(Hero hero) {
		float value = 1.0f;
		for(HeroRole role:HeroRoles.roles){
			float best = 0.0f;
			for(Hero member:heroes){
				float compitency = role.compitency(member);
				if(compitency>best){
					best = compitency;
				}
			}
			value*=Math.pow(role.compitency(hero)/best,2);
		}
		return value;
	}

	public int getSize() {
		return heroes.size();
	}	
	public float getSize(Float i) {
		return heroes.size()+i;
	}
	public float getSize(String operator,Float i) {
		if("plus".equals(operator)){
			return heroes.size()+i;
		}
		else if("minus".equals(operator)){
			return heroes.size()-i;
		}
		return heroes.size();
	}

	public float getAttribute(String attribute){
		float sum = 0.0f;
		for(Hero hero:this){
			sum+=hero.getAttribute(attribute);
		}
		return sum;
	}

	public float getAttributeWithout(String attribute,Hero heroine){
		float sum = 0.0f;
		//if(heros.length==1){
			for(Hero hero:this){
				if(hero!=heroine){//heros[0]){
					sum+=hero.getAttribute(attribute);
				}
			}
		//}
		/*else {
			for(Hero hero:this){
				boolean contained = false;
				for(Hero member:heros){
					if(member == hero){
						contained = true;
					}
				}
				if(!contained){
					sum+=hero.getAttribute(attribute);
				}
			}
		}*/
		return sum;
	}

	public float getCountByConditionWithout(Condition condition, Hero heroine) {
		float sum = 0.0f;
		//if(heros.length==1){
			for(Hero hero:this){
				if(hero!=heroine&&condition.satisfies(hero)){
					++sum;
				}
			}
		//}
		/*else {
			for(Hero hero:this){
				boolean contained = false;
				for(Hero member:heros){
					if(member == hero){
						contained = true;
					}
				}
				if(!contained&&condition.satisfies(hero)){
					++sum;
				}
			}
		}*/
		return sum;
		
	}
}
