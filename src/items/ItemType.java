package items;

public class ItemType {	
	private String type;
	public ItemType(String type){
		this.type = type;
	}
	public String getType(){
		return type;
	}
	public static final ItemType shield = new ItemType("Shield");
	public static final ItemType heal =  new ItemType("Heal");
	public static final ItemType armour = new ItemType("Armour");
	public static final ItemType food = new ItemType("Food");
	public static final ItemType raw = new ItemType("Raw");
	public static final ItemType bandage = new ItemType("Bandage");
}
