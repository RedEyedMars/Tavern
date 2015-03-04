package entities.heroes.histories;

import entities.Entity;
import entities.heroes.Hero;

public class PartyMemberWoundedEvent extends HistoryEvent{
	private String event;
	private Entity source;
	private Hero diee;

	public PartyMemberWoundedEvent(Entity source, Hero diee){
		super();
		this.source = source;
		this.diee = diee;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(source.getName());
		eventBuilder.append(" wounded ");
		eventBuilder.append(diee.getName());
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
		return diee;
	}

	public Entity getSource(){
		return source;
	}

	@Override
	public float postivity() {
		return 0.95f;
	}
}
