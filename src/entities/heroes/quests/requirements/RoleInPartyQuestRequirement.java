package entities.heroes.quests.requirements;

import java.util.Comparator;
import java.util.TreeSet;

import entities.heroes.Hero;
import entities.heroes.discussionpoints.RolePerformanceDiscussionPoint;
import entities.heroes.histories.EndQuestEvent;
import entities.heroes.quests.Quest;
import entities.heroes.quests.QuestTrigger;
import entities.heroes.roles.HeroRole;

public class RoleInPartyQuestRequirement implements QuestRequirement {

	private HeroRole role;
	private float competency;
	public RoleInPartyQuestRequirement(HeroRole role, int competency){
		super();
		this.role = role;
		this.competency = ((float)competency)/100;
	}
	
	@Override
	public boolean canSatisfy(Hero hero) {		
		if(hero.getParty()!=null){
			if(hero.getParty().contains(role)){
				return true;
			}
			else return hero.getParty().maxCompentencyForRole(role)>competency;
		}
		else {
			return hero.hasRole(role)||role.compitency(hero)>=competency;
		}
	}

	@Override
	public void satisfy(final Quest quest, final Hero hero) {
		if(hero.getParty()!=null){
			if(!hero.getParty().contains(role)){
				final int size = hero.getParty().getHeroes().size();
				TreeSet<Hero> candidates = new TreeSet<Hero>(new Comparator<Hero>(){
					@Override
					public int compare(Hero a, Hero b) {
						return (int) (role.compitency(b)*size-role.compitency(a));
					}});
				for(Hero h:hero.getParty().getHeroes()){
					if(role.compitency(h)>competency){
						candidates.add(h);
					}
				}
				final Hero winner = candidates.first();
				quest.addTrigger(new QuestTrigger<EndQuestEvent>(EndQuestEvent.class){
					@Override
					public void trigger(EndQuestEvent event) {
						winner.removeRole(role);
						winner.getParty().addDiscussionPoint(new RolePerformanceDiscussionPoint(role,hero,quest));
					}});
			}
		}
		else if(!hero.hasRole(role)){
			quest.addTrigger(new QuestTrigger<EndQuestEvent>(EndQuestEvent.class){
				@Override
				public void trigger(EndQuestEvent event) {
					hero.removeRole(role);
				}});
		}
	}
	
}
