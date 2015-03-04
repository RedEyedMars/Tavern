package entities.heroes.histories;

import entities.Entity;
import entities.stats.skills.Skill;
import items.Item;

public class UseSkillEvent extends HistoryEvent {

	private String event;
	private Entity entity;
	private Skill skill;
	private Entity target;
	
	public UseSkillEvent(Entity source, Skill skill, Entity target){
		super();
		this.target = target;
		this.skill = skill;
		this.entity = source;
		StringBuilder eventBuilder = new StringBuilder();
		eventBuilder.append(skill.getName());
		eventBuilder.append(" was used by ");
		eventBuilder.append(entity.getName());
		eventBuilder.append(" on ");
		eventBuilder.append(target.getName());
		eventBuilder.append("(");
		eventBuilder.append(getTime());
		eventBuilder.append(")");
		event = eventBuilder.toString();
		
	}
	
	@Override
	public String getEvent() {
		return event;
	}
	
	public Skill getSkill(){
		return skill;
	}
	
	public Entity getSource(){
		return entity;
	}
	
	public Entity getTarget(){
		return entity;
	}

	@Override
	public float postivity() {
		return 1f;
	}

}
