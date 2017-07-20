package engine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import input.InputController;
import items.Item;
import items.ItemHandler;
import items.ItemInventory;

import org.newdawn.slick.Color;

import savefiles.SaveGenerator;
import graphics.GUIController;
import graphics.GraphicsController;

public class MenuOptionProcessor {
	
	/* Main Menu Options */
	public static final String GRAPHICS_OPTION = "graphics_option";
	public static final String RESUME_OPTION = "resume_option";
	public static final String CONTROLS_OPTION = "controls_option";
	public static final String SAVE_OPTION = "save_option";
	public static final String LOAD_OPTION = "load_option";
	public static final String EXIT_OPTION = "exit_option";
	
	/* Controls Menu Options */
	public static final String UP_CONTROL_OPTION = "up_control_option";
	public static final String DOWN_CONTROL_OPTION = "down_control_option";
	public static final String LEFT_CONTROL_OPTION = "left_control_option";
	public static final String RIGHT_CONTROL_OPTION = "right_control_option";
	public static final String ATTACK_CONTROL_OPTION = "attack_control_option";
	public static final String INTERACT_CONTROL_OPTION = "interact_control_option";
	public static final String BACK_OPTION = "back_option";
	
	/* Submenu Inventory Item Options */
	public static final String USE_ITEM_OPTION = "use_item_option";
	public static final String UNEQUIP_ITEM_OPTION = "unequip_item_option";
	public static final String EQUIP_ITEM_OPTION = "equip_item_option";
	public static final String DROP_ITEM_OPTION = "drop_item_option";
	public static final String CLOSE_ITEM_OPTION = "close_item_option";
	
	/* Submenu Battle Options */
	public static final String BATTLE_ABILITIES_OPTION = "battle_abilities_option";
	public static final String BATTLE_ITEMS_OPTION = "battle_items_option";
	public static final String BATTLE_FLEE_OPTION = "battle_flee_option";
	
	public static void HandleMenuOption(String option) {
		if (option != null && option.length() > 0) {
			switch(GUIController.GetCurrentMenu()) {
			case GUIController.MENU_MAIN:
				HandleMainMenuOption(option);
				break;
			case GUIController.MENU_CONTROLS:
				HandleControlMenuOption(option);
				break;
			}
			//if (GUIController.subMenuName != null) {
			if (GUIController.GetCurrentSubmenu() != GUIController.MENU_NONE) {
				//switch(GUIController.subMenuName) {
				switch(GUIController.GetCurrentSubmenu()) {
				case GUIController.SUBMENU_INVENTORY_ITEM:
					HandleInventoryItemOption(option);
					break;
				case GUIController.SUBMENU_BATTLE_OPTION:
					HandleBattleOption(option);
					break;
				}
			}
		}
	}
	
	public static void HandleMainMenuOption(String option) {
		int saveNumber = 1;
		String saveFileName = null;
		if (option != null && option.length() > 0) {
			switch(option) {
			case GRAPHICS_OPTION:
				break;
			case RESUME_OPTION:
				Main.SetState(States.RUNNING);
				break;
			case CONTROLS_OPTION:
				GUIController.SetCurrentMenu(GUIController.MENU_CONTROLS);
				break;
			case SAVE_OPTION:
				saveNumber = SaveGenerator.FindLastSaveFileNumber();
				saveNumber++;
				saveFileName = SaveGenerator.CreateSaveFileWithNumber(saveNumber);
				if (saveFileName != null && saveFileName.contains(".save")) {
					SaveGenerator.SaveGame(saveFileName);
				}
				System.out.println("Saved: " + saveFileName);
				break;
			case LOAD_OPTION:
				GUIController.SetCurrentMenu(GUIController.MENU_LOADSAVES);
				break;
			case EXIT_OPTION:
				OptionExit();
				break;
			}
		}
	}
	
	public static void HandleControlMenuOption(String option) {
		java.nio.charset.Charset ENCODING = java.nio.charset.StandardCharsets.UTF_8;
		if (option != null && option.length() > 0) {
			int menuID = Main.world.FindMenu(GUIController.MENU_CONTROLS);
			Menu temp = Main.world.GetMenu(menuID);
			int currentOption = -1;
			java.nio.file.Path file = null;
			BufferedWriter writer = null;
			Iterator<Entry<String, Integer>> mapIterator = null;
			
			for (int i = 0; i < temp.GetMenuItemCount(); i++) {
				MenuItem item = temp.GetMenuItem(i);
				String tempOption = option.replace("_control_option", "");
				if (!item.text.contains("back") && item.text.toLowerCase().equals(tempOption)) {
					currentOption = i;
				}
			}
			
			switch(option) {
			case UP_CONTROL_OPTION:
				if (GUIController.selectedControlField != currentOption) {
					GUIController.selectedControlField = currentOption;
				} else {
					GUIController.selectedControlField = -1;
				}
				break;
			case DOWN_CONTROL_OPTION:
				if (GUIController.selectedControlField != currentOption) {
					GUIController.selectedControlField = currentOption;
				} else {
					GUIController.selectedControlField = -1;
				}
				break;
			case LEFT_CONTROL_OPTION:
				if (GUIController.selectedControlField != currentOption) {
					GUIController.selectedControlField = currentOption;
				} else {
					GUIController.selectedControlField = -1;
				}
				break;
			case RIGHT_CONTROL_OPTION:
				if (GUIController.selectedControlField != currentOption) {
					GUIController.selectedControlField = currentOption;
				} else {
					GUIController.selectedControlField = -1;
				}
				break;
			case ATTACK_CONTROL_OPTION:
				if (GUIController.selectedControlField != currentOption) {
					GUIController.selectedControlField = currentOption;
				} else {
					GUIController.selectedControlField = -1;
				}
				break;
			case INTERACT_CONTROL_OPTION:
				if (GUIController.selectedControlField != currentOption) {
					GUIController.selectedControlField = currentOption;
				} else {
					GUIController.selectedControlField = -1;
				}
				break;
			case BACK_OPTION:
				GUIController.selectedControlField = -1;
				GUIController.SetCurrentMenu(GUIController.GetPreviousMenu());
				InputController.SetKeyMap(GUIController.tempKeyMap);
				
				file = java.nio.file.Paths.get("res/config/keymap.conf");
				
				try {
					writer = java.nio.file.Files.newBufferedWriter(file, ENCODING);
					mapIterator = GUIController.tempKeyMap.entrySet().iterator();
					if (mapIterator != null && writer != null) {
						while (mapIterator.hasNext()) {
							Entry<String, Integer> current = mapIterator.next();
							writer.write(current.getKey() + "=" + current.getValue());
							if (mapIterator.hasNext()) {
								writer.newLine();
							}
						}
						writer.close();
					}
				} catch (IOException e) {
				}
				break;
			}
		}
	}
	
	public static void HandleInventoryItemOption(String option) {
		if (option != null && option.length() > 0) {
			int index = -1;
			boolean success = false;
			switch(option) {
			case USE_ITEM_OPTION:
				success = false;
				Item item = ItemInventory.GetCurrentItem();
				
				success = ItemHandler.UseItem(item);
				
				if (success && item.maxUses > 0 && item.useCount <= item.maxUses) {
					item.IncreaseUseCount();
					//System.out.println("INCREASED");
				}
				
				ItemInventory.SetCurrentItem(item);
				//GUIController.SetSubMenu(null);
				GUIController.ExitSubmenus();
				break;
			case UNEQUIP_ITEM_OPTION:
				success = false;
				index = ItemInventory.currentPosition;
				ItemHandler.UnEquipItem(index);
				//GUIController.SetSubMenu(null);
				GUIController.ExitSubmenus();
				break;
			case EQUIP_ITEM_OPTION:
				success = false;
				index = ItemInventory.currentPosition;
				ItemHandler.EquipItem(index);
				//GUIController.SetSubMenu(null);
				GUIController.ExitSubmenus();
				break;
			case DROP_ITEM_OPTION:
				success = false;
				ItemHandler.DropCurrentItem();
				//GUIController.SetSubMenu(null);
				GUIController.ExitSubmenus();
				break;
			case CLOSE_ITEM_OPTION:
				//GUIController.SetSubMenu(null);
				GUIController.ExitSubmenus();
				break;
			}
		}
	}
	
	public static void HandleBattleOption(String option) {
		if (option != null && option.length() > 0) {
			int index = -1;
			boolean success = false;
			switch(option) {
			case BATTLE_ABILITIES_OPTION:
				//success = false;
				//Item item = ItemInventory.GetCurrentItem();
				
				//success = ItemHandler.UseItem(item);
				
				//if (success && item.maxUses > 0 && item.useCount <= item.maxUses) {
				//	item.IncreaseUseCount();
					//System.out.println("INCREASED");
				//}
				
				//ItemInventory.SetCurrentItem(item);
				//GUIController.SetSubMenu(null);
				break;
			case BATTLE_ITEMS_OPTION:
				//success = false;
				//index = ItemInventory.currentPosition;
				//ItemHandler.UnEquipItem(index);
				//GUIController.SetSubMenu(null);
				break;
			case BATTLE_FLEE_OPTION:
				//success = false;
				//index = ItemInventory.currentPosition;
				//ItemHandler.EquipItem(index);
				//GUIController.SetSubMenu(null);
				break;
			}
		}
	}
	
	private static void OptionExit() {
		Main.game_container.exit();
	}
	
}
