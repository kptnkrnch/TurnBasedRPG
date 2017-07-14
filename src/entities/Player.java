package entities;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import engine.Direction;
import engine.Entity;
import engine.EntityDictionary;
import engine.EntityFactory;
import engine.World;
import gameplay.CollisionController;
import gameplay.CombatCalculator;
import graphics.AnimationController;

public class Player {
	
	public static int BASE_COOLDOWN = 1000;
	
	public static void MovePlayer(World world, Entity player, HashMap<String, Boolean> input, int fps_scaler) {
		if (input != null && player != null && player.type == EntityDictionary.PLAYER && !player.IsTalking()) {
			
			float speed = player.speed;
			int[] tile_collisions = null;
			HashMap<Long, Integer> entity_collisions = null;
			Entity temp = new Entity(player);
			
			if (input.get("KEY_LEFT") && input.get("KEY_UP")) {
				
				speed = (float) (Math.sqrt(Math.pow(speed, 2) / 2));
				
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.LEFT)) {
					temp.changed_rooms = false;
				}
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.UP)) {
					temp.changed_rooms = false;
				}
				
				temp.Move(Direction.LEFT, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				temp.Move(Direction.UP, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				if (temp.last_animation != Direction.LEFT && temp.last_animation != Direction.UP) {
					temp.last_animation = Direction.LEFT;
					if (!temp.attacking) {
						temp.last_animation_name = AnimationController.LEFT;
					}
				}
				
			} else if (input.get("KEY_LEFT") && input.get("KEY_DOWN")) {
				
				speed = (float) (Math.sqrt(Math.pow(speed, 2) / 2));
				
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.LEFT)) {
					temp.changed_rooms = false;
				}
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.DOWN)) {
					temp.changed_rooms = false;
				}
				
				temp.Move(Direction.LEFT, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				temp.Move(Direction.DOWN, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				if (temp.last_animation != Direction.LEFT && temp.last_animation != Direction.DOWN) {
					temp.last_animation = Direction.LEFT;
					if (!temp.attacking) {
						temp.last_animation_name = AnimationController.LEFT;
					}
				}
				
			} else if (input.get("KEY_RIGHT") && input.get("KEY_UP")) {

				speed = (float) (Math.sqrt(Math.pow(speed, 2) / 2));
				
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.RIGHT)) {
					temp.changed_rooms = false;
				}
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.UP)) {
					temp.changed_rooms = false;
				}
				
				temp.Move(Direction.RIGHT, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				temp.Move(Direction.UP, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				if (temp.last_animation != Direction.RIGHT && temp.last_animation != Direction.UP) {
					temp.last_animation = Direction.RIGHT;
					if (!temp.attacking) {
						temp.last_animation_name = AnimationController.RIGHT;
					}
				}
				
			} else if (input.get("KEY_RIGHT") && input.get("KEY_DOWN")) {

				speed = (float) (Math.sqrt(Math.pow(speed, 2) / 2));
				
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.RIGHT)) {
					temp.changed_rooms = false;
				}
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.DOWN)) {
					temp.changed_rooms = false;
				}
				
				temp.Move(Direction.RIGHT, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				temp.Move(Direction.DOWN, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				if (temp.last_animation != Direction.RIGHT && temp.last_animation != Direction.DOWN) {
					temp.last_animation = Direction.RIGHT;
					if (!temp.attacking) {
						temp.last_animation_name = AnimationController.RIGHT;
					}
				}
				
			} else if (input.get("KEY_LEFT")) {
				
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.LEFT)) {
					temp.changed_rooms = false;
				}
				
				temp.Move(Direction.LEFT, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				temp.last_animation = Direction.LEFT;
				if (!temp.attacking) {
					temp.last_animation_name = AnimationController.LEFT;
				}
				
			} else if (input.get("KEY_RIGHT")) {
				
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.RIGHT)) {
					temp.changed_rooms = false;
				}
				
				temp.Move(Direction.RIGHT, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				temp.last_animation = Direction.RIGHT;
				if (!temp.attacking) {
					temp.last_animation_name = AnimationController.RIGHT;
				}
				
			} else if (input.get("KEY_UP")) {
				
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.UP)) {
					temp.changed_rooms = false;
				}
				
				temp.Move(Direction.UP, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				temp.last_animation = Direction.UP;
				if (!temp.attacking) {
					temp.last_animation_name = AnimationController.UP;
				}
				
			} else if (input.get("KEY_DOWN")) {
				
				if (Direction.IsOpposite(Direction.ResolveAnimation(temp.room_change_direction), Direction.DOWN)) {
					temp.changed_rooms = false;
				}
				
				temp.Move(Direction.DOWN, speed, fps_scaler);
				SetCollisionBox(temp, temp.x, temp.y);
				if (!CollisionController.CheckEntityOutOfBounds(world, temp)) {
					tile_collisions = CollisionController.CheckTileCollision(world, temp);
					if (tile_collisions != null) {
						temp.UndoLastMove();
						SetCollisionBox(temp, temp.x, temp.y);
					}
					entity_collisions = CollisionController.CheckEntityCollision(world, temp);
					if (entity_collisions != null) {
						temp = HandleEntityCollisions(world, temp, entity_collisions);
					} else {
						temp.changed_rooms = false;
					}
				} else {
					temp.UndoLastMove();
					SetCollisionBox(temp, temp.x, temp.y);
				}
				
				temp.last_animation = Direction.DOWN;
				if (!temp.attacking) {
					temp.last_animation_name = AnimationController.DOWN;
				}
				
			} else {
				temp.last_direction = Direction.NONE;
			}
			player.Copy(temp);
		}
	}
	
	public static void SetCollisionBox(Entity player, int x, int y) {
		player.collision_box.x = x + 16;
		player.collision_box.y = y + 32;
	}
	
	public static Entity HandleEntityCollisions(World world, Entity player, HashMap<Long, Integer> collisions) {
		boolean col_roomchanger = false;
		Iterator<Entry<Long, Integer>> iterator = collisions.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, Integer> current = iterator.next();
			Entity temp = null;
			switch(current.getValue()) {
			case EntityDictionary.BULLET:
				break;
			case EntityDictionary.ROOM_CHANGER:
				col_roomchanger = true;
				if (player.changed_rooms == false) {
					temp = world.GetEntity(world.FindEntity(current.getKey()));
					RoomChanger.ChangeRoom(world, player, temp);
				}
				break;
			default:
				temp = world.GetEntity(world.FindEntity(current.getKey()));
				if (temp.c_health > 0) {
					player.UndoLastMove();
					SetCollisionBox(player, player.x, player.y);
				}
				break;
			}
		}
		if (col_roomchanger == true) {
			player.changed_rooms = true;
		} else {
			player.changed_rooms = false;
		}
		return player;
	}
	
	public static void Interact(World world, Entity player) {
		Rectangle interaction_box = null;
		
		switch(player.last_animation) {
		case Direction.LEFT:
			interaction_box = new Rectangle(player.collision_box.x - 8, player.collision_box.y, 8, 32);
			break;
		case Direction.RIGHT:
			interaction_box = new Rectangle(player.collision_box.x + player.collision_box.width, 
					player.collision_box.y, 8, 32);
			break;
		case Direction.UP:
			interaction_box = new Rectangle(player.collision_box.x, player.collision_box.y - 8, 32, 8);
			break;
		case Direction.DOWN:
			interaction_box = new Rectangle(player.collision_box.x, 
					player.collision_box.y + player.collision_box.height, 32, 8);
			break;
		default:
			interaction_box = new Rectangle(player.collision_box.x, 
					player.collision_box.y + player.collision_box.height, 32, 8);
			break;
		}
		
		for (int i = 0; i < world.entities.size(); i++) {
			Entity temp = world.entities.get(i);
			if (interaction_box.intersects(temp.collision_box)) {
				switch (temp.type) {
				case EntityDictionary.NPC:
					player.SetTalking(NPC.Speak(world, temp));
					break;
				case EntityDictionary.ENEMY:
					if (player.c_cooldown <= 0) {
						//temp.c_health -= CombatCalculator.CalculateDamage(temp, player);
						//player.c_cooldown = CombatCalculator.CalculateCooldown(temp, BASE_COOLDOWN);
					}
					break;
				}
			}
			world.entities.set(i, temp);
		}
		
	}
	
	public static void Attack(World world, Entity player) {
		/*Rectangle interaction_box = null;
		
		switch(player.last_animation) {
		case Direction.LEFT:
			interaction_box = new Rectangle(player.collision_box.x - 8, player.collision_box.y, 8, 32);
			break;
		case Direction.RIGHT:
			interaction_box = new Rectangle(player.collision_box.x + player.collision_box.width, 
					player.collision_box.y, 8, 32);
			break;
		case Direction.UP:
			interaction_box = new Rectangle(player.collision_box.x, player.collision_box.y - 8, 32, 8);
			break;
		case Direction.DOWN:
			interaction_box = new Rectangle(player.collision_box.x, 
					player.collision_box.y + player.collision_box.height, 32, 8);
			break;
		default:
			interaction_box = new Rectangle(player.collision_box.x, 
					player.collision_box.y + player.collision_box.height, 32, 8);
			break;
		}
		
		for (int i = 0; i < world.entities.size(); i++) {
			Entity temp = world.entities.get(i);
			if (interaction_box.intersects(temp.collision_box)) {
				switch (temp.type) {
				case EntityDictionary.ENEMY:
					if (player.c_cooldown <= 0) {
						temp.c_health -= CombatCalculator.CalculateDamage(player, temp);
						//player.c_cooldown = CombatCalculator.CalculateCooldown(temp, BASE_COOLDOWN);
					}
					break;
				}
			}
			world.entities.set(i, temp);
		}*/
		
		//TURNED OFF COOLDOWNS FOR TESTING HOW IT FEELS
		//if (player.c_cooldown <= 0) {
			player.c_cooldown = CombatCalculator.CalculateCooldown(player, BASE_COOLDOWN);
			player.attacking = true;
		//}
	}
	
	public static void ShootBullet(World world, Entity player) {
		switch (player.last_animation_name) {
		case AnimationController.RIGHT:
			world.AddEntity(EntityFactory.CreateBullet(world, 
					player.collision_box.x + player.collision_box.width,
					player.collision_box.y + 12, player.last_animation_name, player));
			break;
		case AnimationController.LEFT:
			world.AddEntity(EntityFactory.CreateBullet(world, 
					player.collision_box.x,
					player.collision_box.y + 12, player.last_animation_name, player));
			break;
		case AnimationController.UP:
			world.AddEntity(EntityFactory.CreateBullet(world, 
					player.collision_box.x + player.collision_box.width - 7,
					player.collision_box.y, player.last_animation_name, player));
			break;
		case AnimationController.DOWN:
			world.AddEntity(EntityFactory.CreateBullet(world, 
					player.collision_box.x + 4,
					player.collision_box.y + 16, player.last_animation_name, player));
			break;
		}
	}
}
