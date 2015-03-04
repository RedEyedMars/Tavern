package items;

import java.util.*;


public class ItemBag extends Item implements Iterable<Item> {
	private List<Item> contains;
	
	public ItemBag(String string){
		super(string,0);
		contains = new ArrayList<Item>();
	}
	

	public void add(Item item) {
		contains.add(item);
		value+=item.getValue();
	}


	@Override
	public Iterator<Item> iterator() {
		return contains.iterator();
	}
}
