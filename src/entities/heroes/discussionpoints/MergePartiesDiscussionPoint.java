package entities.heroes.discussionpoints;

import java.util.ArrayList;
import java.util.List;

import entities.heroes.Hero;
import entities.heroes.Party;
import entities.heroes.histories.DiscussionEvent;
import entities.heroes.histories.HistoryEvent;

public class MergePartiesDiscussionPoint extends DiscussionPoint{
	private Party subscriber;
	private Party subscribee;
	public MergePartiesDiscussionPoint(Party subscriber, Party subscribee) {
		this.subscriber = subscriber;
		this.subscribee = subscribee;
	}
	public float tone(Hero hero){
		float tone = 1.0f;
		for(Hero answerer:subscribee){
			tone*=hero.respect(answerer);
		}
		return tone;
	}
	public HistoryEvent getDiscussionEvent(){
		return new DiscussionEvent(subscriber.getHeroWithRoleElseRandom("Leader"), " asking to merge party with "+subscribee.getHeroWithRoleElseRandom("Leader").getName(), outcome);
	}
	public List<Hero> involvedHeroes(){		
		return subscriber.with(subscribee);
	}
	public void discuss(){
		if(subscriber.getSize()>0&&subscribee.getSize()>0){
			return;
		}
		outcome = 1.0f;
		for(Hero asker:subscriber){
			outcome*=tone(asker);
		}
		for(Hero hero:subscribee){
			outcome*=subscriber.value(hero);
		}
		for(Hero hero:subscriber){
			outcome*=subscribee.value(hero);
		}
		for(Hero hero:subscribee){
			outcome*=(0.95+hero.getAttribute("extrovert")/10f);
		}
		for(Hero hero:subscriber){
			outcome*=(0.95+hero.getAttribute("extrovert")/10f);
		}
		if(outcome>1){
			List<Hero> heros = new ArrayList<Hero>();
			while(subscribee.getSize()>0){
				heros.add(subscribee.getHeroes().get(0));
				subscribee.removeHero(subscribee.getHeroes().get(0));
			}
			while(subscriber.getSize()>0){
				heros.add(subscriber.getHeroes().get(0));
				subscribee.removeHero(subscriber.getHeroes().get(0));
			}
			Party party = new Party();
			for(Hero hero:heros){
				party.addHero(hero);
			}
		}
		super.discuss();
	}
}
