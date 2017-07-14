package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EntityDictionary {
	
	public static final int NONE = -1;
	public static final int CAMERA = 0;
	public static final int PLAYER = 1;
	public static final int NPC = 2;
	public static final int ENEMY = 3;
	public static final int DIALOG_BOX = 4;
	public static final int BULLET = 5;
	public static final int ROOM_CHANGER = 6;
	
	public Image[] sprites;
	public String[] animation_files;
	public int[] types;
	
	private int entry_count;
	private Scanner scan;
	
	public EntityDictionary() {
		
	}
	
	public EntityDictionary(String dictionary_location) {
		entry_count = 0;
		File dictionary = new File(dictionary_location);
		try {
			scan = new Scanner(dictionary);
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner lineScanner = new Scanner(line);
				if (lineScanner.hasNextInt()) {
					entry_count = lineScanner.nextInt();
					sprites = new Image[entry_count];
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
						System.err.println("Missing entity type code.");
						lineScanner.close();
						throw new Exception();
					}
					if (lineScanner.hasNext()) {
						String image_src = lineScanner.next();
						try {
							sprites[i] = new Image(image_src);
						} catch (SlickException e) {
							System.err.println("Could not find image resource.");
							lineScanner.close();
							throw new Exception();
						}
					} else {
						System.err.println("Error on line: " + (i + 1));
						System.err.println("Missing entity image location.");
						lineScanner.close();
						throw new Exception();
					}
					lineScanner.close();
				}
			}
			scan.close();
		} catch (Exception e) {
			System.err.println("Failed to load Entity Dictionary.");
			if (entry_count != 0) {
				try {
					for (int i = 0; i < entry_count; i++) {
						sprites[i] = null;
						types[i] = -1;
					}
				} catch (Exception outofbounds) {
					
				}
			}
			entry_count = 0;
		}
	}
	
	public Image GetImage(int type) {
		for (int i = 0; i < this.entry_count; i++) {
			if (type == this.types[i]) {
				return sprites[i];
			}
		}
		return null;
	}
	
	public String GetAnimationSource(int type) {
		for (int i = 0; i < this.entry_count; i++) {
			if (type == this.types[i]) {
				return animation_files[i];
			}
		}
		return null;
	}
	
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
					sprites = new Image[entry_count];
					animation_files = new String[entry_count];
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
					if (line.trim().length() != 0) {
						if (lineScanner.hasNextInt()) {
							types[i] = lineScanner.nextInt();
						} else {
							System.err.println("Error on line: " + (i + 1));
							System.err.println("Missing entity type code.");
							lineScanner.close();
							throw new Exception();
						}
						if (lineScanner.hasNext()) {
							String image_src = lineScanner.next();
							try {
								if (image_src.contains(".anim")) {
									animation_files[i] = image_src;
									sprites[i] = null;
								} else {
									sprites[i] = new Image(image_src);
									animation_files[i] = null;
								}
								
							} catch (SlickException e) {
								System.err.println("Could not find image resource.");
								lineScanner.close();
								throw new Exception();
							}
						} else {
							System.err.println("Error on line: " + (i + 1));
							System.err.println("Missing entity image location.");
							lineScanner.close();
							throw new Exception();
						}
					} else {
						i--;
					}
					lineScanner.close();
				}
			}
			scan.close();
		} catch (Exception e) {
			System.err.println("Failed to load Entity Dictionary.");
			if (entry_count != 0) {
				try {
					for (int i = 0; i < entry_count; i++) {
						sprites[i] = null;
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
	
	public HashMap<String, Animation> LoadAnimations(String animation_location) {
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
									frames[n].setFilter(Image.FILTER_NEAREST);
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
}
