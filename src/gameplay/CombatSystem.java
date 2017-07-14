package gameplay;

import pathing.PathFinder;
import engine.Entity;
import engine.EntityDictionary;
import engine.World;
import entities.Enemy;

public class CombatSystem {
	
	public static void UpdateCooldowns(World world, int fps_scaler) {
		for (int i = 0; i < world.GetEntityCount(); i++) {
			Entity temp = world.GetEntity(i);
			
			if (temp.c_cooldown > 0) {
				temp.c_cooldown -= fps_scaler;
			}
			
			world.SetEntity(i, temp);
		}
	}
	
	public static void CleanupDeadEntities(World world) {
		for (int i = 0; i < world.GetEntityCount(); i++) {
			Entity temp = world.GetEntity(i);
			int exp_for_index = -1;
			Entity exp_for_entity = null;
			
			if (temp.c_health <= 0 && temp.last_hit.IsFaded()) {
				int time = 0;
				switch(temp.type) {
				case EntityDictionary.ENEMY:
					time = Enemy.RESPAWN_TIME;
					exp_for_index = world.FindEntity(temp.c_killerID);
					exp_for_entity = world.GetEntity(exp_for_index);
					exp_for_entity.AddExp(Enemy.EXP_WORTH);
					world.SetEntity(exp_for_index, exp_for_entity);
					break;
				}
				temp.path = null;
				temp.last_hit = null;
				temp.spawn_point.SetSpawnTimer(time);
				world.AddDeadEntity(temp);
				world.RemoveEntity(i);
			}
		}
	}
	
	public static void UpdateSpawnTimers(World world, int fps_scaler) {
		for (int i = 0; i < world.GetDeadEntityCount(); i++) {
			Entity temp = world.GetDeadEntity(i);
			temp.spawn_point.UpdateSpawnTimer(fps_scaler);
			if (temp.spawn_point.IsSpawned()) {
				boolean canSpawn = true;
				for (int n = 0; n < world.GetEntityCount(); n++) {
					Entity check = world.GetEntity(n);
					if (check != null) {
						if (check.collision_box.intersects(temp.spawn_point.spawn_box)) {
							canSpawn = false;
							break;
						}
					}
				}
				if (canSpawn) {
					temp.c_health = temp.c_max_health;
					temp.x = temp.spawn_point.x;
					temp.y = temp.spawn_point.y;
					temp.collision_box.x = temp.x;
					temp.collision_box.y = temp.y;
					temp.movx = temp.x;
					temp.movy = temp.y;
					temp.hittimer = 0;
					temp.pathFinder = new PathFinder();
					
					switch(temp.type) {
					case EntityDictionary.ENEMY:
						Enemy.SetCollisionBox(temp, temp.x, temp.y);
						break;
					}
					
					world.AddEntity(temp);
					world.RemoveDeadEntity(i);
				}
			}
		}
	}
}
