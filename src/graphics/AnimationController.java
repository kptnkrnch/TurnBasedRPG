package graphics;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public class AnimationController {
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
	
	public AnimationController() {
		loaded = false;
		durations = null;
		frames = null;
	}
	
	public AnimationController(String animation_location) {
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
							frames[i].setFilter(Image.FILTER_NEAREST);
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
	
	public AnimationController LoadAnimation(String animation_location) {
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
							frames[i].setFilter(Image.FILTER_NEAREST);
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
	
	public static HashMap<String, Animation> LoadAnimations(String animation_location) {
		int animation_count = 0;
		File animation_src = new File(animation_location);
		HashMap<String, Animation> animations = new HashMap<String, Animation>();
		
		try {
			Scanner scan = new Scanner(animation_src);
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner lineScanner = new Scanner(line);
				
				if (lineScanner.hasNextInt()) {
					animation_count = lineScanner.nextInt();
				} else {
					lineScanner.close();
					scan.close();
					throw new Exception();
				}
				
				lineScanner.close();
			}
			for (int i = 0; i < animation_count; i++) {
				int frame_count = 0;
				String animation_name = null;
				int pingpong = 0;
				if (scan.hasNextLine()) {
					String line = scan.nextLine();
					if (line.trim().length() > 0) {
						Scanner lineScanner = new Scanner(line);
						
						if (lineScanner.hasNext()) {
							animation_name = lineScanner.next();
						} else {
							lineScanner.close();
							scan.close();
							throw new Exception();
						}
						
						if (lineScanner.hasNextInt()) {
							frame_count = lineScanner.nextInt();
						} else {
							lineScanner.close();
							scan.close();
							throw new Exception();
						}
						
						if (lineScanner.hasNextInt()) {
							pingpong = lineScanner.nextInt();
						}
						
						int[] durations = new int[frame_count];
						Image[] frames = new Image[frame_count];
						for (int n = 0; n < frame_count; n++) {
							if (scan.hasNextLine()) {
								lineScanner.close();
								line = scan.nextLine();
								lineScanner = new Scanner(line);
								
								if (lineScanner.hasNextInt()) {
									durations[n] = lineScanner.nextInt();
								} else {
									lineScanner.close();
									scan.close();
									throw new Exception();
								}
								
								if (lineScanner.hasNext()) {
									frames[n] = new Image(lineScanner.next());
								} else {
									lineScanner.close();
									scan.close();
									throw new Exception();
								}
							} else {
								lineScanner.close();
								scan.close();
								throw new Exception();
							}
						}
						Animation animation = new Animation(frames, durations);
						if (pingpong == 1) {
							animation.setPingPong(true);
						}
						animations.put(animation_name, animation);
						
						lineScanner.close();
					} else {
						i--;
					}
				}
				
			}
			scan.close();
		} catch (Exception e) {
			return null;
		}
		
		return animations;
	}
	
	public boolean IsLoaded() {
		return this.loaded;
	}
}
