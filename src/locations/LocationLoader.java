package locations;

import java.io.File;
import java.util.Scanner;

public class LocationLoader {
	
	public static void LoadLocations() {
		try {
			Scanner scan = new Scanner(new File("res/dictionaries/LocationDictionary.dict"));
			int linenumber = 0;
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				linenumber++;
				if (line.equalsIgnoreCase("beginlocation:")) {
					Location location = new Location();
					while (scan.hasNextLine()) {
						line = scan.nextLine();
						linenumber++;
						if (line.equalsIgnoreCase("endlocation:")) {
							break;
						}
						
						line = line.trim().replace(":", " ");
						
						Scanner lineScanner = new Scanner(line);
						
						String element = lineScanner.next();
						String mapname = "";
						String svalue = "";
						
						switch (element) {
						case "id":
							location.id = lineScanner.nextInt();
							break;
						case "name":
							while (lineScanner.hasNext()) {
								mapname += lineScanner.next();
								mapname += " ";
							}
							mapname = mapname.trim();
							location.name = mapname;
							break;
						case "mapfile":
							svalue += "res/maps/";
							svalue += lineScanner.next();
							location.mapfile = svalue;
							break;
						case "mapworld":
							location.worldname = lineScanner.next();
							break;
						case "mapx":
							location.mapx = lineScanner.nextInt();
							break;
						case "mapy":
							location.mapy = lineScanner.nextInt();
							break;
						case "mapwidth":
							location.mapwidth = lineScanner.nextInt();
							break;
						case "mapheight":
							location.mapheight = lineScanner.nextInt();
							break;
						case "hasbeenvisited":
							svalue = lineScanner.next().toLowerCase();
							if (svalue.equals("true") || svalue.equals("yes")) {
								location.hasBeenVisited = true;
							} else if (svalue.equals("false") || svalue.equals("no")) {
								location.hasBeenVisited = false;
							} else {
								lineScanner.close();
								System.err.println("LocationLoader: An error has occurred pulling boolean value from location dictionary file.");
								throw new Exception();
							}
							break;
						}
						lineScanner.close();
					}
					LocationDictionary.AddLocation(location);
					scan.close();
				} else if (line.trim().length() == 0) {
					
				} else {
					scan.close();
					System.err.println("LocationLoader: An unexpected element has been found on line: " + linenumber);
					throw new Exception();
				}
			}
		} catch(Exception e) {
			
		}
	}
	
}
