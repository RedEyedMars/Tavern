package entities.heroes.histories;

import entities.heroes.Hero;

public class MurderKillEvent extends HistoryEvent{
	private String event;
	private Hero monster;
	private Hero hero;
	private float positionOnKill;
	
	public MurderKillEvent(Hero hero, Hero monster){
		super();
		this.monster = monster;
		this.hero = hero;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(hero.getName());
		eventBuilder.append(" killed ");
		eventBuilder.append(monster.getName());
		eventBuilder.append(" (");
		eventBuilder.append(getTime());
		eventBuilder.append(")");
		event = eventBuilder.toString();
		positionOnKill = 1.1f+(hero.getSkill("Kindhearted")==null?0.05f:-0.15f);
	}
	
	@Override
	public String getEvent() {
		return event;
	}
	
	public Hero getHero(){
		return hero;
	}
	
	public Hero getMurderer(){
		return monster;
	}

	@Override
	public float postivity() {
		return positionOnKill;
	}
}
