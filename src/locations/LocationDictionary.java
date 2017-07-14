package locations;

import java.util.ArrayList;

public class LocationDictionary {
	public static ArrayList<Location> locations = new ArrayList<Location>();
	
	public static void AddLocation(Location location) {
		locations.add(location);
	}
	
	public static Location GetLocationByID(int id) {
		Location l = null;
		for (int i = 0; i < locations.size(); i++) {
			Location temp = locations.get(i);
			if (temp.id == id) {
				return temp;
			}
		}
		return l;
	}
	
	public static Location GetLocationByIndex(int index) {
		Location l = locations.get(index);
		return l;
	}
	
	public static boolean UpdateLocationByIndex(int index, Location location) {
		boolean success = false;
		locations.add(index, location);
		success = true;
		return success;
	}
	
	public static boolean UpdateLocationByID(int id, Location location) {
		boolean success = false;
		for (int i = 0; i < locations.size(); i++) {
			Location temp = locations.get(i);
			if (temp.id == id) {
				locations.add(i, location);
				break;
			}
		}
		return success;
	}
}
