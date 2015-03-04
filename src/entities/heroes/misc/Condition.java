package entities.heroes.misc;

import entities.heroes.Hero;

public interface Condition {
	public boolean satisfies(Hero hero);
}
