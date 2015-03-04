package entities.heroes.histories;

import items.Item;

public class CreateItemEvent extends HistoryEvent {

	private String event;
	private Item item;
	
	public CreateItemEvent(Item item){
		super();
		this.item = item;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(item.getName());
		eventBuilder.append(" was created (");
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
		return 1f;
	}

}
