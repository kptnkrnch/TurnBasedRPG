package savefiles;

import items.Item;
import items.ItemDictionary;
import items.ItemInventory;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import engine.Entity;
import engine.Main;

public class SaveLoader {
	public static ArrayList<String> savefiles = new ArrayList<String>();
	public static String saveFilePath = "res/savefiles";
	public static int currentPosition = 0;
	
	public static String GetPath() {
		return saveFilePath;
	}
	
	public static int GetSaveFileCount() {
		int count = 0;
		
		if (savefiles != null) {
			count = savefiles.size();
		}
		
		return count;
	}
	
	public static void DiscoverSaveFiles() {
		File folder = new File(saveFilePath);
		File files[] = folder.listFiles();
		
		savefiles = new ArrayList<String>();
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String filename = files[i].getName();
				if (filename.contains(".save")) {
					savefiles.add(filename);
				}
			}
		}
	}
	
	public static void ReverseSaveOrder() {
		String temp[] = new String[savefiles.size()];
		for (int i = savefiles.size() - 1, n = 0; i >= 0; i--, n++) {
			temp[n] = savefiles.get(i);
		}
		savefiles = new ArrayList<String>();
		
		for (int i = 0; i < temp.length; i++) {
			savefiles.add(temp[i]);
		}
	}
	
	public static String GetCurrentSaveFile() {
		if (savefiles.size() > 0) {
			return savefiles.get(currentPosition);
		}
		return null;
	}
	
	public static String GetSaveFile(int index) {
		if (savefiles.size() > 0 && index >= 0 && index < savefiles.size()) {
			return savefiles.get(index);
		}
		return null;
	}
	
	public static void MoveCursorUp() {
		if (currentPosition == 0 && savefiles.size() > 0) {
			currentPosition = savefiles.size() - 1;
		} else if (savefiles.size() > 0) {
			currentPosition--;
		}
	}
	
	public static void MoveCursorDown() {
		if (currentPosition == savefiles.size() - 1) {
			currentPosition = 0;
		} else if (savefiles.size() > 0) {
			currentPosition++;
		}
	}
	
	public static void LoadSaveFile(String filename) {
		try {
			Scanner scan = new Scanner(new File(saveFilePath + "/" + filename));
			int currentline = 0;
			while (scan.hasNextLine()) {
				String line = scan.nextLine().toLowerCase();
				currentline++;
				switch(line) {
				case "playerdata:":
					Entity player = Main.world.GetEntity(Main.world.FindPlayer());
					while(scan.hasNextLine()) {
						line = scan.nextLine();
						if (line.equalsIgnoreCase("endplayerdata:")) {
							break;
						}
						line = line.trim().replace(":", " ");
						
						Scanner lineScanner = new Scanner(line);
						String element = "";
						
						String svalue = "";
						int ivalue = 0;
						float fvalue = 0;
						
						if (lineScanner.hasNext()) {
							element = lineScanner.next().toLowerCase();
							switch (element) {
							case "x":	
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.SetPosition(ivalue, player.y);
								}
								break;
							case "y":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.SetPosition(player.x, ivalue);
								}
								break;
							case "width":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.width = ivalue;
									player.collision_box.width = ivalue;
								}
								break;
							case "height":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.height = ivalue;
									player.collision_box.height = ivalue;
								}
								break;
							case "locationid":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									Main.world.currentLocationID = ivalue;
								}
								break;
							case "maxhealth":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_max_health = ivalue;
								}
								break;
							case "health":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_health = ivalue;
								}
								break;
							case "attack":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_attack = ivalue;
								}
								break;
							case "defence":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_defence = ivalue;
								}
								break;
							case "speed":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_speed = ivalue;
								}
								break;
							case "criticalchance":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_critical_chance = ivalue;
								}
								break;
							case "dodgechance":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_dodge_chance = ivalue;
								}
								break;
							case "level":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_level = ivalue;
								}
								break;
							case "exp":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_exp = ivalue;
								}
								break;
							case "expnext":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_exp_next = ivalue;
								}
								break;
							case "criticaldamage":
								if (lineScanner.hasNextFloat()) {
									fvalue = lineScanner.nextFloat();
									player.c_critical_damage = fvalue;
								}
								break;
							case "cooldown":
								if (lineScanner.hasNextInt()) {
									ivalue = lineScanner.nextInt();
									player.c_cooldown = ivalue;
								}
								break;
							}
						}
						
						lineScanner.close();
					}
					Main.world.SetEntity(Main.world.FindPlayer(), player);
					break;
				case "inventorydata:":
					while (scan.hasNextLine()) {
						line = scan.nextLine().toLowerCase().trim();
						currentline++;
						if (line.equals("beginitem:")) {
							Item item = new Item();
							while (!line.equals("enditem:")) {
								line = scan.nextLine().trim();
								currentline++;
								if (line.equalsIgnoreCase("enditem:")) {
									break;
								} else {
									line = line.replace(':', ' ');
									Scanner parameter = new Scanner(line);
									String pname = parameter.next();
									pname = pname.toLowerCase();
									
									float fvalue = -1;
									int ivalue = -1;
									String svalue = "";
									
									switch (pname) {
									case "id":
										ivalue = parameter.nextInt();
										item.id = ivalue;
										break;
									case "name":
										while (parameter.hasNext()) {
											svalue += parameter.next();
											svalue += " ";
										}
										svalue = svalue.trim();
										item.name = svalue;
										break;
									case "type":
										ivalue = parameter.nextInt();
										item.type = ivalue;
										break;
									case "armor":
										ivalue = parameter.nextInt();
										item.armor = ivalue;
										break;
									case "damage":
										ivalue = parameter.nextInt();
										item.damage = ivalue;
										break;
									case "criticalchance":
										fvalue = parameter.nextFloat();
										item.criticalChance = fvalue;
										break;
									case "criticaldamage":
										fvalue = parameter.nextFloat();
										item.criticalDamage = fvalue;
										break;
									case "freezingdamage":
										ivalue = parameter.nextInt();
										item.freezingDamage = ivalue;
										break;
									case "freezingchance":
										fvalue = parameter.nextFloat();
										item.freezingChance = fvalue;
										break;
									case "poisondamage":
										ivalue = parameter.nextInt();
										item.poisonDamage = ivalue;
										break;
									case "poisonchance":
										fvalue = parameter.nextFloat();
										item.poisonChance = fvalue;
										break;
									case "firedamage":
										ivalue = parameter.nextInt();
										item.fireDamage = ivalue;
										break;
									case "firechance":
										fvalue = parameter.nextFloat();
										item.fireChance = fvalue;
										break;
									case "stunchance":
										fvalue = parameter.nextFloat();
										item.stunChance = fvalue;
										break;
									case "stuntime":
										ivalue = parameter.nextInt();
										item.stunTime = ivalue;
										break;
									case "slowchance":
										fvalue = parameter.nextFloat();
										item.slowChance = fvalue;
										break;
									case "slowtime":
										ivalue = parameter.nextInt();
										item.slowTime = ivalue;
										break;
									case "dodgechance":
										fvalue = parameter.nextFloat();
										item.dodgeChance = fvalue;
										break;
									case "attackspeed":
										ivalue = parameter.nextInt();
										item.attackSpeed = ivalue;
										break;
									case "healingamount":
										ivalue = parameter.nextInt();
										item.healingAmount = ivalue;
										break;
									case "healingchance":
										fvalue = parameter.nextFloat();
										item.healingChance = fvalue;
										break;
									case "maxuses":
										ivalue = parameter.nextInt();
										item.maxUses = ivalue;
										break;
									case "usecount":
										ivalue = parameter.nextInt();
										item.useCount = ivalue;
										break;
									case "fireresist":
										ivalue = parameter.nextInt();
										item.fireResist = ivalue;
										break;
									case "freezingresist":
										ivalue = parameter.nextInt();
										item.freezingResist = ivalue;
										break;
									case "poisonresist":
										ivalue = parameter.nextInt();
										item.poisonResist = ivalue;
										break;
									case "cureburningchance":
										fvalue = parameter.nextFloat();
										item.cureBurningChance = fvalue;
										break;
									case "curefreezingchance":
										fvalue = parameter.nextFloat();
										item.cureFreezingChance = fvalue;
										break;
									case "curepoisonchance":
										fvalue = parameter.nextFloat();
										item.curePoisonChance = fvalue;
										break;
									case "abilitycooldowntime":
										ivalue = parameter.nextInt();
										item.abilityCooldownTime = ivalue;
										break;
									case "currentcooldowntime":
										ivalue = parameter.nextInt();
										item.currentCooldownTime = ivalue;
										break;
									case "inventoryposition":
										ivalue = parameter.nextInt();
										item.inventoryPosition = ivalue;
										break;
									case "isdroppable":
										svalue = parameter.next().toLowerCase();
										if (svalue.equals("true") || svalue.equals("yes")) {
											item.isDroppable = true;
										} else if (svalue.equals("false") || svalue.equals("no")) {
											item.isDroppable = false;
										} else {
											parameter.close();
											System.err.println("An error has occurred pulling boolean value from item dictionary file.");
											throw new Exception();
										}
										break;
									case "isquestitem":
										svalue = parameter.next().toLowerCase();
										if (svalue.equals("true") || svalue.equals("yes")) {
											item.isQuestItem = true;
										} else if (svalue.equals("false") || svalue.equals("no")) {
											item.isQuestItem = false;
										} else {
											parameter.close();
											System.err.println("An error has occurred pulling boolean value from item dictionary file.");
											throw new Exception();
										}
										break;
									case "isbanked":
										svalue = parameter.next().toLowerCase();
										if (svalue.equals("true") || svalue.equals("yes")) {
											item.isBanked = true;
										} else if (svalue.equals("false") || svalue.equals("no")) {
											item.isBanked = false;
										} else {
											parameter.close();
											System.err.println("An error has occurred pulling boolean value from item dictionary file.");
											throw new Exception();
										}
										break;
									case "issold":
										svalue = parameter.next().toLowerCase();
										if (svalue.equals("true") || svalue.equals("yes")) {
											item.isSold = true;
										} else if (svalue.equals("false") || svalue.equals("no")) {
											item.isSold = false;
										} else {
											parameter.close();
											System.err.println("An error has occurred pulling boolean value from item dictionary file.");
											throw new Exception();
										}
										break;
									case "isequipped":
										svalue = parameter.next().toLowerCase();
										if (svalue.equals("true") || svalue.equals("yes")) {
											item.isEquipped = true;
										} else if (svalue.equals("false") || svalue.equals("no")) {
											item.isEquipped = false;
										} else {
											parameter.close();
											System.err.println("An error has occurred pulling boolean value from item dictionary file.");
											throw new Exception();
										}
										break;
									case "value":
										ivalue = parameter.nextInt();
										item.value = ivalue;
										break;
									case "rarity":
										ivalue = parameter.nextInt();
										item.rarity = ivalue;
										break;
									default:
										break;
									}
									parameter.close();
								}
							}
							//System.out.println("item name: " + item.name);
							ItemInventory.AddItem(item);
						} else if (line.equals("enditem:")) {
							System.err.println("A misplaced enditem: has occurred on line: " + currentline + " in the savefile.");
							scan.close();
							throw new Exception();
						} else {
							scan.close();
							throw new Exception();
						}
					}
					break;
				case "mapdata:":
					break;
				case "npcdata:":
					break;
				case "questdata:":
					break;
				/*default:
					scan.close();
					System.err.println("SaveLoader: An unexpected element has been encountered on line: " + currentline);
					throw new Exception();*/
				}
			}
		} catch (Exception e) {
			
		}
	}
}
