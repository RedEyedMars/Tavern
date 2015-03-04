package entities.heroes.discussionpoints;

import java.util.List;

import entities.heroes.Hero;
import entities.heroes.Party;
import entities.heroes.histories.DiscussionEvent;
import entities.heroes.histories.HistoryEvent;
import entities.heroes.quests.Quest;

public class NextQuestDiscussionPoint extends DiscussionPoint{

	private Party party;
	public NextQuestDiscussionPoint(Party party) {
		this.party = party;
	}
	public float tone(Hero hero){
		return 1f;

	}
	public HistoryEvent getDiscussionEvent(){
		return new DiscussionEvent(party.getHeroWithRoleElseRandom("Leader")," the next quest to undertake ", outcome);
	}
	public List<Hero> involvedHeroes(){
		return party.getHeroes();
	}
	public void discuss(){
		Quest bestQuest = null;
		int bestScore = 0;
		for(Hero hero:party){
			for(Quest quest:hero.getKnownQuests()){
				int score = 0;
				for(Hero h:party){
					score+=quest.evaluateForHero(h);
				}
				if(score>bestScore){
					bestQuest = quest;
					bestScore = score;
				}
			}
		}
		if(bestQuest!=null){
			float average = ((float)bestScore)/party.getHeroes().size();
			for(Hero hero:party){
				hero.continueQuest(bestQuest);
				outcome*=Math.pow(bestQuest.evaluateForHero(hero)/average,2);
			}
		}
		else {
			for(Hero hero1:party){
				for(Hero hero2:party){
					if(!hero2.equals(hero1)){
						outcome*=hero1.respect(hero2);
					}
				}
			}
			party.addDiscussionPoint(new DisbandDiscussionPoint(party,DisbandDiscussionPoint.noQuests));
		}
		super.discuss();
	}
}
