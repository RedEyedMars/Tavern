package entities.heroes.roles;

import java.util.ArrayList;
import java.util.List;

import entities.heroes.SocialHero;

public abstract class RoledHero extends SocialHero {
	private List<HeroRole> roles;

	public RoledHero(String name) {
		super(name);
		roles = new ArrayList<HeroRole>();
	}
	
	public void removeRole(HeroRole role) {
		roles.remove(role);
	}


	public boolean hasRole(String role) {
		for(HeroRole r:roles){
			if(role.equals(r.getName())){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasRole(HeroRole role) {
		return roles.contains(role);
	}
	

	public List<HeroRole> getRoles() {
		return roles;
	}


}
