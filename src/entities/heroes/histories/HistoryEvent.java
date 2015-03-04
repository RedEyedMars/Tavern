package entities.heroes.histories;

import game.Time;
import game.Timer;

public abstract class HistoryEvent {
	protected Time time;
	public HistoryEvent(){
		this.time = Timer.getTime();
	}
	public abstract String getEvent();
	public abstract float postivity();
	public Time getTime(){
		return time;
	}
}
