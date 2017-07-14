package pathing;

public class PathTile {
	public int id;
	public int x;
	public int y;
	public int h;
	public int g;
	public int f;
	
	public PathTile parent;
	
	public boolean solid;
	public boolean onPath;
	
	public PathTile(int id, int x, int y, boolean s) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.solid = s;
		this.h = 0;
		this.g = 0;
		this.f = 0;
		this.onPath = false;
		this.parent = null;
	}
	
	public void SetSolid() {
		this.solid = true;
	}
}
