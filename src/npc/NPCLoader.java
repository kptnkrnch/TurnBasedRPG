package npc;

import java.io.File;
import java.util.Scanner;

public class NPCLoader {
	public static void LoadNPCs() {
		try {
			Scanner scan = new Scanner(new File("res/dictionaries/NPCDictionary.dict"));
			int currentline = 0;
			while (scan.hasNextLine()) {
				String line = scan.nextLine().trim();
				currentline++;
				
				if (line.equalsIgnoreCase("beginnpc:")) {
					while (scan.hasNextLine()) {
						line = scan.nextLine();
						line = line.trim().replace(":", " ");
						Scanner lineScanner = new Scanner(line);
						
						if (lineScanner.hasNext()) {
							String element = lineScanner.next().toLowerCase();
							
							switch(element) {
							case "":
								break;
							}
						}
						
					}
				} else {
					scan.close();
					System.err.println("NPCLoader: An unexpected element has occurred on line: " + currentline);
					throw new Exception();
				}
			}
			
			scan.close();
		} catch (Exception e) {
			
		}
	}
}
