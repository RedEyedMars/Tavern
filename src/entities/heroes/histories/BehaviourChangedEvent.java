package entities.heroes.histories;

import entities.heroes.Hero;
import entities.stats.Behaviour;


public class BehaviourChangedEvent extends HistoryEvent {

	private String event;
	private Behaviour behaviour;
	private Hero hero;
	
	public BehaviourChangedEvent(Hero hero, Behaviour behaviour){
		super();
		this.hero = hero;
		this.behaviour = behaviour;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(hero.getName());
		eventBuilder.append(" started ");
		eventBuilder.append(behaviour.toString());
		eventBuilder.append(" (");
		eventBuilder.append(getTime());
		eventBuilder.append(")");
		event = eventBuilder.toString();
		
	}
	
	@Override
	public String getEvent() {
		return event;
	}

	@Override
	public float postivity() {
		return 1f;
	}

	public Behaviour getBehaviour() {
		return behaviour;
	}

	public Hero getHero() {
		return hero;
	}

}
