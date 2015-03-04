package entities.heroes.histories;

import items.Item;
import game.interfaces.Nameable;

public class GiveItemToEvent extends HistoryEvent {

	private String event;
	private Item item;

	public GiveItemToEvent(Item item, Nameable hero){
		super();
		this.item = item;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(item.getName());
		eventBuilder.append(" was given to ");
		eventBuilder.append(hero.getName());
		eventBuilder.append(" (");
		eventBuilder.append(getTime());
		eventBuilder.append(")");
		event = eventBuilder.toString();
		
	}
	
	@Override
	public String getEvent() {
		return event;
	}
	
	public Item getItem(){
		return item;
	}
	
	@Override
	public float postivity() {
		return 1.1f;
	}

}
