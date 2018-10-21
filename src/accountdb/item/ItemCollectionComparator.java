package accountdb.item;

import java.util.Comparator;

public class ItemCollectionComparator implements Comparator<ItemCollection> {

	@Override
	public int compare(ItemCollection a, ItemCollection b) {
		
		int valye = a.item.type < b.item.type ? -1 : a.item.type == b.item.type ? 0 : 1;
		System.out.println(valye);
		return valye;
	}

}