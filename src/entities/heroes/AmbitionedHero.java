package entities.heroes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import entities.heroes.quests.QuestedHero;

public abstract class AmbitionedHero extends QuestedHero{
	public AmbitionedHero(String name) {
		super(name);
		ambitions = new ArrayList<Ambition>();
	}

	private List<Ambition> ambitions;
	
	public int getNumberOfAmbitions(){
		return ambitions.size();
	}
	public Collection<Ambition> getAmbitions() {
		return ambitions;
	}


}
