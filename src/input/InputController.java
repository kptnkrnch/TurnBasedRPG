package input;

import engine.Main;
import graphics.GUIController;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

import org.lwjgl.input.Controller;
import org.newdawn.slick.Input;

public class InputController {
	public static final int KEYBOARD = 0;
	public static final int JOYSTICK = 1;
	
	private static HashMap<String, Boolean> held_input = null;
	private static HashMap<String, Boolean> pressed_input = null;
	private static HashMap<String, Boolean> old_pressed_input = null;
	private static HashMap<String, Integer> keymap = null;
	
	public static HashMap<String, Boolean> HandleHeldInput(Input input_obj, Controller controller) {
		if (keymap != null) {
			
			/* Resets the input pressed map */
			Iterator<Entry<String, Boolean>> inputIterator = held_input.entrySet().iterator();
			while (inputIterator.hasNext()) {
				Entry<String, Boolean> current = inputIterator.next();
				held_input.put(current.getKey(), false);
			}
			
			Iterator<Entry<String, Integer>> mapIterator = keymap.entrySet().iterator();
			while (mapIterator.hasNext()) {
				Entry<String, Integer> current = mapIterator.next();
				if (input_obj.isKeyDown(current.getValue())) {
					held_input.put(current.getKey(), true);
					Main.SetController(KEYBOARD);
				}
			}
			
			HashMap<String, Boolean> tempmap = JoystickController.GetControllerHeldInput(controller);
			if (tempmap != null) {
				Iterator<Entry<String, Boolean>> tempIterator = tempmap.entrySet().iterator();
				while (tempIterator.hasNext()) {
					Entry<String, Boolean> current = tempIterator.next();
					if (current.getValue()) {
						held_input.put(current.getKey(), true);
					}
				}
			}
			
			return held_input;
		} else {
			return null;
		}
	}
	
	public static HashMap<String, Boolean> HandlePressedInput(Input input_obj, Controller controller) {
		if (keymap != null) {
			
			/* Resets the input pressed map */
			Iterator<Entry<String, Boolean>> inputIterator = pressed_input.entrySet().iterator();
			while (inputIterator.hasNext()) {
				Entry<String, Boolean> current = inputIterator.next();
				if (old_pressed_input.get(current.getKey())) {
					old_pressed_input.put(current.getKey(), true);
				} else {
					old_pressed_input.put(current.getKey(), current.getValue());
				}
				pressed_input.put(current.getKey(), false);
			}
			
			Iterator<Entry<String, Integer>> mapIterator = keymap.entrySet().iterator();
			while (mapIterator.hasNext()) {
				Entry<String, Integer> current = mapIterator.next();
				if (input_obj.isKeyPressed(current.getValue())) {
					pressed_input.put(current.getKey(), true);
					Main.SetController(KEYBOARD);
				}
			}
			
			HashMap<String, Boolean> tempmap = JoystickController.GetControllerPressedInput(controller);
			if (tempmap != null) {
				Iterator<Entry<String, Boolean>> tempIterator = tempmap.entrySet().iterator();
				while (tempIterator.hasNext()) {
					Entry<String, Boolean> current = tempIterator.next();
					if (current.getValue() && old_pressed_input.get(current.getKey()) == false) {
						pressed_input.put(current.getKey(), true);
					}
					if (current.getValue() == false) {
						old_pressed_input.put(current.getKey(), current.getValue());
					}
				}
			}
			
			return pressed_input;
		} else {
			return null;
		}
	}
	
	public static boolean LoadKeyMapping(String keyMapConfig_location) {
		keymap = new HashMap<String, Integer>();
		pressed_input = new HashMap<String, Boolean>();
		old_pressed_input = new HashMap<String, Boolean>();
		held_input = new HashMap<String, Boolean>();
		File keyMapFile = new File(keyMapConfig_location);
		try {
			Scanner scan = new Scanner(keyMapFile);
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				String command = null;
				int key = -1;
				line = line.replace('=', ' ');
				Scanner lineScanner = new Scanner(line);
				
				if (lineScanner.hasNext()) {
					command = lineScanner.next();
				} else {
					System.err.println("Error! Invalid key config file!");
					lineScanner.close();
					throw new Exception();
				}
				
				if (lineScanner.hasNextInt()) {
					key = lineScanner.nextInt();
				} else {
					System.err.println("Error! Invalid key config file!");
					lineScanner.close();
					throw new Exception();
				}
				
				lineScanner.close();
				keymap.put(command, key);
				GUIController.tempKeyMap.put(command, key);
				pressed_input.put(command, false);
				old_pressed_input.put(command, false);
				held_input.put(command, false);
			}
			scan.close();
		} catch (Exception e) {
			keymap = null;
			pressed_input = null;
			held_input = null;
			old_pressed_input = null;
		}
		
		return true;
	}
	
	public static Integer GetKeyValue(String key) {
		if (keymap.containsKey(key)) {
			return keymap.get(key);
		} else {
			return null;
		}
	}
	
	public static void SetKeyMap(HashMap<String, Integer> map) {
		Iterator<Entry<String, Integer>> mapIterator = map.entrySet().iterator();
		while (mapIterator.hasNext()) {
			Entry<String, Integer> current = mapIterator.next();
			keymap.put(current.getKey(), current.getValue());
		}
	}
}
