package locations;

public class Location {
	public int id;
	public String name;
	public String worldname;
	public String mapfile;
	public int mapx;
	public int mapy;
	public int mapwidth;
	public int mapheight;
	public boolean hasBeenVisited;
	
	public Location() {
		id = -1;
		name = "";
		mapfile = "";
		mapx = 0;
		mapy = 0;
		mapwidth = 0;
		mapheight = 0;
		hasBeenVisited = false;
	}
}
