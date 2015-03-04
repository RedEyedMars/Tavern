package entities.stats;

import entities.Entity;

public abstract class Behaviour {
	protected boolean done = false;
	public abstract void act(Entity entity);
	public boolean isDone(){
		return done;
	}
}
