package entities.heroes;

import java.util.*;

import entities.heroes.quests.Quest;
import entities.monsters.Monster;
import game.Area;

public class Notification {

	
	public static final int partyChanged = 0;
	public static final int heroLeft = 1;
	public static final int heroJoined = 2;
	public static final int heroUpdate = 3;
	public static final int heroDies = 4;
	
	public static final int questEnded = 5;
	
	public static final int pleaseMove = 6;
	
	private int subject;
	private List<Integer> subtopics;
	private List<Hero> herotopics;
	private List<Monster> monstertopics;
	private List<Area> areatopics;
	private List<Quest> questtopics;
	
	public Notification(int head, Object... args){
		subject = head;
		for(Object arg:args){
			if(arg instanceof Integer){
				if(subtopics==null)
					subtopics = new ArrayList<Integer>();
				subtopics.add((Integer)arg);
			}
			else if(arg instanceof Hero){
				if(herotopics==null)
					herotopics = new ArrayList<Hero>();
				herotopics.add((Hero)arg);
			}
			else if(arg instanceof Monster){
				if(monstertopics==null)
					monstertopics = new ArrayList<Monster>();
				monstertopics.add((Monster)arg);
			}
			else if(arg instanceof Area){
				if(areatopics==null)
					areatopics = new ArrayList<Area>();
				areatopics.add((Area)arg);
			}
			else if(arg instanceof Quest){
				if(questtopics==null)
					questtopics = new ArrayList<Quest>();
				questtopics.add((Quest)arg);
			}
		}
	}
	public int getSubject(){
		return subject;
	}
	
	public boolean is(Integer sub){
		return subtopics.contains(sub);
	}
	
	public Hero getHero(){
		if(herotopics==null||herotopics.size()==0){
			return null;
		}
		else return herotopics.get(0);
	}
	public Hero getHero(int index){
		if(herotopics==null||herotopics.size()==0){
			return null;
		}
		else return herotopics.get(index);
	}
	public Monster getMonster(){
		if(monstertopics==null||monstertopics.size()==0){
			return null;
		}
		else return monstertopics.get(0);
	}
	public Monster getMonster(int index){
		if(monstertopics==null||monstertopics.size()==0){
			return null;
		}
		else return monstertopics.get(index);
	}
	public Area getArea(){
		if(areatopics==null||areatopics.size()==0){
			return null;
		}
		else return areatopics.get(0);
	}
	public Area getArea(int index){
		if(areatopics==null||areatopics.size()==0){
			return null;
		}
		else return areatopics.get(index);
	}
	public Quest getQuest() {
		if(questtopics==null||questtopics.size()==0){
			return null;
		}
		else return questtopics.get(0);
	}
	public Quest getQuest(int index) {
		if(questtopics==null||questtopics.size()==0){
			return null;
		}
		else return questtopics.get(index);
	}
}
