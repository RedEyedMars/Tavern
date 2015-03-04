package entities.heroes.quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Entity;
import entities.heroes.Ambition;
import entities.heroes.Hero;
import entities.heroes.Notification;
import entities.heroes.Reward;
import entities.heroes.histories.EndQuestEvent;
import entities.heroes.histories.History;
import entities.heroes.histories.HistoryEvent;
import entities.heroes.quests.requirements.QuestRequirement;
public class Quest {

	protected String name;
	protected String type;
	private List<Reward> rewards;
	private int totalScore;
	private Map<Hero, Integer> evaluations;
	private List<QuestRequirement> requirements;
	private History history;
	
	protected Map<String,Object> map;
	protected Entity target;
	private boolean mallicious = true;

	@SuppressWarnings("rawtypes")
	private Map<Class<? extends HistoryEvent>,List<QuestTrigger>> triggers;

	@SuppressWarnings("rawtypes")
	public Quest(String name, QuestRequirement... requirements){
		this.name = name;
		this.totalScore = 0;
		evaluations = new HashMap<Hero, Integer>();
		rewards = new ArrayList<Reward>();
		map = new HashMap<String,Object>();
		this.requirements = new ArrayList<QuestRequirement>();
		this.requirements.addAll(Arrays.asList(requirements));
		this.triggers = new HashMap<Class<? extends HistoryEvent>,List<QuestTrigger>>();
		this.history = new History();
	}

	public String getName() {
		return name;
	}

	public int evaluateForHero(Hero hero){
		if(evaluations.containsKey(hero)){
			int score = 0;
			//TODO for each ambition in the hero, rate the quest's fulfillment of that ambition
			for(Ambition ambition: hero.getAmbitions()){
				score+=ambition.getEvaluation(this);
			}
			evaluations.put(hero,score);
			totalScore+=score;
		}
		return evaluations.get(hero);
	}

	public float getEvaluationForHero(Hero hero) {
		return 0.9f+((float)evaluations.get(hero))/totalScore;
	}

	public Collection<Reward> getReward() {
		return rewards;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addTrigger( QuestTrigger questTrigger) {
		Class<? extends HistoryEvent> triggerOn = questTrigger.onEventTrigger();
		if(!triggers.containsKey(triggerOn)){
			triggers.put(triggerOn,new ArrayList<QuestTrigger>());
		}
		triggers.get(triggerOn).add(questTrigger);
	}

	public History getHistory() {
		return history;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void reportEvent(HistoryEvent event) {
		history.append(event);
		if(triggers.containsKey(event.getClass())){
			for(QuestTrigger trigger:triggers.get(event.getClass())){
				trigger.trigger(event);
			}
		}
	}

	public void unreportEvent(HistoryEvent event) {
		history.deappend(event);
	}

	public Entity getTarget() {
		return target;
	}
	
	public boolean isMallicious(){
		return mallicious;
	}

	public boolean getBoolean(String string) {
		return (Boolean)map.get(string);
	}
	
	public String getString(String string) {
		return (String)map.get(string);
	}
	
	public int getInteger(String string) {
		return (Integer)map.get(string);
	}
	
	public float getFloat(String string){
		return (Float)map.get(string);
	}

	public String getType() {
		return type;
	}
	
	public void finish(Hero hero){
		HistoryEvent event = new EndQuestEvent(this);
		hero.report(event);
		for(Hero h:hero.getParty()){
			h.notify(new Notification(Notification.questEnded,this));
		}
	}

}
