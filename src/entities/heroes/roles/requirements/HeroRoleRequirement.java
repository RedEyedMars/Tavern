package entities.heroes.roles.requirements;

import entities.heroes.Hero;

public interface HeroRoleRequirement {
	public boolean satisfy(Hero hero);
}
