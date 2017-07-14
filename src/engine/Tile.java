package engine;

import java.awt.Rectangle;

public class Tile {
	public int x;
	public int y;
	public int width;
	public int height;
	public int type;
	public boolean solid;
	public Rectangle collision_box;
	
	public Tile() {
		
	}
	
	public Tile(int type, int x_pos, int y_pos, int width, int height) {
		this.type = type;
		this.x = x_pos;
		this.y = y_pos;
		this.width = width;
		this.height = height;
		this.solid = false;
		collision_box = new Rectangle(x_pos, y_pos, width, height);
	}
	
	public Tile(int type, int x_pos, int y_pos, int width, int height, boolean solid) {
		this.type = type;
		this.x = x_pos;
		this.y = y_pos;
		this.width = width;
		this.height = height;
		this.solid = solid;
		collision_box = new Rectangle(x_pos, y_pos, width, height);
	}
	
	public boolean IsSolid() {
		return solid;
	}
	
}
