package engine;

public class TileFactory {
	public static int tileSize = 0;
	
	public static Tile CreateTile(int type, int x, int y, int tilesize) {
		tileSize = tilesize;
		Tile tile = null;
		
		switch(type) {
		case TileDictionary.GRASS: //grass tile
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.TREE: //tree tile
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.WATER: //water tile
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.BEACH_TOP:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.TREE_TOPLEFT: //tree tile
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.TREE_TOPRIGHT: //tree tile
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.TREE_BOTLEFT: //tree tile
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.TREE_BOTRIGHT: //tree tile
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.PALMTREE_GRASS_TOPLEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.PALMTREE_GRASS_TOPRIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.PALMTREE_GRASS_BOTLEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize / 2, tilesize, true);
			tile.collision_box.x += tilesize / 2;
			break;
		case TileDictionary.PALMTREE_GRASS_BOTRIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize / 2, tilesize, true);
			break;
		case TileDictionary.PALMTREE_SAND_TOPLEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.PALMTREE_SAND_TOPRIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.PALMTREE_SAND_BOTLEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize / 2, tilesize, true);
			tile.collision_box.x += tilesize / 2;
			break;
		case TileDictionary.PALMTREE_SAND_BOTRIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize / 2, tilesize, true);
			break;
		case TileDictionary.SAND:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_TOP:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_LEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_RIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_BOT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_BOTLEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_BOTRIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_TOPLEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_TOPRIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_CORNERBOTLEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_CORNERBOTRIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_CORNERTOPLEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBEACH_CORNERTOPRIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.BEACHTOGRASS_BOT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.BEACHTOGRASS_BOTLEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.BEACHTOGRASS_BOTRIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.BEACHTOGRASS_LEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.BEACHTOGRASS_RIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.BEACHTOGRASS_TOP:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.BEACHTOGRASS_TOPLEFT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.BEACHTOGRASS_TOPRIGHT:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize);
			break;
		case TileDictionary.SANDBUSH:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.GRASSBUSH:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.SANDROCK:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.GRASSROCK:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		case TileDictionary.SANDFLOWER:
			tile = new Tile(type, x * tilesize, y * tilesize, tilesize, tilesize, true);
			break;
		}
		
		return tile;
	}
	
}
