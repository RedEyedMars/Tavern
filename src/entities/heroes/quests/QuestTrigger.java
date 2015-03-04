package entities.heroes.quests;

import entities.heroes.histories.HistoryEvent;

public abstract class QuestTrigger <Event extends HistoryEvent>{
	private Class<? extends Event> onTrigger;
	public QuestTrigger(Class<? extends Event> ev){
		onTrigger = ev;
	}
	public Class<? extends Event> onEventTrigger(){
		return onTrigger;
	}
	public abstract void trigger(Event event);
}
