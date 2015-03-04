package entities.heroes.histories;

import entities.Entity;
import items.Item;

public class UseItemEvent extends HistoryEvent {

	private String event;
	private Item item;
	private Entity entity;
	
	public UseItemEvent(Item item, Entity entity){
		super();
		this.item = item;
		this.entity = entity;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(item.getName());
		eventBuilder.append(" was used by ");
		eventBuilder.append(entity.getName());
		eventBuilder.append("(");
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
	
	public Entity getUser(){
		return entity;
	}

	@Override
	public float postivity() {
		return 1f;
	}

}
