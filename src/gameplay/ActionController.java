package gameplay;

import items.Item;
import items.ItemDictionary;
import items.ItemInventory;

import java.util.ArrayList;
import java.util.HashMap;

import savefiles.SaveLoader;
import engine.Entity;
import engine.EntityDictionary;
import engine.Main;
import engine.Menu;
import engine.MenuItem;
import engine.MenuOptionProcessor;
import engine.States;
import engine.World;
import entities.Camera;
import entities.Player;
import graphics.GUIController;

public class ActionController {
	public static final int scroll_delay = 200;
	public static final int max_scroll_level = 5;
	public static final int scroll_level_speed = 25;
	public static int scroll_level = 0;
	public static int current_delay = 0;
	public static void HandleEntityAction(World world, HashMap<String, Boolean> input, HashMap<String, Boolean> held_input, int fps_scaler) {
		if (input != null && !input.isEmpty()) {
			/* GUI Keys */
			if (input.get("KEY_MENU")) {
				HandleMenuKey(world);
			}
			if (input.get("KEY_INVENTORY")) {
				HandleInventoryKey(world);
			}
			if ((GUIController.GetCurrentMenu() == GUIController.MENU_INVENTORY && 
					GUIController.GetCurrentSubmenu() == null) || 
					GUIController.GetCurrentMenu() == GUIController.MENU_LOADSAVES) {
				if (input.get("KEY_UP") || (held_input.get("KEY_UP") && !input.get("KEY_UP"))) {
					if (current_delay <= 0) {
						HandleKeyUp(world);
						current_delay = scroll_delay - scroll_level * scroll_level_speed;
						if (scroll_level < max_scroll_level) {
							scroll_level++;
						}
					}
				}
				if (input.get("KEY_DOWN") || (held_input.get("KEY_DOWN") && !input.get("KEY_DOWN"))) {
					if (current_delay <= 0) {
						HandleKeyDown(world);
						current_delay = scroll_delay - scroll_level * scroll_level_speed;
						if (scroll_level < max_scroll_level) {
							scroll_level++;
						}
					}
				}
			} else {
				if (input.get("KEY_UP")) {
					HandleKeyUp(world);
				}
				if (input.get("KEY_DOWN")) {
					HandleKeyDown(world);
				}
			}
			
			if (current_delay > 0) {
				current_delay -= fps_scaler;
			}
			
			if (!(input.get("KEY_DOWN") || held_input.get("KEY_DOWN") || input.get("KEY_UP") || held_input.get("KEY_UP"))) {
				scroll_level = 0;
			}
			
			/* Gameplay Keys*/
			if (Main.GetState() != States.MENU) {
				for (int i = 0; i < world.GetEntityCount(); i++) {
					if (input.get("KEY_SELECT")) {
						HandleSelectKey(world, input, i, fps_scaler);
					}
					if (input.get("KEY_BACK")) {
						HandleBackKey(world, input, i, fps_scaler);
					}
				}
			} else {
				if (input.get("KEY_SELECT")) {
					HandleSelectKey(world, input, -1, fps_scaler);
				}
				if (input.get("KEY_BACK")) {
					HandleBackKey(world, input, -1, fps_scaler);
				}
			}
		}
	}
	
	public static void HandleSelectKey(World world, HashMap<String, Boolean> input, int entityIndex, int fps_scaler) {
		if (Main.GetState() != States.PAUSED && Main.GetState() != States.MENU) {
			Entity e = world.GetEntity(entityIndex);
			
			switch(e.type) {
			case EntityDictionary.PLAYER:
				Player.Interact(world, e);
				world.SetEntity(entityIndex, e);
				break;
			}
		} else if (Main.GetState() == States.MENU) {
			switch(GUIController.currentMenuName) {
			case GUIController.MENU_INVENTORY:
				if (GUIController.subMenuName == null) {
					current_delay = 0;
					scroll_level = 0;
					GUIController.SetSubMenu(GUIController.SUBMENU_INVENTORY_ITEM);
					int menuIndex = Main.world.FindMenu(GUIController.SUBMENU_INVENTORY_ITEM);
					Menu menu = Main.world.GetMenu(menuIndex);
					menu.selectedItem = 0;
					menu.menuitems = new ArrayList<MenuItem>();
					
					Item item = ItemInventory.GetCurrentItem();
					int wordcount = 0;
					if (item.type == ItemDictionary.TYPE_CONSUMABLE) {
						MenuItem temp = new MenuItem("use", menu.x, menu.y + wordcount * 18, "use_item_option");
						if (wordcount == 0) {
							temp.Highlight();
						}
						menu.AddMenuItem(temp);
						wordcount++;
					}
					if (item.type == ItemDictionary.TYPE_WEAPON || ItemDictionary.IsArmor(item)) {
						if (item.isEquipped) {
							MenuItem temp = new MenuItem("unequip", menu.x, menu.y + wordcount * 18, "unequip_item_option");
							if (wordcount == 0) {
								temp.Highlight();
							}
							menu.AddMenuItem(temp);
						} else {
							MenuItem temp = new MenuItem("equip", menu.x, menu.y + wordcount * 18, "equip_item_option");
							if (wordcount == 0) {
								temp.Highlight();
							}
							menu.AddMenuItem(temp);
						}
						wordcount++;
					}
					if (item.isDroppable) {
						MenuItem temp = new MenuItem("drop", menu.x, menu.y + wordcount * 18, "drop_item_option");
						if (wordcount == 0) {
							temp.Highlight();
						}
						menu.AddMenuItem(temp);
						wordcount++;
					}
					
					MenuItem temp = new MenuItem("close", menu.x, menu.y + wordcount * 18, "close_item_option");
					if (wordcount == 0) {
						temp.Highlight();
					}
					menu.AddMenuItem(temp);
					wordcount++;
					
					
					Main.world.SetMenu(menuIndex, menu);
					
					if (wordcount == 0) {
						GUIController.SetSubMenu(null);
					}
				} else {
					int menuIndex = world.FindMenu(GUIController.GetCurrentSubmenu());
					Menu menu = world.GetMenu(menuIndex);
					MenuItem menuItem = menu.GetMenuItem(menu.selectedItem);
					MenuOptionProcessor.HandleMenuOption(menuItem.Option());
				}
				break;
			case GUIController.MENU_LOADSAVES:
				String savePath = null;
				savePath = SaveLoader.GetPath() + "/" + SaveLoader.GetCurrentSaveFile();
				if (savePath.contains(".save")) {
					SaveLoader.LoadSaveFile(savePath);
					Main.SetState(States.RUNNING);
					GUIController.SetCurrentMenu(null);
				}
				break;
			}
		} else if (Main.GetState() == States.BATTLE) {
			if (GUIController.currentMenuName == GUIController.MENU_BATTLE) {
				if (GUIController.subMenuName == null) {
					current_delay = 0;
					scroll_level = 0;
					GUIController.SetSubMenu(GUIController.SUBMENU_BATTLE_OPTION);
					int menuIndex = Main.world.FindMenu(GUIController.SUBMENU_BATTLE_OPTION);
					Menu menu = Main.world.GetMenu(menuIndex);
					menu.selectedItem = 0;
					menu.menuitems = new ArrayList<MenuItem>();
					
					/*Item item = ItemInventory.GetCurrentItem();
					int wordcount = 0;
					if (item.type == ItemDictionary.TYPE_CONSUMABLE) {
						MenuItem temp = new MenuItem("use", menu.x, menu.y + wordcount * 18, "use_item_option");
						if (wordcount == 0) {
							temp.Highlight();
						}
						menu.AddMenuItem(temp);
						wordcount++;
					}
					if (item.type == ItemDictionary.TYPE_WEAPON || ItemDictionary.IsArmor(item)) {
						if (item.isEquipped) {
							MenuItem temp = new MenuItem("unequip", menu.x, menu.y + wordcount * 18, "unequip_item_option");
							if (wordcount == 0) {
								temp.Highlight();
							}
							menu.AddMenuItem(temp);
						} else {
							MenuItem temp = new MenuItem("equip", menu.x, menu.y + wordcount * 18, "equip_item_option");
							if (wordcount == 0) {
								temp.Highlight();
							}
							menu.AddMenuItem(temp);
						}
						wordcount++;
					}
					if (item.isDroppable) {
						MenuItem temp = new MenuItem("drop", menu.x, menu.y + wordcount * 18, "drop_item_option");
						if (wordcount == 0) {
							temp.Highlight();
						}
						menu.AddMenuItem(temp);
						wordcount++;
					}
					
					MenuItem temp = new MenuItem("close", menu.x, menu.y + wordcount * 18, "close_item_option");
					if (wordcount == 0) {
						temp.Highlight();
					}
					menu.AddMenuItem(temp);
					wordcount++;
					
					
					Main.world.SetMenu(menuIndex, menu);
					
					if (wordcount == 0) {
						GUIController.SetSubMenu(null);
					}*/
				} else {
					int menuIndex = world.FindMenu(GUIController.GetCurrentSubmenu());
					Menu menu = world.GetMenu(menuIndex);
					MenuItem menuItem = menu.GetMenuItem(menu.selectedItem);
					MenuOptionProcessor.HandleMenuOption(menuItem.Option());
				}
			}
		}
	}
	
	public static void HandleBackKey(World world, HashMap<String, Boolean> input, int entityIndex, int fps_scaler) {
		if (Main.GetState() == States.RUNNING) {
			Entity e = world.GetEntity(entityIndex);
			
			switch(e.type) {
			case EntityDictionary.PLAYER:
				Player.Attack(world, e);
				world.SetEntity(entityIndex, e);
				break;
			}
		} else if (Main.GetState() == States.MENU) {
			if (GUIController.currentMenuName.equals(GUIController.MENU_INVENTORY) && GUIController.subMenuName != null) {
				int menuIndex = Main.world.FindMenu(GUIController.SUBMENU_INVENTORY_ITEM);
				Menu menu = Main.world.GetMenu(menuIndex);
				menu.menuitems = new ArrayList<MenuItem>();
				
				
				Main.world.SetMenu(menuIndex, menu);
				
				GUIController.SetSubMenu(null);
			} else if (GUIController.currentMenuName.equals(GUIController.MENU_INVENTORY) && GUIController.subMenuName == null) {
				Main.SetState(Main.previous_state);
			}
		}
	}
	
	public static void HandleMenuKey(World world) {
		if (Main.GetState() != States.MENU) {
			Main.SetState(States.MENU);
			GUIController.SetCurrentMenu(GUIController.MENU_MAIN);
		} else if (Main.GetState() == States.MENU){
			int menuIndex = world.FindMenu(GUIController.GetCurrentMenu());
			Menu menu = world.GetMenu(menuIndex);
			if (menu != null) {
				MenuItem menuItem = menu.GetMenuItem(menu.selectedItem);
				MenuOptionProcessor.HandleMenuOption(menuItem.Option());
			}
		} else {
			GUIController.SetSubMenu(null);
			Main.SetState(Main.previous_state);
		}
	}
	
	public static void HandleInventoryKey(World world) {
		if (Main.GetState() != States.MENU) {
			Main.SetState(States.MENU);
			GUIController.SetCurrentMenu(GUIController.MENU_INVENTORY);
		} else {
			GUIController.SetSubMenu(null);
			Main.SetState(Main.previous_state);
		}
	}
	
	public static void HandleKeyUp(World world) {
		if (Main.GetState() == States.MENU) {
			Menu temp = null;
			int tempMenuIndex = -1;
			switch(GUIController.GetCurrentMenu()) {
			case GUIController.MENU_MAIN:
				tempMenuIndex = world.FindMenu(GUIController.GetCurrentMenu());
				temp = world.GetMenu(tempMenuIndex);
				temp.DecrementMenuItem();
				world.SetMenu(tempMenuIndex, temp);
				break;
			case GUIController.MENU_CONTROLS:
				if (GUIController.selectedControlField == -1) {
					tempMenuIndex = world.FindMenu(GUIController.GetCurrentMenu());
					temp = world.GetMenu(tempMenuIndex);
					temp.DecrementMenuItem();
					world.SetMenu(tempMenuIndex, temp);
				}
				break;
			case GUIController.MENU_INVENTORY:
				if (GUIController.subMenuName == null) {
					ItemInventory.MoveCursorUp();
				} else {
					tempMenuIndex = world.FindMenu(GUIController.GetCurrentSubmenu());
					temp = world.GetMenu(tempMenuIndex);
					temp.DecrementMenuItem();
					world.SetMenu(tempMenuIndex, temp);
				}
				break;
			case GUIController.MENU_LOADSAVES:
				SaveLoader.MoveCursorUp();
				break;
			}
		} else if (Main.GetState() == States.BATTLE) {
			Menu temp = null;
			int tempMenuIndex = -1;
			switch(GUIController.GetCurrentMenu()) {
			case GUIController.MENU_BATTLE:
				if (GUIController.subMenuName == null) {
					ItemInventory.MoveCursorUp();
				} else {
					tempMenuIndex = world.FindMenu(GUIController.GetCurrentSubmenu());
					temp = world.GetMenu(tempMenuIndex);
					temp.DecrementMenuItem();
					world.SetMenu(tempMenuIndex, temp);
				}
				break;
			}
		}
	}
	
	public static void HandleKeyDown(World world) {
		if (Main.GetState() == States.MENU) {
			Menu temp = null;
			int tempMenuIndex = -1;
			switch(GUIController.GetCurrentMenu()) {
			case GUIController.MENU_MAIN:
				tempMenuIndex = world.FindMenu(GUIController.GetCurrentMenu());
				temp = world.GetMenu(tempMenuIndex);
				temp.IncrementMenuItem();
				world.SetMenu(tempMenuIndex, temp);
				break;
			case GUIController.MENU_CONTROLS:
				if (GUIController.selectedControlField == -1) {
					tempMenuIndex = world.FindMenu(GUIController.GetCurrentMenu());
					temp = world.GetMenu(tempMenuIndex);
					temp.IncrementMenuItem();
					world.SetMenu(tempMenuIndex, temp);
				}
				break;
			case GUIController.MENU_INVENTORY:
				if (GUIController.subMenuName == null) {
					ItemInventory.MoveCursorDown();
				} else {
					tempMenuIndex = world.FindMenu(GUIController.GetCurrentSubmenu());
					temp = world.GetMenu(tempMenuIndex);
					temp.IncrementMenuItem();
					world.SetMenu(tempMenuIndex, temp);
				}
				break;
			case GUIController.MENU_LOADSAVES:
				SaveLoader.MoveCursorDown();
				break;
			}
		} else if (Main.GetState() == States.BATTLE) {
			Menu temp = null;
			int tempMenuIndex = -1;
			switch(GUIController.GetCurrentMenu()) {
			case GUIController.MENU_BATTLE:
				if (GUIController.subMenuName == null) {
					ItemInventory.MoveCursorDown();
				} else {
					tempMenuIndex = world.FindMenu(GUIController.GetCurrentSubmenu());
					temp = world.GetMenu(tempMenuIndex);
					temp.IncrementMenuItem();
					world.SetMenu(tempMenuIndex, temp);
				}
				break;
			}
		}
	}
	
}
