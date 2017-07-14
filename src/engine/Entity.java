package engine;

import entityproperties.SpawnPoint;
import graphics.AnimationController;
import graphics.FadingText;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import npc.NPCData;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pathing.Path;
import pathing.PathFinder;

public class Entity {
	public long id;
	public int x;
	public int y;
	public int width;
	public int height;
	public int type;
	public float movx;
	public float movy;
	public float speed;
	public boolean solid;
	public boolean controlled;
	public boolean moveable;
	public Rectangle collision_box;
	public Rectangle image_box;
	public NPCData npc_data;
	
	/* Dialog section */
	public ArrayList<String> dialog;
	public boolean talking;
	
	/* Movement section */
	public int last_direction;
	private float last_distance;
	
	/* Graphics section */
	public int last_animation;
	public String last_animation_name;
	public Animation left_anim;
	public Animation right_anim;
	public Animation down_anim;
	public Animation up_anim;
	public boolean animating;
	public HashMap<String, Animation> animation;
	
	/* AI section */
	public PathFinder pathFinder;
	public Path path;
	
	/* Combat section */
	public int c_max_health; //base max health
	public int c_health; //base health
	public int c_attack; //base attack
	public int c_defence; //base defence
	public int c_speed; //base speed
	public int c_critical_chance; //base critical chance
	public int c_dodge_chance; //base dodge chance
	public int c_level; 
	public int c_exp;
	public int c_exp_next;
	public float c_critical_damage; //base critical damage
	public int c_cooldown; //base cooldown
	public long c_creatorID;
	public long c_killerID;
	public FadingText last_hit;
	
	/* Status Effects*/
	public int burnDamage;
	public int burnTime;
	public int poisonDamage;
	public int poisonTime;
	public int frozenTime;
	
	public boolean attacking = false;
	public boolean hit = false;
	public int hittimer = 0;
	public Animation attack;
	
	/* Respawning System */
	public SpawnPoint spawn_point;
	
	/* Patrolling System */
	public int currentPoint;
	public ArrayList<Point> patrol_points;
	
	/* Room Changing System */
	public boolean changed_rooms;
	public String room_change_direction;
	public int target_x;
	public int target_y;
	public String target_map;
	
	public Entity(Entity temp) {
		this.id = temp.id;
		this.type = temp.type;
		this.x = temp.x;
		this.y = temp.y;
		this.width = temp.width;
		this.height = temp.height;
		this.type = temp.type;
		this.movx = temp.movx;
		this.movy = temp.movy;
		this.speed = temp.speed;
		this.solid = temp.solid;
		this.controlled = temp.controlled;
		this.moveable = temp.moveable;
		this.collision_box = new Rectangle(temp.collision_box);
		this.last_direction = temp.last_direction;
		this.last_distance = temp.last_distance;
		
		this.left_anim = temp.left_anim;
		this.right_anim = temp.right_anim;
		this.down_anim = temp.down_anim;
		this.up_anim = temp.up_anim;
		this.last_animation = temp.last_animation;
		this.animating = temp.animating;
		this.animation = temp.animation;
		this.last_animation_name = temp.last_animation_name;
		
		this.pathFinder = temp.pathFinder;
		this.path = temp.path;
		
		this.talking = temp.talking;
		this.dialog = temp.dialog;
		
		this.c_health = temp.c_health;
		this.c_max_health = temp.c_max_health;
		this.c_attack = temp.c_attack;
		this.c_defence = temp.c_defence;
		this.c_speed = temp.c_speed;
		this.c_dodge_chance = temp.c_dodge_chance;
		this.c_critical_chance = temp.c_critical_chance;
		this.c_critical_damage = temp.c_critical_damage;
		this.c_cooldown = temp.c_cooldown;
		this.c_level = temp.c_level;
		this.c_exp = temp.c_exp;
		this.c_exp_next = temp.c_exp_next;
		this.c_creatorID = temp.c_creatorID;
		this.c_killerID = temp.c_killerID;
		this.last_hit = temp.last_hit;
		
		this.burnDamage = temp.burnDamage;
		this.burnTime = temp.burnTime;
		this.poisonDamage = temp.poisonDamage;
		this.poisonTime = temp.poisonTime;
		this.frozenTime = temp.frozenTime;
		
		this.attacking = temp.attacking;
		this.attack = temp.attack;
		this.hittimer = temp.hittimer;
		
		this.spawn_point = temp.spawn_point;
		
		this.currentPoint = temp.currentPoint;
		this.patrol_points = temp.patrol_points;
		
		this.changed_rooms = temp.changed_rooms;
		this.room_change_direction = temp.room_change_direction;
		this.target_x = temp.target_x;
		this.target_y = temp.target_y;
		this.target_map = temp.target_map;
		
		this.npc_data = temp.npc_data;
	}

	public Entity(int type, int x, int y, int width, int height) {
		this.id = -1;
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.movx = x;
		this.movy = y;
		this.solid = false;
		this.controlled = false;
		this.moveable = false;
		this.speed = 0f;
		this.collision_box = new Rectangle(x + 1, y + 1, width - 2, height - 2);
		
		this.last_distance = 0;
		this.last_direction = -1;
		
		this.left_anim = null;
		this.right_anim = null;
		this.down_anim = null;
		this.up_anim = null;
		this.last_animation = Direction.NONE;
		this.animating = false;
		this.animation = null;
		this.last_animation_name = AnimationController.DOWN;
		
		this.pathFinder = new PathFinder();
		this.path = null;
		
		this.talking = false;
		this.dialog = new ArrayList<String>();
		
		this.c_max_health = 1;
		this.c_health = 1;
		this.c_attack = 0;
		this.c_speed = 0;
		this.c_defence = 0;
		this.c_cooldown = 0;
		this.c_dodge_chance = 0;
		this.c_critical_chance = 0;
		this.c_critical_damage = 0f;
		this.c_level = 1;
		this.c_exp = 0;
		this.c_exp_next = 100;
		this.c_creatorID = -1;
		this.c_killerID = -1;
		this.hittimer = 0;
		this.last_hit = null;
		
		this.burnDamage = 0;
		this.burnTime = 0;
		this.poisonDamage = 0;
		this.poisonTime = 0;
		this.frozenTime = 0;
		
		this.spawn_point = new SpawnPoint(x, y, width, height);
		
		this.currentPoint = 0;
		this.patrol_points = new ArrayList<Point>();
		
		this.changed_rooms = false;
		this.room_change_direction = AnimationController.DOWN;
		this.target_x = 0;
		this.target_y = 0;
		this.target_map = null;
		
		this.npc_data = new NPCData();
	}
	
	public boolean IsMoveable() {
		return moveable;
	}
	
	public boolean IsSolid() {
		return solid;
	}
	
	public boolean IsControlled() {
		return controlled;
	}
	
	public void Move(int direction, float speed, int fps_scaler) {
		if (this.IsMoveable()) {
			float distance = speed * fps_scaler;
			switch(direction) {
			case Direction.LEFT:
				this.movx -= distance;
				this.x = (int)Math.floor(this.movx);
				this.last_direction = direction;
				this.last_distance = distance;
				this.animating = true;
				break;
			case Direction.RIGHT:
				this.movx += distance;
				this.x = (int)Math.floor(this.movx);
				this.last_direction = direction;
				this.last_distance = distance;
				this.animating = true;
				break;
			case Direction.UP:
				this.movy -= distance;
				this.y = (int)Math.floor(this.movy);
				this.last_direction = direction;
				this.last_distance = distance;
				this.animating = true;
				break;
			case Direction.DOWN:
				this.movy += distance;
				this.y = (int)Math.floor(this.movy);
				this.last_direction = direction;
				this.last_distance = distance;
				this.animating = true;
				break;
			default:
				this.last_direction = Direction.NONE;
				this.last_distance = 0;
				this.animating = false;
				break;
			}
			this.collision_box.x = this.x + 1;
			this.collision_box.y = this.y + 1;
		}
	}
	
	public void UndoLastMove() {
		if (this.IsMoveable()) {
			int direction = Direction.Opposite(this.last_direction);
			float distance = this.last_distance;
			switch(direction) {
			case Direction.LEFT:
				this.movx -= distance;
				this.x = (int)Math.floor(this.movx);
				//this.last_direction = -1;
				this.last_distance = 0;
				break;
			case Direction.RIGHT:
				this.movx += distance;
				this.x = (int)Math.floor(this.movx);
				//this.last_direction = -1;
				this.last_distance = 0;
				break;
			case Direction.UP:
				this.movy -= distance;
				this.y = (int)Math.floor(this.movy);
				//this.last_direction = -1;
				this.last_distance = 0;
				break;
			case Direction.DOWN:
				this.movy += distance;
				this.y = (int)Math.floor(this.movy);
				//this.last_direction = -1;
				this.last_distance = 0;
				break;
			}
			this.collision_box.x = this.x + 1;
			this.collision_box.y = this.y + 1;
		}
	}
	
	public void SetPosition(int x, int y) {
		this.x = x;
		this.y = y;
		this.movx = x;
		this.movy = y;
		this.collision_box.x = this.x + 1;
		this.collision_box.y = this.y + 1;
	}
	
	public boolean Intersects(Tile tile) {
		return collision_box.intersects(tile.collision_box);
	}
	
	public boolean Intersects(Entity entity) {
		return collision_box.intersects(entity.collision_box);
	}
	
	public void Copy(Entity temp) {
		this.id = temp.id;
		this.x = temp.x;
		this.y = temp.y;
		this.width = temp.width;
		this.height = temp.height;
		this.type = temp.type;
		this.movx = temp.movx;
		this.movy = temp.movy;
		this.speed = temp.speed;
		this.solid = temp.solid;
		this.controlled = temp.controlled;
		this.moveable = temp.moveable;
		this.collision_box.x = temp.collision_box.x;
		this.collision_box.y = temp.collision_box.y;
		this.last_direction = temp.last_direction;
		this.last_distance = temp.last_distance;
		this.last_animation = temp.last_animation;
		this.last_animation_name = temp.last_animation_name;
		
		this.path = temp.path;
		this.pathFinder = temp.pathFinder;
		
		this.animation = temp.animation;
		
		this.talking = temp.talking;
		this.dialog = temp.dialog;
		
		this.c_health = temp.c_health;
		this.c_max_health = temp.c_max_health;
		this.c_attack = temp.c_attack;
		this.c_defence = temp.c_defence;
		this.c_speed = temp.c_speed;
		this.c_dodge_chance = temp.c_dodge_chance;
		this.c_critical_chance = temp.c_critical_chance;
		this.c_critical_damage = temp.c_critical_damage;
		this.c_cooldown = temp.c_cooldown;
		this.c_level = temp.c_level;
		this.c_exp = temp.c_exp;
		this.c_exp_next = temp.c_exp_next;
		this.c_creatorID = temp.c_creatorID;
		this.c_killerID = temp.c_killerID;
		
		this.burnDamage = temp.burnDamage;
		this.burnTime = temp.burnTime;
		this.poisonDamage = temp.poisonDamage;
		this.poisonTime = temp.poisonTime;
		this.frozenTime = temp.frozenTime;
		
		this.attacking = temp.attacking;
		this.attack = temp.attack;
		this.hittimer = temp.hittimer;
		
		this.spawn_point = temp.spawn_point;
		
		this.currentPoint = temp.currentPoint;
		this.patrol_points = temp.patrol_points;
		
		this.changed_rooms = temp.changed_rooms;
		this.room_change_direction = temp.room_change_direction;
		this.target_x = temp.target_x;
		this.target_y = temp.target_y;
		this.target_map = temp.target_map;
		
		this.npc_data = temp.npc_data;
	}
	
	/*public void UpdateAnimations(int fps_scaler) {
		if (left_anim != null) {
			left_anim.update(fps_scaler);
		}
		if (right_anim != null) {
			right_anim.update(fps_scaler);
		}
		if (down_anim != null) {
			down_anim.update(fps_scaler);
		}
		if (up_anim != null) {
			up_anim.update(fps_scaler);
		}
	}*/
	
	public void UpdateAnimations(int fps_scaler) {
		if (this.animation != null) {
			Iterator<Entry<String, Animation>> tempIterator = this.animation.entrySet().iterator();
			while (tempIterator.hasNext()) {
				Entry<String, Animation> current = tempIterator.next();
				Animation cur_anim = current.getValue();
				cur_anim.update(fps_scaler);
				this.animation.put(current.getKey(), cur_anim);
			}
		}
	}
	
	public boolean IsAnimating() {
		return this.animating;
	}
	
	public void SetTalking(boolean talking) {
		this.talking = talking;
	}
	
	public boolean IsTalking() {
		return this.talking;
	}
	
	public void SetHitText(String text) {
		this.last_hit = new FadingText(text, 1);
	}
	
	public void SetHitText(String text, Color color) {
		this.last_hit = new FadingText(text, color, 1);
	}
	
	public void Hit(String damage) {
		this.hittimer = 100;
		this.last_hit = new FadingText(damage, 1);
	}
	
	public void Hit(String damage, boolean critical_hit) {
		this.hittimer = 100;
		if (critical_hit) {
			damage += "!";
			this.last_hit = new FadingText(damage, Color.yellow, 1);
		} else {
			this.last_hit = new FadingText(damage, 1);
		}
	}
	
	public void UpdateHitTimer(int fps_scaler) {
		if (IsHit()) {
			this.hittimer -= fps_scaler;
		}
		if (!IsHit()) {
			this.hittimer = 0;
		}
		if (this.last_hit != null) {
			this.last_hit.Fade(fps_scaler);
		}
	}
	
	public boolean IsHit() {
		if (this.hittimer > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean HasLastHitText() {
		if (this.last_hit != null) {
			if (!this.last_hit.IsFaded()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Point GetCurrentPatrolPoint() {
		if (this.patrol_points != null && this.patrol_points.size() > 0) {
			return this.patrol_points.get(currentPoint);
		} else {
			return null;
		}
	}
	
	public void AddPatrolPoint(Point point) {
		if (this.patrol_points != null && point != null) {
			this.patrol_points.add(point);
		}
	}
	
	public void IncrementPatrolPoint() {
		if (this.patrol_points != null && this.patrol_points.size() > 0) {
			if (this.currentPoint >= this.patrol_points.size() - 1) {
				this.currentPoint = 0;
			} else {
				this.currentPoint += 1;
			}
		}
	}
	
	public void AddExp(int exp) {
		if (exp > 0) {
			this.c_exp += exp;
		}
		if (this.c_exp >= this.c_exp_next) {
			this.c_level += 1;
			this.c_attack += Math.round((1.2 * this.c_level));
			this.c_defence += Math.round((this.c_level));
			this.c_speed += Math.round((0.5 * this.c_level));
			this.c_exp -= this.c_exp_next;
			this.c_exp_next += this.c_exp_next;
		}
	}
}
