package entities;

import java.io.File;
import java.util.Scanner;

import items.Item;
import items.ItemDictionary;

public class EntityManager {
	public static void LoadEntity(String filename) {
		try {
			Scanner scan = new Scanner(new File(filename));
			long linenumber = 0;
			while (scan.hasNextLine()) {
				String line = scan.nextLine().toLowerCase();
				linenumber++;
				if (line.equals("BeginEntity:")) {
					Item item = new Item();
					while (!line.equals("EndEntity:")) {
						line = scan.nextLine();
						linenumber++;
						if (line.equalsIgnoreCase("EndEntity:")) {
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
								if (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									//item.id = ivalue;
								} else if (parameter.hasNext()){
									svalue = parameter.next();
								}
								break;
							case "name":
								while (parameter.hasNext()) {
									svalue += parameter.next();
									svalue += " ";
								}
								svalue = svalue.trim();
								//item.name = svalue;
								break;
							case "type":
								svalue = parameter.next();
								//item.type = ivalue;
								break;
							case "x":
								ivalue = parameter.nextInt();
								//item.armor = ivalue;
								break;
							case "y":
								ivalue = parameter.nextInt();
								//item.damage = ivalue;
								break;
							case "width":
								ivalue = parameter.nextInt();
								//item.criticalChance = fvalue;
								break;
							case "height":
								ivalue = parameter.nextInt();
								//item.criticalDamage = fvalue;
								break;
							case "locationid":
								ivalue = parameter.nextInt();
								//item.freezingDamage = ivalue;
								break;
							case "maxhealth":
								ivalue = parameter.nextInt();
								//item.freezingChance = fvalue;
								break;
							case "health":
								ivalue = parameter.nextInt();
								//item.poisonDamage = ivalue;
								break;
							case "attack":
								ivalue = parameter.nextInt();
								//item.poisonChance = fvalue;
								break;
							case "defence":
								ivalue = parameter.nextInt();
								//item.fireDamage = ivalue;
								break;
							case "speed":
								ivalue = parameter.nextInt();
								//item.fireChance = fvalue;
								break;
							case "criticalchance":
								fvalue = parameter.nextFloat();
								//item.stunChance = fvalue;
								break;
							case "dodgechance":
								fvalue = parameter.nextFloat();
								//item.stunTime = ivalue;
								break;
							case "level":
								ivalue = parameter.nextInt();
								//item.slowChance = fvalue;
								break;
							case "exp":
								ivalue = parameter.nextInt();
								//item.slowTime = ivalue;
								break;
							case "expnext":
								ivalue = parameter.nextInt();
								//item.dodgeChance = fvalue;
								break;
							case "criticalmodifier":
								fvalue = parameter.nextFloat();
								//item.attackSpeed = ivalue;
								break;
							case "cooldown":
								ivalue = parameter.nextInt();
								//item.healingAmount = ivalue;
								break;
							case "abilities":
								svalue = parameter.next();
								if (svalue == "{") {
									//read abilities
								}
								//item.healingChance = fvalue;
								break;
							default:
								break;
							}
							parameter.close();
						}
					}
					//System.out.println("item name: " + item.name);
					//ItemDictionary.AddItem(item);
				} else if (line.equals("EndEntity:")) {
					System.err.println("A misplaced EndEntity: has occurred on line: " + linenumber);
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
