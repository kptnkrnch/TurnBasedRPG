package engine;

import items.ItemHandler;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Player;
import graphics.AnimationController;

public class EntityFactory {
	
	public static Entity CreateEntity(World world, int type, int x, int y, int width, int height) {
		switch (type) {
		case EntityDictionary.CAMERA:
			return CreateCamera(x, y);
		case EntityDictionary.PLAYER:
			return CreatePlayer(world, x, y, width, height);
		case EntityDictionary.NPC:
			return CreateNPC(world, x, y, width, height);
		case EntityDictionary.ENEMY:
			return CreateEnemy(world, x, y, width, height);
		case EntityDictionary.ROOM_CHANGER:
			return CreateRoomChanger(world, x, y, width, height);
		}
		
		return null;
	}
	
	private static Entity CreatePlayer(World world, int x, int y, int width, int height) {
		Entity e = new Entity(EntityDictionary.PLAYER, x, y, width, height);
		e.speed = 0.2f;
		e.controlled = true;
		e.solid = true;
		e.moveable = true;
		
		/* COMBAT SECTION */
		e.c_max_health = 100;
		e.c_health = 100;
		e.c_attack = 10;
		e.c_defence = 5;
		e.c_speed = 50;
		e.c_critical_chance = 20;
		e.c_critical_damage = 0.50f;
		e.c_dodge_chance = 10;
		
		String src = world.entity_dictionary.GetAnimationSource(EntityDictionary.PLAYER);
		e.animation = world.entity_dictionary.LoadAnimations(src);
		
		Player.SetCollisionBox(e, e.x, e.y);
		
		return e;
	}
	
	private static Entity CreateCamera(int x, int y) {
		Entity e = new Entity(EntityDictionary.CAMERA, x, y, 32, 32);
		e.speed = 0.2f;
		e.controlled = false;
		e.solid = false;
		e.moveable = false;
		return e;
	}
	
	private static Entity CreateNPC(World world, int x, int y, int width, int height) {
		Entity e = new Entity(EntityDictionary.NPC, x, y, width, height);
		e.speed = 0f;
		e.controlled = false;
		e.solid = true;
		e.moveable = false;
		
		String src = world.entity_dictionary.GetAnimationSource(EntityDictionary.NPC);
		e.animation = world.entity_dictionary.LoadAnimations(src);
		
		return e;
	}
	
	private static Entity CreateEnemy(World world, int x, int y, int width, int height) {
		Entity e = new Entity(EntityDictionary.ENEMY, x, y, width, height);
		e.speed = 0.1f;
		e.controlled = false;
		e.solid = true;
		e.moveable = true;
		
		/* COMBAT SECTION */
		e.c_max_health = 50;
		e.c_health = 50;
		e.c_attack = 10;
		e.c_defence = 3;
		e.c_speed = 5;
		e.c_critical_chance = 5;
		e.c_critical_damage = 0.20f;
		e.c_dodge_chance = 10;
		
		String src = world.entity_dictionary.GetAnimationSource(EntityDictionary.ENEMY);
		e.animation = world.entity_dictionary.LoadAnimations(src);
		
		return e;
	}
	
	public static Entity CreateDialog(World world, int x, int y) {
		Entity e = new Entity(EntityDictionary.DIALOG_BOX, x, y, 240, 160);
		e.speed = 0f;
		e.controlled = false;
		e.solid = false;
		e.moveable = false;
		
		String src = world.entity_dictionary.GetAnimationSource(EntityDictionary.DIALOG_BOX);
		e.animation = world.entity_dictionary.LoadAnimations(src);
		
		return e;
	}
	
	public static Entity CreateBullet(World world, int x, int y, String animation, Entity creator) {
		Entity e = new Entity(EntityDictionary.BULLET, x, y, 6, 6);
		e.speed = 0.4f;
		e.controlled = false;
		e.solid = true;
		e.moveable = true;
		e.last_animation_name = animation;
		e.c_attack = creator.c_attack;
		e.c_critical_chance = creator.c_critical_chance;
		e.c_critical_damage = creator.c_critical_damage;
		e.c_creatorID = creator.id;
		
		switch(animation) {
		case AnimationController.LEFT:
			e.last_direction = Direction.LEFT;
			break;
		case AnimationController.RIGHT:
			e.last_direction = Direction.RIGHT;
			break;
		case AnimationController.UP:
			e.last_direction = Direction.UP;
			break;
		case AnimationController.DOWN:
			e.last_direction = Direction.DOWN;
			break;
		}
		
		
		String src = world.entity_dictionary.GetAnimationSource(EntityDictionary.BULLET);
		e.animation = world.entity_dictionary.LoadAnimations(src);
		
		return e;
	}
	
	private static Entity CreateRoomChanger(World world, int x, int y, int width, int height) {
		Entity e = new Entity(EntityDictionary.ROOM_CHANGER, x, y, width, height);
		e.speed = 0f;
		e.controlled = false;
		e.solid = true;
		e.moveable = false;
		return e;
	}
	
}
