package engine;

import java.awt.Point;
import java.io.File;
import java.util.Scanner;

public class MapLoader {
	
	private static Scanner scan;
	
	/**
	 * Loads a map into a world object from a file.
	 * @param map_location
	 * @return true if map load is successful
	 */
	public static boolean LoadMap(World world, String map_location) {
		File fmap = new File(map_location);
		try {
			scan = new Scanner(fmap);
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner lineScanner = new Scanner(line);
				int width = 0, height = 0, tilesize = 0;
				if (lineScanner.hasNextInt()) {
					width = lineScanner.nextInt();
				} else {
					System.err.println("Error on line: 1 - Missing Width Parameter.");
					lineScanner.close();
					throw new Exception();
				}
				if (lineScanner.hasNextInt()) {
					height = lineScanner.nextInt();
				} else {
					System.err.println("Error on line: 1 - Missing Height Parameter.");
					lineScanner.close();
					throw new Exception();
				}
				if (lineScanner.hasNextInt()) {
					tilesize = lineScanner.nextInt();
				} else {
					System.err.println("Error on line: 1 - Missing Tile Size Parameter.");
					lineScanner.close();
					throw new Exception();
				}
				world.Init(width, height, tilesize);
				lineScanner.close();
			}
			for (int y = 0; y < world.height; y++) {
				if (scan.hasNextLine()) {
					String line = scan.nextLine();
					Scanner lineScanner = new Scanner(line);
					for (int x = 0; x < world.width; x++) {
						if (lineScanner.hasNextInt()) {
							int type = lineScanner.nextInt();
							world.tile[x][y] = TileFactory.CreateTile(type, x, y, world.tilesize);
						} else {
							System.err.println("Error! There seems to be too few columns of tiles.");
							lineScanner.close();
							throw new Exception();
						}
					}
					lineScanner.close();
				} else {
					System.err.println("Error! There seems to be too few rows of tiles.");
					throw new Exception();
				}
			}
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				if (line.trim().length() > 0) {
					Scanner lineScanner = new Scanner(line);
					String type_name = null;
					int typeID = -1;
					int x = -1;
					int y = -1;
					int width = -1;
					int height = -1;
					int targetX = -1;
					int targetY = -1;
					String targetMap = null;
					int isDialog = 0;
					int isPatrolPath = 0;
					
					if (lineScanner.hasNextInt()) {
						typeID = lineScanner.nextInt();
					}
					
					if (lineScanner.hasNextInt()) {
						x = lineScanner.nextInt();
					}
					
					if (lineScanner.hasNextInt()) {
						y = lineScanner.nextInt();
					}
					
					if (lineScanner.hasNextInt()) {
						width = lineScanner.nextInt();
					}
					
					if (lineScanner.hasNextInt()) {
						height = lineScanner.nextInt();
					}
					
					if (typeID != EntityDictionary.ROOM_CHANGER) {
					
						if (lineScanner.hasNextInt()) {
							isDialog = lineScanner.nextInt();
						}
						
						if (lineScanner.hasNextInt()) {
							isPatrolPath = lineScanner.nextInt();
						}
						
						if (typeID != EntityDictionary.NONE
								&& x != -1 && y != -1 && width != -1 && height != -1) {
							Entity entity = EntityFactory.CreateEntity(world, typeID, x, y, width, height);
							
							if (isDialog != 0) {
								while (scan.hasNextLine()) {
									line = scan.nextLine();
									
									if (line.trim().length() == 0) {
										break;
									} else if (line.contains("[")) {
									} else if (line.contains("]")) {
										break;
									} else {
										entity.dialog.add(line);
									}
								}
							}
							
							if (isPatrolPath != 0) {
								while (scan.hasNextLine()) {
									line = scan.nextLine();
									lineScanner = new Scanner(line);
									
									if (line.trim().length() == 0) {
										break;
									} else if (line.contains("{")) {
									} else if (line.contains("}")) {
										break;
									} else {
										int patrolX = -1;
										int patrolY = -1;
										
										if (lineScanner.hasNextInt()) {
											patrolX = lineScanner.nextInt();
										}
										
										if (lineScanner.hasNextInt()) {
											patrolY = lineScanner.nextInt();
										}
										
										if (patrolX >= 0 && patrolY >= 0 && patrolX < world.width && patrolY < world.height) {
											Point point = new Point(patrolX * world.tilesize, patrolY * world.tilesize);
											entity.AddPatrolPoint(point);
										}
									}
								}
							}
							
							world.AddEntity(entity);
						}
					} else {
						if (typeID != EntityDictionary.NONE
								&& x != -1 && y != -1 && width != -1 && height != -1) {
							Entity entity = EntityFactory.CreateEntity(world, typeID, x, y, width, height);
							
							if (lineScanner.hasNextInt()) {
								targetX = lineScanner.nextInt();
							}
							
							if (lineScanner.hasNextInt()) {
								targetY = lineScanner.nextInt();
							}
							
							if (lineScanner.hasNext()) {
								targetMap = lineScanner.next();
							}
							
							if (targetX != -1 && targetY != -1 && targetMap != null) {
								entity.target_x = targetX;
								entity.target_y = targetY;
								entity.target_map = targetMap;
								world.AddEntity(entity);
							}
						}
					}
					
					lineScanner.close();
				}
			}
			scan.close();
		} catch (Exception e) {
			System.err.println("Failed to load map.");
			world.width = 0;
			world.tilesize = 0;
			world.height = 0;
			world.tile = null;
			world.entities = null;
			scan.close();
			return false;
		}
		return true;
	}
}
