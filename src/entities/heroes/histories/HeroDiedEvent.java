package entities.heroes.histories;

import entities.heroes.Hero;

public class HeroDiedEvent extends HistoryEvent{
	private String event;
	private Hero dead;

	public HeroDiedEvent(Hero dead){
		super();
		this.dead = dead;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(dead.getName());
		eventBuilder.append(" is dead");
		eventBuilder.append(" (");
		eventBuilder.append(getTime());
		eventBuilder.append(")");
		event = eventBuilder.toString();
	}

	@Override
	public String getEvent() {
		return event;
	}

	public Hero getHero(){
		return dead;
	}

	@Override
	public float postivity() {
		return 5f;
	}
}
