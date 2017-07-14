package engine;

import graphics.AnimationController;

import java.io.File;
import java.util.Scanner;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileDictionary {
	
	public static final int NONE = -1;
	public static final int GRASS = 1;
	public static final int TREE = 2;
	public static final int WATER = 3;
	public static final int BEACH_TOP = 4;
	public static final int TREE_TOPLEFT = 5;
	public static final int TREE_TOPRIGHT = 6;
	public static final int TREE_BOTLEFT = 7;
	public static final int TREE_BOTRIGHT = 8;
	public static final int PALMTREE_GRASS_TOPLEFT = 9;
	public static final int PALMTREE_GRASS_TOPRIGHT = 10;
	public static final int PALMTREE_GRASS_BOTLEFT = 11;
	public static final int PALMTREE_GRASS_BOTRIGHT = 12;
	public static final int PALMTREE_SAND_TOPLEFT = 13;
	public static final int PALMTREE_SAND_TOPRIGHT = 14;
	public static final int PALMTREE_SAND_BOTLEFT = 15;
	public static final int PALMTREE_SAND_BOTRIGHT = 16;
	public static final int SAND = 17;
	public static final int SANDBEACH_TOP = 18;
	public static final int SANDBEACH_LEFT = 19;
	public static final int SANDBEACH_RIGHT = 20;
	public static final int SANDBEACH_BOT = 21;
	public static final int SANDBEACH_BOTLEFT = 22;
	public static final int SANDBEACH_BOTRIGHT = 23;
	public static final int SANDBEACH_TOPLEFT = 24;
	public static final int SANDBEACH_TOPRIGHT = 25;
	public static final int SANDBEACH_CORNERBOTLEFT = 26;
	public static final int SANDBEACH_CORNERBOTRIGHT = 27;
	public static final int SANDBEACH_CORNERTOPLEFT = 28;
	public static final int SANDBEACH_CORNERTOPRIGHT = 29;
	public static final int BEACHTOGRASS_TOP = 30;
	public static final int BEACHTOGRASS_BOT = 31;
	public static final int BEACHTOGRASS_LEFT = 32;
	public static final int BEACHTOGRASS_RIGHT = 33;
	public static final int BEACHTOGRASS_BOTLEFT = 34;
	public static final int BEACHTOGRASS_BOTRIGHT = 35;
	public static final int BEACHTOGRASS_TOPLEFT = 36;
	public static final int BEACHTOGRASS_TOPRIGHT = 37;
	public static final int SANDBUSH = 38;
	public static final int GRASSBUSH = 39;
	public static final int SANDROCK = 40;
	public static final int GRASSROCK = 41;
	public static final int SANDFLOWER = 42;
	
	public Animation[] tiles;
	public int[] types;
	
	private int entry_count;
	private Scanner scan;
	
	public TileDictionary() {
		
	}
	
	/**
	 * Loads a new tile image dictionary.
	 * @param dictionary_location - the location of the tile dictionary file.
	 */
	public TileDictionary(String dictionary_location) {
		entry_count = 0;
		File dictionary = new File(dictionary_location);
		try {
			scan = new Scanner(dictionary);
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner lineScanner = new Scanner(line);
				if (lineScanner.hasNextInt()) {
					entry_count = lineScanner.nextInt();
					tiles = new Animation[entry_count];
					types = new int[entry_count];
				} else {
					System.err.println("Invalid dictionary file. Missing entry count.");
					lineScanner.close();
					throw new Exception();
				}
				lineScanner.close();
			}
			for (int i = 0; i < entry_count; i++) {
				if (scan.hasNextLine()) {
					String line = scan.nextLine();
					Scanner lineScanner = new Scanner(line);
					if (lineScanner.hasNextInt()) {
						types[i] = lineScanner.nextInt();
					} else {
						System.err.println("Error on line: " + (i + 1));
						System.err.println("Missing tile type code.");
						lineScanner.close();
						throw new Exception();
					}
					if (lineScanner.hasNext()) {
						String image_src = lineScanner.next();
						try {
							Image[] temp = new Image[1];
							temp[0] = new Image(image_src);
							tiles[i] = new Animation(temp, 0);
						} catch (SlickException e) {
							System.err.println("Could not find image resource.");
							lineScanner.close();
							throw new Exception();
						}
					} else {
						System.err.println("Error on line: " + (i + 1));
						System.err.println("Missing tile image location.");
						lineScanner.close();
						throw new Exception();
					}
					lineScanner.close();
				}
			}
			scan.close();
		} catch (Exception e) {
			System.err.println("Failed to load Tile Dictionary.");
			if (entry_count != 0) {
				try {
					for (int i = 0; i < entry_count; i++) {
						tiles[i] = null;
						types[i] = -1;
					}
				} catch (Exception outofbounds) {
					
				}
			}
			entry_count = 0;
		}
	}
	
	/**
	 * Looks up an image in the dictionary based on a tiles type.
	 * @param type - the tile type to look up.
	 * @return the image if it is found, else null.
	 */
	public Animation GetImage(int type) {
		for (int i = 0; i < this.entry_count; i++) {
			if (type == this.types[i]) {
				return tiles[i];
			}
		}
		return null;
	}
	
	/**
	 * Loads a new tile image dictionary.
	 * @param dictionary_location - the location of the tile dictionary file.
	 * @return true if the dictionary was successfully loaded.
	 */
	public boolean LoadDictionary(String dictionary_location) {
		entry_count = 0;
		File dictionary = new File(dictionary_location);
		try {
			scan = new Scanner(dictionary);
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner lineScanner = new Scanner(line);
				if (lineScanner.hasNextInt()) {
					entry_count = lineScanner.nextInt();
					tiles = new Animation[entry_count];
					types = new int[entry_count];
				} else {
					System.err.println("Invalid dictionary file. Missing entry count.");
					lineScanner.close();
					throw new Exception();
				}
				lineScanner.close();
			}
			for (int i = 0; i < entry_count; i++) {
				if (scan.hasNextLine()) {
					String line = scan.nextLine();
					Scanner lineScanner = new Scanner(line);
					if (lineScanner.hasNextInt()) {
						types[i] = lineScanner.nextInt();
					} else {
						System.err.println("Error on line: " + (i + 1));
						System.err.println("Missing tile type code.");
						lineScanner.close();
						throw new Exception();
					}
					if (lineScanner.hasNext()) {
						String anim_src = lineScanner.next();
						AnimationController loader = new AnimationController(anim_src);
						if (loader.IsLoaded()) {
							tiles[i] = new Animation(loader.frames, loader.durations);
						} else {
							System.err.println("Failed to load animation on line: " + (i + 2));
							lineScanner.close();
							throw new Exception();
						}
					} else {
						System.err.println("Error on line: " + (i + 1));
						System.err.println("Missing tile image location.");
						lineScanner.close();
						throw new Exception();
					}
					lineScanner.close();
				}
			}
			scan.close();
		} catch (Exception e) {
			System.err.println("Failed to load Tile Dictionary.");
			if (entry_count != 0) {
				try {
					for (int i = 0; i < entry_count; i++) {
						tiles[i] = null;
						types[i] = -1;
					}
				} catch (Exception outofbounds) {
					return false;
				}
			}
			entry_count = 0;
			return false;
		}
		return true;
	}
	
	public void UpdateAnimations(int fps_scaler) {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].update(fps_scaler);
		}
	}
}
