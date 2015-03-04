package entities.monsters;

import entities.stats.skills.Skill;
import entities.stats.skills.AttackSkill;

public class MonsterSkills {	
	
	public static final Skill bite  = new AttackSkill("bite",4){
		{
			requiredAttributes.put("Intellect",1);
		}
	};
	public static final Skill claw = new AttackSkill("claw",7){
		{
			requiredAttributes.put("Intellect",2);
		}
	};
	public static final Skill nibble = new AttackSkill("nibble",2){
	};	

	public static final Skill chomp = new AttackSkill("chomp",5){
		{
			requiredAttributes.put("Strength",2);
		}
	};
}
