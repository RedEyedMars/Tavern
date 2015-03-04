package entities.heroes.roles;

import entities.heroes.Hero;
import entities.heroes.behaviours.AttackBehaviour;
import entities.heroes.behaviours.HealOtherBehaviour;
import entities.heroes.behaviours.HuntBehaviour;
import entities.heroes.behaviours.ProtectBehaviour;
import entities.heroes.histories.HistoryEvent;
import entities.heroes.histories.MonsterKillEvent;
import entities.heroes.histories.PartyMemberKillEvent;
import entities.heroes.histories.PartyMemberWoundedEvent;
import entities.heroes.histories.UseSkillEvent;
import entities.heroes.misc.Condition;
import entities.heroes.quests.Quest;
import entities.heroes.skills.HealManySkill;
import entities.heroes.skills.HealOneSkill;
import entities.heroes.skills.TrackingSkill;
import entities.stats.BehaviourGenerator;

public class HeroRoles {
	public static HeroRole hunter = entities.heroes.roles.parser.Parser.parse("roles/Hunter.role");
	public static HeroRole medic = entities.heroes.roles.parser.Parser.parse("roles/Medic.role");
	public static HeroRole[] roles = new HeroRole[]{};
}
