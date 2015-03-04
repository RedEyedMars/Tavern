package items;


public class Items {
	public static Item wolfPelt      = new Item("Wolf Pelt",2);
	public static Item wolfTooth     = new Item("Wolf Tooth",3);
	public static Item toughMeat     = new Item("Tough Meat",1,ItemType.food);
	public static Item bearPelt      = new Item("Beat Pelt",3);
	public static Item bearClaw      = new Item("Bear Claw",4);
	public static Item fish          = new Item("Fish",1,ItemType.raw,ItemType.food);
	public static Item blackBerries  = new Item("Blackberries", 1,ItemType.food);
	public static Item strawBerries  = new Item("Starberries", 2, ItemType.food);
	public static Item poisonBerries = new Item("Poisonberries", 1);
	public static Item rabbitPelt    = new Item("Rabbit Pelt", 1);
	public static Item rabbitFoot    = new Item("Rabbit Foot", 1);
	
	public static Item salve = new HealingItem("Salve",5,0.15f,ItemType.heal,ItemType.bandage);
	public static Item greatersalve = new HealingItem("Greater Salve",20,0.25f,ItemType.heal,ItemType.bandage);
}
