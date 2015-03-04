package entities.flora;

import items.Items;

import java.util.ArrayList;
import java.util.List;


public class Floras {
	
	public static List<FloraType> types;

	public static FloraType forestBerries = new FloraType("Forest Berries");
	
	static
	{
		types = new ArrayList<FloraType>();
		types.add(forestBerries);
		
		forestBerries.setHpRange(2,3);
		forestBerries.addDrop(Items.blackBerries);
		forestBerries.addDrop(Items.strawBerries);
		forestBerries.addDrop(Items.poisonBerries);
	}

}
