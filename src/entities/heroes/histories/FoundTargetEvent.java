package entities.heroes.histories;

import entities.Entity;
import entities.heroes.Hero;


public class FoundTargetEvent extends HistoryEvent {

	private Hero finder;
	private Entity target;
	private String event;
	
	public FoundTargetEvent(Hero finder, Entity target){
		this.finder = finder;
		this.target = target;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(finder.getName());
		eventBuilder.append(" found ");
		eventBuilder.append(target.getName());
		eventBuilder.append(" at ");
		eventBuilder.append(target.getSquare().toString());
		eventBuilder.append(" (");
		eventBuilder.append(getTime());
		eventBuilder.append(")");
		event = eventBuilder.toString();
	}
	
	@Override
	public String getEvent() {
		return event;
	}
	
	public Hero getFinder(){
		return finder;
	}
	
	public Entity getTarget(){
		return target;
	}

	@Override
	public float postivity() {
		return 1.25f;
	}

}
