package entities.stats;

import items.Item;
import entities.Entity;

public interface Damagable {
	public Item damage(float damage, Entity source);
	public boolean isDead();
}
