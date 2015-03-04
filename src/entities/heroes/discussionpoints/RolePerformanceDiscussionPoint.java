package entities.heroes.discussionpoints;

import java.util.List;

import entities.heroes.Hero;
import entities.heroes.histories.DiscussionEvent;
import entities.heroes.histories.HistoryEvent;
import entities.heroes.quests.Quest;
import entities.heroes.roles.HeroRole;

public class RolePerformanceDiscussionPoint extends DiscussionPoint{
	
	private Quest quest;
	private HeroRole role;
	private Hero hero;
	public RolePerformanceDiscussionPoint(HeroRole role, Hero hero, Quest quest) {
		this.role = role;
		this.hero = hero;
		this.quest = quest;
	}
	public float tone(Hero hero){
		return outcome*hero.importance(quest)*hero.respect(this.hero);
		
	}
	public HistoryEvent getDiscussionEvent(){
		return new DiscussionEvent(hero, " performance with the "+role.getName()+" role ", outcome);
	}
	public List<Hero> involvedHeroes(){
		return hero.getParty().getHeroes();
	}
	public void discuss(){
		outcome = 1f;
		for(HistoryEvent event:quest.getHistory()){
			outcome*=event.postivity()*role.factor(event);
		}
		super.discuss();
	}
}
