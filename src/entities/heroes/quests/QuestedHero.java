package entities.heroes.quests;

import java.util.ArrayList;
import java.util.List;

import entities.heroes.Hero;
import entities.heroes.Party;
import entities.heroes.behaviours.BehaviouredHero;
import entities.heroes.histories.HistoryEvent;
import entities.heroes.histories.StartQuestEvent;

public abstract class QuestedHero extends BehaviouredHero {



	private Party party;
	private Quest currentQuest;

	protected boolean consideringQuest = false;
	private List<Quest> ongoingQuests;
	
	public QuestedHero(String name) {
		super(name);
		ongoingQuests = new ArrayList<Quest>();
	}


	public Party getParty(){
		return party;
	}
	
	public Quest getQuest(){
		return currentQuest;
	}
	
	public void report(HistoryEvent event) {
		if(currentQuest!=null){
			currentQuest.reportEvent(event);
		}
		if(party!=null){
			party.reportEvent(event);
		}
	}
	
	public void unreport(HistoryEvent event) {
		if(currentQuest!=null){
			currentQuest.unreportEvent(event);
		}
		if(party!=null){
			party.unreportEvent(event);
		}
	}
	
	public void startQuest(Quest quest){
		consideringQuest = true;

		for(Hero hero:getParty().getHeroes()){
			if(!hero.consideringQuest){
				hero.considerQuest(quest);
			}
		}
	}
	
	public void continueQuest(Quest quest){
		currentQuest = quest;
		if(!ongoingQuests.contains(quest)){
			ongoingQuests.add(quest);
			appendEvent(new StartQuestEvent(quest,(Hero) this));
		}
	}

	public void considerQuest(Quest quest) {
		if(currentQuest == null||currentQuest.getEvaluationForHero((Hero) this)<quest.evaluateForHero((Hero) this)){
			continueQuest(quest);
		}
	}
	
	public List<Quest> getKnownQuests() {
		return ongoingQuests;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public void leaveParty() {
		party.removeHero((Hero) this);
		party = new Party();
		party.addHero((Hero) this);
		party.start();
	}
}
