package input;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Map.Entry;

import org.lwjgl.input.Controller;

import engine.Main;
import engine.Menu;
import engine.MenuItem;
import engine.States;
import engine.World;
import graphics.GUIController;

public class JoystickController {
	public static final int KEYBOARD = 0;
	public static final int JOYSTICK = 1;
	
	private static HashMap<String, Boolean> held_input = null;
	private static HashMap<String, Boolean> pressed_input = null;
	private static HashMap<Integer, String> joymap = null;
	
	private static boolean key_0_released = true; /* A KEY*/
	private static boolean key_1_released = true; /* B KEY*/
	private static boolean key_2_released = true; /* X KEY*/
	private static boolean key_3_released = true; /* Y KEY*/
	private static boolean key_4_released = true; /* LB KEY*/
	private static boolean key_5_released = true; /* RB KEY*/
	private static boolean key_6_released = true; /* Back KEY*/
	private static boolean key_7_released = true; /* Start KEY*/
	private static boolean key_8_released = true; /* Left Analog KEY*/
	private static boolean key_9_released = true; /* Right Analog KEY*/
	
	private static boolean[] key_released = null;
	
	private static final int MAX_BUTTON_COUNT = 30;
	private static final float SENSITIVITY = 0.30f;
	
	private static void Init(Controller controller) {
		if (controller != null) {
			if (controller.getButtonCount() > 0) {
				key_released = new boolean[MAX_BUTTON_COUNT];
				for (int i = 0; i < MAX_BUTTON_COUNT; i++) {
					key_released[i] = true;
				}
			}
		}
	}
	
	public static HashMap<String, Boolean> GetControllerHeldInput(Controller controller) {
		if (controller != null) {
			if (key_released == null) {
				Init(controller);
			}
			if (joymap != null) {
				//System.out.println("DPAD X: " + controller.getPovX());
				//System.out.println("DPAD Y: " + controller.getPovY());
				//System.out.println();
				Iterator<Entry<String, Boolean>> inputIterator = held_input.entrySet().iterator();
				while (inputIterator.hasNext()) {
					Entry<String, Boolean> current = inputIterator.next();
					held_input.put(current.getKey(), false);
					Main.SetController(JOYSTICK);
				}
				
				/* BUTTONS SECTION */
				for (int i = 0; i < controller.getButtonCount(); i++) {
					if (controller.isButtonPressed(i)) {
						//System.out.println(controller.getButtonName(i));
						String command = joymap.get(i);
						if (command != null && command.length() > 0) {
							held_input.put(command, true);
							Main.SetController(JOYSTICK);
						}
					}
				}
				
				/* LEFT ANALOG SECTION*/
				if (controller.getXAxisValue() > SENSITIVITY) { //right
					//14
					String command = joymap.get(14);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getXAxisValue() < -SENSITIVITY) { //left
					//15
					String command = joymap.get(15);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				if (controller.getYAxisValue() < -SENSITIVITY) { //up
					//16
					String command = joymap.get(16);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getYAxisValue() > SENSITIVITY) { //down
					//17
					String command = joymap.get(17);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				
				/* RIGHT ANALOG SECTION*/
				if (controller.getXAxisValue() > SENSITIVITY) { //right
					//18
					String command = joymap.get(18);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getXAxisValue() < -SENSITIVITY) { //left
					//19
					String command = joymap.get(19);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				if (controller.getYAxisValue() < -SENSITIVITY) { //up
					//20
					String command = joymap.get(20);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getYAxisValue() > SENSITIVITY) { //down
					//21
					String command = joymap.get(21);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				
				/* DPAD SECTION*/
				if (controller.getPovX() < -SENSITIVITY) { //left dpad
					//10
					String command = joymap.get(10);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getPovX() > SENSITIVITY) { //right dpad
					//11
					String command = joymap.get(11);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				if (controller.getPovY() < -SENSITIVITY) { //up dpad
					//12
					String command = joymap.get(12);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getPovY() > SENSITIVITY) { //down dpad
					//13
					String command = joymap.get(13);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				
				/* TRIGGERS SECTION */
				if (controller.getZAxisValue() > SENSITIVITY) { //left trigger
					//22
					String command = joymap.get(22);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getZAxisValue() < -SENSITIVITY) { //right trigger
					//23
					String command = joymap.get(23);
					if (command != null && command.length() > 0) {
						held_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				
				return held_input;
			}
		}
		
		return null;
	}
	
	public static HashMap<String, Boolean> GetControllerPressedInput(Controller controller) {
		if (controller != null) {
			if (key_released == null) {
				Init(controller);
			}
			if (joymap != null) {
				
				Iterator<Entry<String, Boolean>> inputIterator = pressed_input.entrySet().iterator();
				while (inputIterator.hasNext()) {
					Entry<String, Boolean> current = inputIterator.next();
					pressed_input.put(current.getKey(), false);
				}
				
				/* BUTTONS SECTION */			
				for (int i = 0; i < controller.getButtonCount(); i++) {
					if (controller.isButtonPressed(i)) {
						if (key_released[i]) {
							String command = joymap.get(i);
							if (command != null && command.length() > 0) {
								pressed_input.put(command, true);
								Main.SetController(JOYSTICK);
							}
							key_released[i] = false;
						}
					} else {
						key_released[i] = true;
					}
				}
				
				/* LEFT ANALOG SECTION*/
				if (controller.getXAxisValue() > SENSITIVITY) { //right
					//14
					String command = joymap.get(14);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getXAxisValue() < -SENSITIVITY) { //left
					//15
					String command = joymap.get(15);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				if (controller.getYAxisValue() < -SENSITIVITY) { //up
					//16
					String command = joymap.get(16);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getYAxisValue() > SENSITIVITY) { //down
					//17
					String command = joymap.get(17);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				
				/* RIGHT ANALOG SECTION*/
				if (controller.getXAxisValue() > SENSITIVITY) { //right
					//18
					String command = joymap.get(18);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getXAxisValue() < -SENSITIVITY) { //left
					//19
					String command = joymap.get(19);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				if (controller.getYAxisValue() < -SENSITIVITY) { //up
					//20
					String command = joymap.get(20);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getYAxisValue() > SENSITIVITY) { //down
					//21
					String command = joymap.get(21);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				
				/* DPAD SECTION*/
				if (controller.getPovX() < -SENSITIVITY) { //left dpad
					//10
					String command = joymap.get(10);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getPovY() > SENSITIVITY) { //right dpad
					//11
					String command = joymap.get(11);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				if (controller.getPovY() < -SENSITIVITY) { //up dpad
					//12
					String command = joymap.get(12);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getPovY() > SENSITIVITY) { //down dpad
					//13
					String command = joymap.get(13);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				
				/* TRIGGERS SECTION */
				if (controller.getZAxisValue() > SENSITIVITY) { //left trigger
					//22
					String command = joymap.get(22);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				} else if (controller.getZAxisValue() < -SENSITIVITY) { //right trigger
					//23
					String command = joymap.get(23);
					if (command != null && command.length() > 0) {
						pressed_input.put(command, true);
						Main.SetController(JOYSTICK);
					}
				}
				
				return pressed_input;
			}
		}
		
		return null;
	}
	
	public static int GetJoyInput(Controller controller) {
		/* BUTTONS SECTION */			
		for (int i = 0; i < controller.getButtonCount(); i++) {
			if (controller.isButtonPressed(i)) {
				return i;
			}
		}
		
		/* LEFT ANALOG SECTION*/
		if (controller.getXAxisValue() > SENSITIVITY) { //right
			//14
			return 14;
		} else if (controller.getXAxisValue() < -SENSITIVITY) { //left
			//15
			return 15;
		}
		if (controller.getYAxisValue() < -SENSITIVITY) { //up
			//16
			return 16;
		} else if (controller.getYAxisValue() > SENSITIVITY) { //down
			//17
			return 17;
		}
		
		/* RIGHT ANALOG SECTION*/
		if (controller.getXAxisValue() > SENSITIVITY) { //right
			//18
			return 18;
		} else if (controller.getXAxisValue() < -SENSITIVITY) { //left
			//19
			return 19;
		}
		if (controller.getYAxisValue() < -SENSITIVITY) { //up
			//20
			return 20;
		} else if (controller.getYAxisValue() > SENSITIVITY) { //down
			//21
			return 21;
		}
		
		/* DPAD SECTION*/
		if (controller.getPovX() < -SENSITIVITY) { //left dpad
			//10
			return 10;
		} else if (controller.getPovY() > SENSITIVITY) { //right dpad
			//11
			return 11;
		}
		if (controller.getPovY() < -SENSITIVITY) { //up dpad
			//12
			return 12;
		} else if (controller.getPovY() > SENSITIVITY) { //down dpad
			//13
			return 13;
		}
		
		/* TRIGGERS SECTION */
		if (controller.getZAxisValue() > SENSITIVITY) { //left trigger
			//22
			return 22;
		} else if (controller.getZAxisValue() < -SENSITIVITY) { //right trigger
			//23
			return 23;
		}
		
		return -1;
	}
	
	public static String LoadKeyMapping(String keyMapConfig_location) {
		joymap = new HashMap<Integer, String>();
		pressed_input = new HashMap<String, Boolean>();
		held_input = new HashMap<String, Boolean>();
		File keyMapFile = new File(keyMapConfig_location);
		String controller_name = null;
		try {
			Scanner scan = new Scanner(keyMapFile);
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				controller_name = line.trim();
			}
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				String command = null;
				int key = -1;
				line = line.replace('=', ' ');
				Scanner lineScanner = new Scanner(line);
				
				if (lineScanner.hasNext()) {
					command = lineScanner.next();
				} else {
					System.err.println("Error! Invalid joystick config file!");
					lineScanner.close();
					throw new Exception();
				}
				
				if (lineScanner.hasNextInt()) {
					key = lineScanner.nextInt();
				} else {
					System.err.println("Error! Invalid joystick config file!");
					lineScanner.close();
					throw new Exception();
				}
				
				lineScanner.close();
				joymap.put(key, command);
				pressed_input.put(command, false);
				held_input.put(command, false);
			}
			scan.close();
		} catch (Exception e) {
			joymap = null;
			pressed_input = null;
			held_input = null;
		}
		
		return controller_name;
	}
	
	public static void MapJoystickButtons(World world, Controller controller) {
		if (Main.GetState() == States.MENU && GUIController.GetCurrentMenu() == GUIController.MENU_CONTROLS
				&& GUIController.selectedControlField != -1 && Main.CurrentController() == JOYSTICK) {
			int menuID = world.FindMenu(GUIController.MENU_CONTROLS);
			Menu temp = world.GetMenu(menuID);
			
			for (int i = 0; i < temp.GetMenuItemCount(); i++) {
				MenuItem item = temp.GetMenuItem(i);
				
				if (i == GUIController.selectedControlField) {
					String keyname = "KEY_" + item.text;
					int key = GetJoyInput(controller);
					
					if (GUIController.GetKeyValue(keyname) != null) {
						if (!GUIController.tempJoyMap.containsValue(key)) {
							GUIController.tempJoyMap.put(keyname, key);
						}
					}
					break;
				}
			}
		}
	}
	
}
