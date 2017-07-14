package entities;

import java.util.ArrayList;

import engine.Entity;
import engine.EntityFactory;
import engine.Main;
import engine.MapLoader;
import engine.World;
import exceptions.CameraNotFoundException;
import graphics.GraphicsController;

public class RoomChanger {
	
	public static void ChangeRoom(World world, Entity player, Entity roomChanger) {
		player.x = roomChanger.target_x;
		player.y = roomChanger.target_y - 32;
		player.movx = roomChanger.target_x;
		player.movy = roomChanger.target_y - 32;
		player.collision_box.x = roomChanger.target_x;
		player.collision_box.y = roomChanger.target_y - 32;
		player.changed_rooms = true;
		player.room_change_direction = player.last_animation_name;
		
		try {
			Entity newcamera = new Entity(world.GetEntity(world.FindCamera()));
			Entity newplayer = new Entity(player);
			
			Main.world.entities = new ArrayList<Entity>();
			MapLoader.LoadMap(world, roomChanger.target_map);
			
			int index = world.AddEntity(newplayer);
			long id = world.GetEntity(index).id;
			newcamera.SetPosition(roomChanger.target_x, roomChanger.target_y);
			world.AddEntity(newcamera);
			Camera.Follow(id);
			GraphicsController.room_changed = true;
		} catch (CameraNotFoundException e) {
		}
	}
	
}
