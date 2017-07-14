package engine;

import java.util.ArrayList;
import java.util.LinkedList;

import exceptions.CameraNotFoundException;
import exceptions.PlayerNotFoundException;

public class World {
	public int currentLocationID;
	public int width;
	public int height;
	public int tilesize;
	public boolean init_anim_synchronizer;
	public Tile[][] tile;
	public ArrayList<Entity> entities;
	public ArrayList<Entity> dead_entities;
	public ArrayList<Menu> menus;
	private long currentID;
	public TileDictionary tile_dictionary;
	public EntityDictionary entity_dictionary;
	
	public World() {
		this.tile_dictionary = new TileDictionary();
		this.entity_dictionary = new EntityDictionary();
		this.menus = new ArrayList<Menu>();
		currentID = 0;
		this.currentLocationID = 0;
		this.width = 0;
		this.height = 0;
		this.tilesize = 0;
		this.entities = new ArrayList<Entity>();
		this.dead_entities = new ArrayList<Entity>();
	}
	
	public void Init(int width, int height, int tilesize) {
		this.width = width;
		this.height = height;
		this.tilesize = tilesize;
		this.tile = new Tile[width][height];
		//this.entities = new ArrayList<Entity>();
		this.dead_entities = new ArrayList<Entity>();
		this.currentID = this.entities.size();
		this.init_anim_synchronizer = false;
		this.currentLocationID = 0;
	}
	
	public boolean LoadTileDictionary(String dictionary_location) {
		return this.tile_dictionary.LoadDictionary(dictionary_location);
	}
	
	public boolean LoadEntityDictionary(String dictionary_location) {
		return this.entity_dictionary.LoadDictionary(dictionary_location);
	}
	
	public Tile GetTile(int x, int y) {
		return this.tile[x][y];
	}
	
	public int GetEntityCount() {
		return this.entities.size();
	}
	
	public int GetDeadEntityCount() {
		return this.dead_entities.size();
	}
	
	public Entity GetEntity(int index) {
		if (index >= 0 && index < this.entities.size()) {
			return this.entities.get(index);
		} else {
			return null;
		}
	}
	
	public Entity GetDeadEntity(int index) {
		if (index >= 0 && index < this.dead_entities.size()) {
			return this.dead_entities.get(index);
		} else {
			return null;
		}
	}
	
	public void SetEntity(int index, Entity entity) {
		if (this.entities != null && index >= 0 && index < entities.size()) {
			this.entities.set(index, entity);
		}
	}
	
	public int AddEntity(Entity entity) {
		entity.id = currentID;
		currentID += 1;
		this.entities.add(entity);
		return entities.size() - 1;
	}
	
	public void AddDeadEntity(Entity entity) {
		this.dead_entities.add(entity);
	}
	
	public void RemoveEntity(int index) {
		if (index >= 0 && index < this.entities.size()) {
			this.entities.remove(index);
		}
	}
	
	public void RemoveDeadEntity(int index) {
		if (index >= 0 && index < this.dead_entities.size()) {
			this.dead_entities.remove(index);
		}
	}
	
	public int FindPlayer() throws PlayerNotFoundException {
		boolean found = false;
		int index = -1;
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).type == EntityDictionary.PLAYER) {
				found = true;
				index = i;
				break;
			}
		}
		
		if (found) {
			return index;
		} else {
			throw new PlayerNotFoundException();
		}
	}
	
	public int FindCamera() throws CameraNotFoundException {
		boolean found = false;
		int index = -1;
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).type == EntityDictionary.CAMERA) {
				found = true;
				index = i;
				break;
			}
		}
		
		if (found) {
			return index;
		} else {
			throw new CameraNotFoundException();
		}
	}
	
	public void UpdateEntityAnimations(int fps_scaler) {
		for (int i = 0; i < entities.size(); i++) {
			Entity temp = entities.get(i);
			temp.UpdateAnimations(fps_scaler);
			entities.set(i, temp);
		}
	}
	
	public void UpdateEntityHitTimers(int fps_scaler) {
		for (int i = 0; i < entities.size(); i++) {
			Entity temp = entities.get(i);
			temp.UpdateHitTimer(fps_scaler);
			entities.set(i, temp);
		}
	}
	
	public void AddMenu(Menu menu) {
		this.menus.add(menu);
	}
	
	public int GetMenuCount() {
		return this.menus.size();
	}
	
	public Menu GetMenu(int index) {
		if (index >= 0 && index < menus.size()) {
			return menus.get(index);
		} else {
			return null;
		}
	}
	
	public void SetMenu(int index, Menu menu) {
		if (index >= 0 && index < menus.size()) {
			menus.set(index, menu);
		}
	}
	
	/* returns -1 on fail 
	 * returns menu index on success
	 */
	public int FindMenu(String name) {
		int index = -1;
		
		for (int i = 0; i < menus.size(); i++) {
			if (menus.get(i).name.contentEquals(name)) {
				return i;
			}
		}
		
		return index;
	}
	
	/* returns -1 on fail 
	 * returns entity index on success
	 * */
	public int FindEntity(long entityID) {		
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).id == entityID) {
				return i;
			}
		}
		
		return -1;
	}
}
