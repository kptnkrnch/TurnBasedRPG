package items;

import java.util.ArrayList;
import java.util.Random;

import engine.Entity;
import engine.Main;
import exceptions.PlayerNotFoundException;
import graphics.GUIController;

public class ItemHandler {
	
	public static void HandleGivingQuestItems(ArrayList<Integer> items, ItemInventory inventory) {
		
	}
	
	public static void CleanUpInventory() {
		for (int i = 0; i < ItemInventory.GetItemCount(); i++) {
			Item temp = ItemInventory.GetItem(i);
			
			if (temp.maxUses > 0) {
				if (temp.useCount >= temp.maxUses) {
					ItemInventory.DropItem(i);
				}
			}
		}
	}
	
	public static boolean UseItem(Item item) {
		boolean didSomething = false;
		if (item.maxUses == -1 || item.useCount < item.maxUses) {
			if (item.cureBurningChance > 0) {
				int chance = Math.round(1.0f / item.cureBurningChance);
				int roll = (int)Math.round(Math.random() % chance);
				if (roll == 0) {
					try {
						int playerIndex = Main.world.FindPlayer();
						Entity player = Main.world.GetEntity(playerIndex);
						if (player != null) {
							player.burnDamage = 0;
							player.burnTime = 0;
							Main.world.SetEntity(playerIndex, player);
						}
					} catch (PlayerNotFoundException e) {
					}
				}
				didSomething = true;
			}
			if (item.cureFreezingChance > 0) {
				int chance = Math.round(1.0f / item.cureFreezingChance);
				int roll = (int)Math.round(Math.random() % chance);
				if (roll == 0) {
					try {
						int playerIndex = Main.world.FindPlayer();
						Entity player = Main.world.GetEntity(playerIndex);
						if (player != null) {
							player.frozenTime = 0;
							Main.world.SetEntity(playerIndex, player);
						}
					} catch (PlayerNotFoundException e) {
					}
				}
				didSomething = true;
			}
			if (item.curePoisonChance > 0) {
				int chance = Math.round(1.0f / item.curePoisonChance);
				int roll = (int)Math.round(Math.random() % chance);
				if (roll == 0) {
					try {
						int playerIndex = Main.world.FindPlayer();
						Entity player = Main.world.GetEntity(playerIndex);
						if (player != null) {
							player.poisonDamage = 0;
							player.poisonTime = 0;
							Main.world.SetEntity(playerIndex, player);
						}
					} catch (PlayerNotFoundException e) {
					}
				}
				didSomething = true;
			}
			if (item.healingChance > 0) {
				int chance = Math.round(1.0f / item.healingChance);
				Random random = new Random();
				int roll = random.nextInt(chance);
				System.out.println("HERE: 1 " + roll);
				if (roll == 0) {
					//System.out.println("HERE: 2");
					try {
						int playerIndex = Main.world.FindPlayer();
						//System.out.println("HERE: 3");
						Entity player = Main.world.GetEntity(playerIndex);
						//System.out.println("HERE: 4");
						if (player != null) {
							player.c_health += item.healingAmount;
							//System.out.println(player.c_health);
							if (player.c_health > player.c_max_health) {
								player.c_health = player.c_max_health;
							}
							//System.out.println("HERE: 5");
							Main.world.SetEntity(playerIndex, player);
							//System.out.println("HERE: 6");
						}
					} catch (PlayerNotFoundException e) {
					}
				}
				didSomething = true;
			}
		}
		
		return didSomething;
	}
	
	public static void DropCurrentItem() {
		int currentPosition = ItemInventory.currentPosition;
		Item temp = ItemInventory.GetCurrentItem();
		if (temp.isDroppable) {
			ItemInventory.DropCurrentItem();
		}
		if (currentPosition >= ItemInventory.GetItemCount()) {
			ItemInventory.currentPosition = ItemInventory.GetItemCount() - 1;
		}
	}
	
	public static void EquipItem(int index) {
		if (index >= 0 && index < ItemInventory.GetItemCount()) {
			Item item = ItemInventory.GetItem(index);
			
			if (ItemDictionary.IsArmor(item) || ItemDictionary.IsWeapon(item)) {
				for (int i = 0; i < ItemInventory.GetItemCount(); i++) {
					Item temp = ItemInventory.GetItem(i);
					if (temp.type == item.type && temp.isEquipped) {
						temp.isEquipped = false;
						ItemInventory.SetItem(i, temp);
						break;
					}
				}
				item.isEquipped = true;
				ItemInventory.SetItem(index, item);
			}
		}
	}
	
	public static void UnEquipItem(int index) {
		if (index >= 0 && index < ItemInventory.GetItemCount()) {
			Item item = ItemInventory.GetItem(index);
			
			item.isEquipped = false;
			
			ItemInventory.SetItem(index, item);
		}
	}
	
	public static int GetEquipmentDefence() {
		int defence = 0;
		
		for (int i = 0; i < ItemInventory.GetItemCount(); i++) {
			Item temp = ItemInventory.GetItem(i);
			if (ItemDictionary.IsArmor(temp) && temp.isEquipped) {
				defence += temp.armor;
			}
		}
		
		return defence;
	}
	
	public static int GetEquipmentAttack() {
		int attack = 0;
		
		for (int i = 0; i < ItemInventory.GetItemCount(); i++) {
			Item temp = ItemInventory.GetItem(i);
			if (ItemDictionary.IsWeapon(temp) && temp.isEquipped) {
				attack += temp.damage;
			}
		}
		
		return attack;
	}
}
