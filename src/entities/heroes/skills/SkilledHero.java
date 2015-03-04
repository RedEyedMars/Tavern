package entities.heroes.skills;

import java.util.ArrayList;
import java.util.List;

import entities.heroes.Hero;
import entities.heroes.roles.RoledHero;
import entities.stats.skills.AttackSkill;
import entities.stats.skills.Skill;

public abstract class SkilledHero extends RoledHero {
	private List<Skill> skills;

	public SkilledHero(String name) {
		super(name);
		skills = new ArrayList<Skill>();
	}


	public Skill getSkill(String name) {
		for(Skill skill:skills){
			if(name.equals(skill.getName())){
				return skill;
			}
		}
		return null;
	}


	public boolean hasSkill(String skillName) {
		for(Skill skill:skills){
			if(skill.getName().equals(skillName)){
				return true;
			}
		}
		return false;
	}

	public List<AttackSkill> getAttackSkills(double distance) {
		List<AttackSkill> skiddles = new ArrayList<AttackSkill>();
		for(Skill skill:skills){
			if(skill.isUsable((Hero) this)&&skill.isAttack()&&skill.getRange((Hero) this)<distance){
				skiddles.add((AttackSkill)skill);
			}
		}
		return skiddles;
	}
	
	public float maxRangeAttack() {
		float max = 0;
		for(Skill skill:skills){
			float range = skill.getRange((Hero) this);
			if(skill.isUsable((Hero)this)&&skill.isAttack()&&range>max){
				max = range;
			}
		}
		return max;
	}


}
