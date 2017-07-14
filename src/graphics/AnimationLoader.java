package graphics;

import java.io.File;
import java.util.Scanner;

import org.newdawn.slick.Image;

public class AnimationLoader {
	public int[] durations;
	public Image[] frames;
	public boolean loaded;
	
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String SHOOT_LEFT = "shoot_left";
	public static final String SHOOT_RIGHT = "shoot_right";
	public static final String SHOOT_UP = "shoot_up";
	public static final String SHOOT_DOWN = "shoot_down";
	public static final String SHOOT = "shoot_";
	
	public AnimationLoader() {
		loaded = false;
		durations = null;
		frames = null;
	}
	
	public AnimationLoader(String animation_location) {
		loaded = false;
		durations = null;
		frames = null;
		File file = new File(animation_location);
		try {
			Scanner scan = new Scanner(file);
			int frame_count = 0;
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner lineScanner = new Scanner(line);
				if (lineScanner.hasNextInt()) {
					frame_count = lineScanner.nextInt();
				} else {
					System.err.println("Error: Frame count not found!");
					lineScanner.close();
					throw new Exception();
				}
				lineScanner.close();
			}
			if (frame_count > 0) {
				frames = new Image[frame_count];
				durations = new int[frame_count];
				for (int i = 0; i < frame_count; i++) {
					if (scan.hasNextLine()) {
						String line = scan.nextLine();
						Scanner lineScanner = new Scanner(line);
						
						if (lineScanner.hasNextInt()) {
							durations[i] = lineScanner.nextInt();
						} else {
							System.err.println("Error: Frame duration not found on line: " + (i + 2));
							lineScanner.close();
							throw new Exception();
						}
						
						if (lineScanner.hasNext()) {
							frames[i] = new Image(lineScanner.next());
						} else {
							System.err.println("Error: Frame location not found on line: " + (i + 2));
							lineScanner.close();
							throw new Exception();
						}
						
						lineScanner.close();
					}
				}
			}
			scan.close();
			loaded = true;
		} catch (Exception e) {
			frames = null;
			durations = null;
			System.err.println("Failed to load animation file: " + animation_location);
		}
	}
	
	public AnimationLoader LoadAnimation(String animation_location) {
		loaded = false;
		durations = null;
		frames = null;
		File file = new File(animation_location);
		try {
			Scanner scan = new Scanner(file);
			int frame_count = 0;
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner lineScanner = new Scanner(line);
				if (lineScanner.hasNextInt()) {
					frame_count = lineScanner.nextInt();
				} else {
					System.err.println("Error: Frame count not found!");
					lineScanner.close();
					throw new Exception();
				}
				lineScanner.close();
			}
			if (frame_count > 0) {
				frames = new Image[frame_count];
				durations = new int[frame_count];
				for (int i = 0; i < frame_count; i++) {
					if (scan.hasNextLine()) {
						String line = scan.nextLine();
						Scanner lineScanner = new Scanner(line);
						
						if (lineScanner.hasNextInt()) {
							durations[i] = lineScanner.nextInt();
						} else {
							System.err.println("Error: Frame duration not found on line: " + (i + 2));
							lineScanner.close();
							throw new Exception();
						}
						
						if (lineScanner.hasNext()) {
							frames[i] = new Image(lineScanner.next());
						} else {
							System.err.println("Error: Frame location not found on line: " + (i + 2));
							lineScanner.close();
							throw new Exception();
						}
						
						lineScanner.close();
					}
				}
			}
			scan.close();
			loaded = true;
		} catch (Exception e) {
			frames = null;
			durations = null;
			System.err.println("Failed to load animation file: " + animation_location);
		}
		return this;
	}
	
	public boolean IsLoaded() {
		return this.loaded;
	}
}
