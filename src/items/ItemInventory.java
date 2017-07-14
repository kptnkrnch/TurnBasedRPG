package items;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ItemInventory {
	public static ArrayList<Item> inventoryItems = new ArrayList<Item>();
	public static int currentPosition = 0;
	public static Image inventoryArrow = null;
	
	public static void Init() {
		try {
			inventoryArrow = new Image("res/gui/inventory-arrow.png");
			inventoryArrow.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
		}
	}
	
	public static void AddItem(int itemCode) {
		Item item = ItemDictionary.GetItemByID(itemCode);
		if (item != null) {
			inventoryItems.add(item);
		}
	}
	
	public static void AddItem(Item item) {
		if (item != null) {
			inventoryItems.add(item);
		}
	}
	
	public static Item GetCurrentItem() {
		if (inventoryItems.size() > 0) {
			return inventoryItems.get(currentPosition);
		}
		return null;
	}
	
	public static void SetCurrentItem(Item item) {
		inventoryItems.set(currentPosition, item);
	}
	
	public static void SetItem(int index, Item item) {
		inventoryItems.set(index, item);
	}
	
	public static Item GetItem(int itemIndex) {
		if (inventoryItems.size() > 0 && itemIndex >= 0 && itemIndex < inventoryItems.size()) {
			return inventoryItems.get(itemIndex);
		}
		return null;
	}
	
	public static int GetItemCount() {
		return inventoryItems.size();
	}
	
	public static void MoveCursorUp() {
		if (currentPosition == 0 && inventoryItems.size() > 0) {
			currentPosition = inventoryItems.size() - 1;
		} else if (inventoryItems.size() > 0) {
			currentPosition--;
		}
	}
	
	public static void MoveCursorDown() {
		if (currentPosition == inventoryItems.size() - 1) {
			currentPosition = 0;
		} else if (inventoryItems.size() > 0) {
			currentPosition++;
		}
	}
	
	public static void UseItem() {
		
	}
	
	public static void DropItem(int index) {
		inventoryItems.remove(index);
	}
	
	public static void DropCurrentItem() {
		inventoryItems.remove(currentPosition);
	}
	
	public static void EquipItem() {
		
	}
	
	public static void UnEquipItem() {
		
	}
}
