package entities.heroes.histories;

import entities.heroes.Hero;
import entities.heroes.Party;

public class HeroLeftPartyEvent extends HistoryEvent {

	private Hero joiner;
	private Party joined;
	private String event;
	
	public HeroLeftPartyEvent(Hero join, Party party){
		this.joiner = join;
		this.joined = party;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(joiner.getName());
		eventBuilder.append(" left ");
		eventBuilder.append(party.getName());
		eventBuilder.append(" (");
		eventBuilder.append(getTime());
		eventBuilder.append(")");
	}
	
	@Override
	public String getEvent() {
		return event;
	}
	
	public Party getParty(){
		return joined;
	}

	@Override
	public float postivity() {
		return 0.75f;
	}
}
