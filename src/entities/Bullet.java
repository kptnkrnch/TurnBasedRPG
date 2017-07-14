package entities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.newdawn.slick.Color;

import engine.Entity;
import engine.EntityDictionary;
import engine.TileDictionary;
import engine.World;
import gameplay.CollisionController;
import gameplay.CombatCalculator;

public class Bullet {
	
	public static void Move(World world, Entity bullet, int fps_scaler) {
		int[] tile_collisions = null;
		HashMap<Long, Integer> entity_collisions = null;
		
		bullet.Move(bullet.last_direction, bullet.speed, fps_scaler);
		
		if (CollisionController.CheckEntityOutOfBounds(world, bullet)) {
			int bulletIndex = world.FindEntity(bullet.id);
			world.RemoveEntity(bulletIndex);
		}
		
		tile_collisions = CollisionController.CheckTileCollision(world, bullet);
		if (tile_collisions != null) {
			HandleTileCollisions(world, bullet, tile_collisions);
		}
		
		entity_collisions = CollisionController.CheckEntityCollision(world, bullet);
		if (entity_collisions != null) {
			HandleEntityCollisions(world, bullet, entity_collisions);
		}
	}
	
	public static Entity HandleTileCollisions(World world, Entity bullet, int[] collisions) {
		for (int i = 0; i < collisions.length; i++) {
			switch(collisions[i]) {
			case TileDictionary.NONE: //needed because it returns a fixed size array
				break;
			case TileDictionary.WATER:
				break;
			default:
				int bulletIndex = world.FindEntity(bullet.id);
				world.RemoveEntity(bulletIndex);
				break;
			}
		}
		return bullet;
	}
	
	public static Entity HandleEntityCollisions(World world, Entity bullet, HashMap<Long, Integer> collisions) {
		int bulletIndex = -1;
		int enemyIndex = -1;
		int damage = 0;
		Iterator<Entry<Long, Integer>> iterator = collisions.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, Integer> current = iterator.next();
			switch(current.getValue()) {
			case EntityDictionary.PLAYER:
				break;
			case EntityDictionary.ENEMY:
				enemyIndex = world.FindEntity(current.getKey());
				Entity enemy = world.GetEntity(enemyIndex);
				if (enemy.c_health > 0) {
					damage = CombatCalculator.CalculateDamage(bullet, enemy);
					
					if (damage > 0) {
						String damage_text = "" + damage;
						enemy.Hit(damage_text, CombatCalculator.IsCriticalHit());
					} else {
						enemy.SetHitText("0!", Color.red);
					}
					enemy.c_health -= damage;
					if (enemy.c_health <= 0) {
						enemy.c_killerID = bullet.c_creatorID;
						String xp = "+" + Enemy.EXP_WORTH + "xp";
						enemy.SetHitText(xp, Color.yellow);
					}
					world.SetEntity(enemyIndex, enemy);
					
					bulletIndex = world.FindEntity(bullet.id);
					world.RemoveEntity(bulletIndex);
				}
				break;
			default:
				bulletIndex = world.FindEntity(bullet.id);
				world.RemoveEntity(bulletIndex);
				break;
			}
		}
		return bullet;
	}
}
