package entities;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.newdawn.slick.Color;

import pathing.Path;
import pathing.PathFindingController;
import engine.Direction;
import engine.Entity;
import engine.EntityDictionary;
import engine.World;
import gameplay.CollisionController;
import gameplay.CombatCalculator;

public class Enemy {
	
	public static int BASE_COOLDOWN = 1000;
	public static int RESPAWN_TIME = 1000;
	public static int VIEW_RADIUS = 5;
	public static int EXP_WORTH = 15;
	
	public static void MoveEnemy(World world, Entity enemy, int targetX, int targetY, int fps_scaler) {
		if (enemy.path == null) {
			PathFindingController.HandlePathFinding(world, enemy, targetX, targetY);
			enemy.path = enemy.pathFinder.FindPath();
		}
		if (enemy.pathFinder.IsFound() && enemy.path != null) {
			if (enemy.path != null && enemy.path.IsComplete()) {
				enemy.path = null;
			}
			if (enemy.path != null) {
				if (enemy.path.Update(enemy) && enemy.path.IsComplete()) {
					enemy.IncrementPatrolPoint();
					PathFindingController.HandlePathFinding(world, enemy, targetX, targetY);
					enemy.path = enemy.pathFinder.FindPath();
				}
			}
			if (enemy.path != null && !enemy.path.IsComplete()) {
				HashMap<Long, Integer> entity_collisions = null;
				if (!enemy.path.IsComplete()) {
					int direction = enemy.path.GetNextDirection();
					Entity temp = new Entity(enemy);
					switch(direction) {
					case Direction.LEFT:
						temp.Move(direction, temp.speed, fps_scaler);
						
						entity_collisions = CollisionController.CheckEntityCollision(world, temp);
						if (entity_collisions != null) {
							temp.UndoLastMove();
							temp.x += 1;
							SetCollisionBox(temp, temp.x, temp.y);
						}
						break;
					case Direction.RIGHT:
						temp.Move(direction, temp.speed, fps_scaler);
						
						entity_collisions = CollisionController.CheckEntityCollision(world, temp);
						if (entity_collisions != null) {
							temp.UndoLastMove();
							temp.x -= 1;
							SetCollisionBox(temp, temp.x, temp.y);
						}
						break;
					case Direction.UP:
						temp.Move(direction, temp.speed, fps_scaler);
						
						entity_collisions = CollisionController.CheckEntityCollision(world, temp);
						if (entity_collisions != null) {
							temp.UndoLastMove();
							temp.y += 1;
							SetCollisionBox(temp, temp.x, temp.y);
						}
						break;
					case Direction.DOWN:
						temp.Move(direction, temp.speed, fps_scaler);
						
						entity_collisions = CollisionController.CheckEntityCollision(world, temp);
						if (entity_collisions != null) {
							temp.UndoLastMove();
							temp.y -= 1;
							SetCollisionBox(temp, temp.x, temp.y);
						}
						break;
					}
					
					if (entity_collisions != null) {
						Iterator<Entry<Long, Integer>> iterator = entity_collisions.entrySet().iterator();
						while(iterator.hasNext()) {
							Entry<Long, Integer> current = iterator.next();
							if (current.getValue() != EntityDictionary.PLAYER && current.getValue() != EntityDictionary.CAMERA) {
								PathFindingController.HandlePathFinding(world, temp, targetX, targetY);
								temp.path = temp.pathFinder.FindPath();
								break;
							}
						}
					}
					
					if (entity_collisions != null) {
						Iterator<Entry<Long, Integer>> iterator = entity_collisions.entrySet().iterator();
						while(iterator.hasNext()) {
							Entry<Long, Integer> current = iterator.next();
							switch(current.getValue()) {
							case EntityDictionary.PLAYER:
								if (temp.c_cooldown <= 0 && temp.c_health > 0) {
									for (int n = 0; n < world.entities.size(); n++) {
										Entity player = world.GetEntity(n);
										if (player.type == EntityDictionary.PLAYER) {
											int damage = CombatCalculator.CalculateDamage(temp, player);
											if (damage > 0) {
												String damage_text = "" + damage;
												player.Hit(damage_text, CombatCalculator.IsCriticalHit());
											} else {
												player.SetHitText("0!", Color.red);
											}
											player.c_health -= damage;
											temp.c_cooldown = CombatCalculator.CalculateCooldown(temp, BASE_COOLDOWN);
											break;
										}
									}
								}
								break;
							}
						}
					}
					
					enemy.Copy(temp);
				}
			}
		}
	}
	
	public static void MoveEnemy(World world, Entity enemy, int fps_scaler) {
		Entity target = null;
		for (int i = 0; i < world.entities.size(); i++) {
			Entity temp = world.GetEntity(i);
			if (temp.type == EntityDictionary.PLAYER) {
				Rectangle fov = CalculateFieldOfView(enemy);
				if (fov.intersects(temp.collision_box)) {
					target = temp;
					break;
				} else if (enemy.IsHit()) {
					target = temp;
					enemy.path = null;
					break;
				}
			}
		}
		if (enemy.path != null && enemy.path.IsComplete()) {
			enemy.path = null;
		}
		if (target != null && enemy.c_health > 0) {
			if (enemy.path == null && target != null) {
				PathFindingController.HandlePathFinding(world, enemy, target.collision_box.x, target.collision_box.y);
				enemy.path = enemy.pathFinder.FindPath();
			}
			if (enemy.pathFinder.IsFound()) {
				if (enemy.path != null && target != null) {
					if (enemy.path.Update(enemy)) {
						PathFindingController.HandlePathFinding(world, enemy, target.collision_box.x, target.collision_box.y);
						enemy.path = enemy.pathFinder.FindPath();
					}
				} else if (target != null) {
					PathFindingController.HandlePathFinding(world, enemy, target.collision_box.x, target.collision_box.y);
					enemy.path = enemy.pathFinder.FindPath();
				}
				if (enemy.path != null && !enemy.path.IsComplete()) {
					HashMap<Long, Integer> entity_collisions = null;
					if (!enemy.path.IsComplete()) {
						int direction = enemy.path.GetNextDirection();
						Entity temp = new Entity(enemy);
						switch(direction) {
						case Direction.LEFT:
							temp.Move(direction, temp.speed, fps_scaler);
							
							entity_collisions = CollisionController.CheckEntityCollision(world, temp);
							if (entity_collisions != null) {
								temp.UndoLastMove();
								temp.x += 1;
								SetCollisionBox(temp, temp.x, temp.y);
							}
							break;
						case Direction.RIGHT:
							temp.Move(direction, temp.speed, fps_scaler);
							
							entity_collisions = CollisionController.CheckEntityCollision(world, temp);
							if (entity_collisions != null) {
								temp.UndoLastMove();
								temp.x -= 1;
								SetCollisionBox(temp, temp.x, temp.y);
							}
							break;
						case Direction.UP:
							temp.Move(direction, temp.speed, fps_scaler);
							
							entity_collisions = CollisionController.CheckEntityCollision(world, temp);
							if (entity_collisions != null) {
								temp.UndoLastMove();
								temp.y += 1;
								SetCollisionBox(temp, temp.x, temp.y);
							}
							break;
						case Direction.DOWN:
							temp.Move(direction, temp.speed, fps_scaler);
							
							entity_collisions = CollisionController.CheckEntityCollision(world, temp);
							if (entity_collisions != null) {
								temp.UndoLastMove();
								temp.y -= 1;
								SetCollisionBox(temp, temp.x, temp.y);
							}
							break;
						}
						
						if (entity_collisions != null && target != null) {
							Iterator<Entry<Long, Integer>> iterator = entity_collisions.entrySet().iterator();
							while(iterator.hasNext()) {
								Entry<Long, Integer> current = iterator.next();
								if (current.getValue() != EntityDictionary.PLAYER && current.getValue() != EntityDictionary.CAMERA) {
									PathFindingController.HandlePathFinding(world, temp, target.collision_box.x, target.collision_box.y);
									temp.path = temp.pathFinder.FindPath();
									break;
								}
							}
						}
						
						if (entity_collisions != null) {
							Iterator<Entry<Long, Integer>> iterator = entity_collisions.entrySet().iterator();
							while(iterator.hasNext()) {
								Entry<Long, Integer> current = iterator.next();
								switch(current.getValue()) {
								case EntityDictionary.PLAYER:
									if (temp.c_cooldown <= 0) {
										for (int n = 0; n < world.entities.size(); n++) {
											Entity player = world.GetEntity(n);
											if (player.type == EntityDictionary.PLAYER) {
												int damage = CombatCalculator.CalculateDamage(temp, player);
												if (damage > 0) {
													String damage_text = "" + damage;
													player.Hit(damage_text, CombatCalculator.IsCriticalHit());
												} else {
													player.SetHitText("0!", Color.red);
												}
												player.c_health -= damage;
												temp.c_cooldown = CombatCalculator.CalculateCooldown(temp, BASE_COOLDOWN);
												break;
											}
										}
									}
									break;
								}
							}
						}
						
						enemy.Copy(temp);
					}
				}
			}
		} else if (enemy.c_health > 0) {
			MoveEnemy(world, enemy, enemy.GetCurrentPatrolPoint().x, enemy.GetCurrentPatrolPoint().y, fps_scaler);
		}
	}
	
	/*public static Rectangle CalculateFieldOfView(Entity enemy) {
		Rectangle fov = null;
		
		switch (enemy.last_animation) {
		case Direction.LEFT:
			fov = new Rectangle(enemy.collision_box.x - 32 * 4,
					enemy.collision_box.y - 64,
					32 * 4,
					32 * 5);
			break;
		case Direction.RIGHT:
			fov = new Rectangle(enemy.collision_box.x + enemy.collision_box.width,
					enemy.collision_box.y - 64,
					32 * 4,
					32 * 5);
			break;
		case Direction.UP:
			fov = new Rectangle(enemy.collision_box.x - 64,
					enemy.collision_box.y + enemy.collision_box.height - 32 * 5,
					32 * 5,
					32 * 5);
			break;
		case Direction.DOWN:
			fov = new Rectangle(enemy.collision_box.x - 64,
					enemy.collision_box.y,
					32 * 5,
					32 * 5);
			break;
		default:
			fov = new Rectangle(enemy.collision_box.x - 64,
					enemy.collision_box.y - 64,
					32 * 5,
					32 * 7);
			break;
		}
		
		return fov;
	}*/
	
	public static Rectangle CalculateFieldOfView(Entity enemy) {
		Rectangle fov = null;
		
		int x = enemy.collision_box.x - enemy.collision_box.width * VIEW_RADIUS;
		int y = enemy.collision_box.y - enemy.collision_box.height * VIEW_RADIUS;
		int width = enemy.collision_box.width * VIEW_RADIUS * 2;
		int height = enemy.collision_box.height * VIEW_RADIUS * 2;
		
		fov = new Rectangle(x, y, width, height);
		
		return fov;
	}
	
	public static void SetCollisionBox(Entity enemy, int x, int y) {
		enemy.collision_box.x = x + 1;
		enemy.collision_box.y = y + 1;
	}
	
}
