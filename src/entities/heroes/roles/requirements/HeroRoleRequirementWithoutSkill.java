package entities.heroes.roles.requirements;

import entities.heroes.Hero;
import entities.stats.skills.Skill;

public class HeroRoleRequirementWithoutSkill implements HeroRoleRequirement {

	private String name;

	public HeroRoleRequirementWithoutSkill(String skillName) {
		super();
		this.name = skillName;
	}

	@Override
	public boolean satisfy(Hero hero) {
		Skill skill = hero.getSkill(name);
		return skill == null;
	}

}
