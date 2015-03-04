package entities.heroes.quests.requirements;

import entities.heroes.Hero;
import entities.heroes.quests.Quest;

public interface QuestRequirement {
	public boolean canSatisfy(Hero hero);
	public void satisfy(Quest quest, Hero hero);
}
