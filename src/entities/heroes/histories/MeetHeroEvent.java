package entities.heroes.histories;

import entities.heroes.Hero;

public class MeetHeroEvent extends HistoryEvent {

	private Hero meeter;
	private Hero meetee;
	private String event;

	public MeetHeroEvent(Hero me, Hero you){
		this.meeter = me;
		this.meetee = you;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(me.getName());
		eventBuilder.append(" first meets ");
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
		return meeter.impression(meetee);
	}
}
