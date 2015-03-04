package entities.heroes.discussionpoints;

import java.util.List;

import entities.heroes.Hero;
import entities.heroes.Party;
import entities.heroes.histories.DiscussionEvent;
import entities.heroes.histories.HistoryEvent;

public class DisbandDiscussionPoint extends DiscussionPoint {

	public static final int noQuests = 0;
	public static final int heroLeft = 1;
	public static final float[] weights = new float[]{1.2f,0.9f};
	private float outcome;
	private Hero hero;
	private int reason;
	
	public DisbandDiscussionPoint(Party party,int reason){
		hero = party.getHeroWithRoleElseRandom("Leader");
		this.reason = reason;
	}

	@Override
	public float tone(Hero hero) {
		return 0.75f;
	}

	@Override
	public HistoryEvent getDiscussionEvent() {
		return new DiscussionEvent(hero, " whether to disband the party ", outcome);
	}

	@Override
	public List<Hero> involvedHeroes() {
		return hero.getParty().getHeroes();
	}
	@Override
	public void discuss(){
		int against = 0;
		Party party = hero.getParty();
		for(Hero hero1:party){
			float feelingTowardOthers = 1.0f;
			for(Hero hero2:party){
				if(!hero1.equals(hero2)){
					feelingTowardOthers*=hero1.respect(hero2);
				}
			}
			if(feelingTowardOthers*weights[reason]>=1){
				++against;
			}
		}
		if(((float)against)/party.getHeroes().size()<2f/3f){
			party.disband();
			outcome*=0.5;
		}
		else {
			boolean left = false;
			for(Hero hero1:party){
				float feelingTowardOthers = 1.0f;
				for(Hero hero2:party){
					if(!hero1.equals(hero2)){
						feelingTowardOthers*=hero1.respect(hero2);
					}
				}
				if(feelingTowardOthers*weights[reason]<0.5){
					hero1.leaveParty();
					left = true;
					outcome*=0.9;
				}
			}
			if(left){
				party.addDiscussionPoint(new DisbandDiscussionPoint(party,DisbandDiscussionPoint.heroLeft));
			}
			else {
				outcome*=1.1;
			}
		}
		super.discuss();
	}
}
