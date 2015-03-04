package entities.heroes.roles.requirements;

import entities.heroes.Hero;
import entities.stats.skills.Skill;

public class HeroRoleRequirementWithSkill implements HeroRoleRequirement {

	private String name;

	public HeroRoleRequirementWithSkill(String skillName) {
		super();
		this.name = skillName;
	}

	@Override
	public boolean satisfy(Hero hero) {
		Skill skill = hero.getSkill(name);
		return skill != null;
	}

}
