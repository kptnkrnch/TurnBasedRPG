package items;

import java.util.ArrayList;

public class ItemDictionary {
	public static final int TYPE_CONSUMABLE = 1;
	public static final int TYPE_WEAPON = 2;
	public static final int TYPE_ARMOR_HEAD = 3;
	public static final int TYPE_ARMOR_BODY = 4;
	public static final int TYPE_ARMOR_LEGS = 5;
	public static final int TYPE_ARMOR_ARMS = 6;
	
	public static final int RARITY_COMMON = 1;
	public static final int RARITY_UNCOMMON = 2;
	public static final int RARITY_RARE = 3;
	public static final int RARITY_LEGENDARY = 4;
	public static final int RARITY_EXOTIC = 5;
	
	public static ArrayList<Item> items = new ArrayList<Item>();
	
	public static void AddItem(Item item) {
		items.add(item);
	}
	
	public static Item GetItemByID(int itemID) {
		Item temp = null;
		if (items.get(itemID - 1).id == itemID) {
			temp = new Item(items.get(itemID - 1));
		} else {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).id == itemID) {
					temp = new Item(items.get(i));
				}
			}
		}
		return temp;
	}
	
	public static boolean IsArmor(Item item) {
		switch(item.type) {
		case TYPE_ARMOR_HEAD:
			return true;
		case TYPE_ARMOR_BODY:
			return true;
		case TYPE_ARMOR_LEGS:
			return true;
		case TYPE_ARMOR_ARMS:
			return true;
		default:
			return false;
		}
	}
	
	public static boolean IsWeapon(Item item) {
		if (item.type == TYPE_WEAPON) {
			return true;
		} else {
			return false;
		}
	}
}
