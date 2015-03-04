package entities.heroes.histories;

import entities.heroes.Hero;
import entities.monsters.Monster;

public class MonsterKillEvent extends HistoryEvent{
	private String event;
	private Monster monster;
	private Hero hero;
	private float positionOnKill;
	
	public MonsterKillEvent(Hero hero, Monster monster){
		super();
		this.monster = monster;
		this.hero = hero;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(hero.getName());
		if(monster.isLegendary()){
			eventBuilder.append(" killed ");
		}
		else {
			eventBuilder.append(" killed a ");
		}
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
	
	public Monster getMonster(){
		return monster;
	}

	@Override
	public float postivity() {
		return positionOnKill;
	}
}
