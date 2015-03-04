package entities.heroes.histories;

import entities.heroes.Hero;

public class DiscussionEvent extends HistoryEvent {
	private Hero discusser;
	private String event;
	private float influence = 1.05f;
	
	public DiscussionEvent(Hero discusser, String topic){
		this.discusser = discusser;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(discusser.getName());
		eventBuilder.append(" discussed ");
		eventBuilder.append(topic);
		eventBuilder.append(" (");
		eventBuilder.append(getTime());
		eventBuilder.append(")");
		event = eventBuilder.toString();
	}
	public DiscussionEvent(Hero discusser, String topic, float outcome){
		this(discusser,topic);
		influence = outcome;
	}
	
	@Override
	public String getEvent() {
		return event;
	}
	
	public Hero getSpeaker(){
		return discusser;
	}

	public void setInfluence(float i){
		influence = i;
	}
	
	@Override
	public float postivity() {
		return influence;
	}
}
