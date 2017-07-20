package engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Menu {
	public String name;
	public Image background;
	public ArrayList<MenuItem> menuitems;
	public int selectedItem = 0;
	public int x;
	public int y;
	public int width;
	public int height;
	
	public Menu(String menu_name, int x, int y, int width, int height) {
		name = menu_name;
		background = null;
		menuitems = new ArrayList<MenuItem>();
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
	}
	
	public Menu(String menu_name, String background_location, int x, int y, int width, int height, float alpha) {
		try {
			background = new Image(background_location);
			background.setFilter(Image.FILTER_NEAREST);
			background.setAlpha(alpha);
			name = menu_name;
			menuitems = new ArrayList<MenuItem>();
			this.x = x;
			this.y = y; 
			this.width = width;
			this.height = height;
		} catch (SlickException e) {
		}
	}
	
	public void AddMenuItem(MenuItem data) {
		menuitems.add(data);
	}
	
	public int GetMenuItemCount() {
		if (menuitems != null) {
			return menuitems.size();
		} else {
			return 0;
		}
	}
	
	public MenuItem GetMenuItem(int index) {
		if (index >= 0 && index < GetMenuItemCount()) {
			return menuitems.get(index);
		} else {
			return null;
		}
	}
	
	public void SetMenuItem(int index, MenuItem item) {
		menuitems.set(index, item);
	}
	
	public void IncrementMenuItem() {
		if (GetMenuItemCount() > 0) {
			MenuItem temp = GetMenuItem(selectedItem);
			temp.DeHighlight();
			SetMenuItem(selectedItem, temp);
			selectedItem++;
			if (selectedItem >= GetMenuItemCount()) {
				selectedItem = 0;
			}
			temp = GetMenuItem(selectedItem);
			temp.Highlight();
			SetMenuItem(selectedItem, temp);
		}
	}
	
	public void DecrementMenuItem() {
		if (GetMenuItemCount() > 0) {
			MenuItem temp = GetMenuItem(selectedItem);
			temp.DeHighlight();
			SetMenuItem(selectedItem, temp);
			selectedItem--;
			if (selectedItem < 0) {
				selectedItem = GetMenuItemCount() - 1;
			}
			temp = GetMenuItem(selectedItem);
			temp.Highlight();
			SetMenuItem(selectedItem, temp);
		}
	}
	
	public void ResetMenuItemHighlight() {
		if (GetMenuItemCount() > 0) {
			MenuItem temp = GetMenuItem(selectedItem);
			temp.DeHighlight();
			SetMenuItem(selectedItem, temp);
			selectedItem = 0;
			temp = GetMenuItem(selectedItem);
			temp.Highlight();
			SetMenuItem(selectedItem, temp);
		}
	}
	
	public static MenuItem CreateMenuItem(String text, int x, int y, String goesTo) {
		MenuItem item = new MenuItem(text, x, y, goesTo);
		return item;
	}
	
	public static MenuItem CreateMenuItem(String text, int x, int y, String goesTo, boolean highlight) {
		MenuItem item = new MenuItem(text, x, y, goesTo);
		item.Highlight();
		return item;
	}
	
	public static ArrayList<Menu> LoadMenus(String menu_config_source) {
		ArrayList<Menu> menus = new ArrayList<Menu>();
		
		if (menu_config_source != null) {
			File file = new File(menu_config_source);
			try {
				Scanner scan = new Scanner(file);
				int menu_count = 0;
				if (scan.hasNextLine()) {
					String line = scan.nextLine();
					Scanner lineScanner = new Scanner(line);
					
					if (lineScanner.hasNextInt()) {
						menu_count = lineScanner.nextInt();
					}
					
					lineScanner.close();
				}
				for (int i = 0; i < menu_count; i++) {
					if (scan.hasNextLine()) {
						String line = scan.nextLine();
						if (line.trim().length() != 0) {
							Scanner lineScanner = new Scanner(line);
							Menu menu = null;
							String menu_name = null;
							String background_src = null;
							int x = 0;
							int y = 0;
							int width = 0;
							int height = 0;
							int hasItems = 0;
							float alpha = 1.0f;
							boolean centerX = false;
							boolean centerY = false;
							
							if (lineScanner.hasNext()) {
								menu_name = lineScanner.next();
							}
							
							if (lineScanner.hasNextInt()) {
								x = lineScanner.nextInt();
							} else if (lineScanner.hasNext()) {
								String sx = lineScanner.next();
								centerX = true;
							}
							
							if (lineScanner.hasNextInt()) {
								y = lineScanner.nextInt();
							} else if (lineScanner.hasNext()) {
								String sy = lineScanner.next();
								centerY = true;
							}
							
							if (lineScanner.hasNextInt()) {
								width = lineScanner.nextInt();
								if (centerX) {
									x = (Main.ScreenResX - width) / 2;
								}
							}
							
							if (lineScanner.hasNextInt()) {
								height = lineScanner.nextInt();
								if (centerY) {
									y = (Main.ScreenResY - height) / 2;
								}
							}
							
							if (lineScanner.hasNextFloat()) {
								alpha = lineScanner.nextFloat();
							}
							
							if (lineScanner.hasNextInt()) {
								hasItems = lineScanner.nextInt();
							}
							
							if (lineScanner.hasNext()) {
								background_src = lineScanner.next();
							}
							
							menu = new Menu(menu_name, background_src, x, y, width, height, alpha);
							
							if (hasItems != 0 && menu != null) {
								while (scan.hasNextLine()) {
									line = scan.nextLine();
									MenuItem item = null;
									String text = null;
									String goesTo = null;
									int menuitem_x = 0;
									int menuitem_y = 0;
									
									if (line.contains("[")) {
									} else if (line.contains("]")) {
										break;
									} else {
										lineScanner = new Scanner(line);
										
										if (lineScanner.hasNext()) {
											text = lineScanner.next();
										}
										
										if (lineScanner.hasNextInt()) {
											menuitem_x = lineScanner.nextInt() + x;
										}
										
										if (lineScanner.hasNextInt()) {
											menuitem_y = lineScanner.nextInt() + y;
										}
										
										if (lineScanner.hasNext()) {
											goesTo = lineScanner.next();
										}
										item = new MenuItem(text, menuitem_x, menuitem_y, goesTo);
										menu.AddMenuItem(item);
									}
								}
							}
							lineScanner.close();
							MenuItem tempItem = menu.GetMenuItem(0);
							if (tempItem != null) {
								tempItem.Highlight();
								menu.SetMenuItem(0, tempItem);
							}
							menus.add(menu);
						} else {
							i--;
						}
					}
				}
				scan.close();
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
		
		return menus;
	}
}
