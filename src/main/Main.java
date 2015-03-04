package main;
import entities.heroes.Hero;
import entities.heroes.behaviours.AttackBehaviour;
import entities.heroes.behaviours.HealOtherBehaviour;
import entities.heroes.behaviours.HuntBehaviour;
import entities.heroes.behaviours.ProtectBehaviour;
import entities.heroes.classes.Attribute;
import entities.heroes.classes.HeroClass;
import entities.heroes.classes.NameGenerator;
import entities.heroes.histories.MonsterKillEvent;
import entities.heroes.histories.UseSkillEvent;
import entities.heroes.quests.HuntQuest;
import entities.heroes.quests.Quest;
import entities.heroes.quests.requirements.QuestRequirement;
import entities.heroes.roles.HeroRoles;
import entities.heroes.roles.parser.Parser;
import entities.heroes.skills.HealOneSkill;
import entities.heroes.skills.TrackingSkill;
import entities.monsters.Monster;
import game.*;
public class Main {
  public static void main(String[] args)
  {
	  NameGenerator.init();
	  HeroClass joe = new HeroClass("joe");
	  joe.addAttribute(new Attribute("hp",10));

	  joe.addAttribute(new Attribute("maxHp",10));
	  Hero jim = joe.createHero();
	  Hero jane = joe.createHero();
	  jim.getParty().addHero(jane);
	  jane.damage(10, jim);
	  Quest quest = new HuntQuest("Bear Hunt", new QuestRequirement[]{}, new Monster("Bear"));
	  jim.considerQuest(quest);
	  System.out.println(HeroRoles.medic.evaluate(jim, HealOtherBehaviour.generator));
    //  Game game = new Game();
	//  Area area = new Area(null);
	//  area.initialiseMap();
	 // area.getSurroundingSquares(area.get(25,25), 1);
  }
}
