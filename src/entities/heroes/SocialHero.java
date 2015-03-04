package entities.heroes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import entities.Direction;
import entities.heroes.behaviours.BriefBehaviour;
import entities.heroes.discussionpoints.NextQuestDiscussionPoint;
import entities.heroes.histories.EndQuestEvent;
import entities.heroes.histories.HeroDiedEvent;
import entities.heroes.histories.HeroLeftPartyEvent;
import entities.heroes.histories.History;
import entities.heroes.histories.HistoryEvent;
import entities.heroes.histories.JoinedPartyEvent;
import entities.heroes.histories.MeetHeroEvent;
import entities.heroes.quests.Quest;

public abstract class SocialHero extends AmbitionedHero {

	private Map<Hero,History> relationships;
	private Map<Hero,Boolean> relationshipChanged;
	private Map<Hero,Float> cachedRespect;
	private Map<Party, Float> partySatisfaction;
	public SocialHero(String name) {
		super(name);
		relationships =	new HashMap<Hero,History>();
		relationshipChanged =	new HashMap<Hero,Boolean>();
		cachedRespect =	new HashMap<Hero,Float>();
		partySatisfaction = new HashMap<Party, Float>();
	}
	public void relationshipDeappend(Hero hero, HistoryEvent event) {
		relationships.get(hero).deappend(event);
		relationshipChanged.put(hero, false);
	}

	public void relationshipAppend(Hero hero, HistoryEvent event) {
		if(!relationships.containsKey(hero)){
			relationships.put(hero, new History());
		}
		relationships.get(hero).append(event);
		relationshipChanged.put(hero, true);
	}
	public void relationshipAppend(Hero hero, HistoryEvent start, HistoryEvent event) {
		if(!relationships.containsKey(hero)){
			relationships.put(hero, new History());
			relationships.get(hero).append(start);
		}
		relationships.get(hero).append(event);
		relationshipChanged.put(hero, true);
	}

	public void relationshipDeappend(Hero hero, HistoryEvent start, HistoryEvent event) {
		if(relationships.get(hero).length()<=2){
			relationships.get(hero).deappend(start);
			relationships.remove(hero);
		}
		relationships.get(hero).deappend(event);
		relationshipChanged.put(hero, false);
	}
	

	public Collection<Hero> knownHeroes() {
		return relationships.keySet();
	}	public void notify(Notification notification){
		switch(notification.getSubject()){
		case Notification.partyChanged:
		{
			if(notification.is(Notification.heroLeft)){
				relationshipAppend(notification.getHero(),new HeroLeftPartyEvent(notification.getHero(),getParty()));
			}
			else if(notification.is(Notification.heroDies)){
				relationshipAppend(notification.getHero(),new HeroDiedEvent(notification.getHero()));
			}
			else if(notification.is(Notification.heroJoined)){
				relationshipAppend(notification.getHero(),new MeetHeroEvent((Hero) this,notification.getHero()), new JoinedPartyEvent(notification.getHero(),getParty()));
			}
			
			break;
		}
		case Notification.pleaseMove:{
			setBehaviour(new BriefBehaviour(getBehaviour()){
				@Override
				public boolean perform(Hero hero){
					Set<Direction> directions = new TreeSet<Direction>();
					while(directions.size()<4){
						directions.add(Direction.random());
					}
					for(Direction direction:directions){
						if(!hero.getParty().getArea().get(hero.getSquare(),direction).isOccupied()){
							return hero.move(direction, hero.getParty().getArea());
						}
					}
					return false;
				}
			});
			break;
		}
		case Notification.questEnded:{
			for(Hero hero:getParty()){
				relationshipAppend(hero,new EndQuestEvent(notification.getQuest()));
			}
			if(getQuest().equals(notification.getQuest())){
				getParty().addDiscussionPoint(new NextQuestDiscussionPoint(getParty()));
			}
			break;
		}
		}
	}

	//NOT CURRENTLY USED
	public void unnotify(Notification notification) {
		switch(notification.getSubject()){
		case Notification.partyChanged:
		{
			if(notification.is(Notification.heroLeft)){
				relationshipDeappend(notification.getHero(),new HeroLeftPartyEvent(notification.getHero(),getParty()));
			}
			else if(notification.is(Notification.heroDies)){
				relationshipDeappend(notification.getHero(),new HeroDiedEvent(notification.getHero()));
			}
			else if(notification.is(Notification.heroJoined)){
				relationshipDeappend(notification.getHero(),new MeetHeroEvent((Hero) this,notification.getHero()), new JoinedPartyEvent(notification.getHero(),getParty()));
			}
			break;
		}
		}
	}
	public void modifyMood(float tone, Hero hero) {
		attributeMultiply("mood",(float) Math.pow(tone,getAttribute("stubborness")+respect(hero)));
	}
	private float enjoyingParty(Party party) {
		float sum = this.partySatisfaction.get(party);
		for(Hero hero:party){
			sum*=respect(hero);
		}		
		return sum;
	}
	public float importance(Quest quest) {
		return getQuest().getEvaluationForHero((Hero) this)+((float)this.getParty().getHeroes().size())/100+enjoyingParty(this.getParty());
	}
	public float respect(Hero hero) {
		if(relationshipChanged.get(hero)){
			float respect = 1.0f;
			for(HistoryEvent ev:relationships.get(hero)){
				respect*=ev.postivity();
			}
			relationshipChanged.put(hero, false);
			cachedRespect.put(hero,respect);
		}
		return cachedRespect.get(hero);
	}
	
	public float impression(Hero meetee) {
		float impression = 1f;
		int rank = 0;
		for(Ambition a:getAmbitions()){
			boolean hasSame = false;
			for(Ambition b:meetee.getAmbitions()){
				if(a.getName().equals(b.getName())){
					hasSame = true;
					impression+=getAttribute("extrovert")*Math.pow((getNumberOfAmbitions()-rank)/getNumberOfAmbitions(),2);
					break;
				}
			}
			if(!hasSame){
				impression-=getAttribute("extrovert") * (getNumberOfAmbitions()-rank)/getNumberOfAmbitions();
			}
			++rank;
		}
		return impression;
	}
}
