package pathing;

import java.awt.Point;
import java.util.ArrayList;

import engine.Direction;
import engine.Entity;

public class Path {
	
	public int id;
	public ArrayList<Integer> targetX;
	public ArrayList<Integer> targetY;
	public ArrayList<Integer> direction;
	public int currentTarget;
	public int targetCount;
	public boolean complete;
	
	public Path() {
		id = -1;
		targetX = new ArrayList<Integer>();
		targetY = new ArrayList<Integer>();
		direction = new ArrayList<Integer>();
		currentTarget = 0;
		targetCount = 0;
		complete = false;
	}
	
	public int GetNextDirection() {
		if (this.direction.size() > 0 && this.currentTarget < this.direction.size()) {
			return direction.get(currentTarget);
		} else {
			return Direction.NONE;
		}
	}
	
	public void AddTarget(int x, int y, int direction) {
		this.targetX.add(x);
		this.targetY.add(y);
		this.direction.add(direction);
		this.targetCount += 1;
	}
	
	public boolean AtCurrentTarget(Entity entity) {
		if (this.direction.size() > 0 && this.targetX.size() > 0 && this.targetY.size() > 0
				&& this.currentTarget < this.direction.size() && this.currentTarget < this.targetX.size()
				&& this.currentTarget < this.targetY.size()) {
			int direction = this.direction.get(currentTarget);
			int curTargetX = this.targetX.get(currentTarget);
			int curTargetY = this.targetY.get(currentTarget);
			
			switch(direction) {
			case Direction.LEFT:
				if (entity.collision_box.x <= curTargetX) {
					return true;
				}
				break;
			case Direction.RIGHT:
				if (entity.collision_box.x >= curTargetX) {
					return true;
				}
				break;
			case Direction.UP:
				if (entity.collision_box.y <= curTargetY) {
					return true;
				}
				break;
			case Direction.DOWN:
				if (entity.collision_box.y >= curTargetY) {
					return true;
				}
				break;
			}
		}
		return false;
	}
	
	public boolean Update(Entity entity) {
		if (this.direction.size() > 0 && this.targetX.size() > 0 && this.targetY.size() > 0
				&& this.currentTarget < this.direction.size() && this.currentTarget < this.targetX.size()
				&& this.currentTarget < this.targetY.size()) {
			int direction = this.direction.get(currentTarget);
			int curTargetX = this.targetX.get(currentTarget);
			int curTargetY = this.targetY.get(currentTarget);
			
			switch(direction) {
			case Direction.LEFT:
				if (entity.collision_box.x <= curTargetX) {
					currentTarget++;
					//entity.SetPosition(curTargetX, curTargetY);
					if (currentTarget == targetCount) {
						complete = true;
					}
					return true;
				}
				break;
			case Direction.RIGHT:
				if (entity.collision_box.x >= curTargetX) {
					currentTarget++;
					//entity.SetPosition(curTargetX, curTargetY);
					if (currentTarget == targetCount) {
						complete = true;
					}
					return true;
				}
				break;
			case Direction.UP:
				if (entity.collision_box.y <= curTargetY) {
					currentTarget++;
					//entity.SetPosition(curTargetX, curTargetY);
					if (currentTarget == targetCount) {
						complete = true;
					}
					return true;
				}
				break;
			case Direction.DOWN:
				if (entity.collision_box.y >= curTargetY) {
					currentTarget++;
					//entity.SetPosition(curTargetX, curTargetY);
					if (currentTarget == targetCount) {
						complete = true;
					}
					return true;
				}
				break;
			}
			return false;
		} else {
			return false;
		}
	}
	
	public boolean IsComplete() {
		return this.complete;
	}
	
	public Point GetCurrentTargetCoordinates() {
		Point p = null;
		
		if (this.direction.size() > 0 && this.targetX.size() > 0 && this.targetY.size() > 0
				&& this.currentTarget < this.direction.size() && this.currentTarget < this.targetX.size()
				&& this.currentTarget < this.targetY.size()) {
			p = new Point(this.targetX.get(currentTarget), this.targetY.get(currentTarget));
		}
		
		return p;
	}
	
}
