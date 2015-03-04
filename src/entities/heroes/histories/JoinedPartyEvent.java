package entities.heroes.histories;

import entities.heroes.Hero;
import entities.heroes.Party;


public class JoinedPartyEvent extends HistoryEvent {

	private Hero joiner;
	private Party joined;
	private String event;
	
	public JoinedPartyEvent(Hero join, Party party){
		this.joiner = join;
		this.joined = party;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(joiner.getName());
		eventBuilder.append(" joined ");
		eventBuilder.append(joined.getName());
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
		return 1.25f;
	}
}
