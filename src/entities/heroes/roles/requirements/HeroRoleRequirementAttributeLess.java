package entities.heroes.roles.requirements;

import entities.heroes.Hero;

public class HeroRoleRequirementAttributeLess implements HeroRoleRequirement {

	private String name;
	private float value;

	public HeroRoleRequirementAttributeLess(String attributeName, int value) {
		super();
		this.name = attributeName;
		this.value = value;
	}

	@Override
	public boolean satisfy(Hero hero) {
		float attribute = hero.getAttribute(name);
		return attribute < value;
	}

}
