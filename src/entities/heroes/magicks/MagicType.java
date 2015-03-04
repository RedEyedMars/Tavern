package entities.heroes.magicks;

public class MagicType {


	public static final MagicType healing = new MagicType("Healing");

	public static final MagicType many = new MagicType("Many");
	
	private String type;
	public MagicType(String type) {
		this.type = type;
	}
	public String getType(){
		return type;
	}

}
