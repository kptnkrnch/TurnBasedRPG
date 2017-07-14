package gameplay;

import java.awt.Rectangle;
import java.util.HashMap;

import engine.Entity;
import engine.EntityDictionary;
import engine.World;

public class CollisionController {

	public static int[] CheckTileCollision(World world, Entity temp_entity) {
		int[] collision_list = new int[9];
		int collision_count = 0;
		
		for (int i = 0; i < 9; i++) {
			collision_list[i] = -1;
		}
		
		int xpos = (temp_entity.x) / world.tilesize;
		int ypos = (temp_entity.y) / world.tilesize;
		if (xpos < 0) {
			xpos = 0;
		}
		if (ypos < 0) {
			ypos = 0;
		}
		for (int x = xpos; x < world.width && x < xpos + 3; x++) {
			for (int y = ypos; y < world.height && y < ypos + 3; y++) {
				if (world.GetTile(x, y).IsSolid() && temp_entity.Intersects(world.GetTile(x, y))) {
					collision_list[collision_count++] = world.GetTile(x, y).type;
				}
			}
		}
		if (collision_count > 0) {
			return collision_list;
		} else {
			return null;
		}
	}
	
	/* HashMap return value is EntityID (key) and EntityType (value) */
	public static HashMap<Long, Integer> CheckEntityCollision(World world, Entity temp_entity) {
		HashMap<Long, Integer> collision_list = new HashMap<Long, Integer>();
		int collision_count = 0;
		
		for (int i = 0; i < world.entities.size(); i++) {
			Entity temp = world.GetEntity(i);
			if (temp.IsSolid() && temp.id != temp_entity.id && temp.type != EntityDictionary.CAMERA) {
				if (temp_entity.Intersects(temp)) {
					collision_list.put(temp.id, temp.type);
					collision_count++;
				}
			}
		}
		
		if (collision_count > 0) {
			return collision_list;
		} else {
			return null;
		}
	}
	
	public static boolean CheckEntityOutOfBounds(World world, Entity temp_entity) {
		Rectangle world_box = new Rectangle(0, 0, world.width * world.tilesize, world.height * world.tilesize);
		boolean outofbounds = !world_box.contains(temp_entity.collision_box);
		return outofbounds;
	}
	
}
