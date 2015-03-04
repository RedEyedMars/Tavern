package entities.heroes.histories;

import entities.heroes.quests.Quest;

public class EndQuestEvent extends HistoryEvent {

	private String event;
	private Quest quest;
	
	public EndQuestEvent(Quest quest){
		super();
		this.quest = quest;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(quest.getName());
		eventBuilder.append(" was ended");
		eventBuilder.append(" (");
		eventBuilder.append(getTime());
		eventBuilder.append(")");
		event = eventBuilder.toString();
		
	}
	
	@Override
	public String getEvent() {
		return event;
	}
	
	public Quest getQuest(){
		return quest;
	}

	@Override
	public float postivity() {
		return 1.1f;
	}
}
