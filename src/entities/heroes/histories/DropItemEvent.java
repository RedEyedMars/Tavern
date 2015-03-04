package entities.heroes.histories;

import items.Item;
import game.Area;
import game.Square;

public class DropItemEvent extends HistoryEvent {

	private String event;
	private Item item;

	public DropItemEvent(Item item, Square square, Area area){
		super();
		this.item = item;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(item.getName());
		eventBuilder.append(" dropped at ");
		eventBuilder.append(square.getX());
		eventBuilder.append(",");
		eventBuilder.append(square.getY());
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
		return 0.95f;
	}

}
