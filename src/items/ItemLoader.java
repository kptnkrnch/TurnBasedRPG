package items;

import java.io.File;
import java.util.Scanner;

public class ItemLoader {
	
	public static void LoadItems() {
		try {
			Scanner scan = new Scanner(new File("res/dictionaries/ItemDictionary.dict"));
			long linenumber = 0;
			while (scan.hasNextLine()) {
				String line = scan.nextLine().toLowerCase();
				linenumber++;
				if (line.equals("beginitem:")) {
					Item item = new Item();
					while (!line.equals("enditem:")) {
						line = scan.nextLine();
						linenumber++;
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
					ItemDictionary.AddItem(item);
				} else if (line.equals("enditem:")) {
					System.err.println("A misplaced enditem: has occurred on line: " + linenumber);
					scan.close();
					throw new Exception();
				} else {
					scan.close();
					throw new Exception();
				}
			}
			scan.close();
		} catch (Exception e) {
			System.err.println("An error has occurred while loading the item dictionary.");
			e.printStackTrace();
		}
	}
	
}
