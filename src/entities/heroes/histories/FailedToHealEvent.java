package entities.heroes.histories;

import entities.heroes.Hero;

public class FailedToHealEvent extends HistoryEvent {

	private Hero protecter;
	private Hero protectee;
	private String event;

	public FailedToHealEvent(Hero me, Hero you){
		this.protecter = me;
		this.protectee = you;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(me.getName());
		eventBuilder.append(" failed to heal ");
		eventBuilder.append(you.getName());
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
		return 1-protecter.impression(protectee);
	}
}
