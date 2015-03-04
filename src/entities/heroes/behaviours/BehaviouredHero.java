package entities.heroes.behaviours;

import entities.heroes.Hero;
import entities.heroes.histories.BehaviourChangedEvent;
import entities.heroes.histories.HistoriedHero;
import entities.stats.Behaviour;
import game.Timer;

public abstract class BehaviouredHero extends HistoriedHero {
	private Behaviour behaviour;

	public BehaviouredHero(String name) {
		super(name);
	}


	public void changeBehaviour(Behaviour newBehaviour) {
		appendEvent(new BehaviourChangedEvent((Hero) this,newBehaviour));
		this.behaviour = newBehaviour;
	}
	
	public void setBehaviour(Behaviour behaviour){
		this.behaviour = behaviour;
	}

	public Behaviour getBehaviour(){
		return behaviour;
	}
	

	@Override
	public void update() {
		if(Timer.getTicks()%((int)getAttribute("speed"))==0){
			if(behaviour == null){
				behaviour = new DecideNextBehaviourBehaviour();
				if(getAttribute("intellect")>8){
					behaviour.act(this);
				}
			}
			else {
				behaviour.act(this);
				if(behaviour.isDone()){
					behaviour = null;
				}
			}
		}
	}
}
