package pathing;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import engine.Direction;
import engine.Tile;


public class PathFinder {
	private PathTile[][] map;
	private int startX, startY;
	private int targetX, targetY;
	private int tilesize;
	private boolean found;
	private boolean finding;
	
	public PathFinder() {
		map = null;
		startX = 0;
		startY = 0;
		targetX = 0;
		targetY = 0;
		tilesize = 0;
		found = false;
		finding = false;
	}
	
	public void run() {
		this.finding = true;
		ArrayList<PathTile> open = new ArrayList<PathTile>();
		ArrayList<PathTile> closed = new ArrayList<PathTile>();
		PathTile current;
		
		for (int x = 0; x < this.map.length; x++) {
			for (int y = 0; y < this.map[0].length; y++) {
				this.map[x][y].h = Math.abs(this.targetX - x) + Math.abs(this.targetY - y);
			}
		}
		
		current = CalculateTotalCost(this.map, this.startX, this.startY, open, closed);
		
		this.found = false;
		while (!this.found) {
			current = FindLeastCost(this.map, open, closed);
			if (current.x == this.targetX && current.y == this.targetY) {
				this.found = true;
			}
			if (open.size() == 0) {
				break;
			}
		}
		this.finding = false;
	}
	
	/* Takes in game coordinates, not tile coordinates */
	public void SetStart(int x, int y) {
		if (map != null) {
			int tempX = x / tilesize;
			int tempY = y / tilesize;
			Rectangle start_box = new Rectangle(x + (tilesize / 2) - 2, y + (tilesize / 2) - 2, 4, 4);
			boolean found = false;
			for (int i = tempX - 1; i <= tempX + 1; i++) {
				for (int n = tempY - 1; n <= tempY + 1; n++) {
					Rectangle tile_box = new Rectangle(i * tilesize, n * tilesize, tilesize, tilesize);
					if (tile_box.contains(start_box)) {
						startX = i;
						startY = n;
						found = true;
					}
				}
			}
			if (!found) {
				startX = x / tilesize;
				startY = y / tilesize;
			}
			//startX = (x + tilesize / 2) / tilesize;
			//startY = (y + tilesize / 2) / tilesize;
			
		}
	}
	
	/* Takes in game coordinates, not tile coordinates */
	public void SetTarget(int x, int y) {
		if (map != null) {
			int tempX = x / tilesize;
			int tempY = y / tilesize;
			Rectangle start_box = new Rectangle(x + (tilesize / 2) - 2, y + (tilesize / 2) - 2, 4, 4);
			boolean found = false;
			for (int i = tempX - 1; i <= tempX + 1; i++) {
				for (int n = tempY - 1; n <= tempY + 1; n++) {
					Rectangle tile_box = new Rectangle(i * tilesize, n * tilesize, tilesize, tilesize);
					if (tile_box.contains(start_box)) {
						targetX = i;
						targetY = n;
						found = true;
					}
				}
			}
			if (!found) {
				targetX = x / tilesize;
				targetY = y / tilesize;
			}
			//targetX = (x + tilesize) / tilesize;
			//targetY = (y + tilesize) / tilesize;
		}
	}
	
	public void LoadMap(Tile[][] tile) {
		if (tile != null && tile.length > 0 && tile[0].length > 0) {
			this.map = new PathTile[tile.length][tile[0].length];
			this.tilesize = tile[0][0].width;
			int id = 0;
			
			for (int y = 0; y < tile[0].length; y++) {
				for (int x = 0; x < tile.length; x++) {
					this.map[x][y] = new PathTile(id, x, y, tile[x][y].IsSolid());
					id++;
				}
			}
		}
	}
	
	public PathTile GetNorth(PathTile[][] map, int x, int y) {
		if (y > 0) {
			return map[x][y - 1];
		} else {
			return null;
		}
	}
	
	public PathTile GetSouth(PathTile[][] map, int x, int y) {
		if (y < map[0].length - 1) {
			return map[x][y + 1];
		} else {
			return null;
		}
	}
	
	public PathTile GetEast(PathTile[][] map, int x, int y) {
		if (x < map.length - 1) {
			return map[x + 1][y];
		} else {
			return null;
		}
	}
	
	public PathTile GetWest(PathTile[][] map, int x, int y) {
		if (x > 0) {
			return map[x - 1][y];
		} else {
			return null;
		}
	}
	
	public PathTile CalculateTotalCost(PathTile[][] map, int x, int y, ArrayList<PathTile> open, ArrayList<PathTile> closed) {
		if (x < map.length && y < map[0].length) {
			PathTile current = map[x][y];
			PathTile temp;
			
			temp = GetNorth(map, x, y);
			if (temp != null && !temp.solid) {
				if (!closed.contains(temp) && !open.contains(temp)) {
					temp.g = current.g + 1;
					temp.f = temp.g + temp.h;
					temp.parent = current;
					map[temp.x][temp.y] = temp;
					open.add(temp);
				}
			}
			
			temp = GetSouth(map, x, y);
			if (temp != null && !temp.solid) {
				if (!closed.contains(temp) && !open.contains(temp)) {
					temp.g = current.g + 1;
					temp.f = temp.g + temp.h;
					temp.parent = current;
					map[temp.x][temp.y] = temp;
					open.add(temp);
				}
			}
			
			temp = GetEast(map, x, y);
			if (temp != null && !temp.solid) {
				if (!closed.contains(temp) && !open.contains(temp)) {
					temp.g = current.g + 1;
					temp.f = temp.g + temp.h;
					temp.parent = current;
					map[temp.x][temp.y] = temp;
					open.add(temp);
				}
			}
			
			temp = GetWest(map, x, y);
			if (temp != null && !temp.solid) {
				if (!closed.contains(temp) && !open.contains(temp)) {
					temp.g = current.g + 1;
					temp.f = temp.g + temp.h;
					temp.parent = current;
					map[temp.x][temp.y] = temp;
					open.add(temp);
				}
			}
			
			
			open.remove(current);
			closed.add(current);
			
			return current;
		}
		
		return null;
	}
	
	public PathTile FindLeastCost(PathTile[][] map, ArrayList<PathTile> open, ArrayList<PathTile> closed) {
		Iterator<PathTile> open_it = open.iterator();
		int leastX = 0;
		int leastY = 0;
		int lowestCost = Integer.MAX_VALUE;
		while (open_it.hasNext()) {
			PathTile temp = open_it.next();
			if (temp.f < lowestCost) {
				lowestCost = temp.f;
				leastX = temp.x;
				leastY = temp.y;
			}
		}
		return CalculateTotalCost(map, leastX, leastY, open, closed);
	}
	
	/* returned Path contains game coordinates, not tile coordinates */
	public Path FindPath() {
		if (this.found) {
			PathTile end = this.map[this.targetX][this.targetY];
			PathTile current = end.parent;
			ArrayList<PathTile> temp = new ArrayList<PathTile>();
			Path path = new Path();
			
			end.onPath = true;
			temp.add(end);
			while (current != null) {
				current.onPath = true;
				if (!(current.x == this.startX && current.y == this.startY)) {
					temp.add(current);
				}
				current = current.parent;
			}
			int sx = this.startX;
			int sy = this.startY;
			for (int i = temp.size() - 1; i >= 0; i--) {
				PathTile tempTile = temp.get(i);
				
				if (tempTile.x < sx) {
					path.AddTarget(tempTile.x * tilesize, tempTile.y * tilesize, Direction.LEFT);
				} else if (tempTile.x > sx) {
					path.AddTarget(tempTile.x * tilesize, tempTile.y * tilesize, Direction.RIGHT);
				} else if (tempTile.y < sy) {
					path.AddTarget(tempTile.x * tilesize, tempTile.y * tilesize, Direction.UP);
				} else if (tempTile.y > sy) {
					path.AddTarget(tempTile.x * tilesize, tempTile.y * tilesize, Direction.DOWN);
				}
				
				sx = tempTile.x;
				sy = tempTile.y;
			}
			
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[0].length; y++) {
					map[x][y].parent = null;
					map[x][y].f = 0;
					map[x][y].g = 0;
					map[x][y].onPath = false;
				}
			}
			
			return path;
		} else {
			return null;
		}
		
	}
	
	public boolean IsFound() {
		return this.found;
	}
	
	public boolean IsFinding() {
		return this.finding;
	}
	
	public boolean IsMapLoaded() {
		if (map == null) {
			return false;
		} else {
			return true;
		}
	}
	
}
