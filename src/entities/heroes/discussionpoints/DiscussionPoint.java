package entities.heroes.discussionpoints;

import java.util.List;

import entities.heroes.Hero;
import entities.heroes.histories.HistoryEvent;

public abstract class DiscussionPoint {
	protected float outcome = 1f;
	public abstract float tone(Hero hero);
	public abstract HistoryEvent getDiscussionEvent();
	public abstract List<Hero> involvedHeroes();
	public void discuss(){
		for(Hero hero:involvedHeroes()){
			hero.modifyMood(tone(hero)*outcome,hero);
		}
	}
}
