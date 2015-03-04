package entities.heroes.roles.requirements;

import entities.heroes.Hero;
import entities.stats.skills.Skill;

public class HeroRoleRequirementSkillLess implements HeroRoleRequirement {
	private String name;
	private int value;

	public HeroRoleRequirementSkillLess(String skillName, int value) {
		super();
		this.name = skillName;
		this.value = value;
	}

	@Override
	public boolean satisfy(Hero hero) {
		Skill skill = hero.getSkill(name);
		return skill == null?false:skill.getExperience()>value;
	}


}
