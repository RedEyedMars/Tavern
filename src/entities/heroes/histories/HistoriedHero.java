package entities.heroes.histories;

import entities.heroes.AttributedHero;

public abstract class HistoriedHero extends AttributedHero {

	private History history;
	public HistoriedHero(String name) {
		super(name);
		history = new History();
	}
	
	public void appendEvent(HistoryEvent event){
		history.append(event);
	}

}
