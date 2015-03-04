package entities.heroes.quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import entities.heroes.histories.MonsterKillEvent;
import entities.heroes.quests.requirements.QuestRequirement;
import entities.monsters.Monster;

public class HuntQuest extends Quest{

	private List<Monster> remainingTargets;
	
	public HuntQuest(String name, QuestRequirement[] requirements, final Monster target, Monster... targets) {
		super(name, requirements);
		this.remainingTargets = new ArrayList<Monster>();
		this.type = targets.length>0?"HuntMany":"HuntOne";

		this.target = target;
		if(targets.length>0){
			remainingTargets.addAll(Arrays.asList(targets));
		}
		map.put("targetFound", false);
		final Quest self = this;
		this.addTrigger(new QuestTrigger<MonsterKillEvent>(MonsterKillEvent.class){

			@Override
			public void trigger(MonsterKillEvent event) {
				if(target.equals(event.getMonster())){
					if(!remainingTargets.isEmpty()){
						self.target = remainingTargets.remove((int)(Math.random()*remainingTargets.size()));
						self.type = remainingTargets.size()>0?"HuntMany":"HuntOne";
					}
					else {
						self.target = null;
						finish(event.getHero());
					}
				}
				else if(remainingTargets.contains(event.getMonster())){
					remainingTargets.remove(event.getMonster());
					self.type = remainingTargets.size()>0?"HuntMany":"HuntOne";
				}
			}});
	}
	
	
	
}
