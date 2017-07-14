package pathing;

import engine.Entity;
import engine.World;

public class PathFindingController {
	
	public static void HandlePathFinding(World world, Entity ai, int targetX, int targetY) {
		if (!ai.pathFinder.IsFinding()) {
			if (!ai.pathFinder.IsMapLoaded()) {
				ai.pathFinder.LoadMap(world.tile);
			}
			ai.pathFinder.SetStart(ai.x, ai.y);
			ai.pathFinder.SetTarget(targetX, targetY);
			ai.pathFinder.run();
		}
	}
	
}
