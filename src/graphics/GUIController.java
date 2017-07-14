package graphics;

import input.InputController;
import items.Item;
import items.ItemDictionary;
import items.ItemInventory;

import java.awt.Rectangle;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

import savefiles.SaveLoader;
import engine.Entity;
import engine.EntityDictionary;
import engine.Main;
import engine.Menu;
import engine.MenuItem;
import engine.States;
import engine.World;
import exceptions.PlayerNotFoundException;

public class GUIController {
	
	public static String currentMenuName = "info_panel";
	public static String previousMenuName = null;
	public static String subMenuName = null;
	
	public static final String MENU_MAIN = "main";
	public static final String MENU_INFO_PANEL = "info_panel";
	public static final String MENU_PAUSE = "pause";
	public static final String MENU_CONTROLS = "controls";
	public static final String MENU_INVENTORY = "inventory";
	public static final String MENU_LOADSAVES = "load_saves";
	public static final String SUBMENU_INVENTORY_ITEM = "inventory_item";
	
	public static int selectedControlField = -1;
	public static HashMap<String, Integer> tempKeyMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> tempJoyMap = new HashMap<String, Integer>();
	
	public static void SetCurrentMenu(String menu) {
		previousMenuName = currentMenuName;
		currentMenuName = menu;
	}
	
	public static void SetSubMenu(String menu) {
		subMenuName = menu;
	}
	
	public static String GetCurrentMenu() {
		return currentMenuName;
	}
	
	public static String GetCurrentSubmenu() {
		return subMenuName;
	}
	
	public static String GetPreviousMenu() {
		return previousMenuName;
	}
	
	public static void DrawMenus(World world, Graphics g) {
		if (Main.GetState() == States.RUNNING) {
			SetCurrentMenu(GUIController.MENU_INFO_PANEL);
		}
		String menu = GetCurrentMenu();
		if (menu != null) {
			switch(menu) {
			case MENU_MAIN:
				DrawMainMenu(world, g);
				break;
			case MENU_INFO_PANEL:
				DrawPlayerInfoPanel(world, g);
				break;
			case MENU_PAUSE:
				DrawPauseScreen(world, g);
				break;
			case MENU_CONTROLS:
				DrawControlsMenu(world, g);
				break;
			case MENU_INVENTORY:
				DrawInventoryMenu(world, g);
				break;
			case MENU_LOADSAVES:
				DrawLoadSaveFileMenu(world, g);
				break;
			}
		}
		String subMenu = GetCurrentSubmenu();
		if (subMenu != null) {
			switch(subMenu) {
			case SUBMENU_INVENTORY_ITEM:
				DrawInventoryItemMenu(world, g);
				break;
			}
		}
	}
	
	public static void DrawPlayerInfoPanel(World world, Graphics g) {
		int menuID = world.FindMenu(MENU_INFO_PANEL);
		Menu temp = world.GetMenu(menuID);
		if (temp != null && temp.background != null
				&& Main.GetState() != States.TALKING) {
			
			try {
				Entity player = world.GetEntity(world.FindPlayer());
				Rectangle infoPanelBox = new Rectangle(temp.x + GraphicsController.VIEWPORT_X, 
						temp.y + GraphicsController.VIEWPORT_Y, temp.width, temp.height);
				
				if (!infoPanelBox.intersects(player.collision_box)) {
					temp.background.setAlpha(0.8f);
				} else {
					temp.background.setAlpha(0.4f);
				}
				g.drawImage(temp.background, temp.x + GraphicsController.VIEWPORT_X, 
						temp.y + GraphicsController.VIEWPORT_Y);
				DrawHealthBar(world, g, temp.x, temp.y);
				DrawEXPBar(world, g, temp.x, temp.y);
			} catch (PlayerNotFoundException e) {
			}
		}
	}
	
	public static void DrawHealthBar(World world, Graphics g, int menux, int menuy) {
		try {
			int entityIndex = world.FindPlayer();
			Entity temp = world.entities.get(entityIndex);
			if (temp != null) {
				g.setColor(new Color(255, 0, 0));
				if (temp.c_health > 0) {
					g.fillRect(menux + 13 + GraphicsController.VIEWPORT_X, menuy + 13 + GraphicsController.VIEWPORT_Y, 
							(int)((188) * ((double)temp.c_health / temp.c_max_health)), 
							13);
				}
				g.setColor(new Color(255, 255, 255));
				g.drawString(temp.c_health + "/" + temp.c_max_health, 
						menux + 14 + GraphicsController.VIEWPORT_X, menuy + 10 + GraphicsController.VIEWPORT_Y);
			}
		} catch (PlayerNotFoundException e) {
		}
	}
	
	public static void DrawEXPBar(World world, Graphics g, int menux, int menuy) {
		try {
			int entityIndex = world.FindPlayer();
			Entity temp = world.entities.get(entityIndex);
			if (temp != null) {
				g.setColor(Color.yellow);
				if (temp.c_health > 0) {
					g.fillRect(menux + 12 + GraphicsController.VIEWPORT_X, menuy + 71 + GraphicsController.VIEWPORT_Y, 
							(int)((188) * ((double)temp.c_exp / temp.c_exp_next)), 
							13);
				}
				g.setColor(Color.white);
				g.drawString("L: " + temp.c_level, 
						menux + 13 + GraphicsController.VIEWPORT_X, menuy + 50 + GraphicsController.VIEWPORT_Y);
				
				if (temp.c_exp != 0) {
					g.setColor(Color.darkGray);
				} else {
					g.setColor(Color.white);
				}
				
				g.drawString(temp.c_exp + "/" + temp.c_exp_next, 
						menux + 13 + GraphicsController.VIEWPORT_X, menuy + 68 + GraphicsController.VIEWPORT_Y);
				
				g.setColor(Color.white);
			}
		} catch (PlayerNotFoundException e) {
		}
	}
	
	public static void DrawPauseScreen(World world, Graphics g) {
		if (Main.GetState() == States.PAUSED) {
			g.drawString("paused",
					Main.ResX / 2 - 20 + GraphicsController.VIEWPORT_X, 
					Main.ResY / 2 + GraphicsController.VIEWPORT_Y);
		}
	}
	
	public static void DrawMainMenu(World world, Graphics g) {
		int menuID = world.FindMenu(MENU_MAIN);
		Menu temp = world.GetMenu(menuID);
		if (temp != null && temp.background != null
				&& Main.GetState() == States.MENU) {
			
			g.drawImage(temp.background, temp.x + GraphicsController.VIEWPORT_X, 
					temp.y + GraphicsController.VIEWPORT_Y);
			
			for (int i = 0; i < temp.GetMenuItemCount(); i++) {
				MenuItem item = temp.GetMenuItem(i);
				if (item.IsHighlighted()) {
					g.setColor(Color.black);
				}
				g.drawString(item.text, item.x + GraphicsController.VIEWPORT_X,
						item.y + GraphicsController.VIEWPORT_Y);
				g.setColor(Color.white);
			}
		}
	}
	
	public static void DrawInventoryMenu(World world, Graphics g) {
		int menuID = world.FindMenu(MENU_INVENTORY);
		Menu temp = world.GetMenu(menuID);
		if (temp != null && temp.background != null
				&& Main.GetState() == States.MENU) {
			
			g.drawImage(temp.background, temp.x + GraphicsController.VIEWPORT_X, 
					temp.y + GraphicsController.VIEWPORT_Y);
			
			int i = 0;
			if (ItemInventory.GetItemCount() >= 14) {
				if (ItemInventory.currentPosition < 7) {
					i = 0;
				} else if (ItemInventory.currentPosition >= 7 && ItemInventory.GetItemCount() >= 14 && ItemInventory.currentPosition > ItemInventory.GetItemCount() - 8) {
					i = ItemInventory.GetItemCount() - 14;
				}  else {
					i = ItemInventory.currentPosition - 6;
				}
			} else {
				i = 0;
			}
			for (int n = 0; i < ItemInventory.GetItemCount() && n < 14; i++, n++) {
				Item item = null;
				item = ItemInventory.GetItem(i);
				
				if (item != null) {
					if (i == ItemInventory.currentPosition) {
						g.setColor(Color.lightGray);
						g.fillRect(259 + GraphicsController.VIEWPORT_X, 40 + (20 * n) + GraphicsController.VIEWPORT_Y, 356, 20);
						g.drawImage(ItemInventory.inventoryArrow, 266 + GraphicsController.VIEWPORT_X, 
								40 + (20 * n)+ GraphicsController.VIEWPORT_Y);
					}
					
					switch(item.rarity) {
					case ItemDictionary.RARITY_COMMON:
						g.setColor(Color.white);
						break;
					case ItemDictionary.RARITY_UNCOMMON:
						g.setColor(Color.green);
						break;
					case ItemDictionary.RARITY_RARE:
						g.setColor(Color.blue);
						break;
					case ItemDictionary.RARITY_LEGENDARY:
						g.setColor(new Color(102, 0, 204));
						break;
					case ItemDictionary.RARITY_EXOTIC:
						g.setColor(Color.orange);
						break;
					default:
						g.setColor(Color.white);
						break;
					}
					g.drawString(item.name, 290 + GraphicsController.VIEWPORT_X,
							40 + 20 * n + GraphicsController.VIEWPORT_Y);
					g.setColor(Color.black);
					if (item.isEquipped) {
						g.drawString("E", 480 + GraphicsController.VIEWPORT_X,
								40 + 20 * n + GraphicsController.VIEWPORT_Y);
					}
					if (ItemDictionary.IsWeapon(item)) {
						g.setColor(new Color(180,0,0));
						g.drawString("ATTACK: " + item.damage, 500 + GraphicsController.VIEWPORT_X,
								40 + 20 * n + GraphicsController.VIEWPORT_Y);
					} else if (ItemDictionary.IsArmor(item)) {
						g.setColor(new Color(0,0,120));
						g.drawString("ARMOR: " + item.armor, 500 + GraphicsController.VIEWPORT_X,
								40 + 20 * n + GraphicsController.VIEWPORT_Y);
					}
					g.setColor(Color.white);
				}
			}
		}
	}
	
	public static void DrawInventoryItemMenu(World world, Graphics g) {
		int menuID = world.FindMenu(SUBMENU_INVENTORY_ITEM);
		Menu temp = world.GetMenu(menuID);
		if (temp != null && temp.background != null
				&& Main.GetState() == States.MENU) {
			
			g.drawImage(temp.background, temp.x + GraphicsController.VIEWPORT_X, 
					temp.y + GraphicsController.VIEWPORT_Y);
			
			g.setColor(Color.black);
			for (int i = 0; i < temp.GetMenuItemCount(); i++) {
				MenuItem tempItem = temp.GetMenuItem(i);
				if (tempItem.IsHighlighted()) {
					//g.setColor(Color.blue);
					g.setColor(Color.darkGray);
					g.fillRect(temp.x + 5 + GraphicsController.VIEWPORT_X, temp.y + 8 + 20 * i + GraphicsController.VIEWPORT_Y, 120, 20);
					g.setColor(Color.white);
				} else {
					g.setColor(Color.black);
				}
				g.drawString(tempItem.text, temp.x + 8 + GraphicsController.VIEWPORT_X,
						temp.y + 8 + 20 * i + GraphicsController.VIEWPORT_Y);
				g.setColor(Color.black);
				if (tempItem.otherInformation.containsKey("equipped")) {
					g.drawString("E", temp.x + 100 + GraphicsController.VIEWPORT_X,
							temp.y + 8 + 20 * i + GraphicsController.VIEWPORT_Y);
				}
			}
			g.setColor(Color.white);
		}
	}
	
	public static void DrawLoadSaveFileMenu(World world, Graphics g) {
		int menuID = world.FindMenu(MENU_INVENTORY);
		Menu temp = world.GetMenu(menuID);
		SaveLoader.DiscoverSaveFiles();
		SaveLoader.ReverseSaveOrder();
		if (temp != null && temp.background != null
				&& Main.GetState() == States.MENU) {
			
			g.drawImage(temp.background, temp.x + GraphicsController.VIEWPORT_X, 
					temp.y + GraphicsController.VIEWPORT_Y);
			
			int i = 0;
			if (SaveLoader.GetSaveFileCount() >= 14) {
				if (SaveLoader.currentPosition < 7) {
					i = 0;
				} else if (SaveLoader.currentPosition >= 7 && SaveLoader.GetSaveFileCount() >= 14 && SaveLoader.currentPosition > SaveLoader.GetSaveFileCount() - 8) {
					i = SaveLoader.GetSaveFileCount() - 14;
				}  else {
					i = SaveLoader.currentPosition - 6;
				}
			} else {
				i = 0;
			}
			for (int n = 0; i < SaveLoader.GetSaveFileCount() && n < 14; i++, n++) {
				String savefile = null;
				savefile = SaveLoader.GetSaveFile(i);
				
				if (savefile != null) {
					if (i == SaveLoader.currentPosition) {
						g.setColor(Color.lightGray);
						g.fillRect(259 + GraphicsController.VIEWPORT_X, 40 + (20 * n) + GraphicsController.VIEWPORT_Y, 356, 20);
					}
					g.setColor(Color.black);
					g.drawString(savefile, 290 + GraphicsController.VIEWPORT_X,
							40 + 20 * n + GraphicsController.VIEWPORT_Y);
					
					g.setColor(Color.white);
				}
			}
		}
	}
	
	public static void DrawControlsMenu(World world, Graphics g) {
		int menuID = world.FindMenu(MENU_CONTROLS);
		Menu temp = world.GetMenu(menuID);
		if (temp != null && temp.background != null
				&& Main.GetState() == States.MENU) {
			try {
				Image unfocused_input = new Image("res/gui/textfield/textfield_unfocused.png");
				Image focused_input = new Image("res/gui/textfield/textfield_focused.png");
				g.drawImage(temp.background, temp.x + GraphicsController.VIEWPORT_X, 
						temp.y + GraphicsController.VIEWPORT_Y);
				if (tempKeyMap.isEmpty() == false) {
					
					if (Main.CurrentController() == InputController.KEYBOARD) {
						g.setColor(Color.white);
						g.drawString("Controller: KEYBOARD", 15 + temp.x + GraphicsController.VIEWPORT_X, 
								14 + temp.y + GraphicsController.VIEWPORT_Y);
					} else {
						g.setColor(Color.white);
						g.drawString("Controller: JOYSTICK", 15 + temp.x + GraphicsController.VIEWPORT_X, 
								14 + temp.y + GraphicsController.VIEWPORT_Y);
					}
					
					for (int i = 0; i < temp.GetMenuItemCount(); i++) {
						MenuItem item = temp.GetMenuItem(i);
						if (item.IsHighlighted()) {
							g.setColor(Color.black);
						} else {
							g.setColor(Color.white);
						}
						g.drawString(item.text, item.x + GraphicsController.VIEWPORT_X,
								item.y + GraphicsController.VIEWPORT_Y);
						
						String key = "KEY_" + item.text;
						String key_value = Input.getKeyName(GetKeyValue(key));
						
						if (key_value.contains("LCONTROL")) {
							key_value = "LCTRL";
						} else if (key_value.contains("RCONTROL")) {
							key_value = "RCTRL";
						} else if (key_value.contains("APOSTROPHE")) {
							key_value = "'";
						} else if (key_value.contains("LBRACKET")) {
							key_value = "[";
						} else if (key_value.contains("RBRACKET")) {
							key_value = "]";
						} else if (key_value.contains("BACKSLASH")) {
							key_value = "\\";
						} else if (key_value.contains("SUBTRACT")) {
							key_value = "-";
						} else if (key_value.contains("SLASH")) {
							key_value = "/";
						} else if (key_value.contains("ADD")) {
							key_value = "+";
						} else if (key_value.contains("SEMICOLON")) {
							key_value = ";";
						} else if (key_value.contains("MULTIPLY")) {
							key_value = "*";
						} else if (key_value.contains("DIVIDE")) {
							key_value = "/";
						}
						
						if (i == selectedControlField) {
							g.setColor(Color.black);
							if (InputController.GetKeyValue(key) != null) {
								g.drawImage(focused_input, temp.x + temp.width - 100 + GraphicsController.VIEWPORT_X, 
										item.y + GraphicsController.VIEWPORT_Y);
								g.drawString(key_value, temp.x + temp.width - 96 + GraphicsController.VIEWPORT_X, 
										item.y + 3 + GraphicsController.VIEWPORT_Y);
							}
						} else {
							g.setColor(Color.black);
							if (InputController.GetKeyValue(key) != null) {
								g.drawImage(unfocused_input, temp.x + temp.width - 100 + GraphicsController.VIEWPORT_X, 
										item.y + GraphicsController.VIEWPORT_Y);
								g.drawString(key_value, temp.x + temp.width - 96 + GraphicsController.VIEWPORT_X, 
										item.y + 3 + GraphicsController.VIEWPORT_Y);
							}
						}
						
						g.setColor(Color.white);
					}
				}
			} catch (Exception e) {
			}
		}
	}
	
	public static Integer GetKeyValue(String key) {
		if (Main.CurrentController() == InputController.KEYBOARD) {
			if (tempKeyMap.containsKey(key)) {
				return tempKeyMap.get(key);
			} else {
				return null;
			}
		} else {
			if (tempJoyMap.containsKey(key)) {
				return tempJoyMap.get(key);
			} else {
				return null;
			}
		}
	}
	
}
