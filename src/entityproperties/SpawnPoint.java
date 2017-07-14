package entityproperties;

import java.awt.Rectangle;

public class SpawnPoint {
	public int x;
	public int y;
	public int width;
	public int height;
	public Rectangle spawn_box;
	public int spawn_timer;
	public boolean dead;
	
	public SpawnPoint(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.spawn_box = new Rectangle(x, y, width, height);
		this.spawn_timer = 0;
		this.dead = false;
	}
	
	public void UpdateSpawnTimer(int fps_scaler) {
		if (this.dead) {
			if (spawn_timer > 0) {
				spawn_timer -= fps_scaler;
			} else {
				spawn_timer = 0;
				this.dead = false;
			}
		}
	}
	
	public boolean IsSpawned() {
		if (spawn_timer <= 0 && !dead) {
			return true;
		} else {
			return false;
		}
	}
	
	public void SetSpawnTimer(int time) {
		this.spawn_timer = time;
		this.dead = true;
	}
}
